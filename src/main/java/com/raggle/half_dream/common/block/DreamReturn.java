package com.raggle.half_dream.common.block;

import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import com.raggle.half_dream.api.DreamServerPlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DreamReturn extends Block {
	
	public DreamReturn() {
		super(QuiltBlockSettings.copyOf(Blocks.STONE));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		
		/*player.addStatusEffect(new StatusEffectInstance(HDStatusEffectRegistry.HALF_ASLEEP, -1));
		
		if(world instanceof ServerWorld sw) {
			Entity e = QuiltDimensions.teleport(player, sw.getServer().getWorld(World.OVERWORLD), new TeleportTarget(player.getPos(), new Vec3d(0,0,0), jumpVelocityMultiplier, jumpVelocityMultiplier));
			
		}*/
		if(player instanceof DreamServerPlayer dp) {
			dp.setDream(!dp.isDream());
		}
		
		return ActionResult.SUCCESS;
	}

}