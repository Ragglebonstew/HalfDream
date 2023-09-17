package com.raggle.half_dream.common.block;

import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import com.raggle.half_dream.common.world.feature.tree.DreamSaplingGenerator;

import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;

public class DreamSaplingBlock extends SaplingBlock {

	public DreamSaplingBlock() {
		super(new DreamSaplingGenerator(), QuiltBlockSettings.copyOf(Blocks.OAK_SAPLING));
	}

}
