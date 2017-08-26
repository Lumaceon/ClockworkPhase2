package lumaceon.mods.clockworkphase2.api.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

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
        setMetadataForItem();
        return previousRet;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int previousRet = super.extractEnergy(maxExtract, simulate);
        setMetadataForItem();
        return previousRet;
    }

    private void setMetadataForItem()
    {
        if(this.stack != null)
        {
            if(this.getMaxEnergyStored() <= 0)
            {
                this.stack.setItemDamage(stack.getMaxDamage());
            }
            else
            {
                int damage = this.stack.getMaxDamage() - (int) ( ((double) energy / (double) getMaxEnergyStored()) * stack.getMaxDamage() );
                if(damage <= 0)
                {
                    damage = 1;
                }

                this.stack.setItemDamage(damage);
            }
        }
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
