package lumaceon.mods.clockworkphase2.tile.machine.lifeform;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotNonFullEntityContainer;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.recipe.EntityConstructionRecipes;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.inventory.Slot;

public class TileLifeformConstructor extends TileClockworkMachine
{
    public EntityConstructionRecipes.EntityConstructionRecipe activeRecipe = EntityConstructionRecipes.INSTANCE.getRecipe("chicken");

    public TileLifeformConstructor() {
        super(11, 64, 100, 10000);
        this.slots = new Slot[]
                {
                        new Slot(this, 0, 32, 6),
                        new Slot(this, 1, 50, 6),
                        new Slot(this, 2, 68, 6),
                        new Slot(this, 3, 32, 24),
                        new Slot(this, 4, 50, 24),
                        new Slot(this, 5, 68, 24),
                        new Slot(this, 6, 32, 42),
                        new Slot(this, 7, 50, 42),
                        new Slot(this, 8, 68, 42),
                        new SlotNonFullEntityContainer(this, 9, 50, 62),
                        new SlotNever(this, 10, 191, 59)
                };
        EXPORT_SLOTS = new int[] { 10 };
    }

    @Override
    public boolean canWork() {
        return false;
    }

    @Override
    public void completeAction() {

    }

    @Override
    public int temporalActions(int maxNumberOfActions) {
        return 0;
    }
}
