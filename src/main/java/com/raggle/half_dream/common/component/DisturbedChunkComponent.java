package com.raggle.half_dream.common.component;

import com.raggle.half_dream.common.registry.HDComponentRegistry;

import net.minecraft.world.chunk.Chunk;

public class DisturbedChunkComponent extends HalfDreamChunkComponent {

	public DisturbedChunkComponent(Chunk chunk) {
		super(chunk);
		this.id = "disturbed";
	}

	@Override
	public void sync() {
		HDComponentRegistry.DISTURBED_CHUNK.sync(this.provider);
	}/*
	@Override
	public boolean add(BlockPos pos) {
		HalfDream.LOGGER.info("Adding disturbed at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
		return super.add(pos);
	}
	@Override
	public boolean remove(BlockPos pos) {
		HalfDream.LOGGER.info("Removing disturbed at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
		return super.remove(pos);
	}*/

}
