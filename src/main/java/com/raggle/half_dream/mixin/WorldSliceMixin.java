package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.util.HDUtilClient;

import me.jellysquid.mods.sodium.client.world.WorldSlice;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

@ClientOnly
@Mixin(WorldSlice.class)
public abstract class WorldSliceMixin {

	@Inject(method = "getBlockState(III)Lnet/minecraft/block/BlockState;", at = @At("HEAD"), cancellable = true)
	private void getBlockState(int x, int y, int z, CallbackInfoReturnable<BlockState> cir){
		BlockPos pos = new BlockPos(x, y, z);
		if(HDUtilClient.isDisturbed(pos) && HDUtilClient.getPlayerDream() != 0) {
			cir.setReturnValue(Blocks.AIR.getDefaultState());
		}
	}
	@Inject(method = "getLightLevel", at = @At("HEAD"), cancellable = true)
	private void getLightLevel(LightType type, BlockPos pos, CallbackInfoReturnable<Integer> cir){
		if(HDUtilClient.getPlayerDream() == 1) {
			if(type == LightType.SKY) {
				cir.setReturnValue(0);
			}
			else if(HDUtilClient.isDisturbed(pos)) {
				cir.setReturnValue(4);
			}
		}
	}
}
