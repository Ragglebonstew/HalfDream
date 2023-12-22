package com.raggle.half_dream.util;

import java.util.Optional;

import com.raggle.half_dream.api.DreamComponent;
import com.raggle.half_dream.api.DreamEntity;
import com.raggle.half_dream.common.registry.HDComponentRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class HDUtil {

	public static byte getDream(Entity e) {
		Optional<DreamEntity> de = HDComponentRegistry.DREAM_ENTITY.maybeGet(e);
		if(de.isEmpty())
			return 0;
		return de.get().getDream();
	}

	public static void setDream(Entity entity, byte b) {
		Optional<DreamEntity> de = HDComponentRegistry.DREAM_ENTITY.maybeGet(entity);
		if(de.isEmpty())
			return;
		de.get().setDream(b);
	}
	public static boolean isDream(BlockPos pos, World world) {
		if(world != null) {
			Chunk chunk = world.getChunk(pos);
			if(chunk == null) return false;
			return isDream(pos, chunk);
		}
		return false;
	}
	public static boolean isDream(BlockPos pos, Chunk chunk) {
		Optional<DreamComponent> op = HDComponentRegistry.DREAM_CHUNK.maybeGet(chunk);
		if(op.isEmpty())
			return false;
		return op.get().contains(pos);
	}
	public static boolean setDream(BlockPos pos, boolean add, World world) {
		if(world != null) {
			Chunk chunk = world.getChunk(pos);
			
			if(chunk == null)
				return false;
			else {
				Optional<DreamComponent> op = HDComponentRegistry.DREAM_CHUNK.maybeGet(chunk);
				if(op.isEmpty())
					return false;
				if(add)
					return op.get().add(pos);
				else
					return op.get().remove(pos);
			}
		}
		return false;
	}
	public static boolean isDisturbed(BlockPos pos, World world) {
		if(world != null) {
			Chunk chunk = world.getChunk(pos);
			if(chunk == null) return false;
			return isDisturbed(pos, chunk);
		}
		return false;
	}
	public static boolean isDisturbed(BlockPos pos, Chunk chunk) {
		Optional<DreamComponent> op = HDComponentRegistry.DISTURBED_CHUNK.maybeGet(chunk);
		if(op.isEmpty())
			return false;
		return op.get().contains(pos);
	}
	public static boolean setDisturbed(BlockPos pos, boolean dreamless, World world) {
		if(world != null) {
			Chunk chunk = world.getChunk(pos);
			
			if(chunk == null)
				return false;
			else {
				Optional<DreamComponent> op = HDComponentRegistry.DISTURBED_CHUNK.maybeGet(chunk);
				if(op.isEmpty())
					return false;
				if(dreamless)
					return op.get().add(pos);
				else
					return op.get().remove(pos);
			}
		}
		return false;
	}
	public static boolean sameDream(Entity e1, Entity e2) {
		byte d1 = getDream(e1);
		byte d2 = getDream(e2);
		return d1 == 2 || d2 == 2 || d1 == d2;
	}
	
}
