package com.raggle.half_dream.common.registry;

import com.mojang.serialization.Codec;
import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.common.world.feature.tree.DreamTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class HDStructureRegistry {

	public static final TrunkPlacerType<DreamTrunkPlacer> DREAM_TRUNK_PLACER = register("dream_trunk_placer", DreamTrunkPlacer.CODEC);;

	public static void init() {
		 
	}
	
	private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String id, Codec<P> codec) {
		return Registry.register(Registries.TRUNK_PLACER_TYPE, new Identifier(HalfDream.MOD_ID, id), new TrunkPlacerType<>(codec));
	}
}
