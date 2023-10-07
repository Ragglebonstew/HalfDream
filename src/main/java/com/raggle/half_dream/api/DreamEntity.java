package com.raggle.half_dream.api;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface DreamEntity extends Component {

	public boolean isDream();
	public byte getDream();
	public void setDream(byte b);
	
}
