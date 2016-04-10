package index.alchemy.seasons;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;

public class Seasons {
	
	public static final int Spring = 0, Summer = 1, Autumn = 2, Winter = 3, Null = -1;
	public static int auto_cycle = 28 * 24000;
	public static List<Seasons> ls = new ArrayList<Seasons>();
	public World world;
	public int last_seasons, seasons, cycle;
	
	public static Seasons findSeasons(World world) {
		for (Seasons s : ls) 
			if (s.world == world) return s;
		Seasons s;
		ls.add(s = new Seasons(world));
		return s;
	}
	
	public Seasons(World world) {
		this(world, auto_cycle);
	}
	
	public Seasons(World world, int cycle) {
		this.world = world;
		this.cycle = cycle;
		
		nextSeasons();
		last_seasons = seasons;
		ls.add(this);
	}
	
	public void onTick() {
		nextSeasons();
		if (last_seasons != seasons) {
			
			
		}
	}
	
	public void nextSeasons() {
		last_seasons = seasons;
		seasons = (int) (world.getWorldTime() % cycle) % 4;
	}
	
}
