package com.raggle.half_dream.util;

import com.mojang.blaze3d.shader.FogShape;

import net.minecraft.client.render.BackgroundRenderer;

public class HDFogParameters {
	public final BackgroundRenderer.FogType fogType;
	public float red;
	public float green;
	public float blue;
	public float alpha;
	public float fogStart;
	public float fogEnd;
	public FogShape shape = FogShape.SPHERE;

	public HDFogParameters(BackgroundRenderer.FogType fogType) {
		this.fogType = fogType;
	}
}
