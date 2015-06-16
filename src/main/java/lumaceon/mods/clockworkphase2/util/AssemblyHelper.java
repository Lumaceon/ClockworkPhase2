package lumaceon.mods.clockworkphase2.util;

import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkComponent;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.timestream.IToolTimestream;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.item.ItemStack;

/**
 * Provides a variety of classes for easy item assembly.
 */
public class AssemblyHelper
{
    /**
     * Methods to be called during createComponentInventory.
     */
    public static class INITIALIZE_SLOTS
    {
        /**
         * Loads the component inventory from the main item into the assembly slots. This includes disabled slots.
         */
        public static void loadStandardComponentInventory(ItemStack workItem, AssemblySlot[] slots)
        {
            ItemStack[] items = NBTHelper.INVENTORY.get(workItem, NBTTags.COMPONENT_INVENTORY);
            AssemblySlot slot;
            ItemStack item;
            if(items != null && slots != null)
            {
                for(int n = 0; n < slots.length; n++)
                {
                    slot = slots[n];
                    if(n < items.length)
                    {
                        item = items[n];
                        if(slot != null && item != null)
                            slot.setItemStack(item);
                    }
                }
            }
        }
    }

    /**
     * Methods to be called during onComponentChange.
     */
    public static class COMPONENT_CHANGE
    {
        /**
         * Handles the changes to clockwork.
         */
        public static void assembleClockwork(ItemStack workItem, AssemblySlot[] slots)
        {
            ItemStack item;
            int quality = 0; int speed = 0; int memory = 0; int harvestLevel = -1;
            for(int n = 0; n < slots.length; n++)
            {
                item = slots[n].getItemStack();
                if(item != null && item.getItem() instanceof IClockworkComponent)
                {
                    IClockworkComponent component = (IClockworkComponent) item.getItem();
                    quality += component.getQuality(item);
                    speed += component.getSpeed(item);
                    memory += component.getMemory(item);
                    harvestLevel = Math.max(harvestLevel, component.getHarvestLevel(item));
                }
            }
            NBTHelper.INT.set(workItem, NBTTags.QUALITY, quality);
            NBTHelper.INT.set(workItem, NBTTags.SPEED, speed);
            NBTHelper.INT.set(workItem, NBTTags.MEMORY, memory);
            NBTHelper.INT.set(workItem, NBTTags.HARVEST_LEVEL, harvestLevel);
        }

        /**
         * Used for items that run with clockwork and a mainspring. Sets the main item's clockwork and tension data.
         */
        public static void assembleClockworkConstruct(ItemStack workItem, AssemblySlot mainspringSlot, AssemblySlot clockworkSlot)
        {
            ItemStack mainspring = mainspringSlot.getItemStack();
            ItemStack clockwork = clockworkSlot.getItemStack();

            if(mainspring != null)
            {
                NBTHelper.INT.set(workItem, NBTTags.MAX_TENSION, NBTHelper.INT.get(mainspring, NBTTags.MAX_TENSION));
                NBTHelper.INT.set(workItem, NBTTags.CURRENT_TENSION, 0);
                workItem.setItemDamage(workItem.getMaxDamage());
            }
            else
            {
                NBTHelper.INT.set(workItem, NBTTags.MAX_TENSION, 0);
                NBTHelper.INT.set(workItem, NBTTags.CURRENT_TENSION, 0);
                workItem.setItemDamage(workItem.getMaxDamage());
            }

            if(clockwork != null)
            {
                NBTHelper.INT.set(workItem, NBTTags.QUALITY, NBTHelper.INT.get(clockwork, NBTTags.QUALITY));
                NBTHelper.INT.set(workItem, NBTTags.SPEED, NBTHelper.INT.get(clockwork, NBTTags.SPEED));
                NBTHelper.INT.set(workItem, NBTTags.MEMORY, NBTHelper.INT.get(clockwork, NBTTags.MEMORY));
                if(workItem.getItem() instanceof IClockworkConstruct)
                    ((IClockworkConstruct) workItem.getItem()).setHarvestLevels(workItem, NBTHelper.INT.get(clockwork, NBTTags.HARVEST_LEVEL));
            }
            else
            {
                NBTHelper.INT.set(workItem, NBTTags.QUALITY, 0);
                NBTHelper.INT.set(workItem, NBTTags.SPEED, 0);
                NBTHelper.INT.set(workItem, NBTTags.MEMORY, 0);
                if(workItem.getItem() instanceof IClockworkConstruct)
                    ((IClockworkConstruct) workItem.getItem()).setHarvestLevels(workItem, -1);
            }
        }

        /**
         * Used to set the quality/speed/memory modifiers. To be called after assembleClockworkConstruct.
         */
        public static void assembleClockworkTemporalTool(ItemStack workItem, AssemblySlot[] slots)
        {
            if(workItem != null && workItem.getItem() instanceof IClockworkConstruct && slots != null)
            {
                ItemStack item;
                IClockworkConstruct construct = (IClockworkConstruct) workItem.getItem();
                int quality = construct.getQuality(workItem);
                int speed = construct.getSpeed(workItem);
                int memory = construct.getMemory(workItem);
                for(int n = 0; n < slots.length; n++)
                {
                    item = slots[n].getItemStack();
                    if(item != null && item.getItem() instanceof IToolTimestream)
                    {
                        IToolTimestream temp = (IToolTimestream) item.getItem();
                        NBTHelper.INT.set(workItem, NBTTags.QUALITY, (int) (construct.getQuality(workItem) + quality * (temp.getQualityMultiplier(item) - 1)));
                        NBTHelper.INT.set(workItem, NBTTags.SPEED, (int) (construct.getSpeed(workItem) + speed * (temp.getSpeedMultiplier(item) - 1)));
                        NBTHelper.INT.set(workItem, NBTTags.MEMORY, (int) (construct.getMemory(workItem) + memory * (temp.getMemoryMultiplier(item) - 1)));
                    }
                }
            }
        }
    }

    /**
     * Methods to be called during saveComponentInventory.
     */
    public static class SAVE_COMPONENT_INVENTORY
    {
        /**
         * Saves the slot items into the item being assembled. This includes disabled slots.
         */
        public static void saveNewComponentInventory(ItemStack item, AssemblySlot[] slots)
        {
            ItemStack[] componentsToSave = new ItemStack[slots.length];
            for(int n = 0; n < slots.length; n++)
                componentsToSave[n] = slots[n].getItemStack();
            NBTHelper.INVENTORY.set(item, NBTTags.COMPONENT_INVENTORY, componentsToSave);
        }
    }
}
