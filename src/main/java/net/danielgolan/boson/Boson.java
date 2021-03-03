package net.danielgolan.boson;

import net.danielgolan.boson.registry.BosonElements;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Boson implements ModInitializer {

    public static final String BOSON_MOD_ID = "boson";

    public static final ItemGroup EXPERIMENTAL = FabricItemGroupBuilder.create(new Identifier(Boson.BOSON_MOD_ID, "experimental"))
            .icon(() -> new ItemStack(Blocks.COMMAND_BLOCK))
            .appendItems(itemStacks -> {
                itemStacks.add(new ItemStack(Blocks.COMMAND_BLOCK));
                itemStacks.add(new ItemStack(Blocks.CHAIN_COMMAND_BLOCK));
                itemStacks.add(new ItemStack(Blocks.REPEATING_COMMAND_BLOCK));
                itemStacks.add(new ItemStack(Items.COMMAND_BLOCK_MINECART));
                itemStacks.add(new ItemStack(Blocks.BARRIER));
                itemStacks.add(new ItemStack(Blocks.SPAWNER));
                itemStacks.add(new ItemStack(Blocks.STRUCTURE_BLOCK));
                itemStacks.add(new ItemStack(Blocks.STRUCTURE_VOID));
                itemStacks.add(new ItemStack(Blocks.JIGSAW));
                itemStacks.add(new ItemStack(Items.FILLED_MAP));
                itemStacks.add(new ItemStack(Items.ENCHANTED_BOOK));
                itemStacks.add(new ItemStack(Items.WRITTEN_BOOK));
                itemStacks.add(new ItemStack(Items.KNOWLEDGE_BOOK));
                itemStacks.add(new ItemStack(Items.DEBUG_STICK));
            }).build();

    @Override
    public void onInitialize() {

        BosonElements.register();
    }
}
