package de.geheimagentnr1.rapid_leaf_decay.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.tags.BlockTags;


public class LeavesHelper {
	
	
	public static boolean isValidDecayingLeaf( BlockState state ) {
		
		return ( BlockTags.LEAVES.func_230235_a_( state.getBlock() ) || state.getBlock() instanceof LeavesBlock ) &&
			state.func_235901_b_( LeavesBlock.DISTANCE );
	}
	
	public static boolean isNotPersistent( BlockState state ) {
		
		return state.func_235901_b_( LeavesBlock.PERSISTENT ) && !state.get( LeavesBlock.PERSISTENT );
	}
	
	public static int getDistance( BlockState state ) {
		
		return state.func_235901_b_( LeavesBlock.DISTANCE ) ? state.get( LeavesBlock.DISTANCE ) : 7;
	}
	
	public static BlockState setDistance( BlockState state, int distance ) {
		
		return isValidDecayingLeaf( state ) ? state.with( LeavesBlock.DISTANCE, distance ) : state;
	}
}
