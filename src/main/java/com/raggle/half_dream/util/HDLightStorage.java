package com.raggle.half_dream.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.ChunkToNibbleArrayMap;
import net.minecraft.world.chunk.light.LightStorage;

public class HDLightStorage extends LightStorage<HDLightStorage.Data>{

	protected HDLightStorage(ChunkProvider chunkProvider) {
		super(LightType.BLOCK, chunkProvider, new HDLightStorage.Data(new Long2ObjectOpenHashMap<>()));
	}

	protected static final class Data extends ChunkToNibbleArrayMap<HDLightStorage.Data> {
		public Data(Long2ObjectOpenHashMap<ChunkNibbleArray> long2ObjectOpenHashMap) {
			super(long2ObjectOpenHashMap);
		}

		public HDLightStorage.Data copy() {
			return new HDLightStorage.Data(this.arrays.clone());
		}
	}

	@Override
	protected int getLight(long blockPos) {
		long l = ChunkSectionPos.fromBlockPos(blockPos);
		ChunkNibbleArray chunkNibbleArray = this.getLightSection(l, false);
		return chunkNibbleArray == null
			? 0
			: chunkNibbleArray.get(
				ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)),
				ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)),
				ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos))
			);
	}
}
