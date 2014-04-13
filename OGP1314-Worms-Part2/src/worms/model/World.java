package worms.model;
import java.util.ArrayList;

import worms.gui.Level;
import java.util.Random;
import be.kuleuven.cs.som.annotate.*;

public class World 
{
	private double worldWidth;
	private double worldHeight;
	private ArrayList<Team> allTeams = new ArrayList<Team>();
	private ArrayList<GameObject> allObjects = new ArrayList<GameObject>();
	private boolean destroyed = false;
	private static final int maxTeams = 10;
	private boolean[][] passableMap;
	private final Random random;
	private Projectile projectile;
	private Worm worm;
	private boolean finished;

	public World(Level level) 
	{
		this(level.getWorldWidth(),level.getWorldHeight(),level.getPassableMap(),new Random());

	}
	public World(double width, double height, boolean[][] passableMap, Random random){
		this.passableMap = passableMap;
		this.random = random;
		setWidth(width);
		setHeight(height);
	}
	public void start() 
	{
		finished = false;
		selectNextWorm();
	}

	public void selectNextWorm() throws IllegalArgumentException
	{
		ArrayList<Worm> worms = getObjectsOfType(Worm.class, true);
		if (worms.size() == 0) throw new IllegalArgumentException("No live worms.");
		int current = worms.indexOf(worm);
		
		if (current == worms.size() - 1 || current == -1)
			worm = worms.get(0);
		else
			worm = worms.get(current + 1);
	}
	
	public boolean isGameFinished()
	{
		return finished;
	}
	private double getCellWidth()
	{
		return getWidth() / passableMap[0].length;
	}
	private double getCellHeight()
	{
		return getHeight() / passableMap.length;
	}

	
	@Basic
	public double getWidth()
	{
		return worldWidth;
	}
	
	@Basic
	public double getHeight()
	{
		return worldHeight;
	}
	

	@Immutable
	public static final double getMaxWidth()
	{
		return Double.MAX_VALUE;
	}
	
	@Immutable
	public static final double getMaxHeight()
	{
		return Double.MAX_VALUE;
	}

	public Projectile getActiveProjectile()
	{
		return projectile;
	}

	public Worm getCurrentWorm()
	{
		return worm;
	}

	public <T extends GameObject> ArrayList<T> getObjectsOfType(Class<T> Class)
	{
		return getObjectsOfType(Class, false);
	}
	
	public <T extends GameObject> ArrayList<T> getObjectsOfType(Class<T> Class, boolean requireLive)
	{
		ArrayList<T> result = new ArrayList<T>();
		for (GameObject object: allObjects)
		{
			if (Class.isInstance(object))
			{
				if(!requireLive || !object.isDestroyed()) 
					result.add(Class.cast(object));
			}
		}
		return result;
	}


	public void setWidth(double width)
	{
		if (width > getMaxWidth())
			width = getMaxWidth();
		
		if (width < 0) 
			width = 0;
		
		worldWidth = width;
	}

	public void setHeight(double heigth)
	{
		if (heigth > getMaxHeight())
			heigth = getMaxHeight();
		
		if (heigth < 0) 
			heigth = 0;
		
		worldHeight = heigth;
	}
	
	public boolean isImpassable(Position position, double radius)
	{
		int X = (int) Math.floor(position.X / getCellWidth());
		int Y = (int) Math.floor(position.Y / getCellHeight());
		int XdistanceMax = (int) Math.ceil(radius / getCellWidth());
		int YdistanceMax = (int) Math.ceil(radius / getCellHeight());
		for(int x = 0; x <= XdistanceMax; x++)
		{
			for(int y = 0; y <= YdistanceMax; y++)
			{
				try
				{
					if(!passableMap[Y + y][X + x]) return true;
				} 
				catch(IndexOutOfBoundsException e)
				{
				}
			}
		}
		return false;
	}
	
	public boolean isOccupiable(Position position, double radius)
	{
		return !isImpassable(position, radius) && isImpassable(position, radius * 1.1);
	}
	
	public static int getMaxTeams()
	{
		return maxTeams;
	}
	
	@Basic @Raw
	public Team[] getTeams()
	{
		Team[] result = new Team[allTeams.size()];
		allTeams.toArray(result);
		return result;
	}
	
	@Raw
	public boolean canHaveAsTeam(Team team)
	{
		return team != null && (!team.isDestroyed() && !this.isDestroyed());
	}
	
	public void addTeam(Team team) throws IllegalArgumentException 
	{
		if (!team.canHaveAsWorld(this) || !this.canHaveAsTeam(team)) 
			throw new IllegalArgumentException("Team and world incompatible.");
		if (allTeams.size() >= maxTeams) 
			throw new IllegalArgumentException("The maximal number of teams has been reached for this world.");
		
		allTeams.add(team);
		team.setWorld(this);
	}
	
	public void removeTeam(Team team)
	{
		if (team == null) 
			return;
		
		allTeams.remove(team);
		team.setWorld(null);
	}
	
	public boolean hasTeam(Team team)
	{
		return allTeams.contains(team);
	}
	
	public boolean hasProperTeams()
	{
		for(Team team : allTeams)
		{
			if (team == null || team.getWorld() != this || !team.canHaveAsWorld(this) || !this.canHaveAsTeam(team)) 
				return false;
		}
		return true;
	}
	
	@Basic @Raw
	public GameObject[] getObjects()
	{
		GameObject[] result = new GameObject[allObjects.size()];
		allObjects.toArray(result);
		return result;
	}
	
	@Raw
	public boolean canHaveAsObject(GameObject object)
	{
		return object != null && (!object.isDestroyed() && !this.isDestroyed());
	}
	
	public boolean hasObject(GameObject object)
	{
		return allObjects.contains(object);
	}
	
	public boolean hasProperObjects()
	{
		for (GameObject object : allObjects)
		{
			if (object == null || object.getWorld() != this || !object.canHaveAsWorld(this) || !this.canHaveAsObject(object)) 
				return false;
		}
		return true;
	}
	
	public void addObject(GameObject object) throws IllegalArgumentException
	{
		if (!object.canHaveAsWorld(this) || !this.canHaveAsObject(object)) 
			throw new IllegalArgumentException("Object and world incompatible.");
		if (object instanceof Projectile)
		{
			if (projectile == null)
			{
				projectile = (Projectile) object;
			}
			else throw new IllegalArgumentException("Live projectile present.");
		}
		allObjects.add(object);
		object.setWorld(this);
	}
	
	public void removeObject(GameObject object)
	{
		if (object == null) return;
		if (object == projectile)
		{
			projectile = null;
		}
		allObjects.remove(object);
		object.setWorld(null);
	}
	
	@Basic
	public boolean isDestroyed()
	{
		return destroyed;
	}
	
	public void destroy()
	{
		for(Team team : allTeams)
		{
			removeTeam(team);
		}
		for(GameObject object : allObjects)
		{
			removeObject(object);
		}
		destroyed = true;
	}
	
	public GameObject[] getObjectsAt(Position position, double radius)
	{
		ArrayList<GameObject> objectList = new ArrayList<GameObject>();
		for (GameObject object: allObjects)
		{
			double distance = position.distanceTo(object.getPosition());
			if (Math.abs(object.getRadius() - radius) <= distance && distance <= object.getRadius() + radius)
			{
				objectList.add(object);
			}
		}
		GameObject[] result = new GameObject[objectList.size()];
		objectList.toArray(result);
		return result;
	}
	
	public void addWorm(Worm worm, Team team) throws IllegalArgumentException
	{
		addObject(worm);
		if (team != null)
		{
			if (!this.hasTeam(team)) 
				throw new IllegalArgumentException("Team not in this world.");
			team.addMember(worm);
		}
	}
	
	public Position findValidPosition(GameObject o)
	{
		//TODO
		final double STEP_SIZE=0.1*o.getRadius();
		final double DISTANCE_CUTOFF=0.5;
		final int MAX_TOTAL_TRIES=1000;
		final int REGEN_MOD=30;
		int tries = 0;
		double r=o.getRadius();
		Position mapcenter=new Position(worldWidth/2,worldHeight/2);
		Position p=new Position(worldWidth*random.nextDouble(),worldHeight*random.nextDouble());
		double ang=Math.atan2(p.Y-mapcenter.Y,p.X-mapcenter.X);
		while(!isOccupiable(p,r))
		{
			p=new Position(p.X-STEP_SIZE*Math.cos(ang),p.Y-STEP_SIZE*Math.sin(ang));
			tries++;
			if(p.distanceTo(mapcenter)<DISTANCE_CUTOFF || tries==MAX_TOTAL_TRIES) return null;
			if(tries%REGEN_MOD==0) p=new Position(worldWidth*random.nextDouble(),worldHeight*random.nextDouble());
		}
		return p;
	}
	
	void spawnObject(GameObject object)
	{
		Position position = findValidPosition(object);
		if(position != null)
		{
			object.setPosition(position);
			addObject(object);
		}
	}

}