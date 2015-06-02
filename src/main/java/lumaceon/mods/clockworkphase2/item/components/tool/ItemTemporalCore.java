package lumaceon.mods.clockworkphase2.item.components.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalCore;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.TimeSandHelper;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemTemporalCore extends ItemClockworkPhase implements ITemporalCore
{
    public ItemTemporalCore(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addTemporalCoreInformation(is, list);
    }

    @Override
    public long getTimeSand(ItemStack item) {
        return TimeSandHelper.getTimeSand(item);
    }

    @Override
    public long getMaxTimeSand(ItemStack item) {
        return TimeConverter.CENTURY;
    }

    @Override
    public void setTimeSand(ItemStack item, EntityPlayer player, long timeSand) {
        TimeSandHelper.setTimeSand(item, timeSand);
    }

    @Override
    public void setTimeSand(ItemStack item, long timeSand) {
        TimeSandHelper.setTimeSand(item, timeSand);
    }

    @Override
    public long addTimeSand(ItemStack item, EntityPlayer player, long amount) {
        return TimeSandHelper.addTimeSand(item, player, amount);
    }

    @Override
    public long addTimeSand(ItemStack item, long amount) {
        return TimeSandHelper.addTimeSand(item, amount);
    }

    @Override
    public long consumeTimeSand(ItemStack item, EntityPlayer player, long amount) {
        return TimeSandHelper.consumeTimeSand(item, player, amount);
    }

    @Override
    public long consumeTimeSand(ItemStack item, long amount) {
        return TimeSandHelper.consumeTimeSand(item, amount);
    }
}
