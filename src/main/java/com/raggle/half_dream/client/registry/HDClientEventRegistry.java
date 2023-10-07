package com.raggle.half_dream.client.registry;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import com.raggle.half_dream.client.sequence.SequenceManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@ClientOnly
public class HDClientEventRegistry {

	public static void init() {
		//ClientEntityLoadEvents.AFTER_LOAD.register(HDClientEventRegistry::onLoadClient);
		ClientTickEvents.START.register(SequenceManager::tick);
		HudRenderCallback.EVENT.register(SequenceManager::render);
		//ClientTickEvents.START.register(HDEventRegistry::clientTick);
	}
	/*
	private static void onLoadClient(Entity entity, ClientWorld world) {
		if(entity instanceof DreamPlayer de) {
			PacketByteBuf buf = PacketByteBufs.create();
			ClientPlayNetworking.send(HDMessaging.ON_LOAD_CLIENT, buf);
		}
	}
	*/
}
