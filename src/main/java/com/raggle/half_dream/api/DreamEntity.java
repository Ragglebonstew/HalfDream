package com.raggle.half_dream.api;

import net.minecraft.nbt.NbtCompound;

public interface DreamEntity {

	public boolean isDream();
	public void setDream(boolean b);
	
	public byte getDream();
	public void setDream(byte b);
	
	public NbtCompound getPersistantData();
	
}
