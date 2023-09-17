package com.raggle.half_dream.common.world.feature.tree;

import com.raggle.half_dream.common.world.feature.HDConfiguredFeatures;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class DreamSaplingGenerator extends SaplingGenerator {

	@Override
	protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
		return HDConfiguredFeatures.DREAM_KEY;
	}

}
