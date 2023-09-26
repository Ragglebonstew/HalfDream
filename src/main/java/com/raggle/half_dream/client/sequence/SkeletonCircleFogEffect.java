package com.raggle.half_dream.client.sequence;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import com.raggle.half_dream.networking.HDMessaging;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

@ClientOnly
public class SkeletonCircleFogEffect extends FogEffect{

	private int totalLength;
	private int progress;
	
	public SkeletonCircleFogEffect() {
		this.totalLength = 1200;
		
		red = 0.423F;
		green = 0.634F;
		blue = 0.785F;
		fogEnd = 24.0F;
	}
	@Override
	public void tick(MinecraftClient client) {
		
		if(!this.cancelled) {
			this.progress++;
		}
		else {
			this.progress--;
		}
		
		if (progress > totalLength + 244) {
			finished = true;
		}
		else if(progress < 0) {
			finished = true;
		}
		//set colors
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
	@Override
	public void stop() {
		PacketByteBuf buf = PacketByteBufs.create();
		ClientPlayNetworking.send(HDMessaging.DEEP_DREAM, buf);
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

}
