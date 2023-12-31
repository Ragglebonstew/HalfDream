package com.raggle.half_dream.common.item;

import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import com.raggle.half_dream.util.HDUtil;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DreamPowderItem extends Item {

	public DreamPowderItem() {
		super(new QuiltItemSettings());
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		
		if(!world.isClient() && context.getStack().getCount() >= 1 
				&& HDUtil.setDream(pos, !HDUtil.isDream(pos, world), world)) 
		{
			context.getStack().decrement(1);
			world.playSound(null, pos, SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.BLOCKS);
			world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.BLOCKS);
		}
		return ActionResult.success(world.isClient());
	}
}
