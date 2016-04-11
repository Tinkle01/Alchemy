package index.alchemy.item;

import index.alchemy.core.AlchemyModLoader;
import index.alchemy.core.AlchemyResourceLocation;
import index.alchemy.core.Constants;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagicSolvent extends AlchemyItem implements IItemColor, IBrewingRecipe {
	
	public static final ResourceLocation BOTTLE = new AlchemyResourceLocation("solvent_bottle");
	
	protected int color, metadata;
	protected Item material;
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
		tooltip.add(Type.get(item.getTagCompound()).getTextWithoutFormattingCodes());
    }
	
	@Override
	public boolean canUseItemStack(EntityLivingBase living, ItemStack item) {
		return false;
	}

	@Override
	public int getColorFromItemstack(ItemStack item, int index) {
		return index == 0 ? color : -1;
	}

	@Override
	public boolean isInput(ItemStack input) {
		return input.getItem() == Items.potionitem && input.getMetadata() == 0;
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return ingredient.getItem() == material && ingredient.getMetadata() == metadata;
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		return new ItemStack(this, 1, 0, Type.randomOutput());
	}
	
	@Override
	public ResourceLocation getResourceLocation() {
		return BOTTLE;
	}
	
	public static enum Type {
		NULL(TextFormatting.WHITE),
		UNSTABLE(TextFormatting.GRAY),
		POWERFUL(TextFormatting.BLUE),
		BENIGN(TextFormatting.GREEN),
		WONDERFUL(TextFormatting.GOLD),
		DANGEROUS(TextFormatting.RED);
		
		private TextFormatting formatting;
		
		Type(TextFormatting formatting) {
			this.formatting = formatting;
		}
		
		private static final String EFFECT = "solvent_effect";
		private static final Random RANDOM = new Random();
		
		public boolean has(Type type){
			return ordinal() == type.ordinal();
		}
		
		public boolean has(NBTTagCompound nbt){
			return has(get(nbt));
		}
		
		@Override
		public String toString() {
			return I18n.translateToLocal("solvent.type." + name().toLowerCase());
		}
		
		public String getTextWithoutFormattingCodes() {
			return formatting.getTextWithoutFormattingCodes(toString());
		}
		
		public static Type random() {
			return values()[RANDOM.nextInt(values().length)];
		}
		
		public static NBTTagCompound randomOutput() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger(EFFECT, random().ordinal());
			return nbt;
		}
		
		public static Type get(NBTTagCompound nbt) {
			if (nbt == null) return NULL;
			int id = nbt.getInteger(EFFECT);
			for (Type type : values()) {
				if (id == type.ordinal())
					return type;
			}
			return NULL;
		}
		
	}
	
	public ItemMagicSolvent(String name, int color, Item material) {
		this(name, color, material, 0);
	}
	
	public ItemMagicSolvent(String name, int color, Item material, int metadata) {
		super("solvent_" + name);
		this.color = color;
		this.material = material;
		setMaxStackSize(1);
		BrewingRecipeRegistry.addRecipe(this);
	}
	
}
