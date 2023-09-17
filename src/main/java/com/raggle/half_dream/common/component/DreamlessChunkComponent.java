package com.raggle.half_dream.common.component;

import java.util.ArrayList;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.api.DreamlessComponent;
import com.raggle.half_dream.common.registry.HDComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class DreamlessChunkComponent implements DreamlessComponent, AutoSyncedComponent {

	public final Chunk provider;
	private final ArrayList<BlockPos> dreamlessList;
	
	public DreamlessChunkComponent(Chunk chunk) {
		this.provider = chunk;
		this.dreamlessList = new ArrayList<BlockPos>();
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		for(long entry : tag.getLongArray("dreamlessList")) {
			dreamlessList.add(BlockPos.fromLong(entry));
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		long[] list = new long[dreamlessList.size()];
		for(int i = 0; i < dreamlessList.size(); i++) {
			list[i] = dreamlessList.get(i).asLong();
		}
		tag.putLongArray("dreamlessList", list);
	}

	@Override
	public ArrayList<BlockPos> getDreamlessList() {
		return dreamlessList;
	}

	@Override
	public boolean isDreamless(BlockPos pos) {
		if(this.dreamlessList.isEmpty()) {
			return false;
		}
		return dreamlessList.contains(pos);
	}

	@Override
	public void addPosToList(BlockPos pos) {
		if(!dreamlessList.contains(pos)) {
			HalfDream.LOGGER.info("adding pos to list: " + pos);
			dreamlessList.add(pos);
			HDComponentRegistry.DREAMLESS.sync(provider);
		}
	}
}
