package com.raggle.half_dream.common.item;

import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import com.raggle.half_dream.util.HDUtil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CheckBlockStateItem extends Item {

	public CheckBlockStateItem() {
		super(new QuiltItemSettings().fireproof());
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		PlayerEntity player = context.getPlayer();
		
		if(!world.isClient()) {
			if(HDUtil.getDream(player) == 0) {
				if(HDUtil.isDisturbed(pos, world)) {
					world.playSound(null, pos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS);
					world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.BLOCKS);
				}
			}
			else if(HDUtil.getDream(player) == 1) {
				if(HDUtil.isDream(pos, world)) {
					world.playSound(null, pos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS);
					world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.BLOCKS);
				}
			}
		}
		return ActionResult.success(world.isClient());
	}
}
