package com.raggle.half_dream.common.registry;

import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.common.block.DreamBlock;
import com.raggle.half_dream.common.block.DreamSaplingBlock;
import com.raggle.half_dream.common.block.SheepLaurelBush;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HDBlockRegistry {
	
	public static final DreamBlock DREAM_LOG = new DreamBlock(QuiltBlockSettings.copyOf(Blocks.OAK_LOG));
	public static final DreamBlock DREAM_WOOD = new DreamBlock(QuiltBlockSettings.copyOf(Blocks.OAK_LOG));
	public static final DreamBlock STRIPPED_DREAM_LOG = new DreamBlock(QuiltBlockSettings.copyOf(Blocks.OAK_LOG));
	public static final DreamBlock STRIPPED_DREAM_WOOD = new DreamBlock(QuiltBlockSettings.copyOf(Blocks.OAK_LOG));
	public static final DreamBlock DREAM_LEAVES = new DreamBlock(QuiltBlockSettings.copyOf(Blocks.OAK_LEAVES));
	public static final DreamSaplingBlock DREAM_SAPLING = new DreamSaplingBlock();
	public static final SheepLaurelBush SHEEP_LAUREL_BUSH = new SheepLaurelBush();
	
	public static void init() {
		
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "sheep_laurel_bush"), SHEEP_LAUREL_BUSH);
		
		//dream wood stuff
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_log"), DREAM_LOG);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_wood"), DREAM_WOOD);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "stripped_dream_log"), STRIPPED_DREAM_LOG);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "stripped_dream_wood"), STRIPPED_DREAM_WOOD);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_leaves"), DREAM_LEAVES);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_sapling"), DREAM_SAPLING);
		
		
	}

}
