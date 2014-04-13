package worms.model;

public final class Location 
{
	
	public final double X;
	
	public final double Y;
	private static final double[] FORBIDDEN = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
	
	public static boolean isValidPosition(double X, double Y)
	{
		for (double forbidden : FORBIDDEN)
		{
			if (X == forbidden || Y == forbidden) return false;
		}
		return true;
	}
	
	public Location(double X, double Y)
	{
		if (!isValidPosition(X,Y)) 
			throw new IllegalArgumentException("Illegal position.");
		
		this.X=X;
		this.Y=Y;
	}
	
	public Location()
	{
		this(0,0);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(!(other instanceof Location)) 
			return false;
		Location position = (Location) other;
		
		if(position.X == X && position.Y == Y) 
			return true;
		
		else 
			return false;
	}
	
	public double distanceTo(Location other)
	{
		return Math.sqrt((X - other.X) * (X - other.X) + (Y - other.Y) * (X - other.Y));
	}
	
	public String toString()
	{
		return "(" + X + "," + Y + ")";
	}

}
