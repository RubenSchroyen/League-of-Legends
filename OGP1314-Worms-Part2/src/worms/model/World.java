package worms.model;

import java.util.Collection;
import java.util.Random;

import worms.model.Objects.Food;
import worms.model.Objects.Worm;

public class World 
{
	private double worldWidth;
	private double worldHeight;
	
	public World(double worldWidth, double worldHeight, boolean[][] passableMap, Random random)
	{
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight; 
	}
	
	public boolean ValidDimensions()
	{
		if (this.worldWidth < 0 || this.worldWidth > Double.MAX_VALUE || this.worldHeight < 0 || this.worldHeight > Double.MAX_VALUE)
				return false;
		
		return true;
		
	}
	
	public double getWorldHeight() 
	{
		return worldHeight;
	}
	
	
	public void setWorldHeight(double worldHeight) 
	{
		this.worldHeight = worldHeight;
	}
	
	
	public double getWorldWidth() 
	{
		return worldWidth;
	}
	
	
	public void setWorldWidth(double worldWidth) 
	{
		this.worldWidth = worldWidth;
	}

	public String getWinner() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Worm> getWorms() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Food> getFood() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Worm getCurrentWorm() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Worm createWorm(Worm worm) 
	{
		return worm = new Worm(worm.getPosX(), worm.getPosY(), worm.getRadius(), worm.getAngle(), worm.getName());
	}
	
	
}
