package com.raggle.half_dream.common.entity.ai.goal;

import com.raggle.half_dream.api.FollowerTracker;
import com.raggle.half_dream.common.entity.HDSkeleton;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.network.ServerPlayerEntity;

public class FormCircleGoal extends Goal{
	
	private final HDSkeleton skeleton;
	private final double speed;
	private final double distance;

	public FormCircleGoal(HDSkeleton hdSkeleton) {
		this.skeleton = hdSkeleton;
		this.speed = 1.0;
		this.distance = 3.0;
	}

	@Override
	public boolean canStart() {
		if(skeleton.getFollowing() instanceof FollowerTracker ft) {
			return ft.hasEnough(4) && ft.getList().contains(skeleton);
		}
		return false;
	}
	@Override
	public boolean shouldContinue() {
		return skeleton.getFollowing() instanceof FollowerTracker ft
				&& ft.getList().contains(skeleton)
				&& ft.hasEnough(4)
				&& skeleton.getFollowing().isAlive();
	}
	@Override
	public void start() {}
	@Override
	public void stop() {
		this.skeleton.removeFromList();;
	}
	@Override
	public void tick() {
		ServerPlayerEntity player = skeleton.getFollowing();
		if(player instanceof FollowerTracker ft) {
			int posInList = ft.getList().indexOf(skeleton);
			int listSize = ft.getList().size();
			
			double anglePos = 2 * Math.PI * posInList / listSize;
			
			double x = Math.cos(anglePos)*distance + player.getX();
			double z = Math.sin(anglePos)*distance + player.getZ();
			
			skeleton.getNavigation().startMovingTo(x, player.getY(), z, speed);
			skeleton.getLookControl().lookAt(player);
		}
	}

}
