package lumaceon.mods.clockworkphase2.api.util;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkComponent;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.item.ItemStack;

/**
 * Provides a variety of classes for easy item assembly.
 */
public class AssemblyHelper
{
    /**
     * Methods to be called during the getGuiInventory method of IAssemblable.
     */
    public static class GET_GUI_INVENTORY
    {
        /**
         * Loads the component inventory from the main item into the inventory.
         * Note that the inventory MUST be large enough to hold all of the items being loaded.
         * @param container The container to get the main item from.
         * @param inventory The inventory to load the items into.
         */
        public static void loadStandardComponentInventory(ContainerAssemblyTable container, InventoryAssemblyTableComponents inventory)
        {
            ItemStack mainItem = container.mainInventory.getStackInSlot(0);
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
     * Methods to be called during the onInventoryChange method of IAssemblable.
     */
    public static class ON_INVENTORY_CHANGE
    {
        public static void assembleClockworkCore(ContainerAssemblyTable container)
        {
            ItemStack mainItem = container.mainInventory.getStackInSlot(0);
            ItemStack item;
            int quality = 0; int speed = 0; int harvestLevel = -1;
            for(int n = 0; n < container.componentInventory.getSizeInventory(); n++)
            {
                item = container.componentInventory.getStackInSlot(n);
                if(item != null && item.getItem() instanceof IClockworkComponent)
                {
                    quality += ((IClockworkComponent) item.getItem()).getQuality(item);
                    speed += ((IClockworkComponent) item.getItem()).getSpeed(item);
                    harvestLevel = Math.max(harvestLevel, ((IClockworkComponent) item.getItem()).getTier(item));
                }
            }
            NBTHelper.INT.set(mainItem, NBTTags.QUALITY, quality);
            NBTHelper.INT.set(mainItem, NBTTags.SPEED, speed);
            NBTHelper.INT.set(mainItem, NBTTags.TIER, harvestLevel);
        }

        public static void assembleClockworkConstruct(ContainerAssemblyTable container, int mainspringSlotIndex, int clockworkSlotIndex)
        {
            ItemStack mainItem = container.mainInventory.getStackInSlot(0);
            ItemStack mainspring = container.componentInventory.getStackInSlot(mainspringSlotIndex);
            ItemStack clockwork = container.componentInventory.getStackInSlot(clockworkSlotIndex);

            if(mainspring != null) //There is a mainspring.
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

            if(clockwork != null) //There is clockwork.
            {
                NBTHelper.INT.set(mainItem, NBTTags.QUALITY, NBTHelper.INT.get(clockwork, NBTTags.QUALITY));
                NBTHelper.INT.set(mainItem, NBTTags.SPEED, NBTHelper.INT.get(clockwork, NBTTags.SPEED));
                if(mainItem.getItem() instanceof IClockworkConstruct)
                    ((IClockworkConstruct) mainItem.getItem()).setTier(mainItem, NBTHelper.INT.get(clockwork, NBTTags.TIER));
            }
            else
            {
                NBTHelper.INT.set(mainItem, NBTTags.QUALITY, 0);
                NBTHelper.INT.set(mainItem, NBTTags.SPEED, 0);
                if(mainItem.getItem() instanceof IClockworkConstruct)
                    ((IClockworkConstruct) mainItem.getItem()).setTier(mainItem, -1);
            }
        }
    }

    /**
     * Methods to be called during the saveComponentInventory method of IAssemblable.
     */
    public static class SAVE_COMPONENT_INVENTORY
    {
        /**
         * Saves the component inventory into the item being assembled.
         * @param container The container to save from.
         */
        public static void saveComponentInventory(ContainerAssemblyTable container)
        {
            ItemStack[] componentsToSave = new ItemStack[container.componentInventory.getSizeInventory()];
            for(int n = 0; n < container.componentInventory.getSizeInventory(); n++)
                componentsToSave[n] = container.componentInventory.getStackInSlot(n);
            NBTHelper.INVENTORY.set(container.mainInventory.getStackInSlot(0), NBTTags.COMPONENT_INVENTORY, componentsToSave);
        }
    }
}
