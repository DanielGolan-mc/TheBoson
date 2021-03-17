package net.danielgolan.boson;

import net.danielgolan.boson.integration.FuelIntegration;
import net.danielgolan.boson.integration.LootTablesIntegration;
import net.danielgolan.boson.registry.BosonElements;
import net.fabricmc.api.ModInitializer;

public class Boson implements ModInitializer {

    public static final String BOSON_MOD_ID = "boson";
    public static final String MINECRAFT_ID = "minecraft";

    @Override
    public void onInitialize() {
        BosonElements.register();
        LootTablesIntegration.modifyLootTables();
        FuelIntegration.registerFuels();
    }

    public static int inTicks(double seconds) {
        return (int) (seconds * 20);
    }

    public static int inTicks(float minutes) {
        return inTicks(minutes * 60.0);
    }
}
