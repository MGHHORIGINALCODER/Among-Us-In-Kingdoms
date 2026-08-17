package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class DissapearAfterCall{

    public static void DisableSelf(World world, BlockPos Center, int Raid, Block blockOfChoice){
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        double RaidSq = Raid * Raid;
        if(!world.isClient){
            for (int x = -Raid; x <= Raid; x++) {
                for (int y = -Raid; y <= Raid; y++) {
                    for (int z = -Raid; z <= Raid; z++) {
                        if (x * x + y * y + z * z <= RaidSq) {
                            mutablePos.set(Center.getX() + x, Center.getY() + y, Center.getZ() + z);
                            BlockState BS = world.getBlockState(mutablePos);

                            if(BS.isOf(blockOfChoice)){
                                world.breakBlock(mutablePos,false);
                            }
                        }

                    }
                }
            }


        }
    }
}
