package de.geheimagentnr1.rapid_leaf_decay;

import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.rapid_leaf_decay.config.ServerConfig;
import de.geheimagentnr1.rapid_leaf_decay.handlers.DecayWorkHandler;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;


@Mod( RapidLeafDecay.MODID )
public class RapidLeafDecay extends AbstractMod {
	
	
	@NotNull
	static final String MODID = "rapid_leaf_decay";
	
	@NotNull
	@Override
	public String getModId() {
		
		return MODID;
	}
	
	@Override
	protected void initMod() {
		
		ServerConfig serverConfig = registerConfig( ServerConfig::new );
		registerEventHandler( new DecayWorkHandler( serverConfig ) );
	}
}
