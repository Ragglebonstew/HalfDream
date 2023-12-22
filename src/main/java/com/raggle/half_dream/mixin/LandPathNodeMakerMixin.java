package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.util.HDUtil;

import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@Mixin(LandPathNodeMaker.class)
public abstract class LandPathNodeMakerMixin extends PathNodeMaker {
	
	@Inject(method = "getCommonNodeType", at = @At("HEAD"), cancellable = true)
	private static void getCommonNodeType(BlockView world, BlockPos pos, CallbackInfoReturnable<PathNodeType> cir) {
		if(world instanceof World world2 && HDUtil.isDream(pos, world2)) {
			cir.setReturnValue(PathNodeType.DOOR_OPEN);
		}
	}
	
	@Inject(method = "adjustNodeType", at = @At("HEAD"), cancellable = true)
	private void adjustNodeType(BlockView world, BlockPos pos, PathNodeType type, CallbackInfoReturnable<PathNodeType> cir) {
		if(HDUtil.getDream(entity) != 0) {
			if(world instanceof World && HDUtil.isDisturbed(pos, (World)world)) {
				cir.setReturnValue(PathNodeType.OPEN);
			}
		}
	}

}
