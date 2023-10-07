package com.raggle.half_dream.api;

import java.util.ArrayList;

import net.minecraft.entity.LivingEntity;

public interface FollowerTracker<T extends LivingEntity> {

	public boolean hasEnough(int target);
	public ArrayList<T> getList();
	public void addToList(T entity);
	public void removeFromList(T entity);
	
}
