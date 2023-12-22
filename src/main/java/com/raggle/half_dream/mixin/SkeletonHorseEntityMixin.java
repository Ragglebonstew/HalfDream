package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.raggle.half_dream.api.HorseRiderAccess;
import com.raggle.half_dream.common.entity.ai.goal.CrossRiverGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(SkeletonHorseEntity.class)
public abstract class SkeletonHorseEntityMixin extends HorseBaseEntity implements HorseRiderAccess {

	public PlayerEntity mountedPlayer;
	
	protected SkeletonHorseEntityMixin(EntityType<? extends HorseBaseEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Inject(method = "initCustomGoals", at = @At("TAIL"))
	protected void initCustomGoals(CallbackInfo ci) {
		this.goalSelector.add(1, new CrossRiverGoal((SkeletonHorseEntity)(Object)this));
	}

	@Override
	public PlayerEntity getPlayer() {
		return this.mountedPlayer;
	}
	@Override
	public void setPlayer(PlayerEntity player) {
		this.mountedPlayer = player;
	}


	
}
