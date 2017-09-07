package lumaceon.mods.clockworkphase2.api.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class EnergyStorageModular extends EnergyStorage
{
    ItemStack stack;

    public EnergyStorageModular(int capacity) {
        super(capacity);
    }

    public EnergyStorageModular(int capacity, ItemStack stack) {
        super(capacity);
        this.stack = stack;
    }

    public EnergyStorageModular(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public EnergyStorageModular(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int previousRet = super.receiveEnergy(maxReceive, simulate);
        return previousRet;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int previousRet = super.extractEnergy(maxExtract, simulate);
        return previousRet;
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

    @Override
    public boolean equals(@Nullable final Object obj)
    {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        final IEnergyStorage that = (IEnergyStorage) obj;

        return this.getMaxEnergyStored() == that.getMaxEnergyStored() && this.getEnergyStored() == that.getEnergyStored();
    }

    @Override
    public int hashCode() {
        return capacity + energy % 100;
    }
}
