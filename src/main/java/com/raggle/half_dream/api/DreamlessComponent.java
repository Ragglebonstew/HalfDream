package com.raggle.half_dream.api;
import java.util.ArrayList;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.util.math.BlockPos;

public interface DreamlessComponent extends ComponentV3 {

	public ArrayList<BlockPos> getDreamlessList();
	
	public boolean isDreamless(BlockPos pos);
	
	public void addPosToList(BlockPos pos);
	
}