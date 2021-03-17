package net.danielgolan.boson.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class Stone extends Block {
    public Stone(BlockSoundGroup blockSoundGroup){
        super(FabricBlockSettings.
                of(Material.STONE).
                requiresTool()
                .sounds(blockSoundGroup)
        );
    }
}
