package com.raggle.half_dream.common.block;

import org.jetbrains.annotations.Nullable;
import com.raggle.half_dream.common.registry.HDEntityRegistry;
import com.raggle.half_dream.util.HDUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DreamBlock extends Block {
	
	public DreamBlock(Settings settings) {
		super(settings
				.nonOpaque()
				.mapColor(MapColor.NONE)
				.allowsSpawning(DreamBlock::onlyDreamEntity)
				);
	}
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if(placer instanceof ServerPlayerEntity player) {
			HDUtil.setDream(pos, HDUtil.getDream(player) == 1, world);
		}
		else {
			HDUtil.setDream(pos, true, world);
		}
	}
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		HDUtil.setDream(pos, true, world);
	}
	private static boolean onlyDreamEntity(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == HDEntityRegistry.HDSKELETON;
	}
	
	
}
