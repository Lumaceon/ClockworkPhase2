package lumaceon.mods.clockworkphase2.api.timezone.function;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A consistent generic class to be used for storage and processing of data relating to the construction of timezone
 * functions.
 *
 * Because a lot of calculation can go into the construction of a timezone function, we make use of layers. Each
 * "layer" represents a specific part of the process, while "progress" represents how close each layer is to
 * completion.
 *
 * For example: The Purgatory function requires iron golems to be consumed for one layer, and potions of health to
 * be consumed fot the next layer.
 *
 * Progress and layers are to be modified internally, with the getter methods used mostly to display progress in a
 * consistent way for GUIs. canComplete is the final word on whether or not this is ready for construction.
 */
public abstract class TimezoneFunctionConstructor
{
    protected TimezoneFunctionType type;
    protected int numberOfLayers;

    protected int layer;
    protected long progress;

    public TimezoneFunctionConstructor(TimezoneFunctionType type, int numberOfLayers) {
        this.type = type;
        this.numberOfLayers = numberOfLayers;
    }

    public TimezoneFunctionType getTimezoneFunctionType() {
        return type;
    }

    public int getActiveLayer() {
        return layer;
    }

    public long getProgress() {
        return progress;
    }

    /**
     * The number of construction layers to complete for the creation of a TimezoneFunction.
     *
     * @return The number of construction layers that requires completion, or 0 for auto-complete.
     */
    public int getConstructionLayerCount(ITimezone timezone) {
        return numberOfLayers;
    }

    /**
     * Gets the maximum index for the progress of the construction layer passed in. This starts at 0 and continues up
     * to the number provided here (inclusively).
     *
     * @return The last progress index that requires completion for the given layer (should always be 0 or higher)
     */
    public abstract long getMaxProgressIndexForLayer(ITimezone timezone, int layerIndex);

    /**
     * Called every tick from the timezone-providing tile entity to construct the timezone function. This is where
     * you'd actually consume stuff.
     */
    public abstract void onUpdate(ITimezone timezone);

    /**
     * Stacks of items may be "inserted" into this construction. In most cases, you'll just want to consume the item
     * and increment a number. This is a one-way transfer: if this method takes the item, there's no way to retrieve it
     * during construction.
     *
     * @return The remaining items that WEREN'T taken, or ItemStack.EMPTY if completely taken.
     */
    public abstract ItemStack insertStack(ITimezone timezone, ItemStack stackToInsert);

    /**
     * @return Whether or not the timezone function can be created at this point.
     */
    public abstract boolean canComplete(ITimezone timezone);

    /**
     * Called once all construction is deemed complete. The return value is then mapped to this type in the timezone.
     * This can be called at any point that canComplete returns true.
     *
     * This may be called prematurely. For example: the reservoir can be created with a very small tank, or it could
     * continue to progress and make the tank larger.
     */
    public abstract TimezoneFunction createTimezoneFunction(ITimezone timezone);

    /**
     * Overloaded to construct the function straight from a provided NBT tag. Used to recreate functions on load. Note
     * that deserializeNBT is called for the return value after this, so using the nbt may be unnecessary.
     */
    public abstract TimezoneFunction createTimezoneFunction(ITimezone timezone, NBTTagCompound nbt);

    /**
     * For use in the function construction GUI to display what each layer actually does.
     *
     * @param layerIndex The index of the layer.
     * @param detailed If false, keep the return string to a few words. Otherwise, offer more detail.
     * @return A localized description for the layer's process.
     */
    public abstract String getLayerDisplayName(ITimezone timezone, int layerIndex, boolean detailed);

    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("type_id", type.getUniqueID());
        nbt.setInteger("layer_count", numberOfLayers);
        nbt.setInteger("layer", getActiveLayer());
        nbt.setLong("progress", getProgress());
        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt)
    {
        numberOfLayers = nbt.getInteger("layer_count");
        layer = nbt.getInteger("layer");
        progress = nbt.getLong("progress");
    }
}
