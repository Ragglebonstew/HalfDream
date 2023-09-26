package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.util.HDUtil;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

@ClientOnly
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	
	@Inject(method = "hasSkyBlockingEffect", at = @At("HEAD"), cancellable = true)
	private void hasSkyBlockingEffect(Camera camera, CallbackInfoReturnable<Boolean> cir) {
		Entity var3 = camera.getFocusedEntity();
		if(var3 instanceof DreamClientPlayer dcp && dcp.isDream())
			cir.setReturnValue(true);
	}
	
	@Inject(method = "getLightmapCoordinates(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I", at = @At("TAIL"), cancellable = true)
	private static void getLightmapCoordinates(BlockRenderView world, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		if(HDUtil.isPlayerDream()) {
			
			int lightmap = cir.getReturnValue();
			int blockLight = LightmapTextureManager.getBlockLightCoordinates(lightmap);
			if(HDUtil.isDreamless(pos)) {
				blockLight = 4;
			}

			cir.setReturnValue(0 << 20 | blockLight << 4);
		}
	}
}
