package worms.model;
public class Food extends Packages {

	private Worm worm;
	public double getGrowAmount()
	{
		return 1.1;
	}
	public void consume(Worm worm) 
	{
		worm.setRadius(worm.getRadius() * getGrowAmount());
		destroy();
	}

	public double getMinRadius() 
	{
		return 0.20;
	}

	public double getRadius()
	{
		return 0.20;
	}
	public boolean isDestroyed() 
	{
		if (worm.getPosition().X == this.getPosition().X || worm.getPosition().Y == this.getPosition().Y)
			return true;
		else
			return false;
	}

}