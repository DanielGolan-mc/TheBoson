package net.danielgolan.boson.enchantments;

import net.danielgolan.boson.Boson;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;

public class IceAspect extends Enchantment {
    public IceAspect(EnchantmentTarget target) {
        super(Rarity.VERY_RARE, target, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND,
                EquipmentSlot.OFFHAND
        });
    }

    @Override
    public int getMinPower(int level) {
        return 10 * level;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!(target instanceof LivingEntity))
            return;

        LivingEntity livingTarget = (LivingEntity) target;
        World world = target.world;

        world.setBlockState(target.getBlockPos(), Blocks.POWDER_SNOW.getDefaultState());

        if (level > 1)
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, Boson.inTicks(level - 1), level));
    }
}
