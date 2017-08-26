package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

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

    public static void addEnergyInformation(IEnergyStorage energyStorage, List<String> tooltip)
    {
        String color = getColorFromTension(energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
        tooltip.add("Energy: " + color + energyStorage.getEnergyStored() + " / " + energyStorage.getMaxEnergyStored() + " FE");
    }

    /**
     * Adds information based on a simple tiles component (such as a gear).
     * @param component
     * @param list
     */
    public static void addClockworkComponentInformation(ItemStack component, List list)
    {
        if(component.getItem() instanceof IClockwork)
        {
            IClockwork clockworkComponent = (IClockwork) component.getItem();
            int quality = clockworkComponent.getQuality(component);
            int speed = clockworkComponent.getSpeed(component);
            int harvestLevel = clockworkComponent.getTier(component);

            String color = getColorFromComponentStat(quality);
            list.add(Colors.WHITE + "Quality: " + color + quality);

            color = getColorFromComponentStat(speed);
            list.add(Colors.WHITE + "Speed: " + color + speed);

            list.add(Colors.WHITE + "Harvest Level: " + harvestLevel + " " + getMaterialNameFromHarvestLevel(harvestLevel));
        }
    }

    public static void addQualityAndSpeed(ItemStack component, List list)
    {
        if(component.getItem() instanceof IClockwork)
        {
            IClockwork clockworkComponent = (IClockwork) component.getItem();
            int quality = clockworkComponent.getQuality(component);
            int speed = clockworkComponent.getSpeed(component);

            String color = getColorFromComponentStat(quality);
            list.add(Colors.WHITE + "Quality: " + color + quality);

            color = getColorFromComponentStat(speed);
            list.add(Colors.WHITE + "Speed: " + color + speed);
        }
    }

    public static void addClockworkToolInformation(ItemStack tool, EntityPlayer player, List list)
    {
        IClockwork clockworkComponent = (IClockwork) tool.getItem();
        int quality = clockworkComponent.getQuality(tool);
        int speed = clockworkComponent.getSpeed(tool);
        int harvestLevel = Math.max(Math.max(tool.getItem().getHarvestLevel(tool, "pickaxe", player, null), tool.getItem().getHarvestLevel(tool, "axe", player, null)), tool.getItem().getHarvestLevel(tool, "shovel", player, null));

        list.add(Colors.WHITE + "Harvest Level: " + Colors.GOLD + harvestLevel + " " + getMaterialNameFromHarvestLevel(harvestLevel));
        list.add(Colors.WHITE + "Mining Speed: " + Colors.GOLD + speed / 25);
        list.add(Colors.WHITE + "Energy Per Block: " + Colors.GOLD + ClockworkHelper.getTensionCostFromStats(defaultTensionPerBlock, quality, speed));
    }

    public static void addMainspringInformation(ItemStack is, List list)
    {
        if(is.getItem() instanceof IMainspring)
        {
            IMainspring mainspring = (IMainspring) is.getItem();
            list.add("Capacity: " + mainspring.getCurrentCapacity(is));
        }
    }

    public static void addClockworkInformation(ItemStack is, List list)
    {
        if(is.getItem() instanceof IClockwork)
        {
            IClockwork clockwork = (IClockwork) is.getItem();
            list.add("Quality: " + clockwork.getQuality(is));
            list.add("Speed: " + clockwork.getSpeed(is));
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
        else if(tension >= (double) maxTension * 0.25)
            return Colors.GOLD;
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
                return Colors.BLUE + "(Diamond+ Tool)";
            default:
                if(harvestLevel > 4)
                    return Colors.LIGHT_PURPLE + "(Diamond++ Tool)";
                return "";
        }
    }
}