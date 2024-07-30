package com.raggle.half_dream.common.registry;

import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

import com.raggle.half_dream.HalfDream;
import com.raggle.half_dream.common.entity.HDSkeleton;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biomes;

@SuppressWarnings("deprecation")
public class HDEntityRegistry {

    public static final EntityType<HDSkeleton> HDSKELETON = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(HalfDream.MOD_ID, "hdskeleton"),
            QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, HDSkeleton::new).build()
    );
    
    public static void init() {
    	
        FabricDefaultAttributeRegistry.register(HDSKELETON, HDSkeleton.createAttributes());
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.PLAINS), SpawnGroup.MONSTER, HDSKELETON, 40, 1, 2);
        SpawnRestriction.register(HDSKELETON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        
    }
}
