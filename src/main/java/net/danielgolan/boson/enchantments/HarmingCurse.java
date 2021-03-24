package net.danielgolan.boson.enchantments;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class HarmingCurse extends Curse{
    protected HarmingCurse(Rarity weight) {
        super(weight, EnchantmentTarget.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD
        });
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        user.damage(DamageSource.MAGIC, level);
    }
}
