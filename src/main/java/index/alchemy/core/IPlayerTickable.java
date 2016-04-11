package index.alchemy.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public interface IPlayerTickable {
	
	public Side getSide();
	
	public void onTick(EntityPlayer player);
	
}
