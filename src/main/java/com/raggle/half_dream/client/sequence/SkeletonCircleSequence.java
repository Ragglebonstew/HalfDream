package com.raggle.half_dream.client.sequence;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import com.raggle.half_dream.api.HDFogParameters;
import com.raggle.half_dream.networking.HDMessaging;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.network.PacketByteBuf;

@ClientOnly
public class SkeletonCircleSequence extends DreamSequence{
	
	//initial conditions reflect color of default dream fog
	public float red = 0.423F;
	public float green = 0.634F;
	public float blue = 0.785F;
	public float fogEnd = 24.0F;
	
	public SkeletonCircleSequence() {
		this.ticks = 0;
		this.totalLength = 1200;
	}
	
	@Override
	public void tick() {
		ticks++;
		
		if(!this.cancelled) {
			this.progress++;
		}
		else {
			this.progress--;
		}
		
		if (progress > totalLength + 244) {
			finished = true;
			PacketByteBuf buf = PacketByteBufs.create();
			ClientPlayNetworking.send(HDMessaging.DEEP_DREAM, buf);
		}
		else if(progress < 0) {
			finished = true;
		}
		
	}
	
	@Override
	public void render(GuiGraphics drawContext, float tickDelta) {
		
		if(progress < totalLength/2) {
			setRedColors();
		}
		else if(progress < totalLength) {
			setGBColors();
		}
		else if(progress > totalLength + 120) {
			setBlackColors();
		}
		
		
	}
	private void setRedColors() {
		
		float scale = 1 - progress*2.0F/totalLength;
		
		red =  0.785F - ((0.785F - 0.423F) * scale);
	
	}
	private void setGBColors() {

		float scale = 2 - progress*2.0F/totalLength;
		
		green = 0.634F * scale;
		blue = 0.785F * scale;
		fogEnd = 12.0F * scale + 12.0F; 
		
	}
	private void setBlackColors() {

		float scale;
		if(progress < totalLength + 240) {
			scale = 1 - ((float)progress - totalLength - 120)/(120);
		}
		else {
			scale = 0;
		}
		red =  0.785F * scale;
		fogEnd = 22.0F * scale + 2.0F; 
	
	}

	public void applyFogAffects(HDFogParameters fogParameters) {
		float f = 5.0F;
		if (fogParameters.fogType == BackgroundRenderer.FogType.FOG_SKY) {
			fogParameters.fogStart = 0.0F;
		} else {
			fogParameters.fogStart = f * 0.25F;
		}
		fogParameters.red = red;
		fogParameters.green = green;
		fogParameters.blue = blue;
		fogParameters.fogEnd = fogEnd;
	}
}
