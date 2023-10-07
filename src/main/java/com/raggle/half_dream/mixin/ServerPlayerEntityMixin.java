package com.raggle.half_dream.mixin;

import java.util.ArrayList;

import org.spongepowered.asm.mixin.Mixin;
import com.raggle.half_dream.api.FollowerTracker;
import com.raggle.half_dream.common.entity.HDSkeleton;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements FollowerTracker<HDSkeleton> {

	private final ArrayList<HDSkeleton> skeletonList = new ArrayList<HDSkeleton>();

	@Override
	public boolean hasEnough(int target) {
		return this.skeletonList.size() >= target;
	}
	@Override
	public void addToList(HDSkeleton skeleton) {
		if(!skeletonList.contains(skeleton)) {
			skeletonList.add(skeleton);
		}
	}
	@Override
	public void removeFromList(HDSkeleton skeleton) {
		if(!skeletonList.contains(skeleton)) {
			skeletonList.remove(skeleton);
		}
	}
	@Override
	public ArrayList<HDSkeleton> getList() {
		return this.skeletonList;
	}
 

}
