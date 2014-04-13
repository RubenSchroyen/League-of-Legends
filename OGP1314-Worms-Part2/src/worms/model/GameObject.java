package worms.model;


public abstract class GameObject 
{
	private Position position;
	private World world;
	private boolean destroyed = false;
	public abstract double getMinRadius();
	public abstract double getRadius();
	

	public Position getPosition()
	{
		return position;
	}
	
	void setPosition(Position position) 
	{
		this.position = position;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public void setWorld(World world)
	{
		this.world = world;
	}
	
	
	public boolean isDestroyed()
	{
		return destroyed;
	}
	
	public void destroy()
	{
		world.removeObject(this);
		destroyed = true;
	}
	
	public abstract double getMass();

	public abstract void hitWall();

	public abstract void hitObject(GameObject other);

	public boolean canHaveAsWorld(World world)
	{
		return this.world == null || (!this.world.isDestroyed() && !this.isDestroyed() && (this.world == null || this.world == world));
	}

	public boolean hasProperWorld()
	{
		return world == null || (world.canHaveAsObject(this) && this.canHaveAsWorld(world) && world.hasObject(this));
	}

	public boolean hasWorld()
	{
		return world != null;
	}
}