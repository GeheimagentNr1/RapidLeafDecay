package de.geheimagentnr1.rapid_leaf_decay.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import de.geheimagentnr1.rapid_leaf_decay.RapidLeafDecay;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ModConfig {
	
	
	private final static Logger LOGGER = LogManager.getLogger();
	
	private final static String mod_name = "Rapid Leaf Decay";
	
	private final static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	
	private final static ForgeConfigSpec CONFIG;
	
	private final static ForgeConfigSpec.IntValue DECAY_DELAY;
	
	static {
		
		DECAY_DELAY = BUILDER.comment( "Ticks between the leaves decays." )
			.defineInRange( "decay_delay", 5, 0, Integer.MAX_VALUE );
		
		CONFIG = BUILDER.build();
	}
	
	public static void load() {
		
		CommentedFileConfig configData = CommentedFileConfig.builder( FMLPaths.CONFIGDIR.get().resolve(
			RapidLeafDecay.MODID + ".toml" ) ).sync().autosave().writingMode( WritingMode.REPLACE ).build();
		
		LOGGER.info( "Loading \"{}\" Config", mod_name );
		configData.load();
		CONFIG.setConfig( configData );
		LOGGER.info( "{} = {}", DECAY_DELAY.getPath(), DECAY_DELAY.get() );
		LOGGER.info( "\"{}\" Config loaded", mod_name );
	}
	
	public static int getDecayDelay() {
		
		return DECAY_DELAY.get();
	}
}
