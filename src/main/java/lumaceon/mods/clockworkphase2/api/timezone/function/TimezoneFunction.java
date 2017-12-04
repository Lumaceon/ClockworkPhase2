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

    /**
     * Called each tick to update this timezone function if necessary. May safely be a no-op function.
     *
     * If the time cost isn't met this tick, instabilityUpdate is called INSTEAD of this.
     */
    public abstract void onUpdate(ITimezone timezone);

    /**
     * Called each tick the passive time cost isn't satisfied. Should generally have a random chance each tick of
     * something going horribly wrong (related to the merging of this 'alternate reality').
     *
     * This has the potential to be called a lot
     */
    public abstract void instabilityUpdate(ITimezone timezone);

    /**
     * Each tick, the timezone queries this and consumes the given amount of time. If- during that tick -the cost isn't
     * met, the timezone calls the instabilityUpdate method directly after.
     *
     * This includes partial consumption: if the full value isn't met, you get an instabilityUpdate call.
     *
     * @param timezone
     * @return
     */
    public long timeCostPerTick(ITimezone timezone) {
        return 1;
    }

    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("type_id", type.getUniqueID());
        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt) {}
}
