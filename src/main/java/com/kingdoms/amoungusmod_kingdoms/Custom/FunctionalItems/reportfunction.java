package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;

import com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.AutoRounds;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.LockBlock;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.UpdateAfterCall;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.block.BlockState;
import net.minecraft.text.MutableText;

public class reportfunction extends Item {
    public reportfunction(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        LockBlock.On_ = false;
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(context.getBlockPos());
        PlayerEntity user = context.getPlayer();

        UpdateAfterCall.DisableSelf(world, user.getBlockPos(), 500, ModBlocks.LOCKER_BLOCK);


        if (blockState.isOf(ModBlocks.DEATH_BLOCK) && Customstorage.CAN_KILL) {

            world.setBlockState(context.getBlockPos(), Blocks.AIR.getDefaultState());


            for (PlayerEntity p : world.getPlayers()) {
                if (p.isDead()) {
                    MutableText message = Text.literal(p.getName().getString() + " Is Dead");
                    world.getServer().getPlayerManager().broadcast(message, false);
                }
            }


            if (!world.isClient) {
                AutoRounds.CallMeeting(context.getPlayer(), world, context.getHand(), false);
                return ActionResult.PASS;
            } else {
                return ActionResult.FAIL;
            }
        } else {
            return ActionResult.FAIL;
        }
    }
}
