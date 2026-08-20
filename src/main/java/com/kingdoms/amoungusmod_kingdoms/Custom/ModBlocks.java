package com.kingdoms.amoungusmod_kingdoms.Custom;

import com.kingdoms.amoungusmod_kingdoms.Amoungusmod_kingdoms;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.AutoRounds;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.Death_Block;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.FixDoors;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.LockBlock;
import com.kingdoms.amoungusmod_kingdoms.Custom.FunctionalBlocks.VentStorageBlock;
import java.util.function.Function;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;

public class ModBlocks {
    public static final Block DEATH_BLOCK = registerBlock("death_block", (settings) ->
            new Death_Block(settings.resistance(10000.0F).dropsNothing().sounds(BlockSoundGroup.AMETHYST_BLOCK))
    );
    public static final Block DOOR_BLOCK = registerBlock("door_block", (settings) ->
            new VentStorageBlock(FabricBlockSettings.create().strength(4.0F))
    );
    public static final Block LOCKER_BLOCK = registerBlock("locker_block", (settings) ->
            new LockBlock(FabricBlockSettings.create().strength(400.0F))
    );
    public static final Block FIX_DOOR = registerBlock("fix_door", (settings) ->
            new FixDoors(FabricBlockSettings.create().strength(400.0F).nonOpaque().emissiveLighting((state, world, pos) -> true))
    );
    public static final Block AUTO_BLOCK = registerBlock("auto_block", (settings) ->
            new AutoRounds(FabricBlockSettings.create().strength(100.0F).nonOpaque().sounds(BlockSoundGroup.AMETHYST_CLUSTER))
    );

    public ModBlocks() {
    }

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function) {
        Block block = function.apply(AbstractBlock.Settings.copy(Blocks.STONE));
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier("amoungusmod_kingdoms", name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier("amoungusmod_kingdoms", name), new BlockItem(block, new Item.Settings()));
        return null;
    }

    public static void registerModBlocks() {
        Amoungusmod_kingdoms.LOGGER.info("Registering Mod Blocks for amoungusmod_kingdoms");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((entries) -> entries.add(DEATH_BLOCK));
    }
}
