package net.danielgolan.boson.items;

import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;

public class ToolSet implements ToolMaterial {
    public final int DURABILITY, MINING_LEVEL, ENCHANTABILITY;
    public final float MINING_LEVEL_SPEED_MULTIPILER, ATTACK_DAMAGE;
    public final Ingredient REPAIR_INGREDIENT;

    public ToolSet(int durability, int mining_level, int enchantability, float mining_level_speed_multipiler,
                   float attack_damage, Ingredient repair_ingredient) {
        DURABILITY = durability;
        MINING_LEVEL = mining_level;
        ENCHANTABILITY = enchantability;
        MINING_LEVEL_SPEED_MULTIPILER = mining_level_speed_multipiler;
        ATTACK_DAMAGE = attack_damage;
        REPAIR_INGREDIENT = repair_ingredient;
    }

    @Override
    public int getDurability() {
        return DURABILITY;
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
    public Ingredient getRepairIngredient() {
        return REPAIR_INGREDIENT;
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

    public static class WhiteGold extends ToolSet {
        public static final Item[] WHITE_GOLD_INGREDIENTS = new Item[]{
                Items.GOLD_INGOT, Items.QUARTZ
        };

        public WhiteGold() {
            super(2100, 4, 40, 5, 1f, Ingredient.ofItems(WhiteGold.WHITE_GOLD_INGREDIENTS));
        }
    }
}
