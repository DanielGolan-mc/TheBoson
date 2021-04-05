package net.danielgolan.boson.blocks.containers;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FuelChest extends BarrelBlock {
    public FuelChest() {
        this(Settings.of(Material.WOOD).strength(2.5F).sounds(BlockSoundGroup.WOOD));
    }

    public FuelChest(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Entity(pos, state);
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return super.createScreenHandlerFactory(state, world, pos);
    }

    protected static class Entity extends BarrelBlockEntity {
        protected Entity(BlockPos pos, BlockState state) {
            super(pos, state);
        }

        @Override
        protected Text getContainerName() {
            return new TranslatableText("container.boson.fuel_chest");
        }

        @Override
        public Text getDisplayName() {
            return super.getDisplayName();
        }

        @Override
        protected net.minecraft.screen.ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
            return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, this);
        }
    }
}
