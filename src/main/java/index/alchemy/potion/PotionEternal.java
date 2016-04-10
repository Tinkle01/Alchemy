package index.alchemy.potion;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PotionEternal extends AlchemyPotion {

	public PotionEternal() {
		super("eternal", false, 0xFBD860);
	}
	
	public void onLivingHurt(LivingHurtEvent event) {
		if (event.getEntityLiving().isPotionActive(this)) {
			event.setAmount(Math.min(event.getEntityLiving().getHealth() - 0.1F, event.getAmount()));
		}
	}

}
