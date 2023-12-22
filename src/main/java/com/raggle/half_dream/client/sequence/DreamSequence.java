package com.raggle.half_dream.client.sequence;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;

@ClientOnly
public abstract class DreamSequence {
	
	protected static final MinecraftClient client = MinecraftClient.getInstance();

	protected int ticks;
	protected int totalLength = 60;
	protected boolean finished;
	protected boolean cancelled;
	
	public boolean hasFogEffect() {
		return false;
	}
	public FogEffect getFogEffect() {
		return null;
	}
	public abstract byte getDreamState();
	public abstract void start();
	public abstract void stop();
	public void cancel() {
		this.cancelled = true;
	}
	
	public abstract void tick();
	public abstract void render(GuiGraphics drawContext, float tickDelta);
	
}
