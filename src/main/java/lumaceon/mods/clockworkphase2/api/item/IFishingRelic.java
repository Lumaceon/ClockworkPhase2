package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.item.ItemStack;

/**
 * Simple interface to be implemented on items which are relics to be placed inside a temporal fishing rod.
 */
public interface IFishingRelic
{
    /**
     * Get the item that will be fished out of the timestream as a result of this one being in the fishing rod.
     * @param inputStack A stack representing this relic.
     * @return The resulting item, which will be copied and spawned in the world when temporal fishing is successful.
     */
    public ItemStack getResultItem(ItemStack inputStack);
}
