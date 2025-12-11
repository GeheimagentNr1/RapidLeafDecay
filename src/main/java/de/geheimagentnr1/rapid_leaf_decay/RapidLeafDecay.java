package de.geheimagentnr1.rapid_leaf_decay;

import de.geheimagentnr1.rapid_leaf_decay.config.ServerConfig;
import de.geheimagentnr1.rapid_leaf_decay.handlers.DecayWorkHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;


@Mod( RapidLeafDecay.MODID )
public class RapidLeafDecay {
	
	
	@NotNull
	public static final String MODID = "rapid_leaf_decay";
	
	public RapidLeafDecay( IEventBus modEventBus, ModContainer modContainer ) {
		
		modContainer.registerConfig( ModConfig.Type.SERVER, ServerConfig.SPEC );
		NeoForge.EVENT_BUS.register( new DecayWorkHandler() );
	}
}
