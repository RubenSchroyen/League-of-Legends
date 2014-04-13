package worms.model;
import be.kuleuven.cs.som.annotate.*;

public abstract class Supplies extends GameObject 
{

	@Override
	public void hitWall() throws IllegalArgumentException
	{
		throw new IllegalArgumentException("Illegal supply position.");
	}

	@Override
	public void hitObject(GameObject other) 
	{
		return;
	}
	
	public abstract void consume(Worm worm);

	@Override @Immutable
	public double getMass()
	{
		return 0;
	}

}