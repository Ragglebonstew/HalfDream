package com.raggle.half_dream.common.registry;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;
import com.raggle.half_dream.api.DreamPlayer;
import com.raggle.half_dream.api.DreamServerPlayer;
import com.raggle.half_dream.common.block.DreamBlock;
import com.raggle.half_dream.util.HDUtil;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
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
			if(context.getSource().getEntity() instanceof DreamServerPlayer dsp) {
				dsp.setDream(!dsp.isDream());
			}
	     
			return 1;
		})));
	}
	
	private static void afterRespawn(ServerPlayerEntity copy, ServerPlayerEntity original, boolean wasDeath) {
		if(original instanceof DreamPlayer doriginal && copy instanceof DreamPlayer dcopy) {
			dcopy.setDream(doriginal.isDream());
		}
	}
	private static boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity){
		if(!(state.getBlock() instanceof DreamBlock)) {
			if(player instanceof DreamServerPlayer dsp && dsp.isDream()) {
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
	/*static BlockPos lastPlayerPos;
	private static void clientTick(MinecraftClient mc) {
		if(!mc.isPaused() && mc.player instanceof DreamClientPlayer dcp && dcp.isDream()) {
			if(mc != null && mc.world != null) {
				
				if(lastPlayerPos == mc.player.getBlockPos()) {
					return;
				}
				lastPlayerPos = mc.player.getBlockPos();
				
				//maximum light level emitted by player
				int l = 7;
				
				int x = mc.player.getBlockX();
				int y = mc.player.getBlockY();
				int z = mc.player.getBlockZ();
				boolean[] direction = new boolean[3];
				if(x >> 4 == x+l >> 4) {
					direction[0] = true;
				}
				if(y >> 4 == y+l >> 4) {
					direction[1] = true;
				}
				if(z >> 4 == z+l >> 4) {
					direction[2] = true;
				}
				
				int x1 = x >> 4;
				int x2 = x + (direction[0] ? -l : l) >> 4;
				int y1 = x >> 4;
				int y2 = x + (direction[1] ? -l : l) >> 4;
				int z1 = x >> 4;
				int z2 = x + (direction[2] ? -l : l) >> 4;

				HDUtil.scheduleChunkRenderAt(x1, y1, z1);
				HDUtil.scheduleChunkRenderAt(x2, y1, z1);
				HDUtil.scheduleChunkRenderAt(x1, y2, z1);
				HDUtil.scheduleChunkRenderAt(x2, y2, z1);
				HDUtil.scheduleChunkRenderAt(x1, y1, z2);
				HDUtil.scheduleChunkRenderAt(x2, y1, z2);
				HDUtil.scheduleChunkRenderAt(x1, y2, z2);
				HDUtil.scheduleChunkRenderAt(x2, y2, z2);

			}
		}
	}*/
}
