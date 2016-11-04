package lumaceon.mods.clockworkphase2.api;

import lumaceon.mods.clockworkphase2.api.util.WeightedChance;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class RelicExcavationRegistry
{
    private static final ArrayList<WeightedChance<ItemStack>> MOON_FLOWER_RELIC_DROPS = new ArrayList<WeightedChance<ItemStack>>();
    private static final ArrayList<WeightedChance<ItemStack>> UNKNOWN_RELIC_DROPS = new ArrayList<WeightedChance<ItemStack>>();

    public static void registerMoonFlowerRelicDrop(WeightedChance<ItemStack> chance) {
        MOON_FLOWER_RELIC_DROPS.add(chance);
    }

    public static ArrayList<WeightedChance<ItemStack>> getMoonFlowerRelicDropList() {
        return MOON_FLOWER_RELIC_DROPS;
    }

    public static void registerUnknownRelicDrop(WeightedChance<ItemStack> chance) {
        UNKNOWN_RELIC_DROPS.add(chance);
    }

    public static ArrayList<WeightedChance<ItemStack>> getUnknownRelicDropList() {
        return UNKNOWN_RELIC_DROPS;
    }
}
