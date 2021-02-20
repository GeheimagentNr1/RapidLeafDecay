package de.geheimagentnr1.rapid_leaf_decay.decayer;

import de.geheimagentnr1.rapid_leaf_decay.config.ServerConfig;
import de.geheimagentnr1.rapid_leaf_decay.helpers.LeavesHelper;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.WorldWorkerManager;

import java.util.ArrayList;
import java.util.TreeSet;


public class DecayWorker implements WorldWorkerManager.IWorker {
	
	
	private int tickCount = 0;
	
	@Override
	public boolean hasWork() {
		
		return true;
	}
	
	/**
	 * Perform a task, returning true from this will have the manager call this function again this tick if there is
	 * time left.
	 * Returning false will skip calling this worker until next tick.
	 */
	@Override
	public boolean doWork() {
		
		tickCount++;
		if( tickCount < ServerConfig.getDecayDelay() ) {
			return false;
		}
		tickCount = 0;
		for( DecayTask decayTask : DecayQueue.getElementsAndReset() ) {
			BlockState state = decayTask.getState();
			if( LeavesHelper.isValidDecayingLeaf( state ) ) {
				ServerWorld world = decayTask.getWorld();
				BlockPos pos = decayTask.getPos();
				if( world.isBlockPresent( pos ) ) {
					calculateDistances( state, pos, world );
					world.getBlockState( pos ).randomTick( world, pos, world.getRandom() );
				}
			}
		}
		return ServerConfig.getDecayDelay() == 0 && DecayQueue.isNotEmpty();
	}
	
	private void calculateDistances( BlockState start_state, BlockPos start_pos, ServerWorld world ) {
		
		ArrayList<BlockState> toCalculateStates = new ArrayList<>();
		ArrayList<BlockPos> toCalculatePoses = new ArrayList<>();
		TreeSet<BlockPos> inQueuePoses = new TreeSet<>();
		toCalculateStates.add( start_state );
		toCalculatePoses.add( start_pos );
		inQueuePoses.add( start_pos );
		
		while( !toCalculateStates.isEmpty() ) {
			
			BlockState toCalculateState = toCalculateStates.get( 0 );
			BlockPos toCalculatePos = toCalculatePoses.get( 0 );
			ArrayList<BlockState> directionStates = new ArrayList<>();
			ArrayList<BlockPos> directionPoses = new ArrayList<>();
			
			for( Direction direction : Direction.values() ) {
				
				BlockPos directionPos = toCalculatePos.offset( direction );
				
				if( world.isBlockPresent( directionPos ) ) {
					
					BlockState directionState = world.getBlockState( directionPos );
					
					if( LeavesHelper.isValidDecayingLeaf( directionState ) &&
						LeavesHelper.isNotPersistent( directionState ) &&
						!inQueuePoses.contains( directionPos ) ) {
						
						directionStates.add( directionState );
						directionPoses.add( directionPos );
					}
				}
			}
			
			if( calculateDistance( toCalculateState, toCalculatePos, world ) ) {
				
				toCalculateStates.addAll( directionStates );
				toCalculatePoses.addAll( directionPoses );
				inQueuePoses.addAll( directionPoses );
			}
			
			toCalculateStates.remove( 0 );
			toCalculatePoses.remove( 0 );
			inQueuePoses.remove( toCalculatePos );
		}
	}
	
	private boolean calculateDistance( BlockState queueState, BlockPos pos, ServerWorld world ) {
		
		int old_distance = LeavesHelper.getDistance( queueState );
		int distance = 7;
		
		for( Direction direction : Direction.values() ) {
			BlockPos directionPos = pos.offset( direction );
			if( world.isBlockPresent( directionPos ) ) {
				distance = Math.min( distance, getDistance( world.getBlockState( directionPos ) ) + 1 );
				if( distance == 1 ) {
					break;
				}
			}
		}
		if( old_distance != distance ) {
			world.setBlockState( pos, LeavesHelper.setDistance( world.getBlockState( pos ), distance ) );
			return true;
		}
		return false;
	}
	
	private int getDistance( BlockState state ) {
		
		if( BlockTags.LOGS.func_230235_a_( state.getBlock() ) ) {
			return 0;
		} else {
			return LeavesHelper.isValidDecayingLeaf( state ) ? LeavesHelper.getDistance( state ) : 7;
		}
	}
}
