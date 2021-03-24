package net.danielgolan.boson.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class Metal extends Block {

    public Metal(int miningLevel, float hardness, float resistance, int luminance, BlockSoundGroup blockSoundGroup){
        super(FabricBlockSettings.
                of(Material.METAL).
                breakByTool(FabricToolTags.PICKAXES, miningLevel).
                requiresTool().
                strength(hardness, resistance).
                sounds(blockSoundGroup).
                luminance(luminance)
        );
    }

    public Metal(int miningLevel, float hardness, float resistance, BlockSoundGroup blockSoundGroup){
        this(miningLevel, hardness, resistance, 0, blockSoundGroup);
    }

    public Metal(int miningLevel, float strength, BlockSoundGroup blockSoundGroup){
        super(FabricBlockSettings.
                of(Material.METAL).
                breakByTool(FabricToolTags.PICKAXES, miningLevel).
                requiresTool().
                strength(strength)
                .sounds(blockSoundGroup)
        );
    }

    public Metal(int miningLevel, float hardness, float resistance, int luminance) {
        this(miningLevel, hardness, resistance, luminance, BlockSoundGroup.METAL);
    }

    public Metal(int miningLevel, float hardness, float resistance) {
        this(miningLevel, hardness, resistance, BlockSoundGroup.METAL);
    }

    public Metal(int miningLevel, float strength){
        this(miningLevel, strength, BlockSoundGroup.METAL);
    }

    public Metal(BlockSoundGroup blockSoundGroup){
        this(2, 3, blockSoundGroup);
    }

    public Metal(){
        this(2, 3);
    }
}
