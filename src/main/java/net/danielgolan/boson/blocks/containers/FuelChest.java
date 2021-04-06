package net.danielgolan.boson.blocks.containers;

import net.danielgolan.boson.registry.BosonElements;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.entity.AbstractFurnaceBlockEntity.canUseAsFuel;

public class FuelChest extends BarrelBlock implements Waterloggable {
    protected static final VoxelShape COLLIDING_BOX_UP = Block.createCuboidShape(2, 0, 2, 14, 10, 14).simplify();

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

                if (state.get(FACING) != Direction.UP) {
                    player.incrementStat(Stats.OPEN_BARREL);
                    PiglinBrain.onGuardedBlockInteracted(player, true);
                }
            } else if (blockEntity instanceof BarrelBlockEntity) super.onUse(state, world, pos, player, hand, hit);

            return ActionResult.CONSUME;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FuelChest.Entity) {
                ((FuelChest.Entity) blockEntity).setCustomName(itemStack.getName());
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(FACING) == Direction.UP) {
            return COLLIDING_BOX_UP;
        } else {
            return super.getOutlineShape(state, world, pos, context);
        }
    }

    public static class Entity extends LootableContainerBlockEntity {
        public static BlockEntityType<Entity> TYPE;
        protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
        protected ChestStateManager stateManager = new ChestStateManager() {
            protected void onChestOpened(World world, BlockPos pos, BlockState state) {
                if (state.get(FACING) != Direction.UP)
                    FuelChest.Entity.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
                FuelChest.Entity.this.setOpen(state, true);
            }

            protected void onChestClosed(World world, BlockPos pos, BlockState state) {
                if (state.get(FACING) != Direction.UP)
                    FuelChest.Entity.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
                FuelChest.Entity.this.setOpen(state, false);
            }

            protected void onInteracted(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            protected boolean isPlayerViewing(PlayerEntity player) {
                if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                    return inventory == FuelChest.Entity.this;
                } else {
                    return false;
                }
            }
        };


        public static void register(){
            TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "boson:fuel_chest",
                    FabricBlockEntityTypeBuilder.create(FuelChest.Entity::new, BosonElements.Blocks.FUEL_CHEST).build());
        }

        public Entity(BlockPos pos, BlockState state) {
            super(TYPE, pos, state);
        }

        @Override
        protected Text getContainerName() {
            if (hasCustomName()) return getCustomName();
            else return new TranslatableText(world == null || world.getBlockState(pos).get(FACING) == Direction.UP
                    ? "container.boson.fuel_pile" : "container.boson.fuel_chest");
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
        protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
            return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, this);
        }

        private void setOpen(BlockState state, boolean open) {
            this.world.setBlockState(this.getPos(), state.with(BarrelBlock.OPEN, open), 3);
        }

        private void playSound(BlockState state, SoundEvent soundEvent) {
            Vec3i vec3i = state.get(FuelChest.FACING).getVector();
            double d = (double)this.pos.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
            double e = (double)this.pos.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
            double f = (double)this.pos.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
            this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
        }

        @Override
        public boolean isValid(int slot, ItemStack stack) {
            return (canUseAsFuel(stack) || stack.isOf(Items.BUCKET)) && !stack.hasCustomName();
        }

        public void onOpen(PlayerEntity player) {
            if (!player.isSpectator()) {
                this.stateManager.openChest(player, this.getWorld(), this.getPos(), this.getCachedState());
            }

        }

        public void onClose(PlayerEntity player) {
            if (!player.isSpectator()) {
                this.stateManager.closeChest(player, this.getWorld(), this.getPos(), this.getCachedState());
            }

        }

        public void tick() {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }
}
