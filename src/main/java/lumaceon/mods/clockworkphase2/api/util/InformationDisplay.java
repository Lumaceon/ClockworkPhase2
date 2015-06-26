package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.*;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkComponent;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporal;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalCore;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalable;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
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

    public static int defaultTensionPerBlock = 50;

    public static void addClockworkComponentInformation(ItemStack component, List list)
    {
        if(component.getItem() instanceof IClockworkComponent)
        {
            IClockworkComponent clockworkComponent = (IClockworkComponent) component.getItem();
            int quality = clockworkComponent.getQuality(component);
            int speed = clockworkComponent.getSpeed(component);
            int memory = clockworkComponent.getMemory(component);
            int harvestLevel = clockworkComponent.getHarvestLevel(component);
            String color = getColorFromComponentStat(quality);

            if(quality > 0)
                list.add(Colors.WHITE + "Quality: " + color + quality);
            color = getColorFromComponentStat(speed);
            if(speed > 0)
                list.add(Colors.WHITE + "Speed: " + color + speed);
            if(memory > 0)
                list.add(Colors.WHITE + "Memory: " + memory);
            list.add(Colors.WHITE + "Harvest Level: " + harvestLevel + " " + getMaterialNameFromHarvestLevel(harvestLevel));
        }
    }

    public static void addClockworkConstructInformation(ItemStack construct, EntityPlayer player, List list, boolean isTool)
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
                clockworkConstruct.addClockworkInformation(construct, player, list);
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


            if(construct.getItem() instanceof ITemporal)
            {
                boolean temporal = true;
                if(construct.getItem() instanceof ITemporalable)
                    temporal = ((ITemporalable) construct.getItem()).isTemporal(construct);
                if(isTool && memory > 0)
                    temporal = true;

                if(temporal)
                {
                    if(Keyboard.isKeyDown(Keyboard.KEY_TAB))
                    {
                        list.add("");
                        list.add(Colors.BLUE + "~/Temporal Stats\\~");
                        ((ITemporal) construct.getItem()).addTemporalInformation(construct, player, list);
                        list.add(Colors.BLUE + "~/Temporal Stats\\~");
                        list.add("");
                    }
                    else
                        list.add(Colors.BLUE + "Tab - Temporal Stats");
                }
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
            list.add(Colors.WHITE + "Tension Per Block: " + Colors.GOLD + ClockworkHelper.getTensionCostFromStats(defaultTensionPerBlock, quality, speed));
        }
    }

    public static void addTemporalToolInformation(ItemStack tool, EntityPlayer player, List list)
    {
        if(tool.getItem() instanceof ITemporal && tool.getItem() instanceof IClockworkConstruct)
        {
            long timeSand = TemporalToolHelper.getTimeSand(tool);
            boolean temporalCore = false;
            int memory = ((IClockworkConstruct) tool.getItem()).getMemory(tool);
            if(NBTHelper.hasTag(tool, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] components = NBTHelper.INVENTORY.get(tool, NBTTags.COMPONENT_INVENTORY);
                for(ItemStack item : components)
                {
                    if(item != null && item.getItem() instanceof ITemporalCore)
                        temporalCore = true;
                }
            }
            list.add(getTemporalSignificance(temporalCore, timeSand));
            list.add(Colors.WHITE + "Time Sand Extraction Chance: " + Colors.GOLD + "1 / " + TimeSandHelper.getTimeSandChance(player.experienceLevel));
            list.add(Colors.WHITE + "Time Sand From Extraction: " + Colors.GOLD + TimeConverter.parseNumber(ClockworkHelper.getTimeSandFromStats(memory), 2));
        }
    }

    /*public static void addTimestreamInformation(ItemStack is, EntityPlayer player, List list, float qualityMod, float speedMod, float memoryMod)
    {
        list.add("");

        if(is.getItem() instanceof ITimestream)
        {
            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            {
                list.add(Colors.BLUE + "~/Valid Applications\\~");
                if(is.getItem() instanceof ITimezoneTimestream)
                    list.add(Colors.AQUA + "{Celestial Compass Timezones}");
                if(is.getItem() instanceof IToolTimestream)
                    list.add(Colors.AQUA + "{Temporal Tools}");
                if(is.getItem() instanceof IPACTimestream)
                    list.add(Colors.AQUA + "{Personal Assistant Cubes}");
                if(is.getItem() instanceof IReversableTimestream)
                    list.add(Colors.AQUA + "{Timestream Reversalizers}");
                list.add(Colors.BLUE + "~/Valid Applications\\~");
                list.add("");
            }
            else
                list.add(Colors.BLUE + "Shift - Valid Applications");

            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
            {
                list.add("");
                list.add(Colors.BLUE + "~/Timestream Details\\~");
                ((ITimestream) is.getItem()).addTimestreamInformation(is, player, list);
                list.add(Colors.BLUE + "~/Timestream Details\\~");
                list.add("");
            }
            else
                list.add(Colors.BLUE + "Ctrl - Timestream Details");
        }

        if(is.getItem() instanceof IToolTimestream && qualityMod != 1.0F || speedMod != 1.0F || memoryMod != 1.0F)
        {
            if(Keyboard.isKeyDown(Keyboard.KEY_TAB))
            {
                list.add("");
                list.add(Colors.BLUE + "~/Clockwork Modifiers\\~");
                if(qualityMod > 1.0F)
                    list.add(Colors.GREEN + "+" + (int)((qualityMod - 1.0F) * 100) + "% Quality");
                else if(qualityMod < 1.0F)
                    list.add(Colors.RED + (int)((qualityMod - 1.0F) * 100) + "% Quality");
                if(speedMod > 1.0F)
                    list.add(Colors.GREEN + "+" + (int)((speedMod - 1.0F) * 100) + "% Speed");
                else if(speedMod < 1.0F)
                    list.add(Colors.RED + (int)((speedMod - 1.0F) * 100) + "% Speed");
                if(memoryMod > 1.0F)
                    list.add(Colors.WHITE + "+" + (int)((memoryMod - 1.0F) * 100) + "% Memory");
                else if(memoryMod < 1.0F)
                    list.add(Colors.RED + (int)((memoryMod - 1.0F) * 100) + "% Memory");
                list.add(Colors.BLUE + "~/Clockwork Modifiers\\~");
                list.add("");
            }
            else
                list.add(Colors.BLUE + "Tab - Clockwork Modifiers");
        }
    }*/

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
            list.add("Time Sand: " + Colors.AQUA + TimeConverter.parseNumber(timeSand, 2));
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

    /**
     * Used primarily by temporal objects to print a temporal significance.
     * @param temporalCore Usually represents whether or not a temporal core is present. If not, Unstable Temporality is
     *                     returned. If used in a case where a temporal core is not needed, this should always be true.
     * @param timeSand The time sand to query.
     * @return A colored string representing the temporal significance of the amount of time sand.
     */
    public static String getTemporalSignificance(boolean temporalCore, long timeSand)
    {
        if(!temporalCore)
            return Colors.GREY + "(Unstable Temporality)";
        if(timeSand < TimeConverter.WEEK)
            return Colors.WHITE + "(Self Temporality)";
        else if(timeSand <= TimeConverter.CENTURY)
            return Colors.YELLOW + "(Local Temporality)";
        else if(timeSand <= TimeConverter.AGE)
            return Colors.DARK_AQUA + "(Global Temporality)";
        else if(timeSand <= TimeConverter.EON)
            return Colors.AQUA + "(Interstellar Temporality)";
        else if(timeSand < TimeConverter.INFINITE)
            return Colors.LIGHT_PURPLE + "(Galactic Temporality)";
        else
            return Colors.WHITE + "(" +
                    Colors.YELLOW + "I" +
                    Colors.LIGHT_PURPLE + "n" +
                    Colors.AQUA + "f" +
                    Colors.YELLOW + "i" +
                    Colors.LIGHT_PURPLE + "n" +
                    Colors.AQUA + "i" +
                    Colors.YELLOW + "t" +
                    Colors.LIGHT_PURPLE + "e " +
                    Colors.AQUA + "T" +
                    Colors.YELLOW + "e" +
                    Colors.LIGHT_PURPLE + "m" +
                    Colors.AQUA + "p" +
                    Colors.YELLOW + "o" +
                    Colors.LIGHT_PURPLE + "r" +
                    Colors.AQUA + "a" +
                    Colors.YELLOW + "l" +
                    Colors.LIGHT_PURPLE + "i" +
                    Colors.AQUA + "t" +
                    Colors.YELLOW + "y" +
                    Colors.WHITE + ")";
    }
}