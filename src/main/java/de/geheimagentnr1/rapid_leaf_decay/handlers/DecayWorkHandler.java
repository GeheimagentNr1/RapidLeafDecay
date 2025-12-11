package de.geheimagentnr1.rapid_leaf_decay.handlers;

import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayQueue;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayTask;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayWorker;
import de.geheimagentnr1.rapid_leaf_decay.helpers.LeavesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.common.WorldWorkerManager;
import org.jetbrains.annotations.NotNull;


public class DecayWorkHandler {
	
	
	@NotNull
	private final DecayQueue decayQueue = new DecayQueue();
	
	@SubscribeEvent
	public void handleServerStartingEvent( @NotNull ServerStartingEvent event ) {
		
		decayQueue.init();
		WorldWorkerManager.addWorker( new DecayWorker( decayQueue ) );
	}
	
	@SubscribeEvent
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
