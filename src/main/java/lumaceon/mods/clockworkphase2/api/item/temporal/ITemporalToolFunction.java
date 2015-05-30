package lumaceon.mods.clockworkphase2.api.item.temporal;

import net.minecraft.item.ItemStack;

/**
 * While not a timestream itself, the crafting recipe for one of these should almost ALWAYS require a timestream of some
 * kind. Lore-wise, a temporal tool function is a device within the given tool, which applies a timestream to the
 * physical world in some way.
 *
 * For example, the smelting temporal tool function feeds timesand and an item into a smelting timestream, applying the
 * timestream to said item and resulting in a smelted object. Thus, it makes sense for a timestream to be required.
 *
 *
 * Two different kinds of ITemporalToolFunction interfaces exist: active and passive. Each tool (in vanilla Clockwork
 * Phase) may only contain one active module, which is usually applied on a right-click of some kind. However, a tool
 * may contain a large number of passive modules as they tend to work via events.
 */
public interface ITemporalToolFunction
{
    public boolean isActive(ItemStack item);

    /**
     * Cost and action varies depending on the tool. This is not handled automatically, you will have to handle the
     * costs yourself.
     * @param item The ITemporalToolFunction stack.
     * @return The time sand cost that should be applied every time this function is used.
     */
    public long getTimeSandCostPerApplication(ItemStack item);

    /**
     * Applies for every block broken, regardless of the effect.
     * @param item The ITemporalToolFunction stack.
     * @return The amount of time sand to consume.
     */
    public long getTimeSandCostPerBlock(ItemStack item);

    public float getQualityMultiplier(ItemStack item);
    public float getSpeedMultiplier(ItemStack item);
    public float getMemoryMultiplier(ItemStack item);

    public int getColorRed(ItemStack item);
    public int getColorGreen(ItemStack item);
    public int getColorBlue(ItemStack item);
}