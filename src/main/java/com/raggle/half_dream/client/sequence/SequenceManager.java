package com.raggle.half_dream.client.sequence;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import com.raggle.half_dream.util.HDUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;

@ClientOnly
public class SequenceManager {

	private static DreamSequence dreamSequence;
	private static FogEffect fogEffect;

	public static void tick(MinecraftClient client) {
		if(client.isPaused() || client.player == null)
			return;
		
		//fogEffect ticking
		if(fogEffect != null) {
			fogEffect.tick(client);
			if(fogEffect.finished) {
				fogEffect.stop();
				fogEffect = null;
			}
		}
		else if(HDUtil.isDream(client.player)) {
			setFogEffect(FogEffect.DREAM_FOG);
		}
			
		
		//sequence ticking
		if (hasSequence()) {
			dreamSequence.tick();
			if(dreamSequence.finished) {
				dreamSequence.stop();
				dreamSequence = null;
			}
		}
	}
	public static void render(GuiGraphics drawContext, float tickDelta) {
		if(hasSequence())
			dreamSequence.render(drawContext, tickDelta);
	}
	public static void start(DreamSequence newSequence) {
		if(!hasSequence()) {
			dreamSequence = newSequence;
			dreamSequence.start();
		}
		//if dream status changes mid-animation
		else if(newSequence instanceof FallingHalfAsleepSequence newFhas) {
			if(dreamSequence instanceof FallingHalfAsleepSequence oldFhas) {
				if(newFhas.getEndDream() == oldFhas.getEndDream()) {
					return;
				}
				else if(!oldFhas.hasTransitioned()) {
					oldFhas.setEndDream(newFhas.getEndDream());
				}
				else {
					newFhas.setStartDream(oldFhas.getDreamState());
					dreamSequence = newSequence;
				}
			}
			else {
				dreamSequence = newSequence;
				dreamSequence.start();
			}
			/*
//			(toDream != dreamSequence.dreaming) 
			HalfDream.LOGGER.info("Sequence already playing. Changing to different end dream");
			if(dreamSequence.ticks > totalLength/3) {
				dreamSequence.sequenceState = toDream;
				client.worldRenderer.reload();
			}
			else {
				dreamSequence.dreaming = toDream;
			}
			*/
		}
	}
	public static boolean hasSequence() {
		return dreamSequence != null;
	}
	public static DreamSequence getSequence() {
		return dreamSequence;
	}
	public static boolean hasFogEffect() {
		return sequenceHasFogEffect() || fogEffect != null;
	}
	public static void setFogEffect(FogEffect effect) {
		fogEffect = effect;
		fogEffect.start();
	}
	public static FogEffect getFogEffect() {
		return sequenceHasFogEffect() ? dreamSequence.getFogEffect() : fogEffect;
	}
	public static boolean sequenceHasFogEffect() {
		if(!hasSequence()) {
			return false;
		}
		return dreamSequence.hasFogEffect();
	}
	
}
