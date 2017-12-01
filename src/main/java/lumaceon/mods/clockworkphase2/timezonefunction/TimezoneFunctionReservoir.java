package lumaceon.mods.clockworkphase2.timezonefunction;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunction;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import net.minecraft.nbt.NBTTagCompound;

public class TimezoneFunctionReservoir extends TimezoneFunction
{
    long capacityInMillibuckets = 0;

    public TimezoneFunctionReservoir(TimezoneFunctionType type, long capacityInMillibuckets) {
        super(type);
        this.capacityInMillibuckets = capacityInMillibuckets;
    }

    @Override
    public void onUpdate(ITimezone timezone) {

    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("capacity", this.capacityInMillibuckets);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("capacity"))
        {
            this.capacityInMillibuckets = nbt.getLong("capacity");
        }
    }
}
