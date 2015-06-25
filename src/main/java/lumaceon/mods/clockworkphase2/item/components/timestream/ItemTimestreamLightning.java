package lumaceon.mods.clockworkphase2.item.components.timestream;

import lumaceon.mods.clockworkphase2.api.item.timestream.ITimezoneTimestream;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ItemTimestreamLightning extends ItemClockworkPhase implements ITimezoneTimestream
{
    public ItemTimestreamLightning(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public ResourceLocation getGlyphTexture(ItemStack item) {
        return Textures.ITEM.MAINSPRING;
    }

    @Override
    public int getMagnitude(ItemStack item) {
        return 0;
    }

    @Override
    public void setMagnitude(ItemStack item, int magnitude) {

    }

    @Override
    public int getColorRed(ItemStack item) {
        return 255;
    }

    @Override
    public int getColorGreen(ItemStack item) {
        return 255;
    }

    @Override
    public int getColorBlue(ItemStack item) {
        return 255;
    }

    @Override
    public void addTimestreamInformation(ItemStack item, EntityPlayer player, List list) {

    }
}
