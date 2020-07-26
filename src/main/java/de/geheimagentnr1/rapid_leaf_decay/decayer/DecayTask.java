package de.geheimagentnr1.rapid_leaf_decay.decayer;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;


public class DecayTask {
	
	
	private final ServerWorld world;
	
	private final BlockState state;
	
	private final BlockPos pos;
	
	public DecayTask( ServerWorld _world, BlockState _state, BlockPos _pos ) {
		
		world = _world;
		state = _state;
		pos = _pos;
	}
	
	//package-private
	ServerWorld getWorld() {
		
		return world;
	}
	
	//package-private
	BlockState getState() {
		
		return state;
	}
	
	//package-private
	BlockPos getPos() {
		
		return pos;
	}
}
