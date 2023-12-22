package com.raggle.half_dream.common.registry;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.raggle.half_dream.util.HDUtil;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HDEventRegistry {
	
    
	
	public static void init() {
		ServerPlayerEntityCopyCallback.EVENT.register(HDEventRegistry::afterRespawn);
		PlayerBlockBreakEvents.BEFORE.register(HDEventRegistry::beforeBlockBreak);
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("setdream")
				.then(argument("entity", EntityArgumentType.entity())
				.then(argument("value", IntegerArgumentType.integer())
				.executes(context -> {

			final Entity entity = EntityArgumentType.getEntity(context, "entity");
			final byte value = (byte)IntegerArgumentType.getInteger(context, "value");
			HDUtil.setDream(entity, value);
			     
			return 1;
		})))));
	}
	
	private static void afterRespawn(ServerPlayerEntity copy, ServerPlayerEntity original, boolean wasDeath) {
		HDUtil.setDream(copy, HDUtil.getDream(original));
	}
	private static boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity){
		if(!HDUtil.isDream(pos, world)) {
			if(HDUtil.getDream(player) == 1) {
				HDUtil.setDisturbed(pos, true, world);
				ItemEntity resin = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(HDItemRegistry.DREAM_RESIN));
				HDUtil.setDream(resin, (byte)1);
				world.spawnEntity(resin);
				return false;
			}
			else {
				HDUtil.setDisturbed(pos, false, world);
				HDUtil.setDream(pos, false, world);
			}
		}

		return true;
	}
}
