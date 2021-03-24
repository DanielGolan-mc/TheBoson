package net.danielgolan.boson.integration;

import net.danielgolan.boson.Boson;
import net.danielgolan.boson.registry.BosonElements;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public final class LootTablesIntegration {
    public static final Identifier EMERALD_ORE_LOOT_TABLE_ID = new Identifier(Boson.MINECRAFT_ID, "blocks/emerald_ore");

    public static void modifyLootTables() {
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
            if (EMERALD_ORE_LOOT_TABLE_ID.equals(id)) {
                FabricLootPoolBuilder emeraldOreLootTablePole = FabricLootPoolBuilder.builder()
                        .rolls(UniformLootNumberProvider.create(0, 1))
                        .with(ItemEntry.builder(Items.GOLD_NUGGET))
                        .with(ItemEntry.builder(BosonElements.Items.RUBY))
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1)).build());
                supplier.withPool(emeraldOreLootTablePole.build());
            }
        }));
    }
}
