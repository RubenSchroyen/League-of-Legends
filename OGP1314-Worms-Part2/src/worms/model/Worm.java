package worms.model;
import be.kuleuven.cs.som.annotate.*;


public class Worm extends BallisticObject
{

	private double radius;

	private double direction;

	private Position position;


	private int AP;
	private int HP;

	private String name;
	private Team team;
	
	 @Raw
	public Worm(double x, double y, double direction, double radius, String name)
	 {
		this.position = new Position(x,y);
		this.setRadius(radius);
		this.setDirection(direction);
		this.setName(name);
		this.AP = getMaxActionPoints();
		this.HP = getMaxHP();
	}
	
	@Raw
	public Worm()
	{
		this(0, 0, 0, 0.25 + 1.2*(new java.util.Random()).nextDouble(), "Default");
	}

	public int getHP()
	{
		return HP;
	}
	public int getMaxHP()
	{
		return getMaxActionPoints();
	}
	
	@Basic
	public double getRadius()
	{
		return radius;
	}
	
	@Basic
	public double getDirection()
	{
		return direction;
	}
	
	@Basic
	public String getName()
	{
		return name;
	}
	
	@Basic
	public static double getDensity()
	{
		return 1062;
	}
	
	@Basic
	public Team getTeam()
	{
		return team;
	}
	
	@Basic @Immutable
	public double getMinRadius()
	{
		return 0.25;
	}
	
	public static boolean isValidName(String name)
	{
		if (name.length() < 2 || !Character.isUpperCase(name.charAt(0))) 
			return false;
		for (int i = 1; i < name.length(); i++)
		{
			char character = name.charAt(i);
			if (!Character.isLetter(character) && character != ' ' && character != '\'' && character != '\"') 
				return false;
		}
		return true;
	}
	
	public void setName(String name) throws IllegalArgumentException
	{
		if (!isValidName(name)) 
			throw new IllegalArgumentException("Invalid name.");
		this.name = name;
	}
	
	public double getMass()
	{
		return getDensity() * 4.0 / 3 * Math.PI * Math.pow(radius,3);
	}
	
	public int getMaxActionPoints()
	{
		return (int) Math.round(getMass());
	}
	
	@Basic
	public int getActionPoints()
	{
		return AP;
	}

	
	public void useActionPoints(int usedAP) 
	{
		if (usedAP < 0) 
			return;
		if (AP < usedAP) 
			AP=0;
		else 
			AP -= usedAP;
	}

	
	public void setDirection(double direction)
	{
		assert (direction >= 0 && direction < 2 * Math.PI);
		this.direction = direction;
	}
	
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException
	{
		if (radius < getMinRadius()) 
			throw new IllegalArgumentException("Radius too small.");
		else 
			this.radius = radius;
	}
	
	public void passiveTurn(double radius) 
	{
		assert (radius >- 2*Math.PI) && (radius < 2*Math.PI);
		double newDirection = direction + radius;
		if (newDirection < 0) 
			newDirection += 2*Math.PI;
		if (newDirection > 2*Math.PI) 
			newDirection -= 2*Math.PI;
		setDirection(newDirection);
	}

	
	public boolean canTurn(double radius)
	{
		int cost = (int) Math.abs(Math.ceil(60 / (2 * Math.PI) * radius));
		return AP >= cost && (radius < Math.PI) && (radius >- Math.PI);
	}
	
	public void turn(double radius) 
	{
		int cost = (int) Math.abs(Math.ceil(60 / (2 * Math.PI) * radius));
		assert canTurn(radius);
		passiveTurn(radius);
		useActionPoints(cost);
	}
	
	public void passiveMove(int steps) throws IllegalArgumentException
	{
		if (steps < 0) 
			throw new IllegalArgumentException("Negative amount of steps");
		setPosition(new Position(position.X + steps * radius * Math.cos(direction), position.Y + steps * radius * Math.sin(direction)));
	}
	
	public boolean canMove(int steps)
	{
		int cost = steps * ((int) Math.ceil(Math.abs(Math.cos(direction)) + 4 * Math.sin(direction)));
		return AP >= cost;
	}
	
	public void move(int steps)
	{
		int cost = (int)Math.ceil(Math.abs(Math.cos(direction)) + 4 * Math.sin(direction));
		passiveMove(steps);
		useActionPoints(cost);
	}
	
	public double getInitialVelocity()
	{
		return (5 * AP / getMass() - BallisticObject.gravitation) * 0.5;
	}


	public boolean hasProperTeam()
	{
		return (team == null) || (team.hasMember(this) && team.canHaveAsMember(this));
	}
	
	public void joinTeam(Team team) throws IllegalArgumentException
	{
		if (!canHaveAsTeam(team)) 
			throw new IllegalArgumentException("Invalid team.");
		team.addMember(this);
	}
	
	@Raw
	void setTeam(Team team)
	{
		this.team = team;
	}
	
	@Raw
	public boolean canHaveAsTeam(Team team)
	{
		return team == null || (!team.isDestroyed() && (this.team == null || this.team == team));
	}
	@Override
	public void hitWall() 
	{
		
	}
	@Override
	public void hitObject(GameObject other) 
	{
		//TODO

	}
	
	@Override
	public void destroy()
	{
		//TODO
		team.removeMember(this);
		super.destroy();
	}
	
	@Override
	public boolean canJump() 
	{
		return getDirection() <= Math.PI && getInitialVelocity() > 0 && !getWorld().isImpassable(getPosition(), radius);
	}
	
	@Override
	public boolean isFinalPosition(Position position)
	{
		if (position.distanceTo(this.getPosition()) < this.getRadius()) 
			return false;
		return getWorld().isOccupiable(position, getRadius());
	}
	
	@Override
	public void jump()
	{
		super.jump();
		AP = 0;
	}
	
	public void fall()
	{
		//TODO
	}

	@Override
	public double getVelocity() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}