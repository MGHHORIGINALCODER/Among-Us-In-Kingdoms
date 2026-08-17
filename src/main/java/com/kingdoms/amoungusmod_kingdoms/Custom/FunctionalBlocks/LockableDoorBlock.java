package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
//Source code found online
public class LockableDoorBlock extends Block {
    // 1. Define a boolean state property for locking
    public static final BooleanProperty LOCKED = BooleanProperty.of("locked");

    public LockableDoorBlock(Settings settings) {
        super(settings);
        // Set the default state to unlocked
        this.setDefaultState(this.stateManager.getDefaultState().with(LOCKED, false));
    }

    // 2. Override collision to disappear completely when LOCKED is true
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(LOCKED)) {
            return VoxelShapes.empty(); // Disables collision dynamically
        }
        return state.getOutlineShape(world, pos, context); // Normal solid collision
    }

    // 3. Register the property to the block builder
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LOCKED);
    }
}
