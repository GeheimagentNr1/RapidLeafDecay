package de.geheimagentnr1.rapid_leaf_decay.decayer;

import de.geheimagentnr1.rapid_leaf_decay.config.ServerConfig;
import de.geheimagentnr1.rapid_leaf_decay.helpers.LeavesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
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
				ServerLevel level = decayTask.getLevel();
				BlockPos pos = decayTask.getPos();
				if( level.isLoaded( pos ) ) {
					calculateDistances( state, pos, level );
					level.getBlockState( pos ).randomTick( level, pos, level.getRandom() );
				}
			}
		}
		return ServerConfig.getDecayDelay() == 0 && DecayQueue.isNotEmpty();
	}
	
	private void calculateDistances( BlockState start_state, BlockPos start_pos, ServerLevel level ) {
		
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
				
				BlockPos directionPos = toCalculatePos.relative( direction );
				
				if( level.isLoaded( directionPos ) ) {
					
					BlockState directionState = level.getBlockState( directionPos );
					
					if( LeavesHelper.isValidDecayingLeaf( directionState ) &&
						LeavesHelper.isNotPersistent( directionState ) &&
						!inQueuePoses.contains( directionPos ) ) {
						
						directionStates.add( directionState );
						directionPoses.add( directionPos );
					}
				}
			}
			
			if( calculateDistance( toCalculateState, toCalculatePos, level ) ) {
				
				toCalculateStates.addAll( directionStates );
				toCalculatePoses.addAll( directionPoses );
				inQueuePoses.addAll( directionPoses );
			}
			
			toCalculateStates.remove( 0 );
			toCalculatePoses.remove( 0 );
			inQueuePoses.remove( toCalculatePos );
		}
	}
	
	private boolean calculateDistance( BlockState queueState, BlockPos pos, ServerLevel level ) {
		
		int old_distance = LeavesHelper.getDistance( queueState );
		int distance = 7;
		
		for( Direction direction : Direction.values() ) {
			BlockPos directionPos = pos.relative( direction );
			if( level.isLoaded( directionPos ) ) {
				distance = Math.min( distance, getDistance( level.getBlockState( directionPos ) ) + 1 );
				if( distance == 1 ) {
					break;
				}
			}
		}
		if( old_distance != distance ) {
			level.setBlockAndUpdate( pos, LeavesHelper.setDistance( level.getBlockState( pos ), distance ) );
			return true;
		}
		return false;
	}
	
	private int getDistance( BlockState state ) {
		
		if( state.is( BlockTags.LOGS ) ) {
			return 0;
		} else {
			return LeavesHelper.isValidDecayingLeaf( state ) ? LeavesHelper.getDistance( state ) : 7;
		}
	}
}
