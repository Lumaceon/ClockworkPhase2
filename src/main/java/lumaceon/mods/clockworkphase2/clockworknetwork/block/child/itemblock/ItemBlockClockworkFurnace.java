package lumaceon.mods.clockworkphase2.clockworknetwork.block.child.itemblock;

import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockClockworkFurnace extends ItemBlockCN
{
    public ItemBlockClockworkFurnace(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    public void addConstructInformation(ItemStack item, EntityPlayer player, List list)
    {
        int quality = getQuality(item);
        int speed = getSpeed(item);

        if(speed <= 0 || quality <= 0 || ((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50)) == 0)
        {
            list.add(Colors.RED + "NON-FUNCTIONAL");
            return;
        }

        int timePerSmelt = 10000 / ((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50));
        if(timePerSmelt >= 20)
            list.add(Colors.WHITE + "Time Per Smelt: " + Colors.GOLD + TimeConverter.parseNumber(timePerSmelt, 1));
        else if(timePerSmelt != 1)
            list.add(Colors.WHITE + "Time Per Smelt: " + Colors.GOLD + timePerSmelt + " Ticks");
        else
            list.add(Colors.WHITE + "Time Per Smelt: " + Colors.GOLD + "1 Tick");
        list.add(Colors.WHITE + "~Tension Used Per Operation: " + Colors.GOLD + (ClockworkHelper.getTensionCostFromStats((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50), quality, speed) / 50) * timePerSmelt);
        list.add(Colors.WHITE + "~Tension Used Per Second: " + Colors.GOLD + (ClockworkHelper.getTensionCostFromStats((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50), quality, speed) / 50) * 20);
    }
}
