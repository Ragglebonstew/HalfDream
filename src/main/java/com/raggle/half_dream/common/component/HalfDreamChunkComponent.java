package com.raggle.half_dream.common.component;

import java.util.ArrayList;

import com.raggle.half_dream.api.DreamComponent;
import com.raggle.half_dream.util.HDUtilClient;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public abstract class HalfDreamChunkComponent implements DreamComponent, AutoSyncedComponent {

	protected final Chunk provider;
	private final ArrayList<Long> posList;
	private long renderPos;
	private boolean renderPosb;
	
	protected String id = "default";
	
	public HalfDreamChunkComponent(Chunk chunk) {
		this.provider = chunk;
		this.posList = new ArrayList<Long>();
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		this.posList.clear();
		this.renderPos = Long.MIN_VALUE;
		for(long entry : tag.getLongArray("half_dream:dreampos-" + this.id)) {
			if(!this.posList.contains(entry)) {
				this.posList.add(entry);
			}
		}
		if(!this.posList.isEmpty()) this.sync();
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		long[] tempPosList = new long[this.posList.size()];
		for(int i = 0; i < tempPosList.length; i++) {
			tempPosList[i] = this.posList.get(i);
		}
		tag.putLongArray("half_dream:dreampos-" + this.id, tempPosList);
	}
	@Override
	public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        NbtCompound tag = new NbtCompound();
		tag.putLong("renderpos", this.renderPos);
        
        if(this.renderPos == Long.MIN_VALUE) {
        	if(this.posList.isEmpty()) {
        		buf.writeNbt(null);
        		return;
        	}
        	else {
        		this.writeToNbt(tag);
        	}
		}
        
		tag.putBoolean("renderposb", this.renderPosb);
		buf.writeNbt(tag);
		provider.setNeedsSaving(true);
    }
	@Override
	public void applySyncPacket(PacketByteBuf buf) {
        NbtCompound tag = buf.readNbt();
        if (tag != null) {
        	long pos = tag.getLong("renderpos");
        	
        	if(pos == Long.MIN_VALUE) {
        		this.posList.clear();
        		for(long entry : tag.getLongArray("half_dream:dreampos-" + this.id)) {
        			if(!this.posList.contains(entry)) {
        				this.posList.add(entry);
        			}
        		}
        		return;
        	}
        	else {
        		if(tag.getBoolean("renderposb")) {
            		if(!this.posList.contains(pos)) this.posList.add(pos);
            	}
            	else this.posList.remove(pos);
        		HDUtilClient.scheduleChunkRenderAt(BlockPos.fromLong(pos));
        	}
        }
    }

	@Override
	public boolean contains(BlockPos pos) {
		if(this.posList.isEmpty()) {
			return false;
		}
		return posList.contains(pos.asLong());
	}

	@Override
	public boolean add(BlockPos pos) {
		long posl = pos.asLong();
		if(!posList.contains(posl)) {
			this.posList.add(posl);
			this.renderPos = posl;
			this.renderPosb = true;
			provider.setNeedsSaving(true);
			this.sync();
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(BlockPos pos) {
		long posl = pos.asLong();
		if(this.posList.remove(posl)) {
			this.renderPos = posl;
			this.renderPosb = false;
			provider.setNeedsSaving(true);
			this.sync();
			return true;
		}
		return false;
	}
	
	public abstract void sync();
}
