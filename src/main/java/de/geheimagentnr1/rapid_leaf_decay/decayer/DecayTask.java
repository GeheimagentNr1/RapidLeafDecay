package de.geheimagentnr1.rapid_leaf_decay.decayer;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;


public class DecayTask {
	
	
	private final ServerLevel level;
	
	private final BlockState state;
	
	private final BlockPos pos;
	
	public DecayTask( ServerLevel _world, BlockState _state, BlockPos _pos ) {
		
		level = _world;
		state = _state;
		pos = _pos;
	}
	
	//package-private
	ServerLevel getLevel() {
		
		return level;
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
