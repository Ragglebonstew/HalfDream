package com.raggle.half_dream.client.sequence;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;

public class SequenceManager {

	private static DreamSequence dreamSequence;

	public static void tick(MinecraftClient client) {
		if (hasSequence() && client.player != null && !client.isPaused()) {
			dreamSequence.tick();
			if(dreamSequence.finished)
				dreamSequence = null;
		}
	}
	public static void render(GuiGraphics drawContext, float tickDelta) {
		if(hasSequence())
			dreamSequence.render(drawContext, tickDelta);
	}
	public static void start(DreamSequence newSequence) {
		if(!hasSequence()) {
			dreamSequence = newSequence;
		}
		//if dream status changes mid-animation
		else if(newSequence instanceof FallingHalfAsleepSequence newFhas) {
			if(dreamSequence instanceof FallingHalfAsleepSequence oldFhas) {
				if(!oldFhas.hasTransitioned()) {
					oldFhas.setEndDream(newFhas.getEndDream());
				}
				else {
					newFhas.setStartDream(oldFhas.getDreamState());
					dreamSequence = newSequence;
				}
			}
			else {
				dreamSequence = newSequence;
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
	public static boolean getSequenceDreamState() {
		//only call if the sequence is important [ isCurrentSequenceImportant() ]
		return dreamSequence.getDreamState();
	}
	public static boolean isCurrentSequenceImportant() {
		if(!hasSequence()) {
			return false;
		}
		return dreamSequence.isSequenceImportant();
	}
	
}
