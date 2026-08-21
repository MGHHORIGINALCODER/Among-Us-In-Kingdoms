package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class LockBlock extends Block {
    private static final int TICK_DELAY = 60;
    public static boolean On_ = false;

    public LockBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    // method_9506 determines if the block can emit redstone signals
    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    // method_9524 calculates the actual weak redstone signal strength
    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return On_ ? 15 : 0;
    }
}
