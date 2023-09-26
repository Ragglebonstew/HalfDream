package com.raggle.half_dream.client.sequence;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import com.raggle.half_dream.util.HDFogParameters;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;

@ClientOnly
public abstract class FogEffect {
	
	public static FogEffect DREAM_FOG = new DreamFogEffect();

	protected boolean finished;
	protected boolean cancelled;
	protected byte stage;
	
	//initial conditions reflect color of default dream fog. Recommended to initialize this
	public float red = 0.423F;
	public float green = 0.634F;
	public float blue = 0.785F;
	public float alpha = 1.0F;
	public float fogStart = 1.0F;
	public float fogEnd = 24.0F;

	public void start() {
		this.finished = false;
		this.cancelled = false;
		this.stage = 0;
	}
	public void stop() {}
	public void tick(MinecraftClient client) {}
	public void cancel() {
		this.cancelled = true;
	}
	public void setStage(byte b) {
		this.stage = b;
	}
	
	public void applyFogAffects(HDFogParameters fogParameters) {
		float f = 5.0F;
		if (fogParameters.fogType == BackgroundRenderer.FogType.FOG_SKY) {
			fogParameters.fogStart = 0.0F;
		} else {
			fogParameters.fogStart = f * 0.25F;
		}
		fogParameters.red = this.red;
		fogParameters.green = this.green;
		fogParameters.blue = this.blue;
		fogParameters.alpha = this.alpha;
		fogParameters.fogStart = this.fogStart;
		fogParameters.fogEnd = this.fogEnd;
	}
		
}
