package com.raggle.half_dream.common.entity.ai.goal;

import java.util.ArrayList;

import com.raggle.half_dream.api.DreamServerPlayer;
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
		if(skeleton.getFollowing() instanceof DreamServerPlayer dsp && dsp != null) {
			ArrayList<HDSkeleton> tempList = dsp.getList();
			return tempList != null 
					&& tempList.size() >= 4 
					&& tempList.contains(skeleton);
		}
		return false;
	}
	@Override
	public boolean shouldContinue() {
		DreamServerPlayer player = skeleton.getFollowingAsDream();
		return player != null
				&& player.getList().contains(skeleton)
				&& player.getList().size() >= 4 
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
		DreamServerPlayer dsp = skeleton.getFollowingAsDream();
		int posInList = dsp.getList().indexOf(skeleton);
		int listSize = dsp.getList().size();
		
		double anglePos = 2 * Math.PI * posInList / listSize;
		
		double x = Math.cos(anglePos)*distance + player.getX();
		double z = Math.sin(anglePos)*distance + player.getZ();
		
		skeleton.getNavigation().startMovingTo(x, player.getY(), z, speed);
		skeleton.getLookControl().lookAt(player);
	}

}
