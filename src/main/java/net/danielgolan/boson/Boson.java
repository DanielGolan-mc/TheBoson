package net.danielgolan.boson;

import net.danielgolan.boson.integration.LootTablesIntegration;
import net.danielgolan.boson.registry.BosonElements;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Items;

public class Boson implements ModInitializer{

    public static final String BOSON_MOD_ID = "boson";
    public static final String MINECRAFT_ID = "minecraft";

    @Override
    public void onInitialize() {
        BosonElements.register();
        LootTablesIntegration.modifyLootTables();

        FuelRegistry.INSTANCE.add(Items.MAGMA_BLOCK, inTicks(2.5f));
    }

    public static long inTicks(double seconds) {
        return (long) (seconds * 20);
    }

    public static int inTicks(float minutes) {
        return (int) ((minutes / 60) * 20);
    }
}
