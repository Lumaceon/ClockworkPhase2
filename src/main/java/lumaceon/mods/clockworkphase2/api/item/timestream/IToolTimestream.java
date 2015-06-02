package lumaceon.mods.clockworkphase2.api.item.timestream;

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
 * Two different kinds of IToolTimestreams exist: active and passive. Each tool (in vanilla Clockwork Phase) may only
 * contain one active timestream, which is usually applied on a right-click of some kind. However, a tool may contain a
 * larger number of passive modules as they tend to work via events.
 */
public interface IToolTimestream extends ITimestream
{
    /**
     * @param item The IToolTimestream stack.
     * @return Whether or not this itemstack is enabled, allowing the player to disable passive timestreams.
     */
    public boolean isEnabled(ItemStack item);

    /**
     * Cost and action varies depending on the tool. This is not handled automatically, you will have to handle the
     * costs yourself.
     * @param item The IToolTimestream stack.
     * @return The time sand cost that should be applied every time this function is used.
     */
    public long getTimeSandCostPerApplication(ItemStack item);

    /**
     * Applies for every block broken, regardless of the effect.
     * @param item The IToolTimestream stack.
     * @return The amount of time sand to consume.
     */
    public long getTimeSandCostPerBlock(ItemStack item);

    public float getQualityMultiplier(ItemStack item);
    public float getSpeedMultiplier(ItemStack item);
    public float getMemoryMultiplier(ItemStack item);
}