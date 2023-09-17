package com.raggle.half_dream.common.block;

import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions;

import com.raggle.half_dream.HalfDream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class DreamBed extends Block {
	

	public static final RegistryKey<World> DEEP_DREAM = RegistryKey.of(RegistryKeys.WORLD, new Identifier(HalfDream.MOD_ID, "deep_dream"));

	public DreamBed() {
		super(QuiltBlockSettings.copyOf(Blocks.STONE));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		
		if(world instanceof ServerWorld sw) {
			QuiltDimensions.teleport(player, sw.getServer().getWorld(DEEP_DREAM), new TeleportTarget(player.getPos(), new Vec3d(0,0,0), jumpVelocityMultiplier, jumpVelocityMultiplier));
		}
		
		
		return ActionResult.SUCCESS;
	}

}
