package index.alchemy.block;

import index.alchemy.api.Alway;
import index.alchemy.core.Constants;
import index.alchemy.core.IResourceLocation;
import index.alchemy.item.AlchemyItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AlchemyBlock extends Block implements IResourceLocation {
	
	public AlchemyBlock(String name, Material material) {
		super(material);
		if (hasCreativeTab())
			setCreativeTab(AlchemyItem.CREATIVE_TABS);
		setUnlocalizedName(name);
		setRegistryName(name);
		registerBlock();
	}
	
	@Override
	public ResourceLocation getResourceLocation() {
		return getRegistryName();
	}
	
	public boolean hasCreativeTab() {
		return true;
	}
	
	public Class<? extends TileEntity> getTileEntityClass() {
		return null;
	}
	
	public void registerBlock() {
		GameRegistry.register(this);
		AlchemyBlockLoader.ALL_BLOCK.add(this);
		if (Alway.isClient()) {
			Item item = Item.getItemFromBlock(this);
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(
					getResourceLocation(), "inventory"));
			item.setFull3D();
		}
		if (getTileEntityClass() != null)
			GameRegistry.registerTileEntity(getTileEntityClass(), getUnlocalizedName());
	}
}
