package index.alchemy.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class Alway {
	
	public static Side getSide() {
		return FMLCommonHandler.instance().getEffectiveSide();
	}
	
	public static boolean isServer() {
		return getSide().isServer();
	}
	
	public static boolean isClient() {
		return getSide().isClient();
	}
	
	public static BiomeGenBase getCurrentBiome(EntityPlayer player) {
		return player.worldObj.getBiomeGenForCoords(new BlockPos((int) player.posX, 0, (int) player.posZ));
	}
	
}