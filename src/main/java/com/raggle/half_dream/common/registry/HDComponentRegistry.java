package com.raggle.half_dream.common.registry;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.api.DreamlessComponent;
import com.raggle.half_dream.common.component.DreamlessChunkComponent;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import net.minecraft.util.Identifier;

public class HDComponentRegistry implements ChunkComponentInitializer {

	public static final ComponentKey<DreamlessComponent> DREAMLESS = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(HalfDream.MOD_ID, "dreamless"), DreamlessComponent.class);
	
	public static void init() {
		
	}

	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(DREAMLESS, DreamlessChunkComponent::new);
	}
	
}
