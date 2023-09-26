package com.raggle.half_dream.api;

import net.minecraft.entity.player.PlayerEntity;

public interface DreamHorse extends DreamEntity{

	public void setPlayer(PlayerEntity player);
	
	public PlayerEntity getPlayer();
}
