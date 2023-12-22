package com.raggle.half_dream.networking;

import com.raggle.half_dream.HalfDream;

import net.minecraft.util.Identifier;

public class HDMessaging {
	
	public static final Identifier DREAM_SYNC = new Identifier(HalfDream.MOD_ID, "half_dream");
	public static final Identifier SKELETON_LIST_SIZE = new Identifier(HalfDream.MOD_ID, "skeleton_list_size");
	public static final Identifier DEEP_DREAM = new Identifier(HalfDream.MOD_ID, "deep_dream");
	public static final Identifier ON_LOAD_CLIENT = new Identifier(HalfDream.MOD_ID, "on_load_client");

	private static void registerS2CPackets() {

	}
	private static void registerC2SPackets() {

	}
	public static void init() {
		registerS2CPackets();
		registerC2SPackets();
	}

}
