package com.raggle.half_dream.common.component;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.api.DreamEntity;
import com.raggle.half_dream.client.sequence.FallingHalfAsleepSequence;
import com.raggle.half_dream.client.sequence.SequenceManager;
import com.raggle.half_dream.common.registry.HDComponentRegistry;
import com.raggle.half_dream.util.HDUtil;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class DreamLivingEntityComponent implements DreamEntity, AutoSyncedComponent {
	
	private final LivingEntity entity;
	
	private byte dream;
	
	public DreamLivingEntityComponent(LivingEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public boolean isDream() {
		return dream == 1;
	}
	@Override
	@ClientOnly
	public void applySyncPacket(PacketByteBuf buf) {
		NbtCompound tag = buf.readNbt();
        if (tag != null && tag.getByte("dream_level") != this.dream) {
        	if(entity == HDUtil.getClientPlayer())
        		SequenceManager.start(new FallingHalfAsleepSequence(this.dream, tag.getByte("dream_level")));
            this.readFromNbt(tag);
        }
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		this.dream = tag.getByte("dream_level");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putByte("dream_level", this.dream);
	}
	@Override
	public byte getDream() {
		return this.dream;
	}
	@Override
	public void setDream(byte b) {
		HalfDream.LOGGER.info("running fog sequence on " + entity.getName() + " " + entity.getWorld().isClient());
		this.dream = b;
		if(entity instanceof ServerPlayerEntity)
			HDComponentRegistry.DREAM_ENTITY.sync(entity);
	}

}
