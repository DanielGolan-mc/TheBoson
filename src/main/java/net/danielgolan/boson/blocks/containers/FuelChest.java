package net.danielgolan.boson.blocks.containers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FuelChest extends Block implements BlockEntityProvider {
    //public static BlockEntityType<FuelEntity> ENTITY_TYPE;

    public static void register(){
        /*
        ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Boson.BOSON_MOD_ID, "fuel_chest"),
                BlockEntityType.Builder.create(new FuelEntity(), BosonElements.Blocks.FUEL_CHEST)
        .build());
        */
    }

    public FuelChest() {
        this(Settings.of(Material.STONE));
    }

    public FuelChest(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    /*
    public static class FuelEntity extends BlockEntity {
        public FuelEntity() {
            super(ENTITY_TYPE);
        }
    }
    */
}
