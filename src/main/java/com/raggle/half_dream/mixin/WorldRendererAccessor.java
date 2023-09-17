package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.BuiltChunkStorage;
import net.minecraft.client.render.WorldRenderer;

@ClientOnly
@Mixin(WorldRenderer.class)
public interface WorldRendererAccessor {

	@Accessor("chunks")
	public BuiltChunkStorage getChunks();
	
	@Invoker("scheduleChunkRender")
	public void invokeScheduleChunkRender(int x, int y, int z, boolean important);
	
}
