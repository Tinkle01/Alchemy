package index.alchemy.core;

import index.alchemy.api.Alway;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.LoaderState.ModState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Init(state = ModState.POSTINITIALIZED)
public class AlchemyColorLoader {
	
private static List<Object> item_color = new LinkedList<Object>(), block_color = new LinkedList<Object>();
	
	public static <T extends Item & IItemColor> void addItemColor(T t) {
		item_color.add(t);
	}
	
	public static <T extends Block & IBlockColor> void addBlockColor(T t) {
		block_color.add(t);
	}
	
	public static void registerItemColor() {
		ItemColors colors = Minecraft.getMinecraft().getItemColors();
		for (Object obj : item_color) {
			colors.registerItemColorHandler((IItemColor) obj, (Item) obj);
		}
	}
	
	public static void registerBlockColor() {
		BlockColors colors = Minecraft.getMinecraft().getBlockColors();
		for (Object obj : block_color) {
			colors.registerBlockColorHandler((IBlockColor) obj, (Block) obj);
		}
	}
	
	public static void init() {
		registerItemColor();
		registerBlockColor();
	}

}
