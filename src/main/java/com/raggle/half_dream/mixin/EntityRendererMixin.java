package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.api.DreamEntity;
import com.raggle.half_dream.api.DreamHorse;
import com.raggle.half_dream.common.registry.HDStatusEffectRegistry;
import com.raggle.half_dream.util.HDUtil;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

@SuppressWarnings({ "deprecation", "unused" })
@ClientOnly
@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

	@Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
	private void shouldRender(T entity, Frustum frustum, double d, double e, double f, CallbackInfoReturnable<Boolean> cir) {
		ClientPlayerEntity player = HDUtil.getClientPlayer();
		if (player != null && player instanceof DreamClientPlayer dcp && !(entity instanceof ItemEntity)) {
			if(entity instanceof DreamEntity de && !(entity instanceof DreamHorse)) {
				if(de.isDream() != dcp.isDream()) {
					cir.setReturnValue(false);
				}
			}
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
