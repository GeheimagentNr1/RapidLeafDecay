package de.geheimagentnr1.rapid_leaf_decay.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ServerConfig {
	
	
	private static final Logger LOGGER = LogManager.getLogger( ServerConfig.class );
	
	private static final String MOD_NAME = ModLoadingContext.get().getActiveContainer().getModInfo().getDisplayName();
	
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	
	public static final ForgeConfigSpec CONFIG;
	
	private static final ForgeConfigSpec.IntValue DECAY_DELAY;
	
	static {
		
		DECAY_DELAY = BUILDER.comment( "Ticks between the leaves decays." )
			.defineInRange( "decay_delay", 5, 0, Integer.MAX_VALUE );
		
		CONFIG = BUILDER.build();
	}
	
	public static void printConfig() {
		
		LOGGER.info( "Loading \"{}\" Server Config", MOD_NAME );
		LOGGER.info( "{} = {}", DECAY_DELAY.getPath(), DECAY_DELAY.get() );
		LOGGER.info( "\"{}\" Server Config loaded", MOD_NAME );
	}
	
	public static int getDecayDelay() {
		
		return DECAY_DELAY.get();
	}
}
