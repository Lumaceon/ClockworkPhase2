package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.*;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkComponent;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporal;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalCore;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalToolFunction;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalable;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.lib.NBTTags;
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
                long timeSand;
                if(construct.getItem() instanceof ITemporalable && ((ITemporalable) construct.getItem()).isTemporal(construct))
                {
                    timeSand = TemporalToolHelper.getTimeSand(construct);
                    if(timeSand > 0)
                        list.add("Time Sand: " + Colors.AQUA + TimeConverter.parseNumber(timeSand, 2));
                }
                else
                {
                    timeSand = timeContainer.getTimeSand(construct);
                    if(timeSand > 0)
                        list.add("Time Sand: " + Colors.YELLOW + TimeConverter.parseNumber(timeSand, 2));
                }
            }

            int quality = clockworkConstruct.getQuality(construct);
            int speed = clockworkConstruct.getSpeed(construct);
            int memory = clockworkConstruct.getMemory(construct);

            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            {
                list.add("");
                list.add(Colors.BLUE + "~/Construct Details\\~");
                clockworkConstruct.addClockworkInformation(construct, player, list, flag);
                list.add(Colors.BLUE + "~/Construct Details\\~");
                list.add("");
            }
            else
            {
                list.add("");
                list.add(Colors.BLUE + "Shift - Construct Details");
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
            {
                list.add("");
                list.add(Colors.BLUE + "~/Clockwork Stats\\~");
                list.add(Colors.WHITE + "Quality: " + Colors.GOLD + quality);
                list.add(Colors.WHITE + "Speed: " + Colors.GOLD + speed);
                if(memory > 0)
                    list.add(Colors.WHITE + "Memory: " + Colors.GOLD + memory);
                list.add(Colors.BLUE + "~/Clockwork Stats\\~");
                list.add("");
            }
            else
                list.add(Colors.BLUE + "Ctrl - Clockwork Stats");


            if(clockworkConstruct.getMemory(construct) > 0 && construct.getItem() instanceof ITemporal)
            {
                if(Keyboard.isKeyDown(Keyboard.KEY_TAB)) //TODO fix this.
                {
                    list.add("");
                    list.add(Colors.BLUE + "~/Clockwork Stats\\~");
                    ((ITemporal) construct.getItem()).addTemporalInformation(construct, player, list, flag);
                    list.add(Colors.BLUE + "~/Clockwork Stats\\~");
                    list.add("");
                }
                else
                    list.add(Colors.BLUE + "Tab - Temporal Stats");
            }
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
            int harvestLevel = Math.max(Math.max(tool.getItem().getHarvestLevel(tool, "pickaxe"), tool.getItem().getHarvestLevel(tool, "axe")), tool.getItem().getHarvestLevel(tool, "shovel"));

            list.add(Colors.WHITE + "Harvest Level: " + Colors.GOLD + harvestLevel + " " + getMaterialNameFromHarvestLevel(harvestLevel));
            list.add(Colors.WHITE + "Mining Speed: " + Colors.GOLD + speed / 25);
            list.add(Colors.WHITE + "Tension Per Block: " + Colors.GOLD + ClockworkHelper.getTensionCostFromStats(Defaults.TENSION.perBlock, quality, speed));
        }
    }

    public static void addTemporalToolInformation(ItemStack tool, EntityPlayer player, List list)
    {
        if(tool.getItem() instanceof ITemporal && tool.getItem() instanceof IClockworkConstruct)
        {
            long timeSandCost = 0;
            int memory = ((IClockworkConstruct) tool.getItem()).getMemory(tool);
            boolean temporal = true;
            if(tool.getItem() instanceof ITemporalable)
                temporal = ((ITemporalable) tool.getItem()).isTemporal(tool);
            if(NBTHelper.hasTag(tool, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] components = NBTHelper.INVENTORY.get(tool, NBTTags.COMPONENT_INVENTORY);
                for(ItemStack item : components)
                {
                    if(item.getItem() instanceof ITemporalToolFunction)
                        timeSandCost += ((ITemporalToolFunction) item.getItem()).getTimeSandCostPerBlock(item);
                }
            }
            list.add(Colors.WHITE + "Temporal Significance: " + getTemporalSignificance(temporal));
            list.add(Colors.WHITE + "Time Sand Per Block: " + Colors.GOLD + TimeConverter.parseNumber(timeSandCost, 2));
            list.add(Colors.WHITE + "Time Sand Extraction Chance: " + Colors.GOLD + "1/" + TimeSandHelper.getTimeSandChance(player.experienceLevel));
            list.add(Colors.WHITE + "Time Sand From Extraction: " + Colors.GOLD + TimeConverter.parseNumber(ClockworkHelper.getTimeSandFromStats(memory), 15));
        }
    }

    public static void addTemporalFunctionInformation(ItemStack is, List list, float qualityMod, float speedMod, float memoryMod)
    {

    }

    public static void addMainspringInformation(ItemStack is, List list)
    {
        if(is.getItem() instanceof IMainspring)
        {
            IMainspring mainspring = (IMainspring) is.getItem();
            list.add("Tension: " + mainspring.getTension(is));
        }
    }

    public static void addClockworkInformation(ItemStack is, List list)
    {
        if(is.getItem() instanceof IClockwork)
        {
            IClockwork clockwork = (IClockwork) is.getItem();
            list.add("Quality: " + clockwork.getQuality(is));
            list.add("Speed: " + clockwork.getSpeed(is));
            list.add("Memory: " + clockwork.getMemory(is));
        }
    }

    public static void addTemporalCoreInformation(ItemStack is, List list)
    {
        if(is.getItem() instanceof ITemporalCore)
        {
            ITemporalCore core = (ITemporalCore) is.getItem();
            long timeSand = core.getTimeSand(is);
            long max = core.getMaxTimeSand(is);
            list.add("Time Sand: " + Colors.AQUA + TimeConverter.parseNumber(timeSand, 2) + "/" + TimeConverter.parseNumber(max, 1));
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

    public static String getMaterialNameFromHarvestLevel(int harvestLevel)
    {
        switch(harvestLevel)
        {
            case -1:
                return Colors.BLACK + "(Bare Hands)";
            case 0:
                return Colors.DARK_GREY + "(Wood Tool)";
            case 1:
                return Colors.GREY + "(Stone Tool)";
            case 2:
                return Colors.WHITE + "(Iron Tool)";
            case 3:
                return Colors.AQUA + "(Diamond Tool)";
            case 4:
                return Colors.BLUE + "(Cobalt Tool)";
            case 5:
                return Colors.LIGHT_PURPLE + "(Manyullyn Tool)";
            default:
                return "";
        }
    }

    public static String getTemporalSignificance(boolean temporal)
    {
        if(temporal)
            return Colors.AQUA + "(Temporal)";
        else
            return Colors.GREY + "(Insignificant)";
    }
}
