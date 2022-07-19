package de.geheimagentnr1.rapid_leaf_decay;

import de.geheimagentnr1.rapid_leaf_decay.config.ServerConfig;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.network.NetworkConstants;


@SuppressWarnings( "UtilityClassWithPublicConstructor" )
@Mod( RapidLeafDecay.MODID )
public class RapidLeafDecay {
	
	
	public static final String MODID = "rapid_leaf_decay";
	
	public RapidLeafDecay() {
		
		ModLoadingContext.get().registerConfig( ModConfig.Type.SERVER, ServerConfig.CONFIG );
	}
}
