package net.danielgolan.boson.blocks.containers;

import net.danielgolan.boson.lib.objects.Container;
import net.danielgolan.boson.registry.BosonElements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Objects;

public class FuelChest
        extends Container<Container.Entity<FuelChest>, FuelChest>
        implements Container.Provider<FuelChest>  {

    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;

    public FuelChest(Settings settings) {
        super(settings);
        setProvider(this);

        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false));
    }

    public FuelChest() {
        this(Settings.of(Material.STONE, MapColor.BLACK).requiresTool().strength(5.0F, 6.0F));
    }

    @Override
    public int size() {
        return 54;
    }

    @Override
    public boolean isInstance(BlockEntity blockEntity) {
        return blockEntity instanceof Container.Entity;
    }

    @Override
    public Container.Entity<FuelChest> newInstance(BlockPos pos, BlockState state) {
        return new Container.Entity<FuelChest>(BosonElements.BlockEntities.FUEL_CHEST, this, pos, state);
    }

    Container.Entity<FuelChest> newInstance() {
        try {
            return (Container.Entity<FuelChest>) Entity.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SoundEvent provideSound(Container.SoundType soundType) {
        if (soundType == SoundType.close) return SoundEvents.BLOCK_BARREL_CLOSE;
        if (soundType == SoundType.open ) return SoundEvents.BLOCK_BARREL_OPEN;

        else return null;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    public void playSound(BlockState state, SoundEvent soundEvent, World world, BlockPos pos) {
        Vec3i vec3i = state.get(FuelChest.FACING).getVector();
        double d = (double) pos.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
        double e = (double) pos.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
        double f = (double) pos.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
        Objects.requireNonNull(world)
                .playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F,
                        world.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected Text getContainerTitle(World world, BlockPos pos) {
        return new TranslatableText(world == null || world.getBlockState(pos).get(FACING) == Direction.UP
                ? "container.boson.fuel_pile" : "container.boson.fuel_chest");
    }
}
