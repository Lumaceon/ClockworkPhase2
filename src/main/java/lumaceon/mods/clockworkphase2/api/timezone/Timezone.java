package lumaceon.mods.clockworkphase2.api.timezone;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunction;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionConstructor;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.capabilities.timestorage.TimeStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * A class representing a timezone, which is an area of space-time manipulated by a timezone provider. Functionally,
 * time is stored within an ITimezoneProvider and effects are determined by the timezone modulators.
 */
public class Timezone implements ITimezone
{
    protected HashMap<TimezoneFunctionType, TimezoneFunctionConstructor> functionConstructors = new HashMap<>(8);
    protected HashMap<TimezoneFunctionType, TimezoneFunction> functions = new HashMap<>(8);

    protected TileEntity te;

    protected TimeStorage timeStorage = new TimeStorage(TimeConverter.DAY);
    int radius = 128;

    public Timezone()
    {

    }

    public Timezone(TileEntity te)
    {
        this.te = te;
    }

    @Override
    public boolean isInRange(double x, double y, double z, World world)
    {
        BlockPos pos = getPosition();
        return y <= 256 && pos.getDistance((int) x, pos.getY(), (int) z) <= radius;
    }

    @Override
    public BlockPos getPosition() {
        return te.getPos();
    }

    @Override
    public TileEntity getTile() {
        return te;
    }

    @Override
    public void onUpdate()
    {
        functionConstructors.forEach((k,v) -> v.onUpdate(this));
        functions.forEach((k, v) -> v.onUpdate(this));
    }

    @Override
    public TimezoneFunctionConstructor getTimezoneFunctionConstructor(TimezoneFunctionType type) {
        return functionConstructors.get(type);
    }

    @Override
    public Collection<TimezoneFunctionConstructor> getTimezoneFunctionConstructors() {
        return functionConstructors.values();
    }

    @Override
    public boolean addTimezoneFunctionConstructor(TimezoneFunctionConstructor constructor)
    {
        if(functionConstructors.containsKey(constructor.getTimezoneFunctionType()))
            return false;
        functionConstructors.put(constructor.getTimezoneFunctionType(), constructor);
        return true;
    }

    @Override
    public TimezoneFunction getTimezoneFunction(TimezoneFunctionType type) {
        return functions.get(type);
    }

    @Override
    public Collection<TimezoneFunction> getTimezoneFunctions() {
        return functions.values();
    }

    @Override
    public boolean addTimezoneFunction(TimezoneFunction function) {
        if(functions.containsKey(function.getType()))
            return false;
        functions.put(function.getType(), function);
        return true;
    }

    @Override
    public ItemStack insertFunctionConstructionStack(ItemStack stack, TimezoneFunctionType type)
    {
        TimezoneFunctionConstructor constructor = getTimezoneFunctionConstructor(type);
        if(constructor != null)
            return constructor.insertStack(this, stack);
        return stack;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("time_stored", timeStorage.getTimeInTicks());
        nbt.setLong("time_capacity", timeStorage.getMaxCapacity());

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        timeStorage.setMaxCapacity(nbt.getLong("time_capacity"));
        timeStorage.setTimeStored(nbt.getLong("time_stored"));
    }

    @Override
    public long insertTime(long ticksToInsert) {
        markDirty();
        return timeStorage.insertTime(ticksToInsert);
    }

    @Override
    public long extractTime(long ticksToExtract) {
        markDirty();
        return timeStorage.extractTime(ticksToExtract);
    }

    @Override
    public long getTimeInTicks() {
        return timeStorage.getTimeInTicks();
    }

    @Override
    public long getMaxCapacity() {
        return timeStorage.getMaxCapacity();
    }

    @Override
    public long setMaxCapacity(long maxCapacity) {
        markDirty();
        return timeStorage.setMaxCapacity(maxCapacity);
    }

    /**
     * Call whenever internal values are changed to make sure they're saved properly.
     */
    public void markDirty()
    {
        if(te != null)
            te.markDirty();
    }
}
