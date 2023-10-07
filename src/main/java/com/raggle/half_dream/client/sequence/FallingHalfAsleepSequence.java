package com.raggle.half_dream.client.sequence;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.util.ColorUtil;

public class FallingHalfAsleepSequence extends DreamSequence {

	private byte endDream;
	private byte startDream;

	public FallingHalfAsleepSequence(byte startDream, byte endDream) {
		this.startDream = startDream;
		this.endDream = endDream;
	}
	@Override
	public void stop() {}

	@Override
	public boolean hasFogEffect() {
		return true;
	}
	@Override
	public FogEffect getFogEffect() {
		if(hasTransitioned() ? endDream == 1 : startDream == 1) {
			return FogEffect.DREAM_FOG;
		}
		else {
			return FogEffect.CLEAR_FOG;
		}
	}
	@Override
	public byte getDreamState() {
		return hasTransitioned() ? endDream : startDream;
	}
	public boolean hasTransitioned() {
		return ticks >= totalLength/3;
	}
	public void setStartDream(byte startDream) {
		this.startDream = startDream;
	}
	public void setEndDream(byte endDream) {
		this.endDream = endDream;
	}
	public byte getStartDream() {
		return startDream;
	}
	public byte getEndDream() {
		return endDream;
	}
	@Override
	public void tick() {
		ticks++;
		
		if(ticks == totalLength/3) {
			client.worldRenderer.reload();
		}
		else if (ticks >= totalLength - 1) {
			finished = true;
		}
	}
	@Override
	public void render(GuiGraphics drawContext, float tickDelta) {

		//render dream fade in and out
		int width = client.getWindow().getScaledWidth();
		int height = client.getWindow().getScaledHeight();
		int backgroundProgress;
		int scaleNum = 255;
		if (ticks < totalLength/3) {
			backgroundProgress = scaleNum*ticks*3/(totalLength);
		} 
		else if(ticks > totalLength*2/3){
			backgroundProgress = (int)(scaleNum*(1 - (ticks*3.0F - totalLength*2)/(totalLength)));
		}
		else {
			backgroundProgress = scaleNum;
		}
		
		drawContext.fill(0, 0, width, height, ColorUtil.ABGR32.getColor(backgroundProgress, 0, 0, 0));
		
	}
	@Override
	public void start() {
		this.ticks = 0;
	}

}
