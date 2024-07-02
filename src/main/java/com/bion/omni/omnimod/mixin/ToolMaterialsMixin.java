package com.bion.omni.omnimod.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ToolMaterials.class)
public abstract class ToolMaterialsMixin {
    @Shadow public abstract Ingredient getRepairIngredient();

    @Shadow @Final private Supplier<Ingredient> repairIngredient;

    @Inject(method = "getRepairIngredient", at = @At("HEAD"), cancellable = true)
    private void swapNetheriteForDiamond(CallbackInfoReturnable<Ingredient> cir){
        Ingredient ingredient = this.repairIngredient.get();
        if(ingredient.test(new ItemStack(Items.NETHERITE_INGOT))){
            ingredient = Ingredient.ofItems(Items.DIAMOND);
        }
        cir.setReturnValue(ingredient);
    }
}
