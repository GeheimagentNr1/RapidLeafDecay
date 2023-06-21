package de.geheimagentnr1.rapid_leaf_decay.decayer;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


@RequiredArgsConstructor
@Data
public class DecayTask {
	
	
	@NotNull
	private final ServerLevel level;
	
	@NotNull
	private final BlockState state;
	
	@NotNull
	private final BlockPos pos;
}
