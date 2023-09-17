package com.raggle.half_dream.common.block;

import com.raggle.half_dream.common.registry.HDItemRegistry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SheepLaurelBush extends SweetBerryBushBlock {

	public SheepLaurelBush() {
		super(AbstractBlock.Settings.create()
				.mapColor(MapColor.PLANT)
				.ticksRandomly()
				.noCollision()
				.sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
				.pistonBehavior(PistonBehavior.DESTROY));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		int i = state.get(AGE);
		boolean bl = i == 3;
		if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
			return ActionResult.PASS;
		} else if (i > 1) {
			int j = 1 + world.random.nextInt(2);
			dropStack(world, pos, new ItemStack(HDItemRegistry.SHEEP_LAUREL, j + (bl ? 1 : 0)));
			world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			BlockState blockState = state.with(AGE, Integer.valueOf(1));
			world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.create(player, blockState));
			return ActionResult.success(world.isClient);
		} else {
			return super.onUse(state, world, pos, player, hand, hit);
		}
	}

}
