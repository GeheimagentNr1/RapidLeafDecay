package de.geheimagentnr1.rapid_leaf_decay;

import de.geheimagentnr1.rapid_leaf_decay.config.MainConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@SuppressWarnings( { "unused", "UtilityClassWithPublicConstructor" } )
@Mod( RapidLeafDecay.MODID )
public class RapidLeafDecay {
	
	
	//package-private
	static final String MODID = "rapid_leaf_decay";
	
	public RapidLeafDecay() {
		
		ModLoadingContext.get().registerConfig( ModConfig.Type.COMMON, MainConfig.CONFIG, MODID + ".toml" );
	}
}
