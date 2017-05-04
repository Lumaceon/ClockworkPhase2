package lumaceon.mods.clockworkphase2.api.capabilities;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageModular extends EnergyStorage
{
    public EnergyStorageModular(int capacity) {
        super(capacity);
    }

    public EnergyStorageModular(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public EnergyStorageModular(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setMaxCapacity(int capacity)
    {
        int previousEnergy = this.energy;
        this.capacity = capacity;
        this.maxExtract = capacity;
        this.maxReceive = capacity;
        this.energy = Math.min(capacity, energy);
    }
}
