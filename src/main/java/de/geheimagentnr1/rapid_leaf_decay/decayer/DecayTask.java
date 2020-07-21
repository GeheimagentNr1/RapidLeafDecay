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
	
	public ServerWorld getWorld() {
		
		return world;
	}
	
	public BlockState getState() {
		
		return state;
	}
	
	public BlockPos getPos() {
		
		return pos;
	}
}
