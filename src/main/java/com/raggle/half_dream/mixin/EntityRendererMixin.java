package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.common.registry.HDTagRegistry;
import com.raggle.half_dream.util.HDUtil;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.util.math.BlockPos;

@ClientOnly
@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

	@Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
	private void shouldRender(T entity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
		if((entity instanceof LivingEntity le && HDUtil.getDream(le) != 2) && !(entity instanceof SkeletonHorseEntity)) {
			if(HDUtil.isDream(le) != HDUtil.isPlayerDream()) {
				cir.setReturnValue(false);
			}
		}
		else if(entity instanceof ItemEntity item) {
			boolean bl = item.getStack().isIn(HDTagRegistry.DREAMING_ITEMS);
			cir.setReturnValue(bl == HDUtil.isPlayerDream());
		}
	}
	
	@Inject(method = "getSkyLight", at = @At("HEAD"), cancellable = true)
	private void getSkyLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		if(HDUtil.isPlayerDream())
			cir.setReturnValue(0);
	}

	@Inject(method = "getBlockLight", at = @At("HEAD"), cancellable = true)
	private void getBlockLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		if(HDUtil.isPlayerDream() && HDUtil.isDreamless(pos))
			cir.setReturnValue(4);
	}
}
