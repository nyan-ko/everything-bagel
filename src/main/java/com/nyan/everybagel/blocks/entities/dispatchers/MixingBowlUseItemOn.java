package com.nyan.everybagel.blocks.entities.dispatchers;

import com.nyan.everybagel.ModItemTags;
import com.nyan.everybagel.blocks.entities.MixingBowlBlockEntity;
import com.nyan.everybagel.blocks.entities.actions.MixingBowlActions;
import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.recipes.MixingBowlRecipe;
import com.nyan.everybagel.recipes.MixingBowlRecipeInput;
import com.nyan.everybagel.recipes.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.List;

public class MixingBowlUseItemOn {
    // TODO sounds

    public static ItemInteractionResult use(MixingBowlBlockEntity be, ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.isEmpty()) {
            if (player.isShiftKeyDown()) {
                MixingBowlActions.mix(be, player);
                return ItemInteractionResult.CONSUME;
            }

            MixingBowlActions.pop(be, player);
            return ItemInteractionResult.CONSUME;
        }
        else {
            var capability = stack.getCapability(Capabilities.FluidHandler.ITEM);
            if (capability != null) {
                FluidStack fluid = capability.getFluidInTank(0);
                var destination = be.getFluidTank();
                if (fluid.isEmpty()) {
                    MixingBowlActions.drain(stack, destination, player, hand);
                }
                else {
                    MixingBowlActions.fill(stack, destination, player, hand);
                }
                return ItemInteractionResult.CONSUME_PARTIAL;
            }
            else {
                var data = stack.getItemHolder().getData(Gateaux.GATEAU_BY_ITEM);
                if (data != null || stack.is(ModItemTags.MIXING_BOWL_INPUT)) {
                    MixingBowlActions.insert(be, stack, player, hand);
                    return ItemInteractionResult.CONSUME_PARTIAL;
                }
                else if (stack.is(ModItemTags.MOD_DEBUG)) {
                    MixingBowlActions.debug(be, player);
                }
                return ItemInteractionResult.CONSUME;
            }
        }
    }
}
