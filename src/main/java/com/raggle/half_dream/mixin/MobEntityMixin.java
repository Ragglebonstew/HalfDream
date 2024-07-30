package com.raggle.half_dream.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.api.DreamEntityComponent;
import com.raggle.half_dream.api.DreamHorse;
import com.raggle.half_dream.api.DreamPlayer;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin implements DreamEntityComponent {

	@Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
	public void setTarget(@Nullable LivingEntity target, CallbackInfo ci) {
		if(target != null) {
			//cancels attack if 
			if(target instanceof DreamEntityComponent de && de.isDream() != isDream()) {
				ci.cancel();
			}
		}
	}
	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public final void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if(player instanceof DreamPlayer dp && dp.isDream() != this.isDream() && !(this instanceof DreamHorse))
			cir.setReturnValue(ActionResult.FAIL);
	}

}
