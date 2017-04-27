package lumaceon.mods.clockworkphase2.api.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerClockwork extends ItemStackHandler
{
    protected int quality, speed, tier;

    public ItemStackHandlerClockwork(int size) {
        super(size);
    }

    public int getQuality() {
        return quality;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = super.serializeNBT();
        nbt.setInteger("CP2_quality", quality);
        nbt.setInteger("CP2_speed", speed);
        nbt.setInteger("CP2_tier", tier);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        super.deserializeNBT(nbt);
        quality = nbt.getInteger("CP2_quality");
        speed = nbt.getInteger("CP2_speed");
        tier = nbt.getInteger("CP2_tier");
    }
}
