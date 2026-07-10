package com.nyan.everybagel.blocks.entities.actions;

import com.nyan.everybagel.blocks.entities.MixingBowlBlockEntity;
import com.nyan.everybagel.blocks.entities.shared.FluidContainerAction;
import com.nyan.everybagel.gateau.mixes.GateauMixes;
import com.nyan.everybagel.recipes.MixingBowlRecipe;
import com.nyan.everybagel.recipes.MixingBowlRecipeInput;
import com.nyan.everybagel.recipes.ModRecipes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.function.Function;

public class MixingBowlActions {
    // TODO: more descriptive return type
    public static void debug(MixingBowlBlockEntity be, Player player) {
        player.displayClientMessage(Component.literal("DEBUG"), false);
        player.displayClientMessage(Component.literal(be.toString()), false);
        if (!player.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
            var stack = player.getItemInHand(InteractionHand.OFF_HAND);
            player.displayClientMessage(Component.literal(stack.getComponents().toString()), false);
        }
    }

    public static void mix(MixingBowlBlockEntity be, RecipeHolder<MixingBowlRecipe> recipe, Player player) {
        be.setRecipe(recipe.value());
        var finished = be.mix(MixingBowlBlockEntity.RECIPE_COMPLETE / 20);
        if (finished) {
            ItemHandlerHelper.giveItemToPlayer(player, recipe.value().getOutput().copy());
            be.setRecipe(null);
        }
//        player.getItemInHand(InteractionHand.MAIN_HAND).get()
    }

    public static void insert(IItemHandler inventory, ItemStack stack, Player player, InteractionHand hand) {
        var result = ItemHandlerHelper.insertItem(inventory, stack.copy(), false);
        player.setItemInHand(hand, result);
    }

    public static void pop(MixingBowlBlockEntity be, Player player) {
        // TODO: can extract this into common logic later (get last item)
        int idx = -1;
        var inv = be.getInventory();
        for (int i = inv.getSlots() - 1; i >= 0; i--) {
            if (!inv.getStackInSlot(i).isEmpty()) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            return;
        }

        ItemStack stack = inv.extractItem(idx, Integer.MAX_VALUE, false);
        ItemHandlerHelper.giveItemToPlayer(player, stack);
    }

    public static boolean drain(ItemStack stack, IFluidHandler capability, Player player, InteractionHand hand) {
        return fluidInteraction(FluidUtil::tryFillContainerAndStow, stack, capability, player, hand);
    }

    public static boolean fill(ItemStack stack, IFluidHandler capability, Player player, InteractionHand hand) {
        return fluidInteraction(FluidUtil::tryEmptyContainerAndStow, stack, capability, player, hand);
    }

    private static boolean fluidInteraction(FluidContainerAction func, ItemStack stack, IFluidHandler capability, Player player, InteractionHand hand) {
        var result = func.apply(stack,
                capability,
                new InvWrapper(player.getInventory()),
                Integer.MAX_VALUE,
                player,
                true);
        if (result.isSuccess()) {
            player.setItemInHand(hand, result.getResult());
            return true;
        }
        else {
            return false;
        }
    }
}
