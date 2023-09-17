package com.raggle.half_dream.common.registry;

import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import com.raggle.half_dream.HalfDream;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HDItemRegistry {

	public static final Item SHEEP_LAUREL = new Item(new QuiltItemSettings());
	public static final BlockItem DREAM_LOG = new BlockItem(HDBlockRegistry.DREAM_LOG, new QuiltItemSettings());
	public static final BlockItem DREAM_LEAVES = new BlockItem(HDBlockRegistry.DREAM_LEAVES, new QuiltItemSettings());
	
	public static void init() {
		
		Registry.register(Registries.ITEM, new Identifier(HalfDream.MOD_ID, "sheep_laurel"), SHEEP_LAUREL);
		Registry.register(Registries.ITEM, new Identifier(HalfDream.MOD_ID, "dream_block"), new BlockItem(HDBlockRegistry.DREAM_BLOCK, new QuiltItemSettings()));
		Registry.register(Registries.ITEM, new Identifier(HalfDream.MOD_ID, "dream_log"), DREAM_LOG);
		Registry.register(Registries.ITEM, new Identifier(HalfDream.MOD_ID, "dream_leaves"), DREAM_LEAVES);
		Registry.register(Registries.ITEM, new Identifier(HalfDream.MOD_ID, "dream_sapling"), new BlockItem(HDBlockRegistry.DREAM_SAPLING, new QuiltItemSettings()));
		
	}
}
