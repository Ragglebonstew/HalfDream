package com.raggle.half_dream.common.registry;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.api.DreamComponent;
import com.raggle.half_dream.api.DreamEntity;
import com.raggle.half_dream.common.component.DreamChunkComponent;
import com.raggle.half_dream.common.component.DreamEntityComponent;
import com.raggle.half_dream.common.component.DisturbedChunkComponent;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class HDComponentRegistry implements ChunkComponentInitializer, EntityComponentInitializer {

	public static final ComponentKey<DreamComponent> DREAM_CHUNK = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(HalfDream.MOD_ID, "dream_chunk"), DreamComponent.class);
	public static final ComponentKey<DreamComponent> DISTURBED_CHUNK = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(HalfDream.MOD_ID, "dreamless_chunk"), DreamComponent.class);
	public static final ComponentKey<DreamEntity> DREAM_ENTITY = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(HalfDream.MOD_ID, "dream_entity"), DreamEntity.class);

	public static void init() {
		
	}

	@Override
	public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
		registry.register(DREAM_CHUNK, DreamChunkComponent::new);
		registry.register(DISTURBED_CHUNK, DisturbedChunkComponent::new);
	}
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(Entity.class, DREAM_ENTITY, DreamEntityComponent::new);
	}
}
