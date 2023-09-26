package com.raggle.half_dream.networking;

import org.quiltmc.qsl.networking.api.PacketSender;

import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.client.sequence.FallingHalfAsleepSequence;
import com.raggle.half_dream.client.sequence.SequenceManager;
import com.raggle.half_dream.client.sequence.SkeletonCircleFogEffect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class HDS2C {

	public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		DreamClientPlayer player = ((DreamClientPlayer)client.player);
		boolean startDream = player.isDream();
		boolean endDream = buf.readBoolean();
		client.execute(() -> {
			SequenceManager.start(new FallingHalfAsleepSequence(player, startDream, endDream));
			player.setDream(endDream);
		});
	}
	
	public static void recieveListSize(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		DreamClientPlayer player = ((DreamClientPlayer)client.player);
		int size = buf.readInt();
		player.setListSize(size);
		if(size >= 4) {
			client.execute(() -> {
				SequenceManager.setFogEffect(new SkeletonCircleFogEffect());
			});
		}
	}
}
