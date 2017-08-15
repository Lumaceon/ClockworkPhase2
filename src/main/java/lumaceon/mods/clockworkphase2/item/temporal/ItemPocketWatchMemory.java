package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemPocketWatchMemory extends ItemClockworkPhase
{
    public ItemPocketWatchMemory(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new Provider();
    }

    private static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {


        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return false;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return null;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {

        }
    }
}
