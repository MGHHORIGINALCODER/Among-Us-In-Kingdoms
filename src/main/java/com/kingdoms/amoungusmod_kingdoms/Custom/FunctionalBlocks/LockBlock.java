package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks;

import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;

public class LockBlock extends Block {
    public LockBlock(Settings settings) {

        super(settings);
    }
    private static final int TICK_DELAY = 60;
    public static boolean On_=false;
    @Override
    public boolean emitsRedstonePower(BlockState state) {

        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if(On_) {
            return 15;
        }else{
            return 0;
        }
    }
}
