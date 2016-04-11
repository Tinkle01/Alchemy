package index.alchemy.core;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class AlchemyConfigLoader {
	
    private Configuration config;

    public AlchemyConfigLoader(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        initConfig();
        config.save();
    }

    private void initConfig() {}

}
