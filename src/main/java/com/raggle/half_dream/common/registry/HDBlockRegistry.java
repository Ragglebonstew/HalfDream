package com.raggle.half_dream.common.registry;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.common.block.DreamBed;
import com.raggle.half_dream.common.block.DreamingBlock;
import com.raggle.half_dream.common.block.DreamReturn;
import com.raggle.half_dream.common.block.DreamSaplingBlock;
import com.raggle.half_dream.common.block.SheepLaurelBush;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HDBlockRegistry {
	
	public static final DreamingBlock DREAM_BLOCK = new DreamingBlock();
	public static final DreamingBlock DREAM_LOG = new DreamingBlock();
	public static final DreamingBlock DREAM_LEAVES = new DreamingBlock();
	public static final DreamSaplingBlock DREAM_SAPLING = new DreamSaplingBlock();
	public static final DreamBed DREAM_BED = new DreamBed();
	public static final DreamReturn DREAM_RETURN = new DreamReturn();
	public static final SheepLaurelBush SHEEP_LAUREL_BUSH = new SheepLaurelBush();
	
	public static void init() {
		
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_block"), DREAM_BLOCK);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_log"), DREAM_LOG);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_leaves"), DREAM_LEAVES);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_sapling"), DREAM_SAPLING);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_bed"), DREAM_BED);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "dream_return"), DREAM_RETURN);
		Registry.register(Registries.BLOCK, new Identifier(HalfDream.MOD_ID, "sheep_laurel_bush"), SHEEP_LAUREL_BUSH);
		
	}

}
