package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.*;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class InformationDisplay
{
    //                crappy < 20;
    public static int poor = 20;
    public static int decent = 30;
    public static int good = 40;
    public static int great = 50;
    public static int excellent = 70;
    public static int epic = 100;

    public static void addClockworkComponentInformation(ItemStack component, List list)
    {
        if(component.getItem() instanceof IClockworkComponent)
        {
            IClockworkComponent clockworkComponent = (IClockworkComponent) component.getItem();
            int quality = clockworkComponent.getQuality(component);
            int speed = clockworkComponent.getSpeed(component);
            int memory = clockworkComponent.getMemory(component);
            String color = getColorFromComponentStat(quality);

            if(quality > 0)
                list.add(Colors.WHITE + "Quality: " + color + quality);
            color = getColorFromComponentStat(speed);
            if(speed > 0)
                list.add(Colors.WHITE + "Speed: " + color + speed);
            if(memory > 0)
                list.add(Colors.WHITE + "Memory: " + memory);
        }
    }

    public static void addClockworkConstructInformation(ItemStack construct, EntityPlayer player, List list, boolean flag)
    {
        if(construct.getItem() instanceof IClockworkConstruct)
        {
            IClockworkConstruct clockworkConstruct = (IClockworkConstruct) construct.getItem();
            String color = getColorFromTension(clockworkConstruct.getTension(construct), clockworkConstruct.getMaxTension(construct));
            list.add("Tension: " + color + clockworkConstruct.getTension(construct) + "/" + clockworkConstruct.getMaxTension(construct));
            if(construct.getItem() instanceof ITimeSand)
            {
                ITimeSand timeContainer = (ITimeSand) construct.getItem();
                long timeSand = timeContainer.getTimeSand(construct);
                if(timeSand > 0)
                    list.add(Colors.YELLOW + "Time Sand: " + TimeConverter.parseNumber(timeSand, 2));
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            {
                list.add("");
                list.add(Colors.BLUE + "~/Construct Details\\~");
                clockworkConstruct.addRelevantInformation(construct, player, list, flag);
                list.add(Colors.BLUE + "~/Construct Details\\~");
                list.add("");
            }
            else
            {
                list.add("");
                list.add("Shift - Construct Details");
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
            {
                list.add("");
                list.add(Colors.BLUE + "~/Clockwork Stats\\~");
                list.add(Colors.WHITE + "Quality: " + Colors.GOLD + clockworkConstruct.getQuality(construct));
                list.add(Colors.WHITE + "Speed: " + Colors.GOLD + clockworkConstruct.getSpeed(construct));
                list.add(Colors.WHITE + "Memory: " + Colors.GOLD + clockworkConstruct.getMemory(construct));
                list.add(Colors.BLUE + "~/Clockwork Stats\\~");
                list.add("");
            }
            else
                list.add("Ctrl - Clockwork Stats");
        }
    }

    public static void addClockworkToolInformation(ItemStack tool, EntityPlayer player, List list)
    {
        if(tool.getItem() instanceof IClockworkConstruct)
        {
            IClockworkConstruct clockworkComponent = (IClockworkConstruct) tool.getItem();
            int quality = clockworkComponent.getQuality(tool);
            int speed = clockworkComponent.getSpeed(tool);
            int memory = clockworkComponent.getMemory(tool);

            list.add(Colors.WHITE + "Mining Speed: " + Colors.GOLD + speed / 25);
            list.add(Colors.WHITE + "Tension Per Block: " + Colors.GOLD + ClockworkHelper.getTensionCostFromStats(Defaults.TENSION.perBlock, quality, speed));
            list.add(Colors.WHITE + "Time Sand Chance: " + Colors.GOLD + "1/" + TimeSandHelper.getTimeSandChance(player.experienceLevel));
            list.add(Colors.WHITE + "Time Sand From Success: " + Colors.GOLD + TimeConverter.parseNumber(ClockworkHelper.getTimeSandFromStats(memory), 15));
        }
    }

    public static void addMainspringInformation(ItemStack is, List list)
    {
        if(is.getItem() instanceof IMainspring)
        {
            IMainspring mainspring = (IMainspring) is.getItem();
            list.add("Tension: " + Colors.WHITE + mainspring.getTension(is));
        }
    }

    public static void addClockworkInformation(ItemStack is, List list)
    {
        if(is.getItem() instanceof IClockwork)
        {
            IClockwork clockwork = (IClockwork) is.getItem();
            list.add("Quality: " + Colors.WHITE + clockwork.getQuality(is));
            list.add("Speed: " + Colors.WHITE + clockwork.getSpeed(is));
            list.add("Memory: " + Colors.WHITE + clockwork.getMemory(is));
        }
    }

    public static String getColorFromComponentStat(int stat)
    {
        if(stat >= epic)
            return Colors.LIGHT_PURPLE;
        if(stat >= excellent)
            return Colors.AQUA;
        if(stat >= great)
            return Colors.GREEN;
        if(stat >= good)
            return Colors.YELLOW;
        if(stat >= decent)
            return Colors.RED;
        if(stat >= poor)
            return Colors.GREY;
        if(stat < poor) //crappy
            return Colors.DARK_GREY;
        return Colors.DARK_GREY;
    }

    public static String getColorFromTension(int tension, int maxTension)
    {
        if(maxTension <= 0 || tension <= 0)
            return Colors.RED;
        if(maxTension / tension <= 2)
            return Colors.GREEN;
        else if(maxTension / tension <= 4)
            return Colors.YELLOW;
        else
            return Colors.RED;
    }
}
