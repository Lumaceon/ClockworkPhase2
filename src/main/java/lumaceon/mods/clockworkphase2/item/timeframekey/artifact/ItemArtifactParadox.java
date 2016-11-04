package lumaceon.mods.clockworkphase2.item.timeframekey.artifact;

import lumaceon.mods.clockworkphase2.api.item.ITimeframeKeyItem;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.lib.LocalizationStrings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.lang3.RandomStringUtils;

public class ItemArtifactParadox extends ItemArtifact
{
    public ItemArtifactParadox(int maxStack, int maxDamage, String registryName, ITimeframeKeyItem key) {
        super(maxStack, maxDamage, registryName, key);
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
