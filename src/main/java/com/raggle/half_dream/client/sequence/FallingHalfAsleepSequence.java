package com.raggle.half_dream.client.sequence;

import com.raggle.half_dream.api.DreamClientPlayer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.util.ColorUtil;

public class FallingHalfAsleepSequence extends DreamSequence {

	private boolean toDream;
	private boolean startDream;

	public FallingHalfAsleepSequence(DreamClientPlayer player, boolean startDream, boolean toDream) {
		this.startDream = startDream;
		this.toDream = toDream;
		ticks = 0;
	}

	@Override
	public boolean isSequenceImportant() {
		return true;
	}
	@Override
	public boolean getDreamState() {
		return hasTransitioned() ? toDream : startDream;
	}
	public boolean hasTransitioned() {
		return ticks >= totalLength/3;
	}
	public void setStartDream(boolean startDream) {
		this.startDream = startDream;
	}
	public void setEndDream(boolean endDream) {
		this.toDream = endDream;
	}
	public boolean getStartDream() {
		return startDream;
	}
	public boolean getEndDream() {
		return toDream;
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

}
