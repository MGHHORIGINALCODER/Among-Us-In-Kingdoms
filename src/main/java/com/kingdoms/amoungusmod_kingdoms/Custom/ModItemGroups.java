package com.kingdoms.amoungusmod_kingdoms.Custom;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup PINK_GARNET_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Amoungusmod_kingdoms.MOD_ID, "pink_garnet_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.CONFIG_STICK_TASKS))
                    .displayName(Text.translatable("itemgroup.amoungusmod_kingdoms.g1"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.EMERGENCY_MEETING);
                        entries.add(ModItems.REPORT);
                        entries.add(ModItems.MURDERED);
                        entries.add(ModItems.START);
                        entries.add(ModItems.VOTE);
                        entries.add(ModItems.VOTE_CHECKER);
                        entries.add(ModItems.SAB);
                        entries.add(ModItems.VENT_USE);
                        entries.add(ModItems.ALIVE_VIEW);
                        entries.add(ModItems.TASK1);
                        entries.add(ModItems.TASK2);
                        entries.add(ModItems.TASK3);
                        entries.add(ModItems.TASK4);
                        entries.add(ModItems.TASK5);
                        entries.add(ModItems.TASK6);
                        entries.add(ModItems.TASK7);
                        entries.add(ModItems.TASK8);
                        entries.add(ModItems.TASK9);
                        entries.add(ModItems.TASK10);
                        entries.add(ModItems.PLAYER_CHIBI_HALEY);
                        entries.add(ModItems.DATA_STICK);
                        entries.add(ModItems.CONFIG_STICK_TASKS);
                        entries.add(ModItems.CONFIG_STICK_SET);
                        entries.add(ModItems.CONFIG_STICK_MEETING_CALL);
                        entries.add(ModItems.CONFIG_STICK_MEETING_COOL);
                        entries.add(ModItems.CONFIG_STICK_SAB_COOL);
                        entries.add(ModItems.CONFIG_STICK_KILL_COOL);
                        entries.add(ModItems.CONFIG_STICK_VENT_TP);
                        entries.add(ModItems.CONFIG_STICK_ENGINEERS);
                        entries.add(ModItems.CONFIG_STICK_SCIENTIST);
                        entries.add(ModItems.CONFIG_STICK_IMPOSTERS);
                        entries.add(ModItems.PURP_EGG);


                    }).build());
    public static final ItemGroup GAME_BLOCKS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Amoungusmod_kingdoms.MOD_ID, "game_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.CONFIG_STICK_TASKS))
                    .displayName(Text.translatable("itemgroup.amoungusmod_kingdoms.game_blocks_main"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.DEATH_BLOCK);
                        entries.add(ModBlocks.DOOR_BLOCK);
                        entries.add(ModItems.AUTO_BLOCK_ITEM);
                        entries.add(ModItems.FIX_DOORS_ITEM);


                    }).build());




    public static void registerItemGroups() {
        Amoungusmod_kingdoms.LOGGER.info("Registering Item Groups for " + Amoungusmod_kingdoms.MOD_ID);
    }
}
