package com.raggle.half_dream.common.component;

import java.util.ArrayList;
import com.raggle.half_dream.api.DreamlessComponent;
import com.raggle.half_dream.common.registry.HDComponentRegistry;
import com.raggle.half_dream.util.HDUtil;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class DreamlessChunkComponent implements DreamlessComponent, AutoSyncedComponent {

	private final Chunk provider;
	private ArrayList<Long> dreamlessPosList;
	private long renderPos;
	
	public DreamlessChunkComponent(Chunk chunk) {
		this.provider = chunk;
		this.dreamlessPosList = new ArrayList<Long>();
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		this.dreamlessPosList.clear();
		for(long entry : tag.getLongArray("dreampos")) {
			this.addPosToList(BlockPos.fromLong(entry));
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		long[] posList = new long[this.dreamlessPosList.size()];
		for(int i = 0; i < posList.length; i++) {
			posList[i] = this.dreamlessPosList.get(i);
		}
		tag.putLongArray("dreampos", posList);
	}
	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        NbtCompound tag = new NbtCompound();
        this.writeToNbt(tag);
		tag.putLong("renderpos", this.renderPos);
        buf.writeNbt(tag);
		provider.setNeedsSaving(true);
    }
	@Override
	public void applySyncPacket(PacketByteBuf buf) {
        NbtCompound tag = buf.readNbt();
        if (tag != null) {
            this.readFromNbt(tag);
    		HDUtil.scheduleChunkRenderAt(BlockPos.fromLong(tag.getLong("renderpos")));
			provider.setNeedsSaving(true);
        }
    }

	@Override
	public boolean isDreamless(BlockPos pos) {
		if(this.dreamlessPosList.isEmpty()) {
			return false;
		}
		return dreamlessPosList.contains(pos.asLong());
	}

	@Override
	public boolean addPosToList(BlockPos pos) {
		if(!dreamlessPosList.contains(pos.asLong())) {
			this.dreamlessPosList.add(pos.asLong());
			this.renderPos = pos.asLong();
			HDComponentRegistry.DREAMLESS.sync(provider);
			return true;
		}
		return false;
	}

	@Override
	public boolean removePosFromList(BlockPos pos) {
		if(this.dreamlessPosList.remove(pos.asLong())) {
			this.renderPos = pos.asLong();
			HDComponentRegistry.DREAMLESS.sync(provider);
			return true;
		}
		return false;
	}
}
