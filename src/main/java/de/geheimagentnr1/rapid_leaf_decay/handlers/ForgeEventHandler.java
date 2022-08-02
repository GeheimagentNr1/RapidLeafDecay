package de.geheimagentnr1.rapid_leaf_decay.handlers;

import de.geheimagentnr1.rapid_leaf_decay.RapidLeafDecay;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayQueue;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayTask;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayWorker;
import de.geheimagentnr1.rapid_leaf_decay.helpers.LeavesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber( modid = RapidLeafDecay.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
	
	
	@SubscribeEvent
	public static void handlerServerStartingEvent( ServerStartingEvent event ) {
		
		DecayQueue.init();
		WorldWorkerManager.addWorker( new DecayWorker() );
	}
	
	@SubscribeEvent
	public static void handleNeighborNotifyEvent( BlockEvent.NeighborNotifyEvent event ) {
		
		LevelAccessor level = event.getLevel();
		BlockPos pos = event.getPos();
		if( level instanceof ServerLevel serverLevel && level.isEmptyBlock( pos ) ) {
			for( Direction direction : event.getNotifiedSides() ) {
				BlockPos directionPos = pos.relative( direction );
				BlockState directionState = level.getBlockState( directionPos );
				if( LeavesHelper.isValidDecayingLeaf( directionState ) &&
					LeavesHelper.isNotPersistent( directionState ) ) {
					DecayQueue.add( new DecayTask( serverLevel, directionState, directionPos ) );
				}
			}
		}
	}
}
