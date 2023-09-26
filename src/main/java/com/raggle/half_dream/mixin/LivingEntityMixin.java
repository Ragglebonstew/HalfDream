package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	@Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
	
	/*@Inject(method = "wakeUp", at = @At("INVOKE"))
	public void wakeup(CallbackInfo ci) {
		if(this instanceof DreamServerPlayer player) {
			player.setDream(!player.isDream());
			player.syncDream();
		}
	}*/
}
