package lumaceon.mods.clockworkphase2.api.capabilities;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemStackHandlerClockworkConstruct extends ItemStackHandlerClockwork
{
    @CapabilityInject(IEnergyStorage.class)
    static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;

    ItemStack is;

    public ItemStackHandlerClockworkConstruct(int size, ItemStack is) {
        super(size);
        this.is = is;
    }

    @Override
    protected void onContentsChanged(int slot)
    {
        quality = speed = tier = 0;
        int maxEnergy = 0;
        for(ItemStack s : stacks)
        {
            if(s != null)
            {
                if(s.getItem() instanceof IClockwork)
                {
                    quality += ((IClockwork) s.getItem()).getQuality(s);
                    speed += ((IClockwork) s.getItem()).getSpeed(s);
                    tier = Math.max(tier, ((IClockwork) s.getItem()).getTier(s));
                }

                if(s.getItem() instanceof IMainspring)
                {
                    maxEnergy += ((IMainspring) s.getItem()).getCurrentCapacity(s);
                }
            }
        }

        if(is != null)
        {
            IEnergyStorage cap = is.getCapability(ENERGY_STORAGE_CAPABILITY, EnumFacing.DOWN);
            if(cap != null && cap instanceof EnergyStorageModular)
            {
                ((EnergyStorageModular) cap).setMaxCapacity(maxEnergy);
            }
            is.setItemDamage(is.getMaxDamage());
        }
    }
}
