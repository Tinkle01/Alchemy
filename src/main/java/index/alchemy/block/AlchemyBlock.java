package index.alchemy.block;

import index.alchemy.api.Alway;
import index.alchemy.core.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AlchemyBlock extends Block {
	
	public static final CreativeTabs CREATIVE_TABS = new CreativeTabs(Constants.MODID) {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(AlchemyBlockLoader.ice_temp);
		}
	};
	
	public AlchemyBlock(String name, Material material) {
		super(material);
		if (hasCreativeTab())
			setCreativeTab(CREATIVE_TABS);
		setUnlocalizedName(name);
		registerBlock();
	}
	
	public boolean hasCreativeTab() {
		return true;
	}
	
	public Class<? extends TileEntity> getTileEntityClass() {
		return null;
	}
	
	public void registerBlock() {
		String name = getResourceLocation();
		GameRegistry.registerBlock(this, name);
		AlchemyBlockLoader.ALL_BLOCK.add(this);
		if (Alway.isClient()) {
			Item item = Item.getItemFromBlock(this);
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(
					Constants.MODID + ":" + name, "inventory"));
			item.setFull3D();
		}
		if (getTileEntityClass() != null)
			GameRegistry.registerTileEntity(getTileEntityClass(), getUnlocalizedName());
	}
	
	public String getResourceLocation() {
		return getUnlocalizedName().substring(Constants.TILE);
	}
	
}
