package index.alchemy.core;

import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {
	
	public static Minecraft minecraft = Minecraft.getMinecraft();
	
	public static int 
		potion_alacrity_cd = 0;
	
	public static long 
		ring_space_pickup_last_time = 0;
	
}