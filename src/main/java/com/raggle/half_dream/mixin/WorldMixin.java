package com.raggle.half_dream.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.common.block.DreamBlock;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

@Mixin(World.class)
public class WorldMixin {
	@SuppressWarnings("deprecation")
	@Inject(at = @At("TAIL"), method = "getTopY", cancellable = true)
    private void getTopY(Heightmap.Type heightmap, int x, int z, CallbackInfoReturnable<Integer> cir) {
        if (heightmap == Heightmap.Type.MOTION_BLOCKING) {
    		MinecraftClient client = MinecraftClient.getInstance();
    		if(client.world == null)
    			return;
        	for(int i = cir.getReturnValueI()-1; i > -65; i--) {
        		BlockState state = client.world.getBlockState(new BlockPos(x, i, z));
                if ((state.blocksMovement() || !state.getFluidState().isEmpty()) && !(state.getBlock() instanceof DreamBlock)) {
                    cir.setReturnValue(i+1);
                    break;
                }
        	}
        }
    }
}
