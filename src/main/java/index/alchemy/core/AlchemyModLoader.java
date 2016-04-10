package index.alchemy.core;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import index.alchemy.api.Alway;
import index.alchemy.block.AlchemyBlockLoader;
import index.alchemy.item.AlchemyItemLoader;
import index.alchemy.network.AlchemyNetworkHandler;
import index.alchemy.potion.AlchemyPotionLoader;
import index.alchemy.util.Tool;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.LoaderState.ModState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Constants.MODID, version = Constants.VERSION)
public class AlchemyModLoader {
	@Instance(Constants.MODID)
	public static AlchemyModLoader instance;
	public static EventSystem eventHook = new EventSystem();
	public static Config config = new Config();
	public static Logger logger = LogManager.getLogger(Constants.MODID);
	
	public static final String mc_dir;
	public static final boolean is_modding;
	public static Map<ModState, List<Class<?>>> init_map = new LinkedHashMap<ModState, List<Class<?>>>();
	
	static {
		String str = null, mod_path;
		try {
			str = AlchemyModLoader.class.getResource("/alchemy.info").toURI().toString()
					.replace("file:/", "").replace("\\", "/")
					.replace("/bin/alchemy.info", "");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		if (!str.contains("alchemy.info")) {
			mod_path = str + "/bin/";
			mc_dir = str + "/eclipse";
			is_modding = true;
		} else {
			mod_path = str.replace("\\", "/").replace("!/alchemy.info", "").replace("jar:", "");
			mc_dir =  str.replaceAll("/mods/.*?jar!.*", "").replace("jar:", "");
			is_modding = false;
		}
		
		try {
			mod_path = URLDecoder.decode(mod_path, "utf-8");
		} catch (UnsupportedEncodingException e) { e.printStackTrace(); }
		
		List<String> class_list = new LinkedList<String>();
		
		if (is_modding) {
			List<String> temp = new LinkedList<String>();
			Tool.getAllFile(new File(mod_path + Constants.PACKAGE.replace('.', '/')), temp);
			for (String name : temp)
				if (name.endsWith(".class"))
					class_list.add(name.replace("\\", "/").replace(mod_path, "")
							.replace(".class", "").replace("/", "."));
		} else {
			JarFile jar = null;
			try {
				jar = new JarFile(new File(mod_path));
				Enumeration<JarEntry> entry = jar.entries();
				while (entry.hasMoreElements()) {
					String name = entry.nextElement().getName();
					if (name.endsWith(".class"))
						class_list.add(name.replace(".class", "").replace("/", "."));
				}
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			} finally {
				if (jar != null)
					try {
						jar.close();
					} catch (IOException e) {}
			}
		}
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		for (String name : class_list) {
			try {
				Class<?> clazz = Class.forName(name, false, loader);
				for (Init init : clazz.getAnnotationsByType(Init.class)) {
					List<Class<?>> list = init_map.get(init.state());
					if (list == null)
						init_map.put(init.state(), list = new LinkedList<Class<?>>());
					list.add(clazz);
				}
			} catch (ClassNotFoundException e) {}
		}
	}
	
	@SidedProxy(clientSide = Constants.PACKAGE + ".core.ClientProxy", serverSide = Constants.PACKAGE + ".core.CommonProxy")
	public static CommonProxy commonProxy;
	
	public static void init(ModState state) {
		for (Class clazz : init_map.get(state))
			init(clazz);
	}
	
	public static void init(Class<?> clazz) {
		try {
			clazz.getMethod("init").invoke(null);
			logger.info("Successful init class: " + clazz.getName());
		} catch (Exception e) {
			logger.error("Failed to init class: " + clazz.getName());
		}
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(eventHook);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, eventHook);
		init(event.getModState());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		init(event.getModState());
	}
	
}