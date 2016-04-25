package lumaceon.mods.clockworkphase2.api.time.timezone;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A timezone modulation, as determined by each ITimezoneModulationItem when added to a timezone modulator tile. This is
 * referenced within the tile entity and saves/loads to the tile as well. Note that you should create a separate class
 * for each modulation, as they are differentiated by class.
 */
public abstract class TimezoneModulation
{
    protected TileTimezoneModulator modulatorTile;

    public TimezoneModulation(TileTimezoneModulator tile) {
        this.modulatorTile = tile;
    }

    public TileTimezoneModulator getTile() {
        return modulatorTile;
    }

    /**
     * Called each tick by the ITimezoneProvider for the timezone. If your modulation constantly consumes time, this is
     * where you would do so. Om noms for days.
     * @param item The stack representing the modulation.
     * @param tileEntity The provider for the timezone.
     */
    public void onUpdate(ItemStack item, ITimezoneProvider tileEntity) {}

    /**
     * Called during the appropriate tile's writeToNBT method.
     */
    public void writeToNBT(NBTTagCompound nbt) {}

    /**
     * Called during the appropriate tile's readFromNBT method.
     */
    public void readFromNBT(NBTTagCompound nbt) {}

    /**
     * Called when the itemstack providing this timezone modulation is about to be removed from the timezone modulator.
     * In most cases, this method should remove the modulation's nbt data from the tile's tag and potentially transfer
     * it to the itemstack, depending on the nature of the modulation.
     * @param item The ITimezoneModulationItem stack being removed from the modulator tile.
     * @param nbt The modulator tile's nbt tag.
     */
    public void onModulationRemoval(ItemStack item, NBTTagCompound nbt) {}
}
