package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;


import com.kingdoms.amoungusmod_kingdoms.Custom.ModItems;
import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class vote extends Item {
    public vote(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        super.useOnEntity(stack, user, entity, hand);
        World world = user.getEntityWorld();

        if (!world.isClient()) {
            ItemStack itemStack = user.getStackInHand(hand);


            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "give " + user.getName().getString() + " minecraft:player_head{SkullOwner:{Name:\"" + entity.getName().getString() + "\"}}"
            );
            for (ItemStack inv : user.getInventory().main) {
                if (inv.itemMatches(ModItems.VOTE.getRegistryEntry())) {
                    itemStack.decrement(1);
                }
            }


                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        ModSounds.VOTE_SOUND,
                        SoundCategory.PLAYERS,
                        1.0f,
                        1.0f
                );


            }
            return ActionResult.SUCCESS;


        }
    }
