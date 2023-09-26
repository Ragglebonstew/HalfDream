package com.raggle.half_dream.common.block;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import com.raggle.half_dream.api.DreamPlayer;
import com.raggle.half_dream.common.registry.HDEntityRegistry;
import com.raggle.half_dream.util.HDUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class DreamBlock extends Block implements Waterloggable {
	
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	
	public DreamBlock() {
		this(QuiltBlockSettings.create());
	}
	public DreamBlock(Settings settings) {
		super(settings
				.replaceable()
				.nonOpaque()
				.suffocates(DreamBlock::never)
				.mapColor(MapColor.NONE)
				.strength(0.2F)
				//.ticksRandomly()
				//.sounds(soundGroup)
				.allowsSpawning(DreamBlock::onlyDreamEntity)
				//.blockVision(DreamBlock::never)
				//.lavaIgnitable()
				.pistonBehavior(PistonBehavior.DESTROY)
				//.solidBlock(DreamBlock::never)
				);

		this.setDefaultState(this.getDefaultState()
				.with(WATERLOGGED, false));
	}
	@ClientOnly
	@Override
    public BlockRenderType getRenderType(BlockState state) {
		if(HDUtil.isPlayerDream())
			return BlockRenderType.MODEL;
		
        return BlockRenderType.INVISIBLE;
    }
	/*
	@ClientOnly
	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(mc != null && mc.player instanceof DreamEntity de && !de.isDream())
			return VoxelShapes.empty();
		return VoxelShapes.fullCube();
	}*
	
	@Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos pos, ShapeContext context) {
		if(context instanceof EntityShapeContext esc) {
			Entity entity = esc.getEntity();
			if(entity != null && entity instanceof DreamEntity de && !de.isDream()) {
				return VoxelShapes.empty();
			}
		}
		return VoxelShapes.fullCube();
    }*/

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		if(!HDUtil.isPlayerDream())
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
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
	@Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState()
            .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}
	@SuppressWarnings("deprecation")
	@Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
 
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
	
	private static boolean never(BlockState state, BlockView world, BlockPos pos) {
		return false;
	}
	private static boolean onlyDreamEntity(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == HDEntityRegistry.HDSKELETON;
	}
	
	
}
