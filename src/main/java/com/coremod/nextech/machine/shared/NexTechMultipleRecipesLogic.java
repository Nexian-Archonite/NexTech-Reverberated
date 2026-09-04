package com.coremod.nextech.machine.shared;

import com.coremod.nextech.api.IThreadedMultiblock;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ActionResult;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NexTechMultipleRecipesLogic extends RecipeLogic {

    /**
     * Slot 0 = main recipe.
     * Slots 1..N = aux, assigned round robin across aux logic types.
     */
    private final List<ActiveSlot> slots = new ArrayList<>();

    public NexTechMultipleRecipesLogic(MetaMachine machine) {
        super((IRecipeLogicMachine) machine);
    }

    // -------------------------------------------------------------------------
    // Slot model
    // -------------------------------------------------------------------------

    public enum SlotType {
        MAIN,
        AUX
    }

    public static class ActiveSlot {
        public final SlotType type;
        public final int auxIndex; // which aux logic type (round robin index), -1 for MAIN
        public GTRecipe recipe;
        public int progress;
        public int maxProgress;

        public ActiveSlot(SlotType type, int auxIndex, GTRecipe recipe) {
            this.type = type;
            this.auxIndex = auxIndex;
            this.recipe = recipe;
            this.progress = 0;
            this.maxProgress = recipe.duration;
        }

        /** @return true when recipe finished */
        public boolean tick() {
            progress++;
            return progress >= maxProgress;
        }
    }

    // -------------------------------------------------------------------------
    // Threading helpers
    // -------------------------------------------------------------------------

    private int getTotalThreadCount() {
        if (machine instanceof IThreadedMultiblock t) {
            return t.getTotalThreadCount();
        }
        return 1; // fallback: just main
    }

    private int getAuxThreadCount() {
        if (machine instanceof IThreadedMultiblock t) {
            return t.getAuxThreadCount();
        }
        return 0;
    }

    private int getBaseAuxCount() {
        if (machine instanceof IThreadedMultiblock t) {
            return t.getBaseAuxCount();
        }
        return 0;
    }

    /**
     * For a given aux slot index (0-based within aux slots),
     * returns which aux logic type it maps to via round robin.
     */
    private int auxTypeForSlot(int auxSlotIndex) {
        int baseAux = getBaseAuxCount();
        if (baseAux == 0) return 0;
        return auxSlotIndex % baseAux;
    }

    private boolean isMainSlotOccupied() {
        return slots.stream().anyMatch(s -> s.type == SlotType.MAIN);
    }

    private int occupiedAuxSlotsOfType(int auxType) {
        return (int) slots.stream()
                .filter(s -> s.type == SlotType.AUX && s.auxIndex == auxType)
                .count();
    }

    /**
     * How many aux slots of each type should exist given current thread count.
     * e.g. CRV with UV hatch: 2 types * 2 multiplier = 4 aux slots,
     * so 2 heating slots and 2 pressure slots.
     */
    private int targetSlotsPerAuxType() {
        int baseAux = getBaseAuxCount();
        if (baseAux == 0) return 0;
        return getAuxThreadCount() / baseAux;
    }

    // -------------------------------------------------------------------------
    // Server tick
    // -------------------------------------------------------------------------

    @Override
    public void serverTick() {
        MetaMachine meta = (MetaMachine) machine;
        if (meta.getLevel() == null || meta.getLevel().isClientSide) return;

        boolean enabled = true;
        if (machine instanceof WorkableMultiblockMachine w) {
            enabled = w.isWorkingEnabled();
        }

        // tick all active slots
        Iterator<ActiveSlot> it = slots.iterator();
        while (it.hasNext()) {
            ActiveSlot slot = it.next();
            if (slot.tick()) {
                completeSlot(slot);
                it.remove();
            }
        }

        if (!enabled) return;

        // fill main slot (slot 0)
        if (!isMainSlotOccupied()) {
            tryFillMainSlot();
        }

        // fill aux slots round robin
        int targetPerType = targetSlotsPerAuxType();
        int baseAux = getBaseAuxCount();

        for (int auxType = 0; auxType < baseAux; auxType++) {
            int occupied = occupiedAuxSlotsOfType(auxType);
            int needed = targetPerType - occupied;
            for (int i = 0; i < needed; i++) {
                if (!tryFillAuxSlot(auxType)) break;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Slot filling
    // -------------------------------------------------------------------------

    private void tryFillMainSlot() {
        GTRecipeType recipeType = machine.getRecipeType();
        if (recipeType == null) return;

        var iterator = recipeType.searchRecipe((IRecipeCapabilityHolder) machine, r -> true);
        while (iterator.hasNext()) {
            GTRecipe recipe = iterator.next();
            if (recipe == null) continue;
            GTRecipe modified = applyMainModifier(recipe);
            if (modified == null) continue;
            if (tryConsumeAndStart(modified, SlotType.MAIN, -1)) return;
        }
    }

    private boolean tryFillAuxSlot(int auxType) {
        GTRecipe auxRecipe = createAuxRecipe(auxType);
        if (auxRecipe == null) return false;
        return tryConsumeAndStart(auxRecipe, SlotType.AUX, auxType);
    }

    /**
     * Override in machine-specific logic subclass to apply
     * the machine's RecipeModifier to main recipes.
     */
    @Nullable
    protected GTRecipe applyMainModifier(GTRecipe recipe) {
        // default: no modification, subclass overrides for OC/parallel etc
        return recipe;
    }

    /**
     * Override in machine-specific logic subclass to create
     * the aux recipe for a given aux type index.
     * Returns null if aux recipe cannot run right now.
     */
    @Nullable
    protected GTRecipe createAuxRecipe(int auxType) {
        return null;
    }

    private boolean tryConsumeAndStart(GTRecipe recipe, SlotType type, int auxIndex) {
        if (!RecipeHelper.matchContents((IRecipeCapabilityHolder) machine, recipe).isSuccess()) {
            return false;
        }
        ActionResult result = RecipeHelper.handleRecipeIO(
                (IRecipeCapabilityHolder) machine, recipe, IO.IN, this.getChanceCaches());
        if (result.isSuccess()) {
            slots.add(new ActiveSlot(type, auxIndex, recipe));
            return true;
        }
        return false;
    }

    private void completeSlot(ActiveSlot slot) {
        RecipeHelper.handleRecipeIO(
                (IRecipeCapabilityHolder) machine, slot.recipe, IO.OUT, this.getChanceCaches());
    }

    // -------------------------------------------------------------------------
    // Persistence
    // -------------------------------------------------------------------------

    @Override
    public void saveCustomPersistedData(@NotNull CompoundTag tag, boolean forDrop) {
        super.saveCustomPersistedData(tag, forDrop);
        tag.putInt("SlotCount", slots.size());
        for (int i = 0; i < slots.size(); i++) {
            ActiveSlot s = slots.get(i);
            CompoundTag st = new CompoundTag();
            st.putString("Type", s.type.name());
            st.putInt("AuxIndex", s.auxIndex);
            st.putInt("Progress", s.progress);
            st.putInt("MaxProgress", s.maxProgress);
            tag.put("Slot" + i, st);
        }
    }

    @Override
    public void loadCustomPersistedData(@NotNull CompoundTag tag) {
        super.loadCustomPersistedData(tag);
        // recipes themselves aren't persisted (they're re-matched on load)
        // just clear, they'll refill next tick
        slots.clear();
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    public List<ActiveSlot> getActiveSlots() {
        return slots;
    }

    public boolean isWorking() {
        return slots.stream().anyMatch(s -> s.type == SlotType.MAIN);
    }
}