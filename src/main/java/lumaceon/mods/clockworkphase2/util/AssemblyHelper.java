package lumaceon.mods.clockworkphase2.util;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkComponent;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalToolFunction;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.lib.NBTTags;
import net.minecraft.item.ItemStack;

/**
 * Provides a variety of classes for easy item assembly.
 */
public class AssemblyHelper
{
    /**
     * Methods to be called during createComponentInventory.
     */
    public static class CREATE_COMPONENT_INVENTORY
    {
        /**
         * Loads the component inventory from the main item into the inventory.
         * Note that the inventory MUST be large enough to hold all of the items being loaded.
         * @param container The container to get the main item from.
         * @param inventory The inventory to load the items into.
         */
        public static void loadStandardComponentInventory(IAssemblyContainer container, InventoryAssemblyComponents inventory)
        {
            ItemStack mainItem = container.getMainInventory().getStackInSlot(0);
            if(mainItem != null)
            {
                ItemStack[] components = NBTHelper.INVENTORY.get(mainItem, NBTTags.COMPONENT_INVENTORY);
                if(components == null)
                    components = new ItemStack[inventory.getSizeInventory()];

                for(int n = 0; n < components.length; n++)
                    inventory.setInventorySlotContentsRemotely(n, components[n]);
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
         * @param container The opened container.
         */
        public static void assembleClockwork(IAssemblyContainer container)
        {
            ItemStack mainItem = container.getMainInventory().getStackInSlot(0);
            ItemStack item;
            int quality = 0; int speed = 0; int memory = 0; int harvestLevel = -1;
            for(int n = 0; n < container.getComponentInventory().getSizeInventory(); n++)
            {
                item = container.getComponentInventory().getStackInSlot(n);
                if(item != null && item.getItem() instanceof IClockworkComponent)
                {
                    IClockworkComponent component = (IClockworkComponent) item.getItem();
                    quality += component.getQuality(item);
                    speed += component.getSpeed(item);
                    memory += component.getMemory(item);
                    harvestLevel = Math.max(harvestLevel, component.getHarvestLevel(item));
                }
            }
            NBTHelper.INT.set(mainItem, NBTTags.QUALITY, quality);
            NBTHelper.INT.set(mainItem, NBTTags.SPEED, speed);
            NBTHelper.INT.set(mainItem, NBTTags.MEMORY, memory);
            NBTHelper.INT.set(mainItem, NBTTags.HARVEST_LEVEL, harvestLevel);
        }

        /**
         * Used for items that run with clockwork and a mainspring. Sets the main item's clockwork and tension data.
         * @param container The opened container.
         * @param mainspringSlotIndex A slot which specifically contains a mainspring.
         * @param clockworkSlotIndex A slot which specifically contains clockwork.
         */
        public static void assembleClockworkConstruct(IAssemblyContainer container, int mainspringSlotIndex, int clockworkSlotIndex)
        {
            ItemStack mainItem = container.getMainInventory().getStackInSlot(0);
            ItemStack mainspring = container.getComponentInventory().getStackInSlot(mainspringSlotIndex);
            ItemStack clockwork = container.getComponentInventory().getStackInSlot(clockworkSlotIndex);

            if(mainspring != null)
            {
                NBTHelper.INT.set(mainItem, NBTTags.MAX_TENSION, NBTHelper.INT.get(mainspring, NBTTags.MAX_TENSION));
                NBTHelper.INT.set(mainItem, NBTTags.CURRENT_TENSION, 0);
                mainItem.setItemDamage(mainItem.getMaxDamage());
            }
            else
            {
                NBTHelper.INT.set(mainItem, NBTTags.MAX_TENSION, 0);
                NBTHelper.INT.set(mainItem, NBTTags.CURRENT_TENSION, 0);
                mainItem.setItemDamage(mainItem.getMaxDamage());
            }

            if(clockwork != null)
            {
                NBTHelper.INT.set(mainItem, NBTTags.QUALITY, NBTHelper.INT.get(clockwork, NBTTags.QUALITY));
                NBTHelper.INT.set(mainItem, NBTTags.SPEED, NBTHelper.INT.get(clockwork, NBTTags.SPEED));
                NBTHelper.INT.set(mainItem, NBTTags.MEMORY, NBTHelper.INT.get(clockwork, NBTTags.MEMORY));
                if(mainItem.getItem() instanceof IClockworkConstruct)
                    ((IClockworkConstruct) mainItem.getItem()).setHarvestLevels(mainItem, NBTHelper.INT.get(clockwork, NBTTags.HARVEST_LEVEL));
            }
            else
            {
                NBTHelper.INT.set(mainItem, NBTTags.QUALITY, 0);
                NBTHelper.INT.set(mainItem, NBTTags.SPEED, 0);
                NBTHelper.INT.set(mainItem, NBTTags.MEMORY, 0);
                if(mainItem.getItem() instanceof IClockworkConstruct)
                    ((IClockworkConstruct) mainItem.getItem()).setHarvestLevels(mainItem, -1);
            }
        }

        /**
         * Used to set the quality/speed/memory modifiers. To be called after assembleClockworkConstruct.
         */
        public static void assembleClockworkTemporalTool(IAssemblyContainer container)
        {
            ItemStack mainItem = container.getMainInventory().getStackInSlot(0);
            InventoryAssemblyComponents inventory = container.getComponentInventory();
            if(mainItem != null && mainItem.getItem() instanceof IClockworkConstruct && inventory != null)
            {
                ItemStack item;
                IClockworkConstruct construct = (IClockworkConstruct) mainItem.getItem();
                int quality = construct.getQuality(mainItem);
                int speed = construct.getSpeed(mainItem);
                int memory = construct.getMemory(mainItem);
                for(int n = 0; n < inventory.getSizeInventory(); n++)
                {
                    item = inventory.getStackInSlot(n);
                    if(item != null && item.getItem() instanceof ITemporalToolFunction)
                    {
                        ITemporalToolFunction temp = (ITemporalToolFunction) item.getItem();
                        NBTHelper.INT.set(mainItem, NBTTags.QUALITY, (int) (construct.getQuality(mainItem) + quality * (temp.getQualityMultiplier(item) - 1)));
                        NBTHelper.INT.set(mainItem, NBTTags.SPEED, (int) (construct.getSpeed(mainItem) + speed * (temp.getSpeedMultiplier(item) - 1)));
                        NBTHelper.INT.set(mainItem, NBTTags.MEMORY, (int) (construct.getMemory(mainItem) + memory * (temp.getMemoryMultiplier(item) - 1)));
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
         * Saves the component inventory into the item being assembled.
         * @param container The container to save from.
         */
        public static void saveNewComponentInventory(IAssemblyContainer container)
        {
            ItemStack[] componentsToSave = new ItemStack[container.getComponentInventory().getSizeInventory()];
            for(int n = 0; n < container.getComponentInventory().getSizeInventory(); n++)
            {
                componentsToSave[n] = container.getComponentInventory().getStackInSlot(n);
            }
            NBTHelper.INVENTORY.set(container.getMainInventory().getStackInSlot(0), NBTTags.COMPONENT_INVENTORY, componentsToSave);
        }
    }
}
