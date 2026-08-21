package com.kingdoms.amoungusmod_kingdoms.Custom.Entitys;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class test_entity_function extends AnimalEntity {

    public test_entity_function(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    // 1. Simple Passive AI Goals
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this)); // Doesn't drown in water
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D)); // Runs away if you hit it
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D)); // Walks around randomly
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F)); // Looks at players
        this.goalSelector.add(4, new LookAroundGoal(this)); // Looks around
    }

    // 2. Base Attributes (Health & Speed)
    public static DefaultAttributeContainer.Builder createTestMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0) // 5 Hearts
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25); // Standard animal walking speed
    }

    // 3. Required for AnimalEntity (Return null so it doesn't try to breed)
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
