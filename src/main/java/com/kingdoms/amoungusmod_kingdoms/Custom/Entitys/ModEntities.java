package com.kingdoms.amoungusmod_kingdoms.Custom.Entitys;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.custom.PurpEntity;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.custom.TestEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TestEntity> TEST_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Amoungusmod_kingdoms.MOD_ID,"test_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE,TestEntity::new).dimensions(EntityDimensions.fixed(1f,1f)).build());

    public static final EntityType<PurpEntity> PURP_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Amoungusmod_kingdoms.MOD_ID,"purp_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PurpEntity::new).dimensions(EntityDimensions.fixed(1f,1f)).build());
}
