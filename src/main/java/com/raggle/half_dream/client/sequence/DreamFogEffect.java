package com.raggle.half_dream.client.sequence;

import com.raggle.half_dream.util.HDUtil;

import net.minecraft.client.MinecraftClient;

public class DreamFogEffect extends FogEffect{
	@Override
	public void tick(MinecraftClient client) {
		if(!HDUtil.isDream(client.player))
			this.finished = true;
	}
}
