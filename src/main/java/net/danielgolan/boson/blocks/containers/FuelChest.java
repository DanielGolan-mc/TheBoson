package net.danielgolan.boson.blocks.containers;

import net.danielgolan.boson.registry.BosonElements;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FuelChest extends BarrelBlock {
    protected static final VoxelShape COLLIDING_BOX_UP = Block.createCuboidShape(2, 0, 2, 12, 10, 12).simplify();

    public FuelChest() {
        this(Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F));
    }

    public FuelChest(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FuelChest.Entity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FuelChest.Entity) {
                player.openHandledScreen((FuelChest.Entity)blockEntity);
                player.incrementStat(Stats.OPEN_BARREL);
                PiglinBrain.onGuardedBlockInteracted(player, true);
            }

            return ActionResult.CONSUME;
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case UP : {
                return COLLIDING_BOX_UP;
            }
            default : {
                return super.getOutlineShape(state, world, pos, context);
            }
        }
    }

    public static class Entity extends BarrelBlockEntity {
        public static BlockEntityType<Entity> TYPE;
        private DefaultedList<ItemStack> inventory;

        public static void register(){
            TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "boson:fuel_chest",
                    FabricBlockEntityTypeBuilder.create(FuelChest.Entity::new, BosonElements.Blocks.FUEL_CHEST).build());
        }

        public Entity(BlockPos pos, BlockState state) {
            super(pos, state);
            this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
        }

        @Override
        protected Text getContainerName() {
            if (hasCustomName()) return getCustomName();
            else return new TranslatableText("container.boson.fuel_chest");
        }

        public NbtCompound writeNbt(NbtCompound nbt) {
            super.writeNbt(nbt);
            if (!this.serializeLootTable(nbt)) {
                Inventories.writeNbt(nbt, this.inventory);
            }

            return nbt;
        }

        public void readNbt(NbtCompound nbt) {
            super.readNbt(nbt);
            this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
            if (!this.deserializeLootTable(nbt)) Inventories.readNbt(nbt, this.inventory);

        }

        public int size() {
            return 54;
        }

        protected DefaultedList<ItemStack> getInvStackList() {
            return this.inventory;
        }

        protected void setInvStackList(DefaultedList<ItemStack> list) {
            this.inventory = list;
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
