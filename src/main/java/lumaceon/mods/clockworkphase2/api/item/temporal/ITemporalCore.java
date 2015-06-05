package lumaceon.mods.clockworkphase2.api.item.temporal;

import lumaceon.mods.clockworkphase2.api.ITemporalMaterial;
import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Temporal core required by most temporal items/blocks/etc. Basically a time battery. Temporal items tend to require
 * a temporal core to function.
 */
public interface ITemporalCore extends ITimeSand, ITemporalMaterial
{
    public ResourceLocation getGlyphTexture(ItemStack item);
}