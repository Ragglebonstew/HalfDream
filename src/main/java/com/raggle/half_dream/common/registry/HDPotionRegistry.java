package com.raggle.half_dream.common.registry;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.mixin.BrewingRecipeRegistryMixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HDPotionRegistry {

    public static final Potion HALF_ASLEEP_POTION =
            Registry.register(Registries.POTION, new Identifier(HalfDream.MOD_ID, "half_asleep_potion"),
                new Potion(new StatusEffectInstance(HDStatusEffectRegistry.HALF_ASLEEP, 3600, 0)));
	
	public static void init() {
		
		BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, HDItemRegistry.SHEEP_LAUREL, HALF_ASLEEP_POTION);

	}
	
}
