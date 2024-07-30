package com.raggle.half_dream.common.component;

import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.api.DreamEntityComponent;
import com.raggle.half_dream.api.DreamPlayer;
import com.raggle.half_dream.client.sequence.FallingHalfAsleepSequence;
import com.raggle.half_dream.client.sequence.SequenceManager;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class DreamEntityComponentP implements DreamEntityComponent, AutoSyncedComponent {
	
	private final DreamPlayer dreamPlayer;
	
	public DreamEntityComponentP(PlayerEntity player) {
		this.dreamPlayer = (DreamPlayer)player;
	}
	@Override
	public void setDream(boolean b) {
		this.dreamPlayer.setDream(b);
	}
	@Override
	public boolean isDream() {
		return this.dreamPlayer.isDream();
	}
	@Override
	public void applySyncPacket(PacketByteBuf buf) {
		NbtCompound tag = buf.readNbt();
        if (tag != null) {
    		SequenceManager.start(new FallingHalfAsleepSequence((DreamClientPlayer) dreamPlayer, dreamPlayer.isDream(), tag.getBoolean("dream")));
            this.readFromNbt(tag);
        }
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		this.dreamPlayer.setDream(tag.getBoolean("dream"));
		this.dreamPlayer.setDream(tag.getByte("dream_level"));
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putBoolean("dream", this.dreamPlayer.isDream());
		tag.putByte("dream_level", this.dreamPlayer.getDream());
	}
	@Override
	public byte getDream() {
		return this.dreamPlayer.getDream();
	}
	@Override
	public void setDream(byte b) {
		this.dreamPlayer.setDream(b);
	}
	@Override
	public NbtCompound getPersistantData() {
		return this.dreamPlayer.getPersistantData();
	}

}
