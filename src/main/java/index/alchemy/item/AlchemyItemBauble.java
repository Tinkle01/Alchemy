package index.alchemy.item;

import index.alchemy.api.Alway;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;

public abstract class AlchemyItemBauble extends AlchemyItem implements IBauble, IBaubleEquipment {
	

	public static class AlchemyItemAmulet extends AlchemyItemBauble {
		
		@Override
		public BaubleType getBaubleType(ItemStack itemstack) {
			return BaubleType.AMULET;
		}
		
		@Override
		public boolean canEquip(ItemStack item, EntityLivingBase player) {
			return BaublesApi.getBaubles((EntityPlayer) player).getStackInSlot(0) == null;
		}
		
		public AlchemyItemAmulet(String name) {
			super(name);
		}
		
	}
	
	public static class AlchemyItemRing extends AlchemyItemBauble {
		
		@Override
		public BaubleType getBaubleType(ItemStack itemstack) {
			return BaubleType.RING;
		}
		
		@Override
		public boolean canEquip(ItemStack item, EntityLivingBase player) {
			IInventory inventory = BaublesApi.getBaubles((EntityPlayer) player);
			return isOnly() ? inventory.getStackInSlot(1) == null || inventory.getStackInSlot(2) == null :
				inventory.getStackInSlot(1) == null || inventory.getStackInSlot(1).getItem() != this && inventory.getStackInSlot(2) == null;
		}
		
		@Override
		public ActionResult<ItemStack> onItemRightClick(ItemStack item, World world, EntityPlayer player, EnumHand hand) {
			if (Alway.isServer() && canEquip(item, player)) {
				IInventory inventory = BaublesApi.getBaubles((EntityPlayer) player);
				for (int i = 1; i < 3; i++)
					if (inventory.getStackInSlot(i) == null) {
						inventory.setInventorySlotContents(i, item.copy());
						item.stackSize--;
						return new ActionResult(EnumActionResult.SUCCESS, item);
					}
			}
	        return new ActionResult(EnumActionResult.PASS, item);
	    }
		
		public AlchemyItemRing(String name) {
			super(name);
		}
		
	}
	
	public static class AlchemyItemBelt extends AlchemyItemBauble {
		
		@Override
		public BaubleType getBaubleType(ItemStack itemstack) {
			return BaubleType.BELT;
		}
		
		@Override
		public boolean canEquip(ItemStack item, EntityLivingBase player) {
			return BaublesApi.getBaubles((EntityPlayer) player).getStackInSlot(3) == null;
		}
		
		
		public AlchemyItemBelt(String name) {
			super(name);
		}
		
	}

	@Override
	public BaubleType getBaubleType(ItemStack item) {
		return null;
	}

	@Override
	public void onWornTick(ItemStack item, EntityLivingBase player) {}

	@Override
	public void onEquipped(ItemStack item, EntityLivingBase player) {}

	@Override
	public void onUnequipped(ItemStack item, EntityLivingBase player) {}

	@Override
	public abstract boolean canEquip(ItemStack item, EntityLivingBase player);

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
	
	@Override
	public boolean isEquipmented(EntityPlayer player) {
		IInventory inventory = BaublesApi.getBaubles(player);
		for (int i = 0, len = inventory.getSizeInventory(); i < len; i++) {
			ItemStack item = inventory.getStackInSlot(i);
			if (item != null && item.getItem() == this)
				return true;
		}
		return false;
	}
	
	@Override
	public ItemStack getFormPlayer(EntityPlayer player) {
		IInventory inventory = BaublesApi.getBaubles(player);
		for (int i = 0, len = inventory.getSizeInventory(); i < len; i++) {
			ItemStack item = inventory.getStackInSlot(i);
			if (item != null && item.getItem() == this)
				return item;
		}
		return null;
	}
	
	public boolean isOnly() {
		return true;
	}
	
	public AlchemyItemBauble(String name) {
		super(name);
		setMaxStackSize(1);
	}

}
