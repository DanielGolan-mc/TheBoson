package net.danielgolan.boson.items;

import net.danielgolan.boson.Boson;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Food extends Item {
    public Food(int hunger, float saturation) {
        this(new FoodComponent.Builder(), hunger, saturation);
    }

    public Food(FoodComponent.Builder builder, int hunger, float saturation){
        super(new Settings().
                group(ItemGroup.FOOD)
                .food(builder.hunger(hunger).alwaysEdible().saturationModifier(saturation).build())
        );
    }

    public static class RubyApple extends Food{
        public RubyApple() {
            super(new FoodComponent.Builder()
                            .snack()
                            .alwaysEdible()
                            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, Boson.inTicks(5), 2), 1)
                            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, Boson.inTicks(3), 5), 1)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, Boson.inTicks(5), 5), 0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, Boson.inTicks(1), 0), 1)
                            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, Boson.inTicks(4)), 0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH), 0.5f),
                    12, 20);
        }
    }
}
