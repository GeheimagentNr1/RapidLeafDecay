package de.geheimagentnr1.rapid_leaf_decay.handlers;

import de.geheimagentnr1.minecraft_forge_api.events.ForgeEventHandlerInterface;
import de.geheimagentnr1.rapid_leaf_decay.config.ServerConfig;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayQueue;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayTask;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayWorker;
import de.geheimagentnr1.rapid_leaf_decay.helpers.LeavesHelper;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;


@RequiredArgsConstructor
public class DecayWorkHandler implements ForgeEventHandlerInterface {
	
	
	@NotNull
	private final ServerConfig serverConfig;
	
	@NotNull
	private final DecayQueue decayQueue = new DecayQueue();
	
	@SubscribeEvent
	@Override
	public void handleServerStartingEvent( @NotNull ServerStartingEvent event ) {
		
		decayQueue.init();
		WorldWorkerManager.addWorker( new DecayWorker( serverConfig, decayQueue ) );
	}
	
	@SubscribeEvent
	@Override
	public void handleBlockNeighborNotifyEvent( @NotNull BlockEvent.NeighborNotifyEvent event ) {
		
		LevelAccessor level = event.getLevel();
		BlockPos pos = event.getPos();
		if( level instanceof ServerLevel serverLevel && level.isEmptyBlock( pos ) ) {
			for( Direction direction : event.getNotifiedSides() ) {
				BlockPos directionPos = pos.relative( direction );
				BlockState directionState = level.getBlockState( directionPos );
				if( LeavesHelper.isValidDecayingLeaf( directionState ) &&
					LeavesHelper.isNotPersistent( directionState ) ) {
					decayQueue.add( new DecayTask( serverLevel, directionState, directionPos ) );
				}
			}
		}
	}
}
