package com.raggle.half_dream.common.world.feature;

import com.raggle.half_dream.HalfDream;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class HDConfiguredFeatures {
	
	public static final RegistryKey<ConfiguredFeature<?, ?>> DREAM_KEY = registerKey("dream_tree");

	public static void init() {
		
	}
	
	public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(HalfDream.MOD_ID, name));
    }
}
