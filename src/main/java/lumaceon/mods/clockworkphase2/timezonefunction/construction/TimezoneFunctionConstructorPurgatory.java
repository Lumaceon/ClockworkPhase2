package lumaceon.mods.clockworkphase2.timezonefunction.construction;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunction;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionConstructor;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TimezoneFunctionConstructorPurgatory extends TimezoneFunctionConstructor
{
    public TimezoneFunctionConstructorPurgatory(TimezoneFunctionType type) {
        super(type, 2);
    }

    @Override
    public long getMaxProgressIndexForLayer(ITimezone timezone, int layerIndex) {
        return 65024;
    }

    @Override
    public void onUpdate(ITimezone timezone)
    {

    }

    @Override
    public ItemStack insertStack(ITimezone timezone, ItemStack stackToInsert)
    {
        return stackToInsert;
    }

    @Override
    public boolean canComplete(ITimezone timezone) {
        return true;
    }

    @Override
    public TimezoneFunction createTimezoneFunction(ITimezone timezone) {
        return null;
    }

    @Override
    public String getLayerDisplayName(ITimezone timezone, int layerIndex, boolean detailed)
    {
        return "";
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {

    }
}
