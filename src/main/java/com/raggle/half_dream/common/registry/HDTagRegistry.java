package com.raggle.half_dream.common.registry;

import com.raggle.half_dream.HalfDream;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class HDTagRegistry {
	  public static final TagKey<Block> DREAMING_BLOCKS = TagKey.of(RegistryKeys.BLOCK, new Identifier(HalfDream.MOD_ID, "dreaming_blocks"));
	  public static final TagKey<Item> DREAMING_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(HalfDream.MOD_ID, "dreaming_items"));

	  public static void init() {}
}
