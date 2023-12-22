package com.raggle.half_dream.util;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import com.raggle.half_dream.client.sequence.SequenceManager;
import com.raggle.half_dream.mixin.WorldRendererAccessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;

@ClientOnly
public class HDUtilClient {

	public static ClientPlayerEntity getClientPlayer() {
		MinecraftClient mc = MinecraftClient.getInstance();
		return mc.player;
	}
	public static boolean hasClientPlayer() {
		return getClientPlayer() != null;
	}
	public static byte getPlayerDream() {
		return SequenceManager.hasSequence() ? SequenceManager.getSequence().getDreamState() : HDUtil.getDream(getClientPlayer());
	}
	public static void scheduleChunkRenderAt(BlockPos pos) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc.world != null) {
			ChunkSectionPos chunkPos = ChunkSectionPos.from(pos);
			((WorldRendererAccessor)mc.worldRenderer).invokeScheduleChunkRender(chunkPos.getSectionX(), chunkPos.getSectionY(), chunkPos.getSectionZ(), true);;
		}
	}
	public static boolean sameDream(Entity entity) {
		byte d1 = HDUtil.getDream(entity);
		byte d2 = getPlayerDream();
		return d1 == 2 || d2 == 2 || d1 == d2;
	}
	public static boolean isDream(BlockPos pos) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc != null && mc.world != null) {
			return HDUtil.isDream(pos, mc.world);
		}
		return false;
	}
	public static boolean isDisturbed(BlockPos pos) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc != null && mc.world != null) {
			return HDUtil.isDisturbed(pos, mc.world);
		}
		return false;
	}
}
