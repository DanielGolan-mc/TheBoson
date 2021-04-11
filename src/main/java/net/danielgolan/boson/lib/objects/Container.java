package net.danielgolan.boson.lib.objects;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockRenderType;
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
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @param <E> the entity which stores the inventory of this container
 * @param <Block> the container block
 */
public abstract class Container<E extends Container.Entity<Block>, Block extends Container<E, Block>>
        extends BlockWithEntity implements FabricBlockEntityTypeBuilder.Factory<E> {
    private Provider<Block> provider;

    protected Container(Settings settings, Provider<Block> provider) {
        super(settings);
        this.provider = provider;
    }

    protected Container(Settings settings) {
        this(settings, Provider.empty());
    }

    public void onBlockPlaced() {};

    public int size() {
        return 27;
    }

    public void setProvider(Provider<Block> provider) {
        this.provider = provider;
    }

    @Nullable
    @Override
    public E createBlockEntity(BlockPos pos, BlockState state) {
        return provider.newInstance(pos, state);
    }

    @Override
    public final E create(BlockPos blockPos, BlockState blockState) {
        return createBlockEntity(blockPos, blockState);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (provider.isInstance(blockEntity))
                player.openHandledScreen(provider.asInstance(blockEntity));

            return ActionResult.CONSUME;
        }
    }

    @Override
    public final void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        onBlockPlaced();

        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (provider.isInstance(blockEntity))
                provider.asInstance(blockEntity).setCustomName(itemStack.getName());
        }
    }

    Provider<Block> getProvider() {
        return provider;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    protected abstract void playSound(BlockState state, SoundEvent soundEvent, World world, BlockPos pos);

    protected abstract Text getContainerTitle(World world, BlockPos pos);

    public boolean isValid(int slot, ItemStack stack) {
        return true;
    }

    /**
     * @param <C> the container type of this entity
     */
    public static class Entity<C extends Container<? extends Entity<C>, C>>
            extends LootableContainerBlockEntity implements NamedScreenHandlerFactory {

        protected DefaultedList<ItemStack> inventory;
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
        private final @NotNull C container;
        private boolean open;

        public Entity(BlockEntityType<?> blockEntityType, @NotNull C c, BlockPos pos, BlockState state) {
            super(blockEntityType, pos, state);
            container = c;
            inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        }

        protected void onChestOpened(World world, BlockPos pos, BlockState state) {
            playSound(state, container.getProvider().provideSound(SoundType.open));
            setOpen(state, true);
        }

        @Override
        public void onOpen(PlayerEntity player) {
            if (player == null) return;

            if (!player.isSpectator()) {
                this.stateManager.openChest(player, this.getWorld(), this.getPos(), this.getCachedState());
            }
        }

        public void onClose(PlayerEntity player) {
            if (player == null) return;

            if (!player.isSpectator()) {
                this.stateManager.closeChest(player, this.getWorld(), this.getPos(), this.getCachedState());
            }
        }

        protected void onChestClosed(World world, BlockPos pos, BlockState state) {
            playSound(state, container.getProvider().provideSound(SoundType.close));
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
            return Objects.requireNonNull(container).size();
        }

        public final void playSound(BlockState state, SoundEvent soundEvent) {
            container.playSound(state, soundEvent, getWorld(), getPos());
        }

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

        @Override
        protected DefaultedList<ItemStack> getInvStackList() {
            return inventory;
        }

        @Override
        protected void setInvStackList(DefaultedList<ItemStack> list) {
            inventory = list;
        }

        @Override
        protected final Text getContainerName() {
            if (hasCustomName()) return getCustomName();
            else return container.getContainerTitle(this.world, this.pos);
        }

        public final @NotNull C getContainer() {
            return container;
        }

        @Nullable
        @Override
        public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
            return super.createMenu(i, playerInventory, playerEntity);
        }

        @Override
        public boolean isValid(int slot, ItemStack stack) {
            return container.isValid(slot, stack);
        }
    }

    /**
     * @param <C> the container this provider returns
     */
    public interface Provider<C extends Container<? extends Container.Entity<C>, C>> {
        static <E extends Container.Entity<C>, C extends Container<E, C>> Provider<C> empty(){
            return new Provider<C>() {};
        }

        default <E extends Container.Entity<C>> E newInstance(BlockPos pos, BlockState state){
            return null;
        }
        default boolean isInstance(BlockEntity blockEntity) {
            return false;
        }
        default <E extends Container.Entity<C>> E asInstance(BlockEntity blockEntity){
            return (E) blockEntity;
        }

        default SoundEvent provideSound(Container.SoundType soundType){
            switch (soundType){
                case open  : return SoundEvents.BLOCK_CHEST_OPEN;
                case close : return SoundEvents.BLOCK_CHEST_CLOSE;

                default    : return null;
            }
        }
    }

    protected enum SoundType {
        open, close
    }
}
