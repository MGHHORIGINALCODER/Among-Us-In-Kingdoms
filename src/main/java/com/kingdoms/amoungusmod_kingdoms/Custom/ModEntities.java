package com.kingdoms.amoungusmod_kingdoms.Custom;
import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.test_entity;
import com.kingdoms.amoungusmod_kingdoms.Custom.Entitys.test_entity_function;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType TEST_MOB=Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Amoungusmod_kingdoms.MOD_ID,"test_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    test_entity_function::new).dimensions(EntityDimensions.fixed(0.0625f, 0.0625f)).build()

    );

    public static void registerModEntities() {
        Amoungusmod_kingdoms.LOGGER.info("Registering Mod Entities for " + Amoungusmod_kingdoms.MOD_ID);
    }
}
