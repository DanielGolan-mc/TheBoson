package net.danielgolan.boson.registry;

import net.danielgolan.boson.Boson;
import net.danielgolan.boson.blocks.Metal;
import net.danielgolan.boson.items.BuildingBlock;
import net.danielgolan.boson.items.Food;
import net.danielgolan.boson.items.Material;
import net.danielgolan.boson.items.ToolSet;
import net.danielgolan.boson.items.ToolSet.Axe;
import net.danielgolan.boson.items.ToolSet.Hoe;
import net.danielgolan.boson.items.ToolSet.Pickaxe;
import net.danielgolan.boson.items.ToolSet.Shovel;
import net.danielgolan.boson.items.ToolSet.Sword;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class BosonElements {

    public static void register(){
        BosonElements.Blocks.register();
        BosonElements.Items.register();
    }
    private static void register(String path, Item item){
        Registry.register(Registry.ITEM, new Identifier(Boson.BOSON_MOD_ID, path), item);
    }
    private static void register(String path, Block block){
        Registry.register(Registry.BLOCK, new Identifier(Boson.BOSON_MOD_ID, path), block);
    }

    public final static ToolSet WHITE_GOLD = new ToolSet.WhiteGold();

    public static final class Blocks {
        public static final Block RUBY_BLOCK = new Metal(3, 5, 30, 3);

        public static void register() {
            BosonElements.register("ruby_block", RUBY_BLOCK);
        }
    }

    public static final class Items {
        public static final Item RUBY = new Material();
        public static final Item RUBY_APPLE = new Food.RubyApple();

        public static final PickaxeItem WHITE_GOLD_PICKAXE = WHITE_GOLD.new Pickaxe(-1, -2.2f);
        public static final SwordItem WHITE_GOLD_SWORD = WHITE_GOLD.new Sword(2, 4);
        public static final AxeItem WHITE_GOLD_AXE = WHITE_GOLD.new Axe(3, 0);
        public static final ShovelItem WHITE_GOLD_SHOVEL = WHITE_GOLD.new Shovel(-1, -2.2f);
        public static final HoeItem WHITE_GOLD_HOE = WHITE_GOLD.new Hoe(-2, -2.2f);

        public static final BlockItem RUBY_BLOCK = new BuildingBlock(Blocks.RUBY_BLOCK);

        public static void register() {
            BosonElements.register("ruby", RUBY);
            BosonElements.register("ruby_block", RUBY_BLOCK);
            BosonElements.register("ruby_apple", RUBY_APPLE);
            BosonElements.register("white_gold_pickaxe", WHITE_GOLD_PICKAXE);
            BosonElements.register("white_gold_sword", WHITE_GOLD_SWORD);
            BosonElements.register("white_gold_axe", WHITE_GOLD_AXE);
            BosonElements.register("white_gold_shovel", WHITE_GOLD_SHOVEL);
            BosonElements.register("white_gold_hoe", WHITE_GOLD_HOE);
        }
    }
}
