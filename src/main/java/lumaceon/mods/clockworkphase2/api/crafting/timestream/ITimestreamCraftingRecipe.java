package lumaceon.mods.clockworkphase2.api.crafting.timestream;

import net.minecraft.item.ItemStack;

public interface ITimestreamCraftingRecipe
{
    public String getUnlocalizedName();

    /**
     * Called every time an event occurs that could increase magnitude.
     * @param eventKey A string representing the event that occurred.
     * @return The amount of magnitude increase that should occur.
     */
    public int getMagnitudeIncrease(String eventKey);

    /**
     * @return How long, in 20th-of-a-second ticks, this crafting recipe lasts.
     */
    public int getCraftingDuration();

    /**
     * This should return each item that this recipe expects as well as the result. This is for informational purposes
     * and doesn't effect the recipe itself.
     * 0-7 - the outlying circles, starting at the top and moving clockwise.
     * 8 - the center circle.
     * 9 - the result.
     *
     * @return The ItemStacks that best represent this recipe.
     */
    public ItemStack[] getRecipe();

    /**
     * Whether or not the following items match this recipe. The array follows the celestial compass clockwise, with
     * index 0 being the top circle and the center being index 8.
     *
     * @param items The items on the 8 elemental circles, as well as the center.
     * @return Whether or not these ItemStacks match this recipe.
     */
    public boolean matches(ItemStack[] items);

    /**
     * This should always return either a new ItemStack or a copy of an old one.
     * @param items The items on the 8 circles, starting at top and moving clockwise, then the center item at index 8.
     * @param magnitude The total magnitude that was accumulated over the course of the crafting recipe.
     * @return The result of this recipe.
     */
    public ItemStack getCraftingResult(ItemStack[] items, int magnitude);
}
