package de.geheimagentnr1.rapid_leaf_decay.helpers;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;


public class LeavesHelper {
	
	
	public static boolean isValidDecayingLeaf( BlockState state ) {
		
		return ( BlockTags.LEAVES.contains( state.getBlock() ) || state.getBlock() instanceof LeavesBlock ) &&
			state.hasProperty( LeavesBlock.DISTANCE );
	}
	
	public static boolean isNotPersistent( BlockState state ) {
		
		return state.hasProperty( LeavesBlock.PERSISTENT ) && !state.getValue( LeavesBlock.PERSISTENT );
	}
	
	public static int getDistance( BlockState state ) {
		
		return state.hasProperty( LeavesBlock.DISTANCE ) ? state.getValue( LeavesBlock.DISTANCE ) : 7;
	}
	
	public static BlockState setDistance( BlockState state, int distance ) {
		
		return isValidDecayingLeaf( state ) ? state.setValue( LeavesBlock.DISTANCE, distance ) : state;
	}
}
