package com.raggle.half_dream.common.component;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import com.raggle.half_dream.api.DreamEntity;
import com.raggle.half_dream.client.sequence.FallingHalfAsleepSequence;
import com.raggle.half_dream.client.sequence.SequenceManager;
import com.raggle.half_dream.common.registry.HDComponentRegistry;
import com.raggle.half_dream.util.HDUtilClient;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class DreamEntityComponent implements DreamEntity, AutoSyncedComponent {
	
	private final Entity entity;
	
	private byte dream;
	
	public DreamEntityComponent(Entity entity) {
		this.entity = entity;
		if(entity.getType() == EntityType.SKELETON_HORSE) this.dream = 2;
	}
	@Override
	@ClientOnly
	public void applySyncPacket(PacketByteBuf buf) {
		NbtCompound tag = buf.readNbt();
        if (tag != null && tag.getByte("dream_level") != this.dream) {
        	if(entity == HDUtilClient.getClientPlayer())
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
		this.dream = b;
		if(entity instanceof ServerPlayerEntity)
			HDComponentRegistry.DREAM_ENTITY.sync(entity);
	}

}
