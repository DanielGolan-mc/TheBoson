package net.danielgolan.boson.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class DecorationBlock extends BlockItem {
    public DecorationBlock(Block block) {
        super(block, new Settings()
                .group(ItemGroup.BUILDING_BLOCKS));
    }
}
