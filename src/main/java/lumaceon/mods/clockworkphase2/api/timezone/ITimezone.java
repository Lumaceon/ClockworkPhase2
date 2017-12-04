package lumaceon.mods.clockworkphase2.api.timezone;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunction;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionConstructor;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.List;

/**
 * Capability interface for a timezone. Usually attached to TileEntities.
 */
public interface ITimezone
{
    /**
     * Called from a location in the given world to check if it's a part of this timezone.
     * This can potentially be called several times per tick, so be efficient.
     *
     * @param x The x coordinate from which something is trying to access this.
     * @param y Etc...
     * @param z Etc...
     * @param world The world from which something is trying to access this. Check that this is the correct world.
     * @return True if the location is a part of this timezone. False otherwise.
     */
    public boolean isInRange(double x, double y, double z, World world);

    /**
     * Gets the origin of the timezone, which should always be the TileEntity's position.
     * @return The position of the TileEntity providing this timezone.
     */
    public BlockPos getPosition();

    /**
     * Get the tile entity providing this timezone.
     *
     * @return The TileEntity providing this timezone. May be null.
     */
    public TileEntity getTile();

    public void onUpdate();

    public TimezoneFunctionConstructor getTimezoneFunctionConstructor(TimezoneFunctionType type);

    public Collection<TimezoneFunctionConstructor> getTimezoneFunctionConstructors();

    /**
     * Generally timezone functions are stored in a hashmap and keyed to their respective type's unique ID. If one of
     * the same type already exists, this should fail to do anything.
     * @return True if successful, false if this failed because one already exists.
     */
    public boolean addTimezoneFunctionConstructor(TimezoneFunctionConstructor constructor);

    public TimezoneFunction getTimezoneFunction(TimezoneFunctionType type);

    public Collection<TimezoneFunction> getTimezoneFunctions();

    /**
     * @return True if successful, false if this failed because one already exists.
     */
    public boolean addTimezoneFunction(TimezoneFunction function);

    /**
     * Some timezone functions require items while constructing. This either consumes the item or returns it if it's
     * irrelevant.
     *
     * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return ItemStack.EMPTY).
     *         May be the same as the input ItemStack if unchanged, otherwise a new ItemStack.
     */
    public ItemStack insertFunctionConstructionStack(ItemStack stack, TimezoneFunctionType type);

    /**
     * Serialize this timezone to NBT. Should also include all ITimezoneFunctions attached to this timezone.
     */
    public NBTTagCompound serializeNBT();

    public void deserializeNBT(NBTTagCompound nbt);



    // === Following methods were copied from ITimeStorage === \\

    /**
     * Inserts the given amount of time into this storage.
     * @param ticksToInsert The time, in ticks, to insert into this storage.
     * @return The amount of ticks accepted into this storage.
     */
    long insertTime(long ticksToInsert);

    /**
     * Extracts the given amount of time out of this storage.
     * @param ticksToExtract The time, in ticks, to extract out of this storage.
     * @return The amount of ticks removed from this storage.
     */
    long extractTime(long ticksToExtract);

    /**
     * Returns the time stored.
     * @return How much time is currently stored.
     */
    long getTimeInTicks();

    /**
     * How much time can currently be stored in here?
     * @return The max capacity of this storage.
     */
    long getMaxCapacity();

    /**
     * Sets a new maximum capacity for this storage. If downsizing occurs, this returns the amount of time removed.
     * @param maxCapacity New max capacity to set this storage to.
     * @return The amount of time that was removed due to downsizing, or 0 if no time was lost.
     */
    long setMaxCapacity(long maxCapacity);
}
