package lumaceon.mods.clockworkphase2.item.components.timestream;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.item.timestream.IToolTimestreamPassive;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.TimestreamHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ItemTimestreamSmelt extends ItemClockworkPhase implements IToolTimestreamPassive
{
    public ItemTimestreamSmelt(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addTimestreamInformation(is, player, list, getQualityMultiplier(is), getSpeedMultiplier(is), getMemoryMultiplier(is));
    }

    @Override
    public boolean isEnabled(ItemStack item) {
        return TimestreamHelper.isEnabled(item);
    }

    @Override
    public long getTimeSandCostPerApplication(ItemStack item) {
        return TimeConverter.MINUTE * 5;
    }

    @Override
    public long getTimeSandCostPerBlock(ItemStack item) {
        return 10;
    }

    @Override
    public float getQualityMultiplier(ItemStack item) {
        return 1.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack item) {
        return 1.2F;
    }

    @Override
    public float getMemoryMultiplier(ItemStack item) {
        return 1.0F;
    }

    @Override
    public int getMagnitude(ItemStack item) {
        return TimestreamHelper.getMagnitude(item);
    }

    @Override
    public void setMagnitude(ItemStack item, int magnitude) {
        TimestreamHelper.setMagnitude(item, magnitude);
    }

    @Override
    public int getColorRed(ItemStack item) {
        return 255;
    }

    @Override
    public int getColorGreen(ItemStack item) {
        return 90;
    }

    @Override
    public int getColorBlue(ItemStack item) {
        return 0;
    }

    @Override
    public void addTimestreamInformation(ItemStack item, EntityPlayer player, List list)
    {

    }
}