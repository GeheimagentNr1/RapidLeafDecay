package de.geheimagentnr1.rapid_leaf_decay.handlers;

import de.geheimagentnr1.rapid_leaf_decay.RapidLeafDecay;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayQueue;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayTask;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayWorker;
import de.geheimagentnr1.rapid_leaf_decay.helpers.LeavesHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;


@Mod.EventBusSubscriber( modid = RapidLeafDecay.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
	
	
	@SubscribeEvent
	public static void handlerServerStartingEvent( FMLServerStartingEvent event ) {
		
		DecayQueue.init();
		WorldWorkerManager.addWorker( new DecayWorker() );
	}
	
	@SubscribeEvent
	public static void handleNeighborNotifyEvent( BlockEvent.NeighborNotifyEvent event ) {
		
		IWorld world = event.getWorld();
		BlockPos pos = event.getPos();
		if( world instanceof ServerWorld && world.isAirBlock( pos ) ) {
			ServerWorld serverWorld = (ServerWorld)world;
			for( Direction direction : event.getNotifiedSides() ) {
				BlockPos directionPos = pos.offset( direction );
				BlockState directionState = world.getBlockState( directionPos );
				if( LeavesHelper.isValidDecayingLeaf( directionState ) &&
					LeavesHelper.isNotPersistent( directionState ) ) {
					DecayQueue.add( new DecayTask( serverWorld, directionState, directionPos ) );
				}
			}
		}
	}
}
