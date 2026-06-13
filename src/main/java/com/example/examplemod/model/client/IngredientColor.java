package com.example.examplemod.model.client;

import com.example.examplemod.ModComponents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IngredientColor {
    private static final int DEFAULT = 0xffeeeeee;

    public static int getTint(ItemStack stack, int tintIndex) {
        if (tintIndex == -1) {
            return DEFAULT;
        }
        else {
            return stack.getOrDefault(ModComponents.INGREDIENT_TINT, DEFAULT);
        }
    }
}
