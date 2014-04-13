package worms.model;
import worms.gui.GUIConstants;

public abstract class JumpCalc extends Objects 
{
	protected static final double gravitation = 9.80665;
	public static final double tick = 1 / (GUIConstants.FRAMERATE * GUIConstants.TIME_SCALE);
	public abstract double getVelocity();
	public abstract boolean canJump();
	public abstract double getDirection();
	public abstract boolean isFinalPosition(Location position);
	
	public Location jumpStep(double elapsed)
	{
		if(canJump())
		{
			double initial = getVelocity();
			double newX = getPosition().X + elapsed * initial * Math.cos(getDirection());
			double newY = getPosition().Y + Math.pow(elapsed, 2) * 0.5 * gravitation + elapsed * initial * Math.sin(getDirection());
			
			return new Location(newX, newY);
		} 
		else return getPosition();

	}
	
	
	public double jumpTime()
	{
		if(canJump())
		{
			double elapsed = 0;
			Location position = getPosition();
			try
			{
				while(!isFinalPosition(position))
				{
					elapsed += tick;
					position = jumpStep(elapsed);
				}
			} 
			catch (ModelException e)
			{
			}
			
			return elapsed;
		} 
		else return 0;
	}

	
	public void jump()
	{
		setPosition(jumpStep(jumpTime()));
	}
}