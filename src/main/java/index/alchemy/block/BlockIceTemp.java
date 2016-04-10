package index.alchemy.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockIceTemp extends AlchemyBlock implements ITileEntityProvider {
	
	@Override
	public boolean hasCreativeTab() {
		return false;
	}
	
	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityIceTemp.class;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityIceTemp();
	}
	
	public BlockIceTemp() {
		super("ice_temp", Material.ice);
		setHardness(0.5F);
		setLightOpacity(3);
		setStepSound(SoundType.GLASS);
		setBlockUnbreakable();
	}
	
}
