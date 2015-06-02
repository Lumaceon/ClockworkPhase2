package lumaceon.mods.clockworkphase2.api.item.timestream;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Items implementing ITimestreamTimezone can be placed into the celestial compass to modify the timezone.
 */
public interface ITimezoneTimestream extends ITimestream
{
    /**
     * Returns a ResourceLocation which will be rendered as a square on top of the celestial compass circle
     * that this timestream is attached to.
     * @return The texture to render, or null if none should be rendered.
     */
    public ResourceLocation getCelestialCompassSymbol(ItemStack item);
}