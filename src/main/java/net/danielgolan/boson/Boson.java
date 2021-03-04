package net.danielgolan.boson;

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
    }
}
