package net.danielgolan.boson.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class Material extends Item {
    public Material() {
        super(new Settings()
                .group(ItemGroup.MATERIALS)
        );
    }
}
