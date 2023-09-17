package com.raggle.half_dream.common.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.entity.effect.StatusEffect;
import com.raggle.half_dream.common.entity.effect.HalfAsleepStatusEffect;

public class HDStatusEffectRegistry {
	
	public static final StatusEffect HALF_ASLEEP = new HalfAsleepStatusEffect();

	public static void init() {
		
		Registry.register(Registries.STATUS_EFFECT, new Identifier("half_dream", "half_asleep"), HALF_ASLEEP);
				
	}
}
