package com.kingdoms.amoungusmod_kingdoms.Custom;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.AlivePeople;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigEngineerSize;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigImposters;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigKillCooldown;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigMeetingCalls;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigMeetingCooldown;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigRaidSize;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigSabCooldown;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigScientistSize;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.ConfigTasks;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.LocationConfig;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.SetConfig;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.callemergancy;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.murdered;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.reportfunction;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.sab;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.start_function;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.vote;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalItems.vote_checker;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task1;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task10;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task2;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task3;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task4;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task5;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task6;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task7;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task8;
import com.kingdoms.amoungusmod_kingdoms.Custom.Tasks.task9;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

public class ModItems {
    public static final Item EMERGENCY_MEETING = registerItem("emergency_meeting", new callemergancy((new Item.Settings()).maxCount(5)));
    public static final Item REPORT = registerItem("report_meeting", new reportfunction((new Item.Settings()).maxCount(1)));
    public static final Item MURDERED = registerItem("murdered", new murdered((new Item.Settings()).maxCount(1)));
    public static final Item START = registerItem("start_button", new start_function((new Item.Settings()).maxCount(1)));
    public static final Item VOTE = registerItem("vote_button", new vote((new Item.Settings()).maxCount(1)));
    public static final Item VOTE_CHECKER = registerItem("vote_checker", new vote_checker((new Item.Settings()).maxCount(1)));
    public static final Item SAB = registerItem("sab", new sab((new Item.Settings()).maxCount(1)));
    public static final Item DATA_STICK = registerItem("data_stick", new LocationConfig((new Item.Settings()).maxCount(1)));
    public static final Item VENT_USE = registerItem("vent_use", new Item((new Item.Settings()).maxCount(1)));
    public static final Item ALIVE_VIEW = registerItem("alive_view", new AlivePeople((new Item.Settings()).maxCount(1)));

    public static final Item VENT_BLOCK_ITEM;
    public static final Item FIX_DOORS_ITEM;
    public static final Item AUTO_BLOCK_ITEM;
    public static final Item TASK1;
    public static final Item TASK2;
    public static final Item TASK3;
    public static final Item TASK4;
    public static final Item TASK5;
    public static final Item TASK6;
    public static final Item TASK7;
    public static final Item TASK8;
    public static final Item TASK9;
    public static final Item TASK10;
    public static final Item CONFIG_STICK_TASKS;
    public static final Item CONFIG_STICK_IMPOSTERS;
    public static final Item CONFIG_STICK_SET;
    public static final Item CONFIG_STICK_MEETING_CALL;
    public static final Item CONFIG_STICK_MEETING_COOL;
    public static final Item CONFIG_STICK_SAB_COOL;
    public static final Item CONFIG_STICK_ENGINEERS;
    public static final Item CONFIG_STICK_SCIENTIST;
    public static final Item CONFIG_STICK_KILL_COOL;
    public static final Item CONFIG_STICK_VENT_TP;
    public static final Item PLAYER_CHIBI_HALEY;

    public ModItems() {
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("amoungusmod_kingdoms", name), item);
    }

    public static void registerModItems() {
        Amoungusmod_kingdoms.LOGGER.info("Registering Mod items for: amoungusmod_kingdoms");
    }

    static {
        VENT_BLOCK_ITEM = registerItem("door_block1", new BlockItem(ModBlocks.DOOR_BLOCK, new Item.Settings()));
        FIX_DOORS_ITEM = registerItem("fix_doors_item", new BlockItem(ModBlocks.FIX_DOOR, new Item.Settings()));
        AUTO_BLOCK_ITEM = registerItem("auto_block_item", new BlockItem(ModBlocks.AUTO_BLOCK, new Item.Settings()));
        TASK1 = registerItem("task1", new task1((new Item.Settings()).maxCount(1)));
        TASK2 = registerItem("task2", new task2((new Item.Settings()).maxCount(1)));
        TASK3 = registerItem("task3", new task3((new Item.Settings()).maxCount(1)));
        TASK4 = registerItem("task4", new task4((new Item.Settings()).maxCount(1)));
        TASK5 = registerItem("task5", new task5((new Item.Settings()).maxCount(1)));
        TASK6 = registerItem("task6", new task6((new Item.Settings()).maxCount(1)));
        TASK7 = registerItem("task7", new task7((new Item.Settings()).maxCount(1)));
        TASK8 = registerItem("task8", new task8((new Item.Settings()).maxCount(1)));
        TASK9 = registerItem("task9", new task9((new Item.Settings()).maxCount(1)));
        TASK10 = registerItem("task10", new task10((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_TASKS = registerItem("cs_tasks", new ConfigTasks((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_IMPOSTERS = registerItem("cs_imposters", new ConfigImposters((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_SET = registerItem("cs_set", new SetConfig((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_MEETING_CALL = registerItem("cs_meeting_calls", new ConfigMeetingCalls((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_MEETING_COOL = registerItem("cs_meeting_cool", new ConfigMeetingCooldown((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_SAB_COOL = registerItem("cs_sab_cool", new ConfigSabCooldown((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_ENGINEERS = registerItem("cs_engineers", new ConfigEngineerSize((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_SCIENTIST = registerItem("cs_scientist", new ConfigScientistSize((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_KILL_COOL = registerItem("cs_kill_cool", new ConfigKillCooldown((new Item.Settings()).maxCount(1)));
        CONFIG_STICK_VENT_TP = registerItem("cs_vent_tp", new ConfigRaidSize((new Item.Settings()).maxCount(1)));
        // Handled the trailing field cut off from compilation source safely
        PLAYER_CHIBI_HALEY = registerItem("player_chibi_haley", new Item((new Item.Settings()).maxCount(1)));
    }
}
