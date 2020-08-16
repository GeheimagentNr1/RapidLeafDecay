package de.geheimagentnr1.rapid_leaf_decay.handlers;

import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayQueue;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayTask;
import de.geheimagentnr1.rapid_leaf_decay.decayer.DecayWorker;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.WorldWorkerManager;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.util.EnumSet;


@SuppressWarnings( "unused" )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.FORGE )
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
		BlockState state = world.getBlockState( pos );
		if( state.isAir( world, pos ) ) {
			EnumSet<Direction> directions = event.getNotifiedSides();
			for( Direction direction : directions ) {
				BlockPos directionPos = pos.offset( direction );
				BlockState directionState = world.getBlockState( directionPos );
				if( BlockTags.LEAVES.contains( directionState.getBlock() ) &&
					!directionState.get( LeavesBlock.PERSISTENT ) ) {
					DecayQueue.add( new DecayTask( world.getWorld(), directionState, directionPos ) );
				}
			}
		}
	}
}
