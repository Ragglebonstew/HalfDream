package com.raggle.half_dream.client.sequence;

import com.raggle.half_dream.api.DreamClientPlayer;

import net.minecraft.client.MinecraftClient;

public class DreamFogEffect extends FogEffect{
	@Override
	public void tick(MinecraftClient client) {
		if(client.player instanceof DreamClientPlayer dcp && !dcp.isDream())
			this.finished = true;
	}
}
