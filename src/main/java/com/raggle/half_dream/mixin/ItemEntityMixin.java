package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.raggle.half_dream.api.DreamItemEntity;
import com.raggle.half_dream.api.DreamServerPlayer;
import com.raggle.half_dream.common.registry.HDTagRegistry;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin implements DreamItemEntity{
	
	@Shadow public abstract ItemStack getStack();

	@Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
	private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
		if(player instanceof DreamServerPlayer dsp && dsp.isDream() != this.isDream()) {
			ci.cancel();
		}
	}
	@Override
	public boolean isDream() {
		return getStack().isIn(HDTagRegistry.DREAMING_ITEMS);
	}
}
