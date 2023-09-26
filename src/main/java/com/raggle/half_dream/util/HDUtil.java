package com.raggle.half_dream.util;

import java.util.Optional;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.api.DreamlessComponent;
import com.raggle.half_dream.common.block.DreamBlock;
import com.raggle.half_dream.common.registry.HDComponentRegistry;
import com.raggle.half_dream.mixin.WorldRendererAccessor;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class HDUtil {

	@ClientOnly
	public static boolean isDreamless(BlockPos pos) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc != null && mc.world != null) {
			return isDreamless(pos, mc.world);
		}
		return false;
	}
	public static boolean isDreamless(BlockPos pos, World world) {
		if(world != null) {
			Chunk chunk = world.getChunk(pos);
			Block block = chunk.getBlockState(pos).getBlock();
			if(block instanceof DreamBlock)
				return false;
			if(chunk != null) {
				Optional<DreamlessComponent> op = HDComponentRegistry.DREAMLESS.maybeGet(chunk);
				if(op.isEmpty())
					return false;
				return op.get().isDreamless(pos);
			}
		}
		return false;
	}
	public static boolean setDreamless(BlockPos pos, boolean dreamless, World world) {
		if(world != null) {
			Chunk chunk = world.getChunk(pos);
			
			if(chunk == null || chunk.getBlockState(pos).getBlock() instanceof DreamBlock)
				return false;
			else {
				Optional<DreamlessComponent> op = HDComponentRegistry.DREAMLESS.maybeGet(chunk);
				if(op.isEmpty())
					return false;
				if(dreamless)
					return op.get().addPosToList(pos);
				else
					return op.get().removePosFromList(pos);
			}
		}
		return false;
	}
	@ClientOnly
	public static ClientPlayerEntity getClientPlayer() {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc == null)
			return null;
		return mc.player;
	}
	@ClientOnly
	public static boolean hasClientPlayer() {
		return getClientPlayer() != null;
	}
	@ClientOnly
	public static boolean isPlayerDream() {
		return getClientPlayer() instanceof DreamClientPlayer dcp && dcp.isDream();
	}
	@ClientOnly
	public static void scheduleChunkRenderAt(BlockPos pos) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc != null && mc.world != null) {
			ChunkSectionPos chunkPos = ChunkSectionPos.from(pos);
			((WorldRendererAccessor)mc.worldRenderer).invokeScheduleChunkRender(chunkPos.getSectionX(), chunkPos.getSectionY(), chunkPos.getSectionZ(), true);;
		}
	}
	@ClientOnly
	public static void scheduleChunkRenderAt(int x, int y, int z) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc.world != null) {
			//mc.worldRenderer.scheduleBlockRender(x, y, z);
			((WorldRendererAccessor)mc.worldRenderer).invokeScheduleChunkRender(x, y, z, true);;
		}
	}
	
}
