package net.danielgolan.boson.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class Shattering extends Enchantment {
    protected Shattering (Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!target.isPlayer()) return;

        target.getArmorItems().forEach(itemStack -> itemStack.damage(1, target.world.random, (ServerPlayerEntity) target));
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if (other instanceof SilkTouchEnchantment) return false;
        else return super.canAccept(other);
    }
}
