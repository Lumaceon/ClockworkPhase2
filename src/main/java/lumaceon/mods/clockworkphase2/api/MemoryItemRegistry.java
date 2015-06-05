package lumaceon.mods.clockworkphase2.api;

import net.minecraft.item.Item;

import java.util.ArrayList;

/**
 * Used to register items as memory items. This will give it a small chance (default being 1 in 100) of being dropped
 * on the death of a mob which drops items.
 */
public class MemoryItemRegistry
{
    public static ArrayList<Item> MEMORY_ITEMS = new ArrayList<Item>();

    public static void registerMemoryItem(Item memoryItem) {
        MEMORY_ITEMS.add(memoryItem);
    }
}
