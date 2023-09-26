package com.raggle.half_dream.mixin;

import java.util.ArrayList;

import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import com.raggle.half_dream.api.DreamServerPlayer;
import com.raggle.half_dream.common.entity.HDSkeleton;
import com.raggle.half_dream.networking.HDMessaging;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements DreamServerPlayer {

	private final ArrayList<HDSkeleton> skeletonList = new ArrayList<HDSkeleton>();

	@Override
	public void syncDream() {
		ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBoolean(isDream());
		ServerPlayNetworking.send(player, HDMessaging.DREAM_SYNC, buf);
		//play sound effect
		player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_CONDUIT_AMBIENT, SoundCategory.BLOCKS);
	}
	
	@Override
	public void setDream(boolean b) {
		getPersistantData().putBoolean("half_dream", b);
		syncDream();
	}
	
	@Override
	public void addToList(HDSkeleton skeleton) {
		if(!skeletonList.contains(skeleton)) {
			skeletonList.add(skeleton);
			syncList();
		}
	}
	@Override
	public void removeFromList(HDSkeleton skeleton) {
		if(!skeletonList.contains(skeleton)) {
			skeletonList.remove(skeleton);
			syncList();
		}
	}

	@Override
	public ArrayList<HDSkeleton> getList(){
		return skeletonList;
	}

	
	private void syncList() {
		ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeInt(skeletonList.size());
		ServerPlayNetworking.send(player, HDMessaging.SKELETON_LIST_SIZE, buf);
	}
 

}
