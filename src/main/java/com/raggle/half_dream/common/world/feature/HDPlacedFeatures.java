package com.raggle.half_dream.common.world.feature;

import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

import com.raggle.half_dream.HalfDream;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class HDPlacedFeatures {

	public static final RegistryKey<PlacedFeature> DREAM_PLACED_KEY = registerKey("dream_tree_placed");
	
	public static void init() {
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.SNOWY_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION, DREAM_PLACED_KEY);
	}
	
	public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(HalfDream.MOD_ID, name));
    }
	
}
