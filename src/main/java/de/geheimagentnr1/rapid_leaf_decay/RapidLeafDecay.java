package de.geheimagentnr1.rapid_leaf_decay;

import de.geheimagentnr1.rapid_leaf_decay.config.ServerConfig;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;


@SuppressWarnings( "UtilityClassWithPublicConstructor" )
@Mod( RapidLeafDecay.MODID )
public class RapidLeafDecay {
	
	
	public static final String MODID = "rapid_leaf_decay";
	
	public RapidLeafDecay() {
		
		ModLoadingContext.get().registerConfig( ModConfig.Type.SERVER, ServerConfig.CONFIG );
		ModLoadingContext.get().registerExtensionPoint( IExtensionPoint.DisplayTest.class,
			() -> new IExtensionPoint.DisplayTest(
				() -> FMLNetworkConstants.IGNORESERVERONLY,
				( remote, isServer ) -> true
			)
		);
	}
}
