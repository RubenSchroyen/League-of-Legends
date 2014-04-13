package worms.model;
public class Food extends Packages {

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
		// TODO Auto-generated method stub
		return false;
	}

}