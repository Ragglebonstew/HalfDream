package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.client.sequence.SequenceManager;

import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements DreamClientPlayer {

	public int listSize;
	
	@Override
	public boolean isDream() {
		return SequenceManager.isCurrentSequenceImportant() ? SequenceManager.getSequence().getDreamState() : getPersistantData().getBoolean("half_dream");
	}
	
	@Override
	public void setListSize(int i) {
		listSize = i;
	}
	@Override
	public int getListSize() {
		return listSize;
	}
	@Override
	public void incrementList() {
		listSize++;
	}
	@Override
	public void decrementList() {
		listSize--;
	}

}
