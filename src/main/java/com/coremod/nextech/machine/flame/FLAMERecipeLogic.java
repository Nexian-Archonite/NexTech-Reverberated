package com.coremod.nextech.machine.flame;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ActionResult;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FLAMERecipeLogic extends RecipeLogic {

    private static final int MAX_ACTIVE_RECIPES = 2;

    private final List<ActiveRecipe> activeRecipes = new ArrayList<>();

    public FLAMERecipeLogic(MetaMachine machine) {
        super((IRecipeLogicMachine) machine);
    }

    public int getActiveRecipeCount() {
        return activeRecipes.size();
    }

    public List<ActiveRecipe> getActiveRecipes() {
        return activeRecipes;
    }

    public boolean isFuelRunning() {
        for (ActiveRecipe active : activeRecipes) {
            if (active.fuel) {
                return true;
            }
        }
        return false;
    }

    public boolean isProductionRunning() {
        for (ActiveRecipe active : activeRecipes) {
            if (!active.fuel) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void serverTick() {
        MetaMachine metaMachine = (MetaMachine) machine;

        if (metaMachine.getLevel() == null || metaMachine.getLevel().isClientSide) {
            return;
        }

        Iterator<ActiveRecipe> iterator = activeRecipes.iterator();

        while (iterator.hasNext()) {
            ActiveRecipe active = iterator.next();

            if (active.update()) {
                completeRecipe(active);
                iterator.remove();
            }
        }

        if (!(machine instanceof FLAMEMachine flameMachine)) {
            return;
        }

        if (!flameMachine.isWorkingEnabled()) {
            return;
        }

        if (activeRecipes.size() >= MAX_ACTIVE_RECIPES) {
            return;
        }

        if (!isFuelRunning()) {
            GTRecipe fuelRecipe = findFuelRecipe();

            if (fuelRecipe != null) {
                tryStartFuel(fuelRecipe);
            }
        }

        if (activeRecipes.size() >= MAX_ACTIVE_RECIPES) {
            return;
        }

        if (!isProductionRunning()) {
            GTRecipe productionRecipe = findProductionRecipe();

            if (productionRecipe != null) {
                tryStartProduction(productionRecipe);
            }
        }
    }

    private GTRecipe findFuelRecipe() {
        GTRecipeType[] recipeTypes = machine.getRecipeTypes();

        if (recipeTypes == null || recipeTypes.length == 0) {
            recipeTypes = new GTRecipeType[] { machine.getRecipeType() };
        }

        for (GTRecipeType recipeType : recipeTypes) {
            if (recipeType == null) {
                continue;
            }

            var iterator = recipeType.searchRecipe((IRecipeCapabilityHolder) machine, this::isFuelRecipe);

            while (iterator.hasNext()) {
                GTRecipe recipe = iterator.next();

                if (recipe != null) {
                    return recipe;
                }
            }
        }

        return null;
    }

    private GTRecipe findProductionRecipe() {
        GTRecipeType[] recipeTypes = machine.getRecipeTypes();

        if (recipeTypes == null || recipeTypes.length == 0) {
            recipeTypes = new GTRecipeType[] { machine.getRecipeType() };
        }

        for (GTRecipeType recipeType : recipeTypes) {
            if (recipeType == null) {
                continue;
            }

            var iterator = recipeType.searchRecipe((IRecipeCapabilityHolder) machine, recipe -> !isFuelRecipe(recipe));

            while (iterator.hasNext()) {
                GTRecipe recipe = iterator.next();

                if (recipe != null) {
                    return recipe;
                }
            }
        }

        return null;
    }

    private boolean isFuelRecipe(GTRecipe recipe) {
        return recipe != null && recipe.data.getBoolean("flame_fuel");
    }

    private boolean tryStartFuel(GTRecipe recipe) {
        GTRecipe fuelRecipe = recipe.copy();

        if (!RecipeHelper.matchContents((IRecipeCapabilityHolder) machine, fuelRecipe).isSuccess()) {
            return false;
        }

        ActionResult result = RecipeHelper.handleRecipeIO((IRecipeCapabilityHolder) machine, fuelRecipe, IO.IN,
                this.getChanceCaches());

        if (!result.isSuccess()) {
            return false;
        }

        activeRecipes.add(new ActiveRecipe(fuelRecipe, fuelRecipe.duration, true, this.getChanceCaches()));
        return true;
    }

    private boolean tryStartProduction(GTRecipe recipe) {
        if (!(machine instanceof FLAMEMachine)) {
            return false;
        }

        GTRecipe modifiedRecipe = recipe.copy();

        modifiedRecipe = ((IRecipeLogicMachine) machine).fullModifyRecipe(modifiedRecipe);

        if (modifiedRecipe == null) {
            return false;
        }

        if (modifiedRecipe.duration < 1) {
            modifiedRecipe.duration = 1;
        }

        if (!RecipeHelper.matchContents((IRecipeCapabilityHolder) machine, modifiedRecipe).isSuccess()) {
            return false;
        }

        ActionResult result = RecipeHelper.handleRecipeIO((IRecipeCapabilityHolder) machine, modifiedRecipe, IO.IN,
                this.getChanceCaches());

        if (!result.isSuccess()) {
            return false;
        }

        activeRecipes.add(new ActiveRecipe(modifiedRecipe, modifiedRecipe.duration, false, this.getChanceCaches()));
        return true;
    }

    private void completeRecipe(ActiveRecipe active) {
        if (active.recipe == null) {
            return;
        }

        RecipeHelper.handleRecipeIO((IRecipeCapabilityHolder) machine, active.recipe, IO.OUT, active.chanceCaches);

        if (active.fuel && machine instanceof FLAMEMachine flameMachine) {
            flameMachine.onFuelRecipeCompleted(active.recipe);
        }
    }

    public static class ActiveRecipe {

        public final GTRecipe recipe;
        public final int maxProgress;
        public final boolean fuel;
        public final Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches;

        public int progress;

        public ActiveRecipe(GTRecipe recipe, int maxProgress, boolean fuel,
                            Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches) {
            this.recipe = recipe;
            this.progress = 0;
            this.maxProgress = Math.max(1, maxProgress);
            this.fuel = fuel;
            this.chanceCaches = chanceCaches;
        }

        public boolean update() {
            progress++;
            return progress >= maxProgress;
        }
    }
}
