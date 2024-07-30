package com.raggle.half_dream.client.registry;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.entity.event.api.client.ClientEntityLoadEvents;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import com.raggle.half_dream.api.DreamPlayer;
import com.raggle.half_dream.client.sequence.SequenceManager;
import com.raggle.half_dream.networking.HDMessaging;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;

@ClientOnly
public class HDClientEventRegistry {

	public static void init() {
		//ClientEntityLoadEvents.AFTER_LOAD.register(HDClientEventRegistry::onLoadClient);
		ClientTickEvents.START.register(SequenceManager::tick);
		HudRenderCallback.EVENT.register(SequenceManager::render);
		//ClientTickEvents.START.register(HDEventRegistry::clientTick);
	}
	private static void onLoadClient(Entity entity, ClientWorld world) {
		if(entity instanceof DreamPlayer de) {
			PacketByteBuf buf = PacketByteBufs.create();
			ClientPlayNetworking.send(HDMessaging.ON_LOAD_CLIENT, buf);
		}
	}
}
