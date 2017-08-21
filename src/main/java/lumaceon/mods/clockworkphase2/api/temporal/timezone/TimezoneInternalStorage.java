package lumaceon.mods.clockworkphase2.api.temporal.timezone;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * Simple extension of Timezone to ignore advanced features. Unlike normal timezones, never register this as a global
 * timezone, so it's only ever accessed from the relay itself.
 *
 * Instead of storing time directly, internal timezones store their time in an hourglass. If there is no hourglass, the
 * timezone is usually just useless, and perpetually empty.
 */
public class TimezoneInternalStorage extends Timezone
{
    @CapabilityInject(ITimeStorage.class)
    public static final Capability<ITimeStorage> TIME_STORAGE_CAPABILITY = null;

    protected ItemStack timeStorageStack = ItemStack.EMPTY;

    public TimezoneInternalStorage(TileEntity te) {
        super(te);
    }

    /**
     * @return The previous stack that was in the slot beforehand. This can be the EMPTY stack.
     */
    public ItemStack setTimeStorageStack(ItemStack stack)
    {
        ItemStack oldStack = timeStorageStack;

        timeStorageStack = stack;
        markDirty();

        return oldStack;
    }

    public ItemStack getTimeStorageStack() {
        return timeStorageStack;
    }

    protected ITimeStorage getTimeStorage() {
        return timeStorageStack.getCapability(TIME_STORAGE_CAPABILITY, EnumFacing.DOWN);
    }

    /**
     * We'll always return false here, so on the off-chance this is registered as a global timezone, everything else
     * will ignore it. Internally, we'll ignore this method and its return value.
     */
    @Override
    public boolean isInRange(double x, double y, double z, World world) {
        return false;
    }

    /**
     * No timezone functions for an internal timezone.
     */
    @Override
    public ITimezoneFunction[] getTimezoneFunctions() {
        return new ITimezoneFunction[0];
    }

    @Override
    public long getTimeInTicks()
    {
        ITimeStorage storage = getTimeStorage();
        if(storage != null)
        {
            return storage.getTimeInTicks();
        }

        return 0;
    }

    @Override
    public long getMaxCapacity()
    {
        ITimeStorage storage = getTimeStorage();
        if(storage != null)
        {
            return storage.getMaxCapacity();
        }

        return 0;
    }

    @Override
    public long setMaxCapacity(long maxCapacity) {
        return 0;
    }

    @Override
    public long insertTime(long ticksToInsert)
    {
        ITimeStorage storage = getTimeStorage();
        if(storage != null)
        {
            markDirty();
            return storage.insertTime(ticksToInsert);
        }

        return 0;
    }

    @Override
    public long extractTime(long ticksToExtract)
    {
        ITimeStorage storage = getTimeStorage();
        if(storage != null)
        {
            markDirty();
            return storage.extractTime(ticksToExtract);
        }

        return 0;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        NBTTagCompound stackTag = new NBTTagCompound();
        timeStorageStack.writeToNBT(stackTag);
        nbt.setTag("time_storage", stackTag);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("time_storage"))
        {
            NBTTagCompound stackTag = nbt.getCompoundTag("time_storage");
            timeStorageStack = new ItemStack(stackTag);
        }
    }
}