package de.geheimagentnr1.rapid_leaf_decay.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainConfig {
	
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final String mod_name = "Rapid Leaf Decay";
	
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	
	public static final ForgeConfigSpec CONFIG;
	
	private static final ForgeConfigSpec.IntValue DECAY_DELAY;
	
	static {
		
		DECAY_DELAY = BUILDER.comment( "Ticks between the leaves decays." )
			.defineInRange( "decay_delay", 5, 0, Integer.MAX_VALUE );
		
		CONFIG = BUILDER.build();
	}
	
	public static void printConfig() {
		
		LOGGER.info( "Loading \"{}\" Config", mod_name );
		LOGGER.info( "{} = {}", DECAY_DELAY.getPath(), DECAY_DELAY.get() );
		LOGGER.info( "\"{}\" Config loaded", mod_name );
	}
	
	public static int getDecayDelay() {
		
		return DECAY_DELAY.get();
	}
}
