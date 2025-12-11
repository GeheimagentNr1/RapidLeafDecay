package de.geheimagentnr1.rapid_leaf_decay.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;


public class ServerConfig {
	
	
	@NotNull
	public static final ModConfigSpec SPEC;
	
	@NotNull
	private static final ModConfigSpec.IntValue DECAY_DELAY;
	
	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		
		DECAY_DELAY = builder
			.comment( "Ticks between the leaves decays." )
			.defineInRange( "decay_delay", 5, 0, Integer.MAX_VALUE );
		
		SPEC = builder.build();
	}
	
	public static int getDecayDelay() {
		
		return DECAY_DELAY.get();
	}
}
