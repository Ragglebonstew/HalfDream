package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.api.DreamClientPlayer;
import com.raggle.half_dream.api.DreamEntity;
import com.raggle.half_dream.common.block.DreamingBlock;
import com.raggle.half_dream.util.DreamlessBlockUtil;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
	
    @Shadow public abstract Block getBlock();
    //@Shadow public abstract VoxelShape getCollisionShape(BlockView world, BlockPos pos, ShapeContext context);
    
	@Inject(at = @At("HEAD"), method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", cancellable = true)
    private void getCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        
        if(context instanceof EntityShapeContext esc) {
            Entity entity = esc.getEntity();
            if(entity != null && entity instanceof DreamEntity dreamEntity) {
            	if(!dreamEntity.isDream()) {
            		if(getBlock() instanceof DreamingBlock)
            			cir.setReturnValue(VoxelShapes.empty());
            	}
            	else {
            		if(DreamlessBlockUtil.isDreamless(pos, (World)world)) {
            			cir.setReturnValue(VoxelShapes.empty());
            		}
            	}
            }
        }
    }
	
	@Inject(method = "getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
	private void getOutlineShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
		if(context instanceof EntityShapeContext esc && world instanceof World aw) {
			Entity entity = esc.getEntity();
			if(entity != null && entity instanceof DreamEntity de) {
				if(de.isDream() && DreamlessBlockUtil.isDreamless(pos, aw))
					cir.setReturnValue(VoxelShapes.empty());
			}
		}
	}
	@ClientOnly
	@Inject(method = "getCameraCollisionShape", at = @At("HEAD"), cancellable = true)
	private void getCameraCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
		
		if(world instanceof World aw && DreamlessBlockUtil.isDreamless(pos, aw)) {
			if(context instanceof EntityShapeContext esc) {
				Entity entity = esc.getEntity();
				if(entity != null && entity instanceof DreamEntity de && de.isDream()) {
					cir.setReturnValue(VoxelShapes.empty());
				}
			}
		}
		
	}
	
	@Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    private void onEntityCollision(World world, BlockPos pos, Entity entity, CallbackInfo ci) {
		if(entity instanceof DreamEntity livingEntity) {
			if(livingEntity.isDream()) {
				if(DreamlessBlockUtil.isDreamless(pos, entity.getWorld())) {
					ci.cancel();
				}
			}
			else {
				if(world.getBlockState(pos).getBlock() instanceof DreamingBlock) {
					ci.cancel();
				}
			}
		}
		
    }
	@Inject(method = "shouldBlockVision", at = @At("HEAD"), cancellable = true)
	private void shouldBlockVision(BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		ClientPlayerEntity player = DreamlessBlockUtil.getClientPlayer();
		if(player != null && player instanceof DreamClientPlayer dcp && dcp.isDream()) {
			if(world instanceof World aw && DreamlessBlockUtil.isDreamless(pos, aw)) {
				cir.setReturnValue(false);
			}
		}
	}
}