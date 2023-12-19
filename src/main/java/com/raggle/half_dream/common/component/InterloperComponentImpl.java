package com.raggle.half_dream.common.component;

import com.raggle.half_dream.api.InterloperComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class InterloperComponentImpl implements InterloperComponent, AutoSyncedComponent {
	
	@SuppressWarnings("unused")
	private final PlayerEntity player;
	
	private boolean ensnared;
	
	public InterloperComponentImpl(PlayerEntity player) {
		this.player = player;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		tag.putBoolean("interloper", ensnared);
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		this.ensnared = tag.getBoolean("interloper");
	}

	@Override
	public boolean ensnared() {
		return ensnared;
	}

	@Override
	public void setEnsnared(boolean b) {
		this.ensnared = b;
	}

}
