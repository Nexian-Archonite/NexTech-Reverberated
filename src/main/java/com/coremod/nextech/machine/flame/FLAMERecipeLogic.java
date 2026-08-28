package com.coremod.nextech.machine.flame;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ActionResult;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class FLAMERecipeLogic extends RecipeLogic {

    private ActiveRecipe heatingRecipe;

    public FLAMERecipeLogic(IRecipeLogicMachine machine) {
        super(machine);
    }

    @Override
    public void serverTick() {
        super.serverTick();
        tickHeatingRecipe();
        keepHeatingSubscription();
    }

    @Override
    public @NotNull Iterator<GTRecipe> searchRecipe() {
        return machine.getRecipeType().searchRecipe(
                machine,
                recipe -> !recipe.data.getBoolean("flame_fuel"));
    }

    private void tickHeatingRecipe() {
        if (heatingRecipe != null) {
            if (!isWorkingEnabled()) {
                return;
            }

            ActionResult conditionResult = RecipeHelper.checkConditions(
                    heatingRecipe.recipe,
                    this);

            if (!conditionResult.isSuccess()) {
                return;
            }

            ActionResult tickResult = handleTickRecipe(heatingRecipe.recipe);

            if (!tickResult.isSuccess()) {
                return;
            }

            if (!super.isWorking() && !machine.onWorking()) {
                return;
            }

            heatingRecipe.progress++;

            if (heatingRecipe.progress >= heatingRecipe.duration) {
                finishHeatingRecipe();
            }

            return;
        }

        if (!isWorkingEnabled()) {
            return;
        }

        startHeatingRecipe();
    }

    private void startHeatingRecipe() {
        GTRecipe recipe = FLAMEHeatingLogic.INSTANCE.createCustomRecipe(machine);

        if (recipe == null) {
            return;
        }

        if (!recipe.data.getBoolean("flame_fuel")) {
            return;
        }

        if (!checkRecipe(recipe).isSuccess()) {
            return;
        }

        if (!machine.beforeWorking(recipe)) {
            return;
        }

        ActionResult inputResult = handleRecipeIO(
                recipe,
                IO.IN);

        if (!inputResult.isSuccess()) {
            return;
        }

        heatingRecipe = new ActiveRecipe(
                recipe,
                recipe.duration);

        if (!super.isWorking()) {
            setStatus(Status.WORKING);
        }
    }

    private void finishHeatingRecipe() {
        if (heatingRecipe == null) {
            return;
        }

        GTRecipe recipe = heatingRecipe.recipe;

        handleRecipeIO(recipe, IO.OUT);

        int heatPerCycle = recipe.data.getInt("heat_per_cycle");
        int heatCap = recipe.data.getInt("heat_cap");

        if (heatPerCycle > 0 && heatCap > 0) {
            if (machine.self() instanceof FLAMEMachine flameMachine) {
                flameMachine.addTemperature(
                        heatPerCycle,
                        heatCap);
            }
        }

        heatingRecipe = null;

        if (!super.isWorking() && !isWaiting() && !isSuspend()) {
            setStatus(Status.IDLE);
        }
    }

    private void keepHeatingSubscription() {
        if (heatingRecipe == null || isSuspend()) {
            return;
        }

        if (subscription == null) {
            subscription = getMachine().subscribeServerTick(
                    subscription,
                    this::serverTick);
        }
    }

    @Override
    public boolean isWorking() {
        return super.isWorking() || heatingRecipe != null;
    }

    public boolean isHeating() {
        return heatingRecipe != null;
    }

    public int getHeatingProgress() {
        return heatingRecipe == null ? 0 : heatingRecipe.progress;
    }

    public int getHeatingDuration() {
        return heatingRecipe == null ? 0 : heatingRecipe.duration;
    }

    private static class ActiveRecipe {

        private final GTRecipe recipe;
        private final int duration;
        private int progress;

        private ActiveRecipe(GTRecipe recipe, int duration) {
            this.recipe = recipe;
            this.duration = duration;
            this.progress = 0;
        }
    }
}
