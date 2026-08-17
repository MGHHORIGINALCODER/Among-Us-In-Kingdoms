package com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems;


import com.kingdoms.amoungusmod_kingdoms.Custom.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SetConfig extends Item {
    public SetConfig(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "gamerule doImmediateRespawn true"
            );
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "team add tagVis"
            );

            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "team modify tagVis nametagVisibility never"
            );
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "team add imposters"
            );

            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "team modify imposters nametagVisibility hideForOtherTeams"
            );
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "gamerule fallDamage false"
            );
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "gamerule showDeathMessages false"
            );
            world.getServer().getCommandManager().executeWithPrefix(
                    world.getServer().getCommandSource(),
                    "gamemode survival @a"
            );

            Customstorage.NUMBER_OF_IMPOSTERS=1;
            Customstorage.IMPOSTERS.clear();
            Customstorage.TASKS_PER_PLAYER=3;

            MutableText message = Text.literal("Number of Imposters: "+Customstorage.TASKS_PER_PLAYER);
            world.getServer().getPlayerManager().broadcast(message, false);
            MutableText messagee = Text.literal("Number of Tasks: "+Customstorage.TASKS_PER_PLAYER);
            world.getServer().getPlayerManager().broadcast(messagee, false);
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

}
