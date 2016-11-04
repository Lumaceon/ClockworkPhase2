package lumaceon.mods.clockworkphase2.api.item;

import lumaceon.mods.clockworkphase2.api.EnumExpTier;
import net.minecraft.item.ItemStack;

/**
 * Hourglasses are used to determine when special phased mobs can spawn (when the player has enough XP). Additionally,
 * they store time, supply time to tools like the temporal excavator, and receive time from time generators.
 *
 * (This interface is based off of the RF API - Credits to King Lemming)
 */
public interface IHourglass
{
    /**
     * @return The tier of this hourglass.
     */
    public EnumExpTier getTier(ItemStack stack);

    /**
     * @return Whether or not this hourglass is currently active (if inactive, it will usually be ignored).
     */
    public boolean isActive(ItemStack stack);

    /**
     * @param xpLevel The experience level of the player trying to spawn entities.
     * @param tier The tier of the entity which is trying to spawn. Can be null.
     * @return Whether or not this item allows phase entities of this tier to spawn. Should usually allow mobs of one
     * tier above this item to spawn, so the player can actually progress.
     */
    public boolean isSpawningPhaseEntities(ItemStack stack, int xpLevel, EnumExpTier tier);

    /**
     * @param xpLevel The experience level of the player trying to add time.
     * @param tier The xp level tier of the potential source of this time. Can be null.
     * @return Whether or not this item is accepting time from time generators. Usually just refers to isActive.
     */
    public boolean isAcceptingTime(ItemStack stack, int xpLevel, EnumExpTier tier);

    /**
     * Receives time into the timeItem passed in.
     * @param timeItem The ItemStack to receive time.
     * @param maxReceive The amount of time trying to be added.
     * @param simulate If true, a simulation will occur, but no time will actually be added.
     * @return The amount of time that was added successfully (or would have been added if simulated).
     */
    public long receiveTime(ItemStack timeItem, long maxReceive, boolean simulate);

    /**
     * Extracts time from the timeItem passed in.
     * @param timeItem The ItemStack to extract time from.
     * @param maxExtract The amount of time trying to be removed.
     * @param simulate If true, a simulation will occur, but no time will actually be removed.
     * @return The amount of time that was removed successfully (or would have been removed if simulated).
     */
    public long extractTime(ItemStack timeItem, long maxExtract, boolean simulate);

    /**
     * Returns the maximum amount of time that can be stored in this ItemStack.
     */
    public long getMaxCapacity(ItemStack timeItem);

    /**
     * Returns the amount of time that's stored in this ItemStack.
     */
    public long getTimeStored(ItemStack timeItem);

    /**
     * Called to add a time compressor (sometimes known as time generators) to this hourglass. Item should implement
     * ITimeCompressor.
     * @return True if the compressor was added, false otherwise.
     */
    public boolean addTimeCompressor(ItemStack hourglassStack, ItemStack compressorStack);
}
