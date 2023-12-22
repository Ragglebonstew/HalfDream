package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.util.HDUtil;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

@Mixin(World.class)
public abstract class WorldMixin {
	
	@Shadow public abstract BlockState getBlockState(BlockPos pos);
	@Shadow public abstract WorldChunk getWorldChunk(BlockPos pos);
	
	@SuppressWarnings("deprecation")
	@Inject(at = @At("TAIL"), method = "getTopY", cancellable = true)
    private void getTopY(Heightmap.Type heightmap, int x, int z, CallbackInfoReturnable<Integer> cir) {
        if (heightmap == Heightmap.Type.MOTION_BLOCKING) {
        	for(int i = cir.getReturnValueI()-1; i > -65; i--) {
        		BlockPos pos = new BlockPos(x, i, z);
        		BlockState state = getBlockState(pos);
                if ((state.blocksMovement() || !state.getFluidState().isEmpty()) && !HDUtil.isDream(pos, this.getWorldChunk(pos))) {
                    cir.setReturnValue(i+1);
                    break;
                }
        	}
        }
    }
}
