package lumaceon.mods.clockworkphase2.item.temporal.excavator;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.capabilities.ActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.IActivatableHandler;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public abstract class ItemToolUpgrade extends ItemClockworkPhase implements IToolUpgrade
{
    @CapabilityInject(IActivatableHandler.class)
    public static final Capability<IActivatableHandler> ACTIVATABLE = null;

    public ItemToolUpgrade(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }

    @Override
    public void setActive(ItemStack upgradeStack, ItemStack toolStack, boolean active)
    {
        IActivatableHandler cap = upgradeStack.getCapability(ACTIVATABLE, EnumFacing.DOWN);
        if(cap != null)
            cap.setActive(active);
    }

    @Override
    public boolean getActive(ItemStack upgradeStack, ItemStack toolStack)
    {
        IActivatableHandler cap = upgradeStack.getCapability(ACTIVATABLE, EnumFacing.DOWN);
        return cap != null && cap.getActive();
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new ToolUpgradeCapabilityProvider();
    }

    protected static class ToolUpgradeCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        ActivatableHandler activatableHandler = new ActivatableHandler();

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability != null && capability == ACTIVATABLE;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability != null)
            {
                if(capability == ACTIVATABLE)
                    return ACTIVATABLE.cast(activatableHandler);
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setBoolean("active", activatableHandler.getActive());
            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            activatableHandler.setActive(nbt.getBoolean("active"));
        }
    }
}
