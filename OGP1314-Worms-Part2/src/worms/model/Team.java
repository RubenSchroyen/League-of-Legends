package worms.model;
import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.*;

public class Team 
{
	private ArrayList<Worm> teamMembers = new ArrayList<Worm>();
	private World world=null;
	private String name;
	private boolean destroyed=false;
	
	public Team(String name) throws ModelException
	{
		if (!isValidName(name)) throw new ModelException("Illegal team name.");
		this.name = name;
	}
	
	public Worm[] getMembers()
	{
		Worm[] result = new Worm[teamMembers.size()];
		teamMembers.toArray(result);
		return result;
	}
	
	@Basic
	public World getWorld()
	{
		return world;
	}
	
	@Basic
	public String getName()
	{
		return name;
	}
	
	public static boolean isValidName(String name)
	{
		return name.matches("[A-Z][A-Za-z]+");
	}


	public void addMember(Worm worm) throws IllegalArgumentException
	{
		if (!worm.canHaveAsTeam(this) || !this.canHaveAsMember(worm)) throw new IllegalArgumentException("Worm and team don't belong together");
		teamMembers.add(worm);
		worm.setTeam(this);
	}
	
	public void removeMember(Worm worm)
	{
		if (worm != null)
		{
			teamMembers.remove(worm);
			worm.setTeam(null);
		}
	}
	
	public boolean isDestroyed()
	{
		return destroyed;
	}
	
	public void destroy()
	{
		for (Worm worm : teamMembers) 
			removeMember(worm);
		
		if (world != null) 
			world.removeTeam(this);
		
		destroyed = true;
	}
	
	@Raw
	public boolean hasMember(Worm worm)
	{
		return teamMembers.contains(worm);
	}
	
	@Raw
	void setWorld(World world)
	{
		this.world = world;
	}
	
	public boolean canHaveAsWorld(World world)
	{
		return this.world == null || (!this.world.isDestroyed() && !this.isDestroyed() && (this.world == null || this.world == world));
	}
	
	public boolean hasProperWorld()
	{
		return world == null || (!world.isDestroyed() && world.hasTeam(this) && world.canHaveAsTeam(this));
	}
	
	@Raw
	public boolean hasProperMembers()
	{
		for (Worm worm:teamMembers)
		{
			if (worm == null || worm.getTeam() != this || !worm.canHaveAsTeam(this) || !this.canHaveAsMember(worm)) 
				return false;
		}
		return true;
	}

	@Raw
	public boolean canHaveAsMember(Worm worm)
	{
		return (worm != null && !worm.isDestroyed() && !this.isDestroyed());
	}

}