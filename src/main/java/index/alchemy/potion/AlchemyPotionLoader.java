package index.alchemy.potion;

import index.alchemy.core.Init;
import index.alchemy.util.Tool;

import java.lang.reflect.Method;

import com.google.common.base.Predicate;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.LoaderState.ModState;

// TODO
// !!!!> Only in the version 1.9 working <!!!!
// This is register ItemPotion in the Minecraft.
// Not guaranteed to work in another version, Field name and
// position will change with the version.
@Init(state = ModState.PREINITIALIZED)
public class AlchemyPotionLoader extends PotionType {
	
	public static Potion
			feather_fall = new PotionFeatherFall(),
			alacrity = new PotionAlacrity(),
			soul_withered = new PotionSoulWithered(),
			eternal = new PotionEternal(),
			dead_or_alive = new PotionDeadOrAlive(),
			multiple_xp = new PotionMultipleXP();
	
	public static final Predicate<ItemStack> 
			nether_wart = getItemPredicate(Items.nether_wart),
			red_stone = getItemPredicate(Items.redstone),
			glow_stone_dust = getItemPredicate(Items.glowstone_dust),
			fermented_spider_eye = getItemPredicate(Items.fermented_spider_eye);
	
	public static Predicate<ItemStack> getItemPredicate(final Item item) {
		return getItemPredicate(item, 0);
	}
	
	public static Predicate<ItemStack> getItemPredicate(final Item item, final int metadata) {
		return new Predicate<ItemStack>() {
			@Override
			public boolean apply(ItemStack input) {
				return input.getItem() == item && input.getMetadata() == metadata;
			}
		};
	}
	
	public static void registerItemPotion(PotionType input, boolean levelII, boolean long_time, int time,
			String name, Predicate<ItemStack> item, Potion... output) {
		PotionEffect[] effects = new PotionEffect[output.length];
		PotionType current_type;
		int index = 0, current_time = time;	
		for (Potion potion : output) {
			effects[index++] = new PotionEffect(potion, current_time);
		}
		
		current_type = new PotionType(effects);
		if (!potionTypeRegistry.containsKey(new ResourceLocation(name)))
			registerPotionType(name, current_type = new PotionType(effects));
		registerPotionTypeConversion(input, item, input = current_type);
		
		if (levelII) {
			index = 0;
			current_time = Math.max(time / 2, 1);
			effects = new PotionEffect[output.length];
			for (Potion potion : output) {
				effects[index++] = new PotionEffect(potion, current_time, 1);
			}
			
			current_type = new PotionType(effects);
			if (!potionTypeRegistry.containsKey(new ResourceLocation("strong_" + name)))
				registerPotionType("strong_" + name, current_type = new PotionType(effects));
			registerPotionTypeConversion(input, glow_stone_dust, current_type);
		}
		
		if (long_time) {
			index = 0;
			current_time = Math.max(time * 8 / 3, 1);
			effects = new PotionEffect[output.length];
			for (Potion potion : output) {
				effects[index++] = new PotionEffect(potion, current_time);
			}
			
			current_type = new PotionType(effects);
			if (!potionTypeRegistry.containsKey(new ResourceLocation("long_" + name)))
				registerPotionType("long_" + name, current_type);
			registerPotionTypeConversion(input, red_stone, current_type);
		}
		
	}
	
	public static void registerItemPotionAndPutrid(PotionType input, boolean levelII, boolean long_time, int time,
			String name1, String name2, Predicate<ItemStack> item1, Predicate<ItemStack> item2, Potion[] output1, Potion[] output2) {
		PotionEffect[] effects1 = new PotionEffect[output1.length], effects2 = new PotionEffect[output2.length];
		PotionType current_type0, current_type1, current_type2 = null, current_type3 = null, input1, input2;
		input2 = input1 = input;
		int index = 0, current_time = time;	
		for (Potion potion : output1) {
			effects1[index++] = new PotionEffect(potion, current_time);
		}
		index = 0;
		for (Potion potion : output2) {
			effects2[index++] = new PotionEffect(potion, current_time);
		}
		
		current_type1 = new PotionType(effects1);
		if (!potionTypeRegistry.containsKey(new ResourceLocation(name1)))
			registerPotionType(name1, current_type1);
		current_type0 = new PotionType(effects2);
		
		registerPotionTypeConversion(input, item1, input1 = current_type1);
		
		input2 = current_type0;
		if (item2 != null)
			registerPotionTypeConversion(input, item2, current_type2);

		registerPotionTypeConversion(current_type1, fermented_spider_eye, current_type0);
		registerPotionTypeConversion(current_type0, fermented_spider_eye, current_type1);
		
		if (levelII) {
			effects1 = new PotionEffect[output1.length];
			effects2 = new PotionEffect[output2.length];
			current_time = Math.max(time / 2, 1);
			index = 0;
			for (Potion potion : output1) {
				effects1[index++] = new PotionEffect(potion, current_time);
			}
			index = 0;
			for (Potion potion : output2) {
				effects2[index++] = new PotionEffect(potion, current_time);
			}
			
			current_type1 = new PotionType(effects1);
			if (!potionTypeRegistry.containsKey(new ResourceLocation("strong_" + name1)))
				registerPotionType("strong_" + name1, current_type1);
			current_type2 = new PotionType(effects2);
			
			registerPotionTypeConversion(input1, glow_stone_dust, current_type1);
			registerPotionTypeConversion(input2, glow_stone_dust, current_type2);
			
			registerPotionTypeConversion(current_type1, fermented_spider_eye, current_type2);
			registerPotionTypeConversion(current_type2, fermented_spider_eye, current_type1);
		}
		
		if (long_time) {
			effects1 = new PotionEffect[output1.length];
			effects2 = new PotionEffect[output2.length];
			current_time = Math.max(time * 8 / 3, 1);
			index = 0;
			for (Potion potion : output1) {
				effects1[index++] = new PotionEffect(potion, current_time);
			}
			index = 0;
			for (Potion potion : output2) {
				effects2[index++] = new PotionEffect(potion, current_time);
			}
			
			current_type1 = new PotionType(effects1);
			if (!potionTypeRegistry.containsKey(new ResourceLocation("strong_" + name1)))
				registerPotionType("long_" + name1, current_type1);
			current_type3 = new PotionType(effects2);
			
			registerPotionTypeConversion(input1, red_stone, current_type1);
			registerPotionTypeConversion(input2, red_stone, current_type3);

			registerPotionTypeConversion(current_type1, fermented_spider_eye, current_type2);
			registerPotionTypeConversion(current_type3, fermented_spider_eye, current_type1);
		}
		
		if (!potionTypeRegistry.containsKey(new ResourceLocation(name2)))
			registerPotionType(name2, current_type0);
		if (current_type2 != null && !potionTypeRegistry.containsKey(new ResourceLocation("strong_" + name2)))
			registerPotionType("strong_" + name2, current_type2);
		if (current_type3 != null && !potionTypeRegistry.containsKey(new ResourceLocation("long_" + name2)))
			registerPotionType("long_" + name2, current_type3);
		
	}
	
	public static Method registerPotionTypeConversion;
	
	static {
		try {
			(registerPotionTypeConversion = Tool.searchMethod(
					PotionHelper.class, PotionType.class, Predicate.class, PotionType.class)).setAccessible(true);
		} catch (Throwable t) {
			throw new RuntimeException(t.getMessage());
		}
	}
	
	public static void registerPotionTypeConversion(PotionType input, Predicate<ItemStack> reagentPredicate, PotionType output){
		try {
			registerPotionTypeConversion.invoke(null, input, reagentPredicate, output);
		} catch (Throwable t) {
			throw new RuntimeException(t.getMessage());
		}
	}
	
	public static void init() {
		registerItemPotionAndPutrid(PotionTypes.awkward, false, true, 20 * 60 * 3, "luck", "unluck",
				getItemPredicate(Items.emerald), null, new Potion[]{MobEffects.luck}, new Potion[]{MobEffects.unluck});
		
		registerItemPotionAndPutrid(PotionTypes.awkward, false, true, 20 * 45, "feather_fall", "levitation",
				getItemPredicate(Items.feather), null, new Potion[]{feather_fall}, new Potion[]{MobEffects.levitation});
		
		registerItemPotion(PotionTypes.awkward, true, true, 20 * 45, "hunger", 
				getItemPredicate(Items.rotten_flesh), MobEffects.hunger);
		
		registerItemPotion(PotionTypes.awkward, true, true, 20 * 60 * 3, "haste", 
				getItemPredicate(Items.blaze_rod), MobEffects.digSpeed);
		
		registerItemPotion(PotionTypes.awkward, true, true, 20 * 60 * 3, "resistance", 
				getItemPredicate(Items.golden_apple), MobEffects.resistance, MobEffects.absorption);
		
		registerItemPotion(PotionTypes.awkward, false, true, 20 * 45, "alacrity", 
				getItemPredicate(Items.diamond), alacrity);
		
		registerItemPotion(PotionTypes.awkward, false, true, 20 * 3, "eternal", 
				getItemPredicate(Items.diamond), eternal);
		
		registerItemPotion(PotionTypes.awkward, true, true, 20 * 60 * 3, "multiple_xp", 
				getItemPredicate(Items.diamond), multiple_xp);

		registerItemPotion(PotionTypes.awkward, false, false, 1, "dead_or_alive", 
				getItemPredicate(Items.diamond), dead_or_alive);
	}
}
