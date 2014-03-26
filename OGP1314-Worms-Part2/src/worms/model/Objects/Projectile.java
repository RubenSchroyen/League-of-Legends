package worms.model.Objects;

public class Projectile {
	
	private double X, Y, Radius;
	private double JumpTime;
	
	public Projectile(double X, double Y, double Radius)
	{
		this.setX(X);
		this.setRadius(Radius);
		this.setY(Y);
	}

	private void setX(double x) 
	{
		this.X = x;
	}

	private void setY(double y) 
	{
		this.Y = y;
	}

	private void setRadius(double radius) 
	{
		this.Radius = radius;
	}

	public double getJumpTime() 
	{
		return JumpTime;
	}

	public double getX() 
	{
		return X;
	}

	public double getY() 
	{
		return Y;
	}

	public double getRadius() 
	{
		return Radius;
	}

	public double[] getJumpStep() 
	{
		return null;
	}

}
