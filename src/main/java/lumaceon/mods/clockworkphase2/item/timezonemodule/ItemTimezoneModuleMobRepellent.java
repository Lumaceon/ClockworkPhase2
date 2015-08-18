package lumaceon.mods.clockworkphase2.item.timezonemodule;

import lumaceon.mods.clockworkphase2.api.item.ITimezoneModule;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemTimezoneModuleMobRepellent extends ItemClockworkPhase implements ITimezoneModule
{
    public ItemTimezoneModuleMobRepellent(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public ResourceLocation getGlyphTexture(ItemStack item) {
        return Textures.PARTICLE.TEST;
    }

    @Override
    public int getColorRed(ItemStack item) {
        return 180;
    }

    @Override
    public int getColorGreen(ItemStack item) {
        return 180;
    }

    @Override
    public int getColorBlue(ItemStack item) {
        return 180;
    }
}
