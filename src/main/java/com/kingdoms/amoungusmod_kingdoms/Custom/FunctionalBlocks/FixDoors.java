package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.Customstorage;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.locks.Lock;

public class FixDoors extends Block {
    public FixDoors(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            ItemStack HeldItem=player.getStackInHand(hand);
            if(HeldItem.isOf(Items.REDSTONE) && Customstorage.CAN_KILL){
                HeldItem.decrement(1);
                LockBlock.On_=true;
                UpdateAfterCall.DisableSelf(player.getWorld(),player.getBlockPos(),15, ModBlocks.LOCKER_BLOCK);
                world.playSound(
                        null,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ(),
                        ModSounds.FIX_SOUND,
                        SoundCategory.MASTER,
                        1, 1,1
                );

            }
        }
        return ActionResult.SUCCESS;
    }
}
