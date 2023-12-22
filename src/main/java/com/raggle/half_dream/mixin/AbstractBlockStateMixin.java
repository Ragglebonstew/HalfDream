package com.raggle.half_dream.mixin;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.raggle.half_dream.util.HDUtil;
import com.raggle.half_dream.util.HDUtilClient;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
	
	private void setVoxelShapes(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
		
		if(context instanceof EntityShapeContext esc
        		&& esc.getEntity() != null
        		&& world instanceof World world2) 
		{
			byte dream = HDUtil.getDream(esc.getEntity());
			if(dream == 0 ? HDUtil.isDream(pos, world2) : HDUtil.isDisturbed(pos, world2)) {
				cir.setReturnValue(VoxelShapes.empty());
			}
		}
		
	}
	
    @Shadow public abstract Block getBlock();
    
	@Inject(at = @At("HEAD"), method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", cancellable = true)
    private void getCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        setVoxelShapes(world, pos, context, cir);
    }
	
	@Inject(method = "getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
	private void getOutlineShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
		setVoxelShapes(world, pos, context, cir);
	}
	
	@ClientOnly
	@Inject(method = "getCameraCollisionShape", at = @At("HEAD"), cancellable = true)
	private void getCameraCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
		setVoxelShapes(world, pos, context, cir);
	}

	@Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    private void onEntityCollision(World world, BlockPos pos, Entity entity, CallbackInfo ci) {
		byte dream = HDUtil.getDream(entity);
		if(dream == 0 ? HDUtil.isDream(pos, world) : HDUtil.isDisturbed(pos, world)){
			ci.cancel();
		}
	}
	@ClientOnly
	@Inject(method = "shouldBlockVision", at = @At("HEAD"), cancellable = true)
	private void shouldBlockVision(BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(world instanceof World world2) {
			byte dream = HDUtilClient.getPlayerDream();
			if(dream == 0 ? HDUtil.isDream(pos, world2) : HDUtil.isDisturbed(pos, world2)) {
				cir.setReturnValue(false);
			}
		}
	}
	/*
	@Inject(method = "getOpacity", at = @At("HEAD"), cancellable = true)
	public void getOpacity(BlockView world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
		if(world instanceof World world2) {
			if(HDUtil.isDream(pos, world2)) {
				cir.setReturnValue(0);
			}
		}
	}
	public float getAmbientOcclusionLightLevel(BlockView world, BlockPos pos) {
		return this.getBlock().getAmbientOcclusionLightLevel(this.asBlockState(), world, pos);
	}
	*/
	@Inject(method = "canReplace", at = @At("TAIL"), cancellable = true)
	public void canReplace(ItemPlacementContext context, CallbackInfoReturnable<Boolean> cir) {
		BlockPos pos = context.getBlockPos();
		World world = context.getWorld();

		if(cir.getReturnValue()) {
			if(!world.isClient()) {
				HDUtil.setDream(pos, false, world);
				HDUtil.setDisturbed(pos, false, world);
			}
		}
		else if(HDUtil.isDream(pos, world) && HDUtil.getDream(context.getPlayer()) == 0) {
			cir.setReturnValue(true);
			if(!world.isClient()) {
				HDUtil.setDream(pos, false, world);
				HDUtil.setDisturbed(pos, false, world);
			}
		}
	}
	
	@Inject(method = "shouldSuffocate", at = @At("TAIL"), cancellable = true)
	public void shouldSuffocate(BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(cir.getReturnValueZ() && world instanceof World world2 && HDUtil.isDream(pos, world2)) {
			cir.setReturnValue(false);
		}
	}

}
