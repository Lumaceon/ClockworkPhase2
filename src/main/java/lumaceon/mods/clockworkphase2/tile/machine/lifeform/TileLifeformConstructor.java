package lumaceon.mods.clockworkphase2.tile.machine.lifeform;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.inventory.Slot;

public class TileLifeformConstructor extends TileClockworkMachine
{
    public TileLifeformConstructor() {
        super(2, 64, 50, 20000);
        this.slots = new Slot[] { new Slot(this, 0, 44, 18), new SlotNever(this, 1, 115, 34) };
        EXPORT_SLOTS = new int[] { 1 };
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
