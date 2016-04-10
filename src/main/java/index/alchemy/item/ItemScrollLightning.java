package index.alchemy.item;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemScrollLightning extends AlchemyItemScroll {
	public ItemScrollLightning() {
		super("scroll_lightning", 30, 64, true, 1);
	}
	
	@Override
	public void useScroll(ItemStack item, World world, EntityPlayer player, int type) {
		for (EntityLivingBase entity : player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, 
				new AxisAlignedBB(player.posX - 30d, player.posY - 30d, player.posZ - 30d,
						player.posX + 30d, player.posY + 30d, player.posZ + 30d))) {
            if(!(entity instanceof EntityPlayer))
            	world.spawnEntityInWorld(new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ, false));
        }
	}
}
