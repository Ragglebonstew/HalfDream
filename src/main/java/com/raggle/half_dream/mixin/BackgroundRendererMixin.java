package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.shader.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.raggle.half_dream.client.sequence.SequenceManager;
import com.raggle.half_dream.util.HDFogParameters;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;

@ClientOnly
@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
	
	@Inject(method = "applyFog", at = @At("TAIL"))
	private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
	
		if(SequenceManager.hasFogEffect()) {
			HDFogParameters fogParameters = new HDFogParameters(fogType);
			SequenceManager.getFogEffect().applyFogAffects(fogParameters);
			
			RenderSystem.setShaderFogColor(fogParameters.red, fogParameters.green, fogParameters.blue, fogParameters.alpha);
			RenderSystem.setShaderFogStart(fogParameters.fogStart);
			RenderSystem.setShaderFogEnd(fogParameters.fogEnd);
			RenderSystem.setShaderFogShape(FogShape.SPHERE);
			RenderSystem.clearColor(fogParameters.red, fogParameters.green, fogParameters.blue, 0.0F);
			
		}

	}
	
	/*@Inject(method = "setShaderFogColor", at = @At("TAIL"))
	private static void setShaderFogColor(CallbackInfo ci) {
		RenderSystem.setShaderFogColor(fogParameters.red, fogParameters.green, fogParameters.blue, fogParameters.alpha);
	}*/

}

