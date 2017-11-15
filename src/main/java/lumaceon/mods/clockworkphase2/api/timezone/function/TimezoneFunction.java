package lumaceon.mods.clockworkphase2.api.timezone.function;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TimezoneFunction
{
    protected TimezoneFunctionType type;

    public TimezoneFunction(TimezoneFunctionType type) {
        this.type = type;
    }

    public TimezoneFunctionType getType() {
        return this.type;
    }

    public abstract void onUpdate(ITimezone timezone);

    public abstract NBTTagCompound serializeNBT();
    public abstract void deserializeNBT(NBTTagCompound nbt);
}
