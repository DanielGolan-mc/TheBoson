package net.danielgolan.boson.blocks.containers;

import net.minecraft.block.ChestBlock;
import net.minecraft.block.Stainable;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class StainedChest extends ChestBlock implements Stainable {
    private @NotNull DyeColor color;

    public StainedChest(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        this(settings, supplier, DyeColor.WHITE);
    }

    public StainedChest(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier, @NotNull DyeColor color) {
        super(settings, supplier);
        this.color = color;
    }

    @Override
    public @NotNull DyeColor getColor() {
        return color;
    }

    public void setColor(@NotNull DyeColor color) {
        if (this.color == null) throw new NullPointerException();
        else this.color = color;
    }

    public void toggleColor() {
        switch (color) {
            case WHITE : {
                this.color = DyeColor.ORANGE;
            } case ORANGE : {
                this.color = DyeColor.MAGENTA;
            } case MAGENTA : {
                this.color = DyeColor.LIGHT_BLUE;
            } case LIGHT_BLUE : {
                this.color = DyeColor.YELLOW;
            } case YELLOW : {
                this.color = DyeColor.LIME;
            } case LIME : {
                this.color = DyeColor.PINK;
            } case PINK : {
                this.color = DyeColor.GRAY;
            } case GRAY : {
                this.color = DyeColor.LIGHT_GRAY;
            } case LIGHT_GRAY : {
                this.color = DyeColor.CYAN;
            } case CYAN : {
                this.color = DyeColor.PURPLE;
            } case PURPLE : {
                this.color = DyeColor.BLUE;
            } case BLUE : {
                this.color = DyeColor.BROWN;
            } case BROWN : {
                this.color = DyeColor.GREEN;
            } case GREEN : {
                this.color = DyeColor.RED;
            } case RED : {
                this.color = DyeColor.BLACK;
            } case BLACK : {
                this.color = DyeColor.WHITE;
            }
        };
    }

    @Override
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        super.onLandedUpon(world, pos, entity, distance);
        toggleColor();
    }
}
