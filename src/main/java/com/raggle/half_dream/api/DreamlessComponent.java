package com.raggle.half_dream.api;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.util.math.BlockPos;

public interface DreamlessComponent extends ComponentV3 {
	
	public boolean isDreamless(BlockPos pos);

	public boolean addPosToList(BlockPos pos);
	
	public boolean removePosFromList(BlockPos pos);
	
}