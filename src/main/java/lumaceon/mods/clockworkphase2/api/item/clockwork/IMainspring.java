package lumaceon.mods.clockworkphase2.api.item.clockwork;

import net.minecraft.item.ItemStack;

public interface IMainspring
{
    /**
     * @return The maximum this can hold, if built up as far as it can go.
     */
    public int getMaximumPossibleCapacity(ItemStack item);

    /**
     * @return The current capacity of energy this can hold as it is now.
     */
    public int getCurrentCapacity(ItemStack item);
}
