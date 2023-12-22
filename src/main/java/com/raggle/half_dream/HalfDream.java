package com.raggle.half_dream;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raggle.half_dream.common.registry.HDBlockRegistry;
import com.raggle.half_dream.common.registry.HDComponentRegistry;
import com.raggle.half_dream.common.registry.HDEntityRegistry;
import com.raggle.half_dream.common.registry.HDEventRegistry;
import com.raggle.half_dream.common.registry.HDItemRegistry;
import com.raggle.half_dream.common.registry.HDTagRegistry;
import com.raggle.half_dream.common.world.feature.HDConfiguredFeatures;
import com.raggle.half_dream.common.world.feature.HDPlacedFeatures;

public class HalfDream implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Half Dream");
	
	public static final String MOD_ID = "half_dream";

	@Override
	public void onInitialize(ModContainer mod) {
		
		HDBlockRegistry.init();
		HDComponentRegistry.init();
		HDEntityRegistry.init();
		HDEventRegistry.init();
		HDItemRegistry.init();
		HDTagRegistry.init();
		
		HDConfiguredFeatures.init();
		HDPlacedFeatures.init();
		
	}
}
/*
bug list (things that are broke)
- water flow gets wack w/ dream blocks
- broken pathfinding
	- entities jump while passing through dream blocks
	- dream mobs get stuck in dreamless blocks
- entities make sounds despite dream state
- can attack entities despite dream state
- entities can push player in dream state, and probably the other way around
- skybox renders w/ sodium
- rain renders in dream state


check list (things that may/may not be broke anymore)
- empty!


ideas (things that aren't in atm)
- only travel into dream world during night. If don't leave by sunrise, semi-trapped
- Potion of Half Sleeping - since the old one was deleted. Should only toggle state, not use StatusEffect
- interloper portal lures players into dream world
- dream horn echolocates interloper portals, giving players access to skeleton horses
*/