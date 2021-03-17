package net.danielgolan.boson.integration;

import net.danielgolan.boson.Boson;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Items;

public class FuelIntegration {
    public static void registerFuels() {
        FuelRegistry.INSTANCE.add(Items.MAGMA_BLOCK, Boson.inTicks(2.5f));
    }
}
