package com.raggle.half_dream.client.sequence;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;

@ClientOnly
public class DreamSequence {
	
	protected static MinecraftClient client = MinecraftClient.getInstance();

	protected int ticks;
	protected int totalLength = 60;
	protected boolean finished;
	protected boolean cancelled;
	
	public boolean isSequenceImportant() {
		return false;
	}
	public boolean getDreamState() {
		return false;
	}
	public void start() {}
	public void stop() {}
	public void cancel() {
		this.cancelled = true;
	}
	
	public void tick() {}
	public void render(GuiGraphics drawContext, float tickDelta) {}
	
}
