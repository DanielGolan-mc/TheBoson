package net.danielgolan.boson.lib.objects;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class ToolSet implements ToolMaterial, ArmorMaterial {
    public static final int[] BASE_DURABILITY = new int[] {13, 15, 16, 11};
    public static final int[] PROTECTION_AMOUNTS = new int[] {2, 5, 6, 3};

    public final int DURABILITY, MINING_LEVEL, ENCHANTABILITY;
    public final float MINING_LEVEL_SPEED_MULTIPILER, ATTACK_DAMAGE, TOUGHNESS, KNOCK_BACK_RESISTANCE;
    public final String NAME;
    public final Ingredient REPAIR_INGREDIENT;
    public final SoundEvent EQUIP_SOUND;

    public ToolSet(int durability, int mining_level, int enchantability, float mining_level_speed_multipiler,
                   float attack_damage, float toughness, float knock_back_resistance, String name, Ingredient repair_ingredient, SoundEvent equipSound) {
        DURABILITY = durability;
        MINING_LEVEL = mining_level;
        ENCHANTABILITY = enchantability;
        MINING_LEVEL_SPEED_MULTIPILER = mining_level_speed_multipiler;
        ATTACK_DAMAGE = attack_damage;
        TOUGHNESS = toughness;
        KNOCK_BACK_RESISTANCE = knock_back_resistance;
        NAME = name;
        REPAIR_INGREDIENT = repair_ingredient;
        EQUIP_SOUND = equipSound;
    }

    @Override
    public int getDurability() {
        return DURABILITY;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * getDurability();
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return MINING_LEVEL_SPEED_MULTIPILER;
    }

    @Override
    public float getAttackDamage() {
        return ATTACK_DAMAGE;
    }

    @Override
    public int getMiningLevel() {
        return MINING_LEVEL;
    }

    @Override
    public int getEnchantability() {
        return ENCHANTABILITY;
    }

    @Override
    public SoundEvent getEquipSound() {
        return EQUIP_SOUND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return REPAIR_INGREDIENT;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public float getToughness() {
        return TOUGHNESS;
    }

    @Override
    public float getKnockbackResistance() {
        return KNOCK_BACK_RESISTANCE;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_AMOUNTS[slot.getEntitySlotId()];
    }

    public class Pickaxe extends PickaxeItem {
        public Pickaxe(int attackDamage, float attackSpeed) {
            super(ToolSet.this, attackDamage, attackSpeed, new Settings().group(ItemGroup.TOOLS));
        }
    }

    public class Sword extends SwordItem {
        public Sword(int attackDamage, float attackSpeed) {
            super(ToolSet.this, attackDamage, attackSpeed, new Settings().group(ItemGroup.COMBAT));
        }
    }

    public class Shovel extends ShovelItem {
        public Shovel(int attackDamage, float attackSpeed) {
            super(ToolSet.this, attackDamage, attackSpeed, new Settings().group(ItemGroup.TOOLS));
        }
    }

    public class Axe extends AxeItem {
        public Axe(int attackDamage, float attackSpeed) {
            super(ToolSet.this, attackDamage, attackSpeed, new Settings().group(ItemGroup.TOOLS));
        }
    }

    public class Hoe extends HoeItem {
        public Hoe(int attackDamage, float attackSpeed) {
            super(ToolSet.this, attackDamage, attackSpeed, new Settings().group(ItemGroup.TOOLS));
        }
    }

    public class Armor extends ArmorItem {
        public Armor(EquipmentSlot equipmentSlot) {
            super(ToolSet.this, equipmentSlot, new Settings().group(ItemGroup.COMBAT));
        }
    }

    public static class WhiteGold extends ToolSet {
        public static final Item[] WHITE_GOLD_INGREDIENTS = new Item[]{
                Items.GOLD_INGOT, Items.QUARTZ
        };

        public WhiteGold() {
            super(2100, 4, 40, 5, 1, 3,
                    1, "white_gold", Ingredient.ofItems(WhiteGold.WHITE_GOLD_INGREDIENTS),
                    SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
        }
    }
}
