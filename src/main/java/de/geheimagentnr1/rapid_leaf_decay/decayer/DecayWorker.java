package de.geheimagentnr1.rapid_leaf_decay.decayer;

import de.geheimagentnr1.rapid_leaf_decay.config.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
		if( tickCount < ModConfig.getDecayDelay() ) {
			return false;
		}
		tickCount = 0;
		for( DecayTask decayTask : DecayQueue.getElementsAndReset() ) {
			BlockState state = decayTask.getState();
			Block block = state.getBlock();
			if( BlockTags.LEAVES.contains( block ) ) {
				World world = decayTask.getWorld();
				BlockPos pos = decayTask.getPos();
				calculateDistances( state, pos, world );
				world.getBlockState( pos ).randomTick( world, pos, world.getRandom() );
			}
		}
		return ModConfig.getDecayDelay() == 0 && DecayQueue.isNotEmpty();
	}
	
	private void calculateDistances( BlockState start_state, BlockPos start_pos, World world ) {
		
		ArrayList<BlockState> blockStates = new ArrayList<>();
		blockStates.add( start_state );
		ArrayList<BlockPos> blockPoses = new ArrayList<>();
		blockPoses.add( start_pos );
		TreeSet<BlockPos> poses = new TreeSet<>();
		poses.add( start_pos );
		
		while( !blockStates.isEmpty() ) {
			BlockState current_blockState = blockStates.get( 0 );
			BlockPos current_blockPos = blockPoses.get( 0 );
			if( calculateDistance( current_blockState, current_blockPos, world ) ) {
				for( Direction direction : Direction.values() ) {
					BlockPos directionPos = current_blockPos.offset( direction );
					if( world.isBlockPresent( directionPos ) ) {
						BlockState directionBlockState = world.getBlockState( directionPos );
						if( BlockTags.LEAVES.contains( directionBlockState.getBlock() ) &&
							!directionBlockState.get( LeavesBlock.PERSISTENT ) &&
							!poses.contains( directionPos ) ) {
							blockStates.add( directionBlockState );
							blockPoses.add( directionPos );
							poses.add( directionPos );
						}
					}
				}
			}
			blockStates.remove( 0 );
			blockPoses.remove( 0 );
			poses.remove( current_blockPos );
		}
	}
	
	private boolean calculateDistance( BlockState state, BlockPos pos, World world ) {
		
		int old_distance = state.get( LeavesBlock.DISTANCE );
		int distance = 7;
		
		for( Direction direction : Direction.values() ) {
			BlockPos directionPos = pos.offset( direction );
			distance = Math.min( distance, getDistance( world.getBlockState( directionPos ) ) + 1 );
			if( distance == 1 ) {
				break;
			}
		}
		if( old_distance != distance ) {
			world.setBlockState( pos, state.with( LeavesBlock.DISTANCE, distance ) );
			return true;
		}
		return false;
	}
	
	private int getDistance( BlockState neighbor ) {
		
		if( BlockTags.LOGS.contains( neighbor.getBlock() ) ) {
			return 0;
		} else {
			return neighbor.getBlock() instanceof LeavesBlock ? neighbor.get( LeavesBlock.DISTANCE ) : 7;
		}
	}
}
