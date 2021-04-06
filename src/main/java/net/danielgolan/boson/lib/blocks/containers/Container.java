package net.danielgolan.boson.lib.blocks.containers;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestStateManager;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class Container<E extends Container<E>.Entity> extends BlockWithEntity {
    private final Provider<E> provider;

    protected Container(Settings settings, Provider<E> provider) {
        super(settings);
        this.provider = provider;
    }

    public abstract void onBlockPlaced();

    public int size() {
        return 27;
    }

    @Nullable
    @Override
    public final BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return provider.newInstance();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            provider.ifInstance(blockEntity, entity -> player.openHandledScreen(provider.asInstance(entity)));

            return ActionResult.CONSUME;
        }
    }

    @Override
    public final void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        onBlockPlaced();

        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            provider.ifInstance(blockEntity, entity -> entity.setCustomName(itemStack.getName()));
        }
    }

    protected abstract class Entity extends LootableContainerBlockEntity {
        protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        protected final ChestStateManager stateManager = new ChestStateManager() {
            protected final void onChestOpened(World world, BlockPos pos, BlockState state) {
                Container.Entity.this.onChestOpened(world, pos, state);
            }
            protected final void onChestClosed(World world, BlockPos pos, BlockState state) {
                Container.Entity.this.onChestClosed(world, pos, state);
            }
            protected final void onInteracted(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
                Container.Entity.this.onInteracted(world, pos, state, oldViewerCount, newViewerCount);
            }
            protected final boolean isPlayerViewing(PlayerEntity player) {
                return Container.Entity.this.isPlayerViewing(player);
            }
        };
        private boolean open;

        protected Entity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
            super(blockEntityType, blockPos, blockState);
        }

        protected void onChestOpened (World world, BlockPos pos, BlockState state) {
            playSound(state, provider.provideSound(SoundType.open));
            setOpen(state, true);
        }

        protected void onChestClosed (World world, BlockPos pos, BlockState state) {
            playSound(state, provider.provideSound(SoundType.close));
            setOpen(state, false);
        }

        protected final void onInteracted(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {}

        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                return inventory == this;
            } else {
                return false;
            }
        }

        @Override
        public final int size() {
            return Container.this.size();
        }

        public abstract void playSound(BlockState state, SoundEvent soundEvent);

        public void setOpen(BlockState state, boolean open){
            this.open = open;
        }

        public boolean isOpen() {
            return open;
        }

        @Override
        protected final ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
            switch (size() / 9) {
                case 1 : return GenericContainerScreenHandler.createGeneric9x1(syncId, playerInventory);
                case 2 : return GenericContainerScreenHandler.createGeneric9x2(syncId, playerInventory);
                case 3 : return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory);
                case 4 : return GenericContainerScreenHandler.createGeneric9x4(syncId, playerInventory);
                case 5 : return GenericContainerScreenHandler.createGeneric9x5(syncId, playerInventory);
                case 6 : return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory);

                default : throw new IllegalArgumentException("cannot handle specified size: " + size() + '\n' + this + '\n');
            }
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
            if (!this.deserializeLootTable(nbt))
                Inventories.readNbt(nbt, this.inventory);
        }
    }

    protected interface Provider<E extends Container<E>.Entity> {
        E newInstance();
        boolean isInstance(BlockEntity blockEntity);
        default E asInstance(BlockEntity blockEntity){
            return (E) blockEntity;
        }
        default void ifInstance(BlockEntity blockEntity, Function<E> function) {
            if (isInstance(blockEntity)) function.then(asInstance(blockEntity));
        }

        default SoundEvent provideSound(Container.SoundType soundType){
            switch (soundType){
                case open  : return SoundEvents.BLOCK_CHEST_OPEN;
                case close : return SoundEvents.BLOCK_CHEST_CLOSE;

                default    : return null;
            }
        }

        interface Function<E extends Container<E>.Entity> {
            void then(E entity);
        }
    }

    enum SoundType {
        open, close
    }
}
