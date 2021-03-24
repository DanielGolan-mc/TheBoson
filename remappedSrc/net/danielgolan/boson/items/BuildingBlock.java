package net.danielgolan.boson.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

public class BuildingBlock extends BlockItem {

    public BuildingBlock(Block block) {
        super(block, new Settings()
                .group(ItemGroup.BUILDING_BLOCKS));
    }
}
