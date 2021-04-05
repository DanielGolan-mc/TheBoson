package net.danielgolan.boson.blocks.containers;

import net.danielgolan.boson.registry.BosonElements;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
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

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Entity) player.openHandledScreen((Entity) blockEntity);

            return ActionResult.CONSUME;
        }
    }

    public static class Entity extends BarrelBlockEntity {
        public static BlockEntityType<Entity> TYPE;
        public static void register(){
            /*
            TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "boson:fuel_chest",
                    BlockEntityType.Builder.create(FuelChest.Entity::new, BosonElements.Blocks.FUEL_CHEST)
                            .build(null));
             */
        }

        public Entity(BlockPos pos, BlockState state) {
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
        protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
            return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, this);
        }
    }
}
