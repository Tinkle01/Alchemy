package index.alchemy.container;

import index.alchemy.item.ItemInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerItemInventory extends Container {
	
	private ItemInventory inventory;
	private int row;

	public ContainerItemInventory(InventoryPlayer player, ItemInventory inventory) {
		this.inventory = inventory;
		row = inventory.getSizeInventory() / 9;
		int offset = (row - 4) * 18;

		for (int i = 0; i < row; i++)
        	for (int k = 0; k < 9; ++k)
        		addSlotToContainer(new Slot(inventory, k + i * 9, 8 + k * 18, 18 + i * 18));

		for (int i = 0; i < 3; i++)
			for (int k = 0; k < 9; k++)
				addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 103 + i * 18 + offset));

		for (int i = 0; i < 9; ++i)
        	addSlotToContainer(new Slot(player, i, 8 + i * 18, 161 + offset));
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack copy = null;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack item = slot.getStack();
            copy = item.copy();

            if (index < row * 9) {
                if (!mergeItemStack(item, row * 9, inventorySlots.size(), true))
                	return null;
            }
            else if (!mergeItemStack(item, 0, row * 9, false))
            	return null;

            if (item.stackSize == 0)
            	slot.putStack(null);
            else
            	slot.onSlotChanged();
        }

        return copy;
    }
}
