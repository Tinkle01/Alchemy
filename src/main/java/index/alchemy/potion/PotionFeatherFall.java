package index.alchemy.potion;

import index.alchemy.core.IPlayerTickable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.relauncher.Side;

public class PotionFeatherFall extends AlchemyPotion {
	
	@Override
	public void performEffect(EntityLivingBase living, int level) {
		if (living.isPotionActive(this) && !living.onGround && living.motionY < 0) {
			living.motionY *= 0.75;
			living.fallDistance = 0;
		}
	}
	
	public PotionFeatherFall() {
		super("feather_fall", false, 0xFFFFFF, true);
	}

}