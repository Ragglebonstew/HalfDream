package com.raggle.half_dream.api;

import java.util.ArrayList;

import com.raggle.half_dream.common.entity.HDSkeleton;

public interface DreamServerPlayer extends DreamPlayer{

	void syncDream();
	
	void addToList(HDSkeleton skeleton);
	void removeFromList(HDSkeleton skeleton);
	
	ArrayList<HDSkeleton> getList();
	
}
