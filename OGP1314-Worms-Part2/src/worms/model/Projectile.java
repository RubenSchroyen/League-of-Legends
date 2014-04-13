package worms.model;

public abstract class Projectile extends JumpCalc 
{
	private final Worm worm;
	private final int yield;
	private static final double density = 7800;
	
	public Projectile(Worm worm, int yield) throws IllegalArgumentException
	{
		this.worm = worm;
		if (yield < 0 || yield > 100) 
			throw new IllegalArgumentException("Invalid propulsion yield.");
		this.yield = yield;
		Location position = worm.getPosition();
		double radius = worm.getRadius() + getRadius();
		worm.getWorld().addObject(this);
		setPosition(new Location(position.X + radius * Math.cos(getDirection()), position.Y + radius * Math.sin(getDirection())));
	}
	
	@Override
	public abstract double getVelocity();

	@Override
	public boolean canJump() 
	{
		return true;
	}
	public int getPropulsionYield()
	{
		return yield;
	}

	@Override
	public double getDirection() 
	{
		return worm.getDirection();
	}

	@Override
	public boolean isFinalPosition(Location position) 
	{
		// TODO
		return getWorld().isImpassable(position, getRadius());
	}

	public double getRadius() 
	{
		return Math.cbrt(0.75 * getMass() / (Math.PI * density));
	}


	@Override
	public void hitWall() 
	{
		destroy();
	}

	@Override
	public void hitObject(Objects other) 
	{
		destroy();
	}

}