package net.danielgolan.boson.registry;

import net.danielgolan.boson.Boson;
import net.danielgolan.boson.blocks.Stone;
import net.danielgolan.boson.items.BuildingBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
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

    public static final class Blocks {
        public static final Block DRIP_STONE_BRICKS = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block DRIP_STONE_PILLAR = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block DRIP_STONE_CHISEL = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block DRIP_STONE_SMOOTH = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block DRIP_STONE_TILES  = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);

        public static void register() {
            BosonElements.register("dripstone_bricks", DRIP_STONE_BRICKS);
            BosonElements.register("dripstone_pillar", DRIP_STONE_PILLAR);
            BosonElements.register("dripstone_tiles", DRIP_STONE_TILES);
            BosonElements.register("chiseled_dripstone", DRIP_STONE_CHISEL);
            BosonElements.register("smooth_dripstone", DRIP_STONE_SMOOTH);
        }
    }

    public static final class Items {
        public static final Item DRIP_STONE_BRICKS = new BuildingBlock(Blocks.DRIP_STONE_BRICKS);
        public static final Item DRIP_STONE_PILLAR = new BuildingBlock(Blocks.DRIP_STONE_PILLAR);
        public static final Item DRIP_STONE_CHISEL = new BuildingBlock(Blocks.DRIP_STONE_CHISEL);
        public static final Item DRIP_STONE_SMOOTH = new BuildingBlock(Blocks.DRIP_STONE_SMOOTH);
        public static final Item DRIP_STONE_TILES  = new BuildingBlock(Blocks.DRIP_STONE_TILES);

        public static void register() {
            BosonElements.register("dripstone_bricks", DRIP_STONE_BRICKS);
            BosonElements.register("dripstone_pillar", DRIP_STONE_PILLAR);
            BosonElements.register("dripstone_tiles", DRIP_STONE_TILES);
            BosonElements.register("chiseled_dripstone", DRIP_STONE_CHISEL);
            BosonElements.register("smooth_dripstone", DRIP_STONE_SMOOTH);
        }
    }
}
