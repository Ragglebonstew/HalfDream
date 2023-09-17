package com.raggle.half_dream.common.world.feature.tree;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.raggle.half_dream.common.registry.HDStructureRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.int_provider.IntProvider;
import net.minecraft.util.math.int_provider.UniformIntProvider;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer.TreeNode;

public class DreamTrunkPlacer extends TrunkPlacer {
	private static final Codec<UniformIntProvider> VALIDATION_CODEC = Codecs.method_48112(
			UniformIntProvider.CODEC,
			uniformIntProvider -> uniformIntProvider.getMax() - uniformIntProvider.getMin() < 1
					? DataResult.error(() -> "Need at least 2 blocks variation for the branch starts to fit both branches")
					: DataResult.success(uniformIntProvider)
		);
    public static final Codec<DreamTrunkPlacer> CODEC = RecordCodecBuilder.create(
    		instance -> fillTrunkPlacerFields(instance)
			.<IntProvider, IntProvider, UniformIntProvider, IntProvider>and(
				instance.group(
					IntProvider.create(1, 3).fieldOf("branch_count").forGetter(dreamTrunkPlacer -> dreamTrunkPlacer.branchCount),
					IntProvider.create(2, 16).fieldOf("branch_horizontal_length").forGetter(dreamTrunkPlacer -> dreamTrunkPlacer.branchHorizontalLength),
					IntProvider.create(-16, 0, VALIDATION_CODEC)
						.fieldOf("branch_start_offset_from_top")
						.forGetter(dreamTrunkPlacer -> dreamTrunkPlacer.branchStartOffsetFromTop),
					IntProvider.create(-16, 16).fieldOf("branch_end_offset_from_top").forGetter(dreamTrunkPlacer -> dreamTrunkPlacer.branchEndOffsetFromTop)
				)
			)
			.apply(instance, DreamTrunkPlacer::new));
 
    private final IntProvider branchCount;
	private final IntProvider branchHorizontalLength;
	private final UniformIntProvider branchStartOffsetFromTop;
	@SuppressWarnings("unused")
	private final UniformIntProvider field_42853;
	private final IntProvider branchEndOffsetFromTop;
    public DreamTrunkPlacer(
    		int baseHeight,
    		int firstRandomHeight,
    		int secondRandomHeight,
    		IntProvider intProvider,
    		IntProvider intProvider2,
    		UniformIntProvider uniformIntProvider,
    		IntProvider intProvider3
    	) {
    		super(baseHeight, firstRandomHeight, secondRandomHeight);
    		this.branchCount = intProvider;
    		this.branchHorizontalLength = intProvider2;
    		this.branchStartOffsetFromTop = uniformIntProvider;
    		this.field_42853 = UniformIntProvider.create(uniformIntProvider.getMin(), uniformIntProvider.getMax() - 1);
    		this.branchEndOffsetFromTop = intProvider3;
    	}
 
    @Override
    protected TrunkPlacerType<?> getType() {
        return HDStructureRegistry.DREAM_TRUNK_PLACER;
    }

	@Override
	public java.util.List<TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
			RandomGenerator random, int height, BlockPos startPos, TreeFeatureConfig config) {
		// Set the ground beneath the trunk to dirt
		setToDirt(world, replacer, random, startPos.down(), config);
	 
		// Iterate until the trunk height limit and place two blocks using the getAndSetState method from TrunkPlacer
		for (int i = 0; i < height; i++) {
			this.trySetState(world, replacer, random, startPos.up(i).mutableCopy(), config);
			this.trySetState(world, replacer, random, startPos.up(i).east().north().mutableCopy(), config);
		}
	 
		// We create two TreeNodes - one for the first trunk, and the other for the second
		// Put the highest block in the trunk as the center position for the FoliagePlacer to use
		return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height), 0, false),
	                                new FoliagePlacer.TreeNode(startPos.east().north().up(height), 0, false));
	}
	
	@SuppressWarnings("unused")
	private FoliagePlacer.TreeNode method_49249(
			TestableWorld world,
			BiConsumer<BlockPos, BlockState> biConsumer,
			RandomGenerator random,
			int i,
			BlockPos pos,
			TreeFeatureConfig treeFeatureConfig,
			Function<BlockState, BlockState> function,
			Direction direction,
			int j,
			boolean bl,
			BlockPos.Mutable mutablePos
		) {
			mutablePos.set(pos).move(Direction.UP, j);
			int k = i - 1 + this.branchEndOffsetFromTop.get(random);
			boolean bl2 = bl || k < j;
			int l = this.branchHorizontalLength.get(random) + (bl2 ? 1 : 0);
			BlockPos blockPos = pos.offset(direction, l).up(k);
			int m = bl2 ? 2 : 1;

			for(int n = 0; n < m; ++n) {
				this.placeTrunkBlock(world, biConsumer, random, mutablePos.move(direction), treeFeatureConfig, function);
			}

			Direction direction2 = blockPos.getY() > mutablePos.getY() ? Direction.UP : Direction.DOWN;

			while(true) {
				int o = mutablePos.getManhattanDistance(blockPos);
				if (o == 0) {
					return new FoliagePlacer.TreeNode(blockPos.up(), 0, false);
				}

				float f = (float)Math.abs(blockPos.getY() - mutablePos.getY()) / (float)o;
				boolean bl3 = random.nextFloat() < f;
				mutablePos.move(bl3 ? direction2 : direction);
				this.placeTrunkBlock(world, biConsumer, random, mutablePos, treeFeatureConfig, bl3 ? Function.identity() : function);
			}
		}

}
