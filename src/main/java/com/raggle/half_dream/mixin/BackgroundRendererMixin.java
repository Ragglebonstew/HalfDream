package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.shader.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.raggle.half_dream.client.sequence.FogEffect;
import com.raggle.half_dream.client.sequence.SequenceManager;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;

@ClientOnly
@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
	
	@Shadow private static float red;
	@Shadow private static float green;
	@Shadow private static float blue;
	
	@Inject(method = "applyFog", at = @At("TAIL"))
	private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
	
		if(SequenceManager.hasFogEffect()) {
			FogEffect fog = SequenceManager.getFogEffect();
			if (fogType == BackgroundRenderer.FogType.FOG_SKY) {
				RenderSystem.setShaderFogStart(0.0f);
				RenderSystem.setShaderFogEnd(viewDistance);
			}
			else {
				RenderSystem.setShaderFogStart(fog.fogStart);
				RenderSystem.setShaderFogEnd(fog.fogEnd);
			}
			
			RenderSystem.setShaderFogShape(FogShape.SPHERE);
		}

	}
	
	@Inject(method = "render", at = @At("TAIL"))
	private static void render(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
		if(SequenceManager.hasFogEffect()) {
			FogEffect fog = SequenceManager.getFogEffect();
			//transitions sky between default and fog colors based on alpha progress
			float r = fog.alpha * (fog.red - red) + red;
			float g = fog.alpha * (fog.green - green) + green;
			float b = fog.alpha * (fog.blue - blue) + blue;
			RenderSystem.clearColor(r, g, b, 0.0F);
		}
	}
	
	@Inject(method = "setShaderFogColor", at = @At("TAIL"))
	private static void setShaderFogColor(CallbackInfo ci) {
		if(SequenceManager.hasFogEffect()) {
			FogEffect fog = SequenceManager.getFogEffect();
			RenderSystem.setShaderFogColor(fog.red, fog.green, fog.blue, fog.alpha);
		}
	}
}

