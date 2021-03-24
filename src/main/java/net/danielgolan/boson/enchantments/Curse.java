package net.danielgolan.boson.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class Curse extends Enchantment {
    protected Curse(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public final boolean isCursed() {
        return true;
    }

    @Override
    public final boolean isTreasure() {
        return true;
    }
}
