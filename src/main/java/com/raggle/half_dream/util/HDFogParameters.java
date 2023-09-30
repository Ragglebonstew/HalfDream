package com.raggle.half_dream.util;

import com.mojang.blaze3d.shader.FogShape;

public class HDFogParameters {
	public float red;
	public float green;
	public float blue;
	public float alpha;
	public float fogStart;
	public float fogEnd;
	public FogShape shape = FogShape.SPHERE;

	public HDFogParameters() {
	}
}
