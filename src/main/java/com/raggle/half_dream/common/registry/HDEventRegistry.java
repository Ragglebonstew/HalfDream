package com.raggle.half_dream.common.registry;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;
import com.raggle.half_dream.common.block.DreamBlock;
import com.raggle.half_dream.util.HDUtil;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HDEventRegistry {
	
    
	
	public static void init() {
		ServerPlayerEntityCopyCallback.EVENT.register(HDEventRegistry::afterRespawn);
		PlayerBlockBreakEvents.BEFORE.register(HDEventRegistry::beforeBlockBreak);
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("toggledream").executes(context -> {
			
			if(context.getSource().getEntity() instanceof LivingEntity entity) {
				boolean b = HDUtil.isDream(entity);
				HDUtil.setDream(entity, b ? 0 : (byte)1);
			}
	     
			return 1;
		})));
	}
	
	private static void afterRespawn(ServerPlayerEntity copy, ServerPlayerEntity original, boolean wasDeath) {
		HDUtil.setDream(copy, HDUtil.getDream(original));
	}
	private static boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity){
		if(!(state.getBlock() instanceof DreamBlock)) {
			if(HDUtil.isDream(player)) {
				HDUtil.setDreamless(pos, true, world);
				world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(HDItemRegistry.DREAM_RESIN)));
				return false;
			}
			else {
				HDUtil.setDreamless(pos, false, world);
			}
		}

		return true;
	}
}
