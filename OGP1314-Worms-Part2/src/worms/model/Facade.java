package worms.model;

import java.util.Collection;
import java.util.Random;

public class Facade implements IFacade 
{

	@Override
	public boolean canTurn(Worm worm, double angle) 
	{
		return worm.canTurn(angle);
	}

	@Override
	public void turn(Worm worm, double angle) 
	{
		worm.turn(angle);
	}

	@Override
	public double getX(Worm worm) 
	{
		return worm.getPosition().X;
	}

	@Override
	public double getY(Worm worm) 
	{
		return worm.getPosition().Y;
	}

	@Override
	public double getOrientation(Worm worm) 
	{
		return worm.getDirection();
	}

	@Override
	public double getRadius(Worm worm) 
	{
		return worm.getRadius();
	}

	@Override
	public void setRadius(Worm worm, double newRadius) 
	{
		worm.setRadius(newRadius);
	}


	@Override
	public int getActionPoints(Worm worm) 
	{

		return worm.getActionPoints();
	}

	@Override
	public int getMaxActionPoints(Worm worm) 
	{

		return worm.getMaxActionPoints();
	}

	@Override
	public String getName(Worm worm) 
	{

		return worm.getName();
	}

	@Override
	public void rename(Worm worm, String newName) 
	{

		worm.setName(newName);
	}

	@Override
	public double getMass(Worm worm) 
	{
		return worm.getMass();
	}

	@Override
	public void addEmptyTeam(World world, String newName) 
	{
		Team team = new Team(newName);
		world.addTeam(team);		
	}

	@Override
	public void addNewFood(World world) 
	{
		Food food = new Food();
		world.spawnObject(food);
	}

	@Override
	public void addNewWorm(World world) 
	{
		Worm worm = new Worm();
		world.spawnObject(worm);

	}

	@Override
	public boolean canFall(Worm worm) 
	{
		return false;
	}

	@Override
	public boolean canMove(Worm worm) 
	{
		//TODO
		return true;
	}

	public Food createFood(World world, double x, double y) 
	{
		Food food = new Food();
		food.setPosition(new Location(x,y));
		world.addObject(food);
		return food;
	}

	@Override
	public World createWorld(double width, double height, boolean[][] passableMap, Random random) 
	{
		return new World(width, height, passableMap, random);
	}

	public Worm createWorm(World world, double x, double y, double direction, double radius, String name) 
	{
		Worm worm = new Worm(x, y, direction, radius, name);
		world.addObject(worm);
		return worm;
	}

	@Override
	public void fall(Worm worm) 
	{
		worm.fall();

	}

	@Override
	public Projectile getActiveProjectile(World world) 
	{
		return world.getActiveProjectile();
	}

	@Override
	public Worm getCurrentWorm(World world) 
	{
		return world.getCurrentWorm();
	}

	@Override
	public Collection<Food> getFood(World world) 
	{
		return world.getObjectsOfType(Food.class);
	}

	@Override
	public double getHitPoints(Worm worm) 
	{
		return worm.getHP();
	}

	@Override
	public double[] getJumpStep(Projectile projectile, double time) 
	{
		Location position = projectile.jumpStep(time);
		double[] Result = {position.X, position.Y};
		return Result;
	}

	@Override
	public double[] getJumpStep(Worm worm, double time) 
	{
		Location position = worm.jumpStep(time);
		double[] Result = {position.X, position.Y};
		return Result;
	}

	@Override
	public double getJumpTime(Projectile projectile, double timeStep) 
	{
		return projectile.jumpTime();
	}

	@Override
	public double getJumpTime(Worm worm, double timeStep)
	{
		return worm.jumpTime();
	}

	@Override
	public double getMaxHitPoints(Worm worm) 
	{
		return worm.getMaxHP();
	}

	@Override
	public double getMinimalRadius(Worm worm) 
	{
		return worm.getMinRadius();
	}

	@Override
	public double getRadius(Food food)
	{
		return food.getRadius();
	}

	@Override
	public double getRadius(Projectile projectile) 
	{
		return projectile.getRadius();
	}

	@Override
	public String getSelectedWeapon(Worm worm) 
	{
		// TODO
		return "Bazooka";
	}

	@Override
	public String getTeamName(Worm worm) 
	{
		if(worm.getTeam() != null)
		{
			return worm.getTeam().getName();
		}
		else return null;
	}

	@Override
	public Team[] getWinner(World world) 
	{
		if (world.getTeams().length == 1)
			return world.getTeams();
		else 
			return null;
	}

	@Override
	public Collection<Worm> getWorms(World world) 
	{
		return world.getObjectsOfType(Worm.class);
	}

	@Override
	public double getX(Food food) 
	{
		return food.getPosition().X;
	}

	@Override
	public double getX(Projectile projectile) 
	{
		return projectile.getPosition().X;
	}

	@Override
	public double getY(Food food) 
	{
		return food.getPosition().Y;
	}

	@Override
	public double getY(Projectile projectile) 
	{
		return projectile.getPosition().Y;
	}

	@Override
	public boolean isActive(Food food) 
	{
		return !food.isDestroyed();
	}

	@Override
	public boolean isActive(Projectile projectile) 
	{
		return projectile.isDestroyed();
	}

	@Override
	public boolean isAdjacent(World world, double x, double y, double radius) 
	{
		return world.isOccupiable(new Location(x, y), radius);
	}

	@Override
	public boolean isAlive(Worm worm) 
	{
		return worm.isDestroyed();
	}

	@Override
	public boolean isGameFinished(World world) 
	{
		return world.isGameFinished();
	}

	@Override
	public boolean isImpassable(World world, double x, double y, double radius) 
	{
		return world.isImpassable(new Location(x, y), radius);
	}

	@Override
	public void jump(Projectile projectile, double timeStep) {
		projectile.jump();
	}

	@Override
	public void jump(Worm worm, double timeStep) 
	{
		worm.jump();
	}

	@Override
	public void move(Worm worm) 
	{
		// TODO Auto-generated method stub
		worm.move(1);

	}

	@Override
	public void selectNextWeapon(Worm worm) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void shoot(Worm worm, int yield) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void startGame(World world) 
	{
		world.start();
	}

	@Override
	public void startNextTurn(World world)
	{
		world.selectNextWorm();
	}

	@Override
	public Worm createWorm(double x, double y, double direction, double radius, String name) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canMove(Worm worm, int nbSteps) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move(Worm worm, int nbSteps) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jump(Worm worm) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getJumpTime(Worm worm) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}