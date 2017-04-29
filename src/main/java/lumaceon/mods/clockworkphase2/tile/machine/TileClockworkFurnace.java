package lumaceon.mods.clockworkphase2.tile.machine;

import net.minecraft.inventory.Slot;
import net.minecraftforge.energy.EnergyStorage;

public class TileClockworkFurnace extends TileClockworkMachine
{
    public TileClockworkFurnace()
    {
        super(2, 64);
        this.slots = new Slot[] { new Slot(this, 0, 0, 0), new Slot(this, 1, 0, 0) };
        energyStorage = new EnergyStorage(1000000);
    }
}
