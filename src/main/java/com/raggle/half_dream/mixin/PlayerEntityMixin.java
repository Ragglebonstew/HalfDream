package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import com.raggle.half_dream.api.DreamPlayer;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements DreamPlayer {
	
}
