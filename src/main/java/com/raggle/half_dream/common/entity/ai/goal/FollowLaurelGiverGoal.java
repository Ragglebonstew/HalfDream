package com.raggle.half_dream.common.entity.ai.goal;

import com.raggle.half_dream.common.entity.HDSkeleton;

import net.minecraft.entity.ai.goal.Goal;

public class FollowLaurelGiverGoal extends Goal {
	
	private final HDSkeleton skeleton;
	private final double speed;
	
	private static final int MAX_TIME = 800;
	private int followTime;
	
	public FollowLaurelGiverGoal(HDSkeleton skeleton) {
		this.skeleton = skeleton;
		this.speed = 1.0;
	}

	@Override
	public boolean canStart() {
		return skeleton.getFollowing() != null;
	}
	@Override
	public boolean shouldContinue() {
		return followTime < MAX_TIME 
				&& skeleton.getFollowing() != null
				&& skeleton.getFollowing().isAlive()
				&& skeleton.getFollowingAsDream().isDream();
	}
	@Override
	public void start() {
		this.followTime = 0;
	}
	@Override
	public void stop() {
		this.skeleton.removeFromList();;
		this.followTime = 0;
	}
	@Override
	public void tick() {
		skeleton.getNavigation().startMovingTo(skeleton.getFollowing(), speed);
		followTime++;
	}

}
