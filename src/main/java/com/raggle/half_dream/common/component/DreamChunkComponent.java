package com.raggle.half_dream.common.component;

import com.raggle.half_dream.common.registry.HDComponentRegistry;

import net.minecraft.world.chunk.Chunk;

public class DreamChunkComponent extends HalfDreamChunkComponent {

	public DreamChunkComponent(Chunk chunk) {
		super(chunk);
		this.id = "dream";
	}

	@Override
	public void sync() {
		HDComponentRegistry.DREAM_CHUNK.sync(this.provider);
	}/*
	@Override
	public boolean add(BlockPos pos) {
		HalfDream.LOGGER.info("Adding dream at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
		return super.add(pos);
	}
	@Override
	public boolean remove(BlockPos pos) {
		HalfDream.LOGGER.info("Removing dream at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
		return super.remove(pos);
	}*/

}
