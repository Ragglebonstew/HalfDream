package com.raggle.half_dream.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.entity.mob.AbstractSkeletonEntity;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import com.raggle.half_dream.api.DreamPlayer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.StrayEntityRenderer;

@ClientOnly
public class HDSkeletonRenderer extends StrayEntityRenderer{

	public HDSkeletonRenderer(Context context) {
		super(context);
	}
	
	@Override
	public boolean shouldRender(AbstractSkeletonEntity entity, Frustum frustum, double x, double y, double z) {
		MinecraftClient mc = MinecraftClient.getInstance();
		return super.shouldRender(entity, frustum, x, y, z) && (mc.player instanceof DreamPlayer dp && dp.isDream());
	}

}
