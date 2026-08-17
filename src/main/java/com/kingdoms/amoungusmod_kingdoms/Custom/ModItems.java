package com.kingdoms.amoungusmod_kingdoms.Custom;
import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;

import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.*;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static com.kingdoms.amoungusmod_kingdoms.Custom.ModBlocks.DOOR_BLOCK;

public class ModItems {
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Amoungusmod_kingdoms.MOD_ID, name), item);
    }



    public static final Item EMERGENCY_MEETING = registerItem("emergency_meeting",
            new callemergancy(new Item.Settings()
                    .maxCount(5))); // was: .useCooldown(10)



    public static final Item REPORT = registerItem("report_meeting",
            new reportfunction(new Item.Settings()
                    .maxCount(1)));

    public static final Item MURDERED = registerItem("murdered",
            new murdered(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(30)

    public static final Item START = registerItem("start_button",
            new start_function(new Item.Settings()
                    .maxCount(1)));

    public static final Item VOTE = registerItem("vote_button",
            new vote(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(30)

    public static final Item VOTE_CHECKER = registerItem("vote_checker",
            new vote_checker(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(30)

    public static final Item SAB = registerItem("sab",
            new sab(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(30)

    public static final Item VENT_BLOCK_ITEM = registerItem(
            "door_block1",
            new BlockItem(DOOR_BLOCK, new Item.Settings())
    );



    //tasks
    public static final Item TASK1 = registerItem("task1",
            new task1(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK2 = registerItem("task2",
            new task2(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK3 = registerItem("task3",
            new task3(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK4 = registerItem("task4",
            new task4(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK5 = registerItem("task5",
            new task5(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK6 = registerItem("task6",
            new task6(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK7 = registerItem("task7",
            new task7(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK8 = registerItem("task8",
            new task8(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK9 = registerItem("task9",
            new task9(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    public static final Item TASK10 = registerItem("task10",
            new task10(new Item.Settings()
                    .maxCount(1))); // was: .useCooldown(5)

    // Config
    public static final Item CONFIG_STICK_TASKS = registerItem("cs_tasks",
            new ConfigTasks(new Item.Settings()
                    .maxCount(1)));

    public static final Item CONFIG_STICK_IMPOSTERS = registerItem("cs_imposters",
            new ConfigImposters(new Item.Settings()
                    .maxCount(1)));

    public static final Item CONFIG_STICK_SET = registerItem("cs_set",
            new SetConfig(new Item.Settings()
                    .maxCount(1)));

    public static final Item PLAYER_CHIBI_HALEY= registerItem("player_chibi_haley", new Item(new Item.Settings().maxCount(1)
            .maxDamage(20)
            .rarity(Rarity.EPIC)));





















    public static void registerModItems(){
        Amoungusmod_kingdoms.LOGGER.info("Registering Mod items for: "+ Amoungusmod_kingdoms.MOD_ID);
    }
}