package com.raggle.half_dream.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.util.HDUtil;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity{

	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}
	
	@Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
	public void setTarget(@Nullable LivingEntity target, CallbackInfo ci) {
		if(target != null && HDUtil.isDream(target) != HDUtil.isDream(this)) {
			ci.cancel();
		}
	}
	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public final void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if(HDUtil.isDream(player) != HDUtil.isDream(this) && HDUtil.getDream(this) != 2 && !((Object)this instanceof SkeletonHorseEntity))
			cir.setReturnValue(ActionResult.FAIL);
	}

}
