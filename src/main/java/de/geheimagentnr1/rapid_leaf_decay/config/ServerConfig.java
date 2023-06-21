package de.geheimagentnr1.rapid_leaf_decay.config;

import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.config.AbstractConfig;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;


public class ServerConfig extends AbstractConfig {
	
	
	@NotNull
	private static final String DECAY_DELAY_KEY = "decay_delay";
	
	public ServerConfig( @NotNull AbstractMod _abstractMod ) {
		
		super( _abstractMod );
	}
	
	@NotNull
	@Override
	public ModConfig.Type type() {
		
		return ModConfig.Type.SERVER;
	}
	
	@Override
	public boolean isEarlyLoad() {
		
		return false;
	}
	
	@Override
	protected void registerConfigValues() {
		
		registerConfigValue(
			"Ticks between the leaves decays.",
			DECAY_DELAY_KEY,
			( builder, path ) -> builder.defineInRange( path, 5, 0, Integer.MAX_VALUE )
		);
	}
	
	public int getDecayDelay() {
		
		return getValue( Integer.class, DECAY_DELAY_KEY );
	}
}
