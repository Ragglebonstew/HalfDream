package com.raggle.half_dream.networking;

import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import com.raggle.half_dream.HalfDream;

import net.minecraft.util.Identifier;

public class HDMessaging {
	
	public static final Identifier DREAM_SYNC = new Identifier(HalfDream.MOD_ID, "half_dream");
	public static final Identifier SKELETON_LIST_SIZE = new Identifier(HalfDream.MOD_ID, "skeleton_list_size");
	public static final Identifier DEEP_DREAM = new Identifier(HalfDream.MOD_ID, "deep_dream");
	public static final Identifier ON_LOAD_CLIENT = new Identifier(HalfDream.MOD_ID, "on_load_client");
	
	public static void init() {
		registerS2CPackets();
		registerC2SPackets();
	}
	private static void registerS2CPackets() {
		ClientPlayNetworking.registerGlobalReceiver(DREAM_SYNC, HDS2C::receive);
		ClientPlayNetworking.registerGlobalReceiver(SKELETON_LIST_SIZE, HDS2C::recieveListSize);
	}
	private static void registerC2SPackets() {
		ServerPlayNetworking.registerGlobalReceiver(DEEP_DREAM, HDC2S::receiveSendToDeepDream);
		ServerPlayNetworking.registerGlobalReceiver(ON_LOAD_CLIENT, HDC2S::onLoadClient);
	}

}
