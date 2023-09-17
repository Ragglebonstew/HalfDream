package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.common.registry.HDStatusEffectRegistry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	@Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
	
	/*@Inject(method = "wakeUp", at = @At("INVOKE"))
	public void wakeup(CallbackInfo ci) {
		if(this instanceof DreamServerPlayer player) {
			player.setDream(!player.isDream());
			player.syncDream();
		}
	}*/
	
	//@Inject(method = "updatePotionVisibility", at = @At("TAIL"))
	private void updatePotionVisibility(CallbackInfo ci) {
		((LivingEntity)(Object)this).setInvisible(this.hasStatusEffect(StatusEffects.INVISIBILITY) || this.hasStatusEffect(HDStatusEffectRegistry.HALF_ASLEEP));
	}
}
