package com.raggle.half_dream.common.block;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import com.raggle.half_dream.api.DreamEntity;
import com.raggle.half_dream.api.DreamPlayer;
import com.raggle.half_dream.common.registry.HDEntityRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class DreamingBlock extends Block {
	
	public DreamingBlock() {
		super(QuiltBlockSettings.create()
				.replaceable()
				.nonOpaque()
				.suffocates(DreamingBlock::never)
				//.mapColor(MapColor.PLANT)
				.strength(0.2F)
				//.ticksRandomly()
				//.sounds(soundGroup)
				.allowsSpawning(DreamingBlock::onlyDreamEntity)
				.blockVision(DreamingBlock::never)
				//.lavaIgnitable()
				.pistonBehavior(PistonBehavior.DESTROY)
				//.solidBlock(Blocks::never)
				);
	}

	@Override
    public BlockRenderType getRenderType(BlockState state) {

		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc != null && mc.player instanceof DreamEntity de && de.isDream())
			return BlockRenderType.MODEL;
		
        return BlockRenderType.INVISIBLE;
    }
	
	@ClientOnly
	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc != null && mc.player instanceof DreamEntity de && !de.isDream())
			return VoxelShapes.empty();
		return VoxelShapes.fullCube();
	}
	
	@Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos pos, ShapeContext context) {
		if(context instanceof EntityShapeContext esc) {
			Entity entity = esc.getEntity();
			if(entity != null && entity instanceof DreamEntity de && !de.isDream()) {
				return VoxelShapes.empty();
			}
		}
		return VoxelShapes.fullCube();
    }

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc != null && mc.player instanceof DreamEntity de && !de.isDream())
			return 1.0F;
		return 0.2F;
	}
	
	@ClientOnly
	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return true;
	}
	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return context.getPlayer() instanceof DreamPlayer dp && !dp.isDream();
	}
	@Override
	public boolean canMobSpawnInside(BlockState state) {
		return true;
	}
	
	private static boolean never(BlockState state, BlockView world, BlockPos pos) {
		return false;
	}
	private static boolean onlyDreamEntity(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == HDEntityRegistry.HDSKELETON;
	}
	
	
}
