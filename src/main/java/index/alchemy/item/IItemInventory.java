package index.alchemy.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IItemInventory {
	
	public ItemInventory getItemInventory(EntityPlayer player);
	
}
