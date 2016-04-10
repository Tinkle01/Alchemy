package index.alchemy.core;

import index.alchemy.util.Tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.LoaderState.ModState;

@Init(state = ModState.POSTINITIALIZED)
public class AlchemyLanguageManager {
	
	private static AlchemyLanguageManager instance;
	
	private Map<String, String> map = new HashMap<String, String>();
	
	public AlchemyLanguageManager(String language) {
		init("en_US");
		init(language);
	}
	
	public void init(String language) {
		String src = "";
		try {
			src = Tool.read(AlchemyLanguageManager.class.getResourceAsStream(
					"/assets/" + Constants.MODID + "/lang/" + language + ".lang"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		for (String str : src.split("\n")) {
			if (str.length() > 0 && str.charAt(0) == '$') {
				String sa[] = str.split("=");
				if (str.length() > 1)
					map.put(sa[0], sa[1]);
			}
		}
	}
	
	public static String getString(String str) {
		return Tool.isNullOr(instance.map.get(str), str);
	}
	
	public static void init() {
		instance = new AlchemyLanguageManager(Minecraft.getMinecraft()
				.getLanguageManager().getCurrentLanguage().getLanguageCode());
	}
	
}
