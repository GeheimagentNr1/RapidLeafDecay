package de.geheimagentnr1.rapid_leaf_decay.helpers;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


public class LeavesHelper {
	
	
	public static boolean isValidDecayingLeaf( @NotNull BlockState state ) {
		
		return ( state.is( BlockTags.LOGS ) || state.getBlock() instanceof LeavesBlock ) &&
			state.hasProperty( LeavesBlock.DISTANCE );
	}
	
	public static boolean isNotPersistent( @NotNull BlockState state ) {
		
		return state.hasProperty( LeavesBlock.PERSISTENT ) && !state.getValue( LeavesBlock.PERSISTENT );
	}
	
	public static int getDistance( @NotNull BlockState state ) {
		
		return state.hasProperty( LeavesBlock.DISTANCE ) ? state.getValue( LeavesBlock.DISTANCE ) : 7;
	}
	
	@NotNull
	public static BlockState setDistance( @NotNull BlockState state, int distance ) {
		
		return isValidDecayingLeaf( state ) ? state.setValue( LeavesBlock.DISTANCE, distance ) : state;
	}
}
