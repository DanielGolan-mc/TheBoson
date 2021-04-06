package net.danielgolan.boson.registry;

import net.danielgolan.boson.Boson;
import net.danielgolan.boson.blocks.Stone;
import net.danielgolan.boson.blocks.containers.FuelChest;
import net.danielgolan.boson.blocks.containers.StainedChest;
import net.danielgolan.boson.enchantments.IceAspect;
import net.danielgolan.boson.gameplay.CreativeMode;
import net.danielgolan.boson.items.BuildingBlock;
import net.danielgolan.boson.items.DecorationBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class BosonElements {

    public static void register(){
        Blocks.register();
        Items.register();
        Enchantments.register();
        FuelChest.Entity.register();
    }
    public static void register(String path, Item item){
        Registry.register(Registry.ITEM, new Identifier(Boson.BOSON_MOD_ID, path), item);
    }
    public static void register(String path, Block block){
        Registry.register(Registry.BLOCK, new Identifier(Boson.BOSON_MOD_ID, path), block);
    }
    public static void register(String path, Enchantment enchantment){
        Registry.register(Registry.ENCHANTMENT, new Identifier(Boson.BOSON_MOD_ID, path), enchantment);
    }
    public static void register(String path, BlockEntityType<? extends BlockEntity> blockEntityType){
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Boson.BOSON_MOD_ID, path), blockEntityType);
    }
    public static void registerCreativeGroups(){
        System.out.print(CreativeMode.EXPERIMENTAL.getName() + ": " +
                (CreativeMode.EXPERIMENTAL.isTopRow() ? "Top" : "Bottom") + "row");
    }

    public static final class Blocks {
        public static final Block DRIP_STONE_BRICKS = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block DRIP_STONE_PILLAR = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block DRIP_STONE_CHISEL = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block DRIP_STONE_SMOOTH = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block DRIP_STONE_TILES  = new Stone(BlockSoundGroup.DRIPSTONE_BLOCK);
        public static final Block FUEL_CHEST = new FuelChest();
        public static final Block STAINED_CHEST = new StainedChest(AbstractBlock.Settings.of(Material.GLASS)
                .strength(2.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK), () -> BlockEntityType.CHEST);

        public static void register() {
            BosonElements.register("dripstone_bricks", DRIP_STONE_BRICKS);
            BosonElements.register("dripstone_pillar", DRIP_STONE_PILLAR);
            BosonElements.register("dripstone_tiles", DRIP_STONE_TILES);
            BosonElements.register("chiseled_dripstone", DRIP_STONE_CHISEL);
            BosonElements.register("smooth_dripstone", DRIP_STONE_SMOOTH);
            BosonElements.register("fuel_chest", FUEL_CHEST);
            BosonElements.register("stained_chest", STAINED_CHEST);
        }
    }

    public static final class Items {
        public static final Item DRIP_STONE_BRICKS = new BuildingBlock(Blocks.DRIP_STONE_BRICKS);
        public static final Item DRIP_STONE_PILLAR = new BuildingBlock(Blocks.DRIP_STONE_PILLAR);
        public static final Item DRIP_STONE_CHISEL = new BuildingBlock(Blocks.DRIP_STONE_CHISEL);
        public static final Item DRIP_STONE_SMOOTH = new BuildingBlock(Blocks.DRIP_STONE_SMOOTH);
        public static final Item DRIP_STONE_TILES  = new BuildingBlock(Blocks.DRIP_STONE_TILES);
        public static final Item FUEL_CHEST        = new DecorationBlock(Blocks.FUEL_CHEST);

        public static void register() {
            BosonElements.register("dripstone_bricks", DRIP_STONE_BRICKS);
            BosonElements.register("dripstone_pillar", DRIP_STONE_PILLAR);
            BosonElements.register("dripstone_tiles", DRIP_STONE_TILES);
            BosonElements.register("chiseled_dripstone", DRIP_STONE_CHISEL);
            BosonElements.register("smooth_dripstone", DRIP_STONE_SMOOTH);
            BosonElements.register("fuel_chest", FUEL_CHEST);
        }
    }

    public static final class Enchantments {
        public static final Enchantment ICE_ASPECT = new IceAspect(EnchantmentTarget.BOW);

        public static void register() {
            BosonElements.register("ice_aspect", ICE_ASPECT);
        }
    }
}
