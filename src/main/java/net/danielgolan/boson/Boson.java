package net.danielgolan.boson;

import net.danielgolan.boson.integration.FuelIntegration;
import net.danielgolan.boson.integration.LootTablesIntegration;
import net.danielgolan.boson.registry.BosonElements;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class Boson implements ModInitializer, ClientModInitializer {

    public static final String BOSON_MOD_ID = "boson";
    public static final String MINECRAFT_ID = "minecraft";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        BosonElements.register();
        BosonElements.registerCreativeGroups();
        LootTablesIntegration.modifyLootTables();
        FuelIntegration.registerFuels();
    }

    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {

    }

    public static int inTicks(double seconds) {
        return (int) (seconds * 20);
    }

    public static int inTicks(float minutes) {
        return inTicks(minutes * 60.0);
    }
}
