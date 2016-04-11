package index.alchemy.potion;

import index.alchemy.core.AlchemyDamageSourceLoader;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class PotionDeadOrAlive extends AlchemyPotion {
	
	public Random random = new Random();
	
	@Override
	public void performEffect(EntityLivingBase living, int level) {
		float f = random.nextFloat() * living.getMaxHealth() * 2 - living.getMaxHealth();
		if (f > 0)
			living.heal(f);
		else 
			living.attackEntityFrom(AlchemyDamageSourceLoader.dead_magic, Math.min(living.getHealth() - 0.1F, -f));
	}
	
	public PotionDeadOrAlive() {
		super("dead_or_alive", false, 0x878787, true);
	}
	
}