package com.raggle.half_dream.api;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.util.math.BlockPos;

public interface DreamComponent extends ComponentV3 {

	public boolean contains(BlockPos pos);
	public boolean add(BlockPos pos);
	public boolean remove(BlockPos pos);
	
}