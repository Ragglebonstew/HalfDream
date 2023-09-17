package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;

@ClientOnly
@Mixin(GameRenderer.class)
public class GameRendererMixin {
	
	//@Inject(method = "getNightVisionStrength", at = @At("HEAD"), cancellable = true)
	private static void getNightVisionStrength(LivingEntity entity, float tickDelta, CallbackInfoReturnable<Float> cir) {
		if(entity.getStatusEffect(StatusEffects.NIGHT_VISION) == null) {
			cir.setReturnValue(1.0F);
		}
	}
	
}
