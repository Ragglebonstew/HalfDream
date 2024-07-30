package com.raggle.half_dream.api;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;

public interface DreamEntityComponent extends Component {

	public boolean isDream();
	public void setDream(boolean b);
	
	public byte getDream();
	public void setDream(byte b);
	
	public NbtCompound getPersistantData();
	
}
