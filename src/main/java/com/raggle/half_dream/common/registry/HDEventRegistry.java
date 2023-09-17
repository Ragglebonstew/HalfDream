package com.raggle.half_dream.common.registry;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;
import org.quiltmc.qsl.entity.event.api.client.ClientEntityLoadEvents;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.api.DreamPlayer;
import com.raggle.half_dream.api.DreamServerPlayer;
import com.raggle.half_dream.api.DreamlessComponent;
import com.raggle.half_dream.client.sequence.SequenceManager;
import com.raggle.half_dream.common.block.DreamingBlock;
import com.raggle.half_dream.networking.HDMessaging;
import com.raggle.half_dream.util.DreamlessBlockUtil;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class HDEventRegistry {
	
	public static void init() {
		ServerPlayerEntityCopyCallback.EVENT.register(HDEventRegistry::afterRespawn);
		PlayerBlockBreakEvents.BEFORE.register(HDEventRegistry::beforeBlockBreak);
		
		//Client side
		ClientEntityLoadEvents.AFTER_LOAD.register(HDEventRegistry::onLoadClient);
		ClientTickEvents.START.register(SequenceManager::tick);
		HudRenderCallback.EVENT.register(SequenceManager::render);
		ClientTickEvents.START.register(HDEventRegistry::clientTick);
	}
	
	private static void afterRespawn(ServerPlayerEntity copy, ServerPlayerEntity original, boolean wasDeath) {
		if(original instanceof DreamPlayer doriginal && copy instanceof DreamPlayer dcopy) {
			dcopy.setDream(doriginal.isDream());
		}
	}
	private static void onLoadClient(Entity entity, ClientWorld world) {
		if(entity instanceof DreamPlayer de) {
			PacketByteBuf buf = PacketByteBufs.create();
			ClientPlayNetworking.send(HDMessaging.ON_LOAD_CLIENT, buf);
		}
	}
	private static boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity){
		if(!(state.getBlock() instanceof DreamingBlock)) {
			if(player instanceof DreamServerPlayer dsp && dsp.isDream()) {
				Chunk chunk = world.getChunk(pos);
				Optional<DreamlessComponent> op = HDComponentRegistry.DREAMLESS.maybeGet(chunk);
				if(op.isEmpty())
					HalfDream.LOGGER.warn("Chunk is null on block break!!!!");
				op.get().addPosToList(pos);
				return false;
			}
		}

		return true;
	}
	private static void clientTick(MinecraftClient mc) {
		if(!mc.isPaused() && mc.player instanceof DreamClientPlayer dcp && dcp.isDream()) {
			if(mc != null && mc.world != null) {
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

				DreamlessBlockUtil.markDirty(x1, y1, z1);
				DreamlessBlockUtil.markDirty(x2, y1, z1);
				DreamlessBlockUtil.markDirty(x1, y2, z1);
				DreamlessBlockUtil.markDirty(x2, y2, z1);
				DreamlessBlockUtil.markDirty(x1, y1, z2);
				DreamlessBlockUtil.markDirty(x2, y1, z2);
				DreamlessBlockUtil.markDirty(x1, y2, z2);
				DreamlessBlockUtil.markDirty(x2, y2, z2);

			}
		}
	}
}
