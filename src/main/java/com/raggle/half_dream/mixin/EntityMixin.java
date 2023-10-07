package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.api.HorseRiderAccess;
import com.raggle.half_dream.util.HDUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Inject(method = "isInsideWall", at = @At("HEAD"), cancellable = true)
	private void isInsideWall(CallbackInfoReturnable<Boolean> cir) {
		if((Object)this instanceof LivingEntity e && HDUtil.isDream(e)) {
			cir.setReturnValue(false);
		}
	}
	
	@Inject(method = "addPassenger", at = @At("TAIL"))
	protected void addPassenger(Entity passenger, CallbackInfo ci) {
		if ((Object)this instanceof HorseRiderAccess dh && passenger instanceof PlayerEntity player) {
			dh.setPlayer(player);
		}
	}

	@Inject(method = "removePassenger", at = @At("TAIL"))
	protected void removePassenger(Entity passenger, CallbackInfo ci) {
		if ((Object)this instanceof HorseRiderAccess dh && passenger instanceof PlayerEntity player) {
			dh.setPlayer(null);
		}
	}
}
