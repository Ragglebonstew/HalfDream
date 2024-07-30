package com.raggle.half_dream.networking;

import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions;

import com.raggle.half_dream.api.DreamServerPlayer;
import com.raggle.half_dream.common.block.DreamBed;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

public class HDC2S {

	public static void receiveSendToDeepDream(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		server.execute(() -> {
			QuiltDimensions.teleport(player, server.getWorld(DreamBed.DEEP_DREAM), new TeleportTarget(player.getPos(), new Vec3d(0,0,0), 0, 0));
		});
	}
	public static void onLoadClient(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		if(player instanceof DreamServerPlayer dp && dp.isDream()) {
			server.execute(() -> dp.syncDream());
		}
	}
	
}
