package lumaceon.mods.clockworkphase2.api;

import lumaceon.mods.clockworkphase2.api.util.WeightedChance;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class HourglassDropRegistry
{
    private static ArrayList<WeightedChance<ItemStack>> timeframeKeyDrops = new ArrayList<WeightedChance<ItemStack>>();

    /**
     * Registers a stack to drop from IPhaseEntity mobs, using the random timeframe key dropping logic.
     * @param drop A weighted chance. Default weight is 100 (which you should probably use here).
     */
    public static void registerTimeframeKeyDrop(WeightedChance<ItemStack> drop) {
        timeframeKeyDrops.add(drop);
    }

    public static ItemStack getRandomTimeframeKeyDrop()
    {
        if(timeframeKeyDrops != null && timeframeKeyDrops.size() > 0)
        {
            WeightedChance[] chances = new WeightedChance[timeframeKeyDrops.size()];
            chances = timeframeKeyDrops.toArray(chances);
            if(chances.length > 0 && chances[0] != null && chances[0].object instanceof ItemStack)
                return ((ItemStack) chances[0].getObjectFromWeightedChance(chances)).copy();
        }
        return null;
    }

    public static ArrayList<WeightedChance<ItemStack>> getTimeframeKeyDrops() {
        return timeframeKeyDrops;
    }
}