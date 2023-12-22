package com.raggle.half_dream.common.entity;

import com.raggle.half_dream.api.FollowerTracker;
import com.raggle.half_dream.common.entity.ai.goal.FollowLaurelGiverGoal;
import com.raggle.half_dream.common.entity.ai.goal.FormCircleGoal;
import com.raggle.half_dream.common.registry.HDItemRegistry;
import com.raggle.half_dream.util.HDUtil;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class HDSkeleton extends StrayEntity {
	
	private ServerPlayerEntity following;

	public HDSkeleton(EntityType<? extends StrayEntity> entityType, World world) {
		super(entityType, world);
		this.setPathfindingPenalty(PathNodeType.DOOR_OPEN, -1.0F);
		
		HDUtil.setDream(this, (byte) 1);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.goalSelector.add(4, new FollowLaurelGiverGoal(this));
		this.goalSelector.add(2, new FormCircleGoal(this));
		this.targetSelector.add(1, new RevengeGoal(this));
		//this.targetSelector.add(2, new TargetGoal(this, PlayerEntity.class, true));
		//this.targetSelector.add(3, new TargetGoal(this, IronGolemEntity.class, true));
	}
	
	@Override
	public void tick() {
		super.tick();
		if(HDUtil.getDream(this.getTarget()) == 0) {
			this.setTarget(null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if(itemStack.isOf(HDItemRegistry.SHEEP_LAUREL) && player instanceof @SuppressWarnings("rawtypes") FollowerTracker ft) {
			ft.addToList(this);
			if(player instanceof ServerPlayerEntity serverPlayer) {
				following = serverPlayer;
			}
		}
		return ActionResult.success(!this.getWorld().isClient());
	}
	@Override
	public void onDeath(DamageSource damageSource) {
		this.removeFromList();
		super.onDeath(damageSource);
	}
	
	public ServerPlayerEntity getFollowing() {
		return following;
	}
	public ServerPlayerEntity getFollowingAsDream() {
		return following;
	}
	@SuppressWarnings("unchecked")
	public void removeFromList() {
		if(following instanceof @SuppressWarnings("rawtypes") FollowerTracker ft) {
			ft.removeFromList(this);
			following = null;
		}
	}
}
