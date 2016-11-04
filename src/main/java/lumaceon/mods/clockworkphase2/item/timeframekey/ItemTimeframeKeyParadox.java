package lumaceon.mods.clockworkphase2.item.timeframekey;

import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.lib.LocalizationStrings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.lang3.RandomStringUtils;

public class ItemTimeframeKeyParadox extends ItemTimeframeKey
{
    public ItemTimeframeKeyParadox(int maxStack, int maxDamage, String registryName) {
        super(maxStack, maxDamage, registryName);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        String localNameForParadox = I18n.translateToLocal(LocalizationStrings.PARADOX_CIV_KEY);
        if(System.nanoTime() % 3 == 0)
            return Colors.LIGHT_PURPLE + localNameForParadox + " " + Colors.WHITE + super.getItemStackDisplayName(stack);
        else
        return RandomStringUtils.randomAlphanumeric(localNameForParadox.length()) + " " + super.getItemStackDisplayName(stack);
    }
}
