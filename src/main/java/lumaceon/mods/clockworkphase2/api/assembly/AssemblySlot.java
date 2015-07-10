package lumaceon.mods.clockworkphase2.api.assembly;

import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Used in IAssemblable to assemble different items, like the clockwork tools.
 */
public abstract class AssemblySlot
{
    private ItemStack itemInSlot;
    private ResourceLocation defaultTexture;
    public boolean isEnabled = true;

    //The center position of this slot. {0, 0} is considered the top-left corner while {1, 1} is the bottom right.
    public float centerX;
    public float centerY;

    //The size (diameter) of this slot. 1 is considered 1 pixel, or 1/16th of the distance from top to bottom.
    public float sizeX = 1.5F;
    public float sizeY = 1.5F;

    //The number of ticks the mouse has been over this slot, used in rendering code.
    public int ticksMousedOver = 0;

    public AssemblySlot(ResourceLocation defaultTexture)
    {
        this.defaultTexture = defaultTexture;
    }

    public AssemblySlot(ResourceLocation defaultTexture, float centerX, float centerY)
    {
        this(defaultTexture);
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public AssemblySlot(ResourceLocation defaultTexture, float centerX, float centerY, float sizeX, float sizeY)
    {
        this(defaultTexture, centerX, centerY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public abstract boolean isItemValid(ItemStack itemToInsert);

    public void onClick(World world, int x, int y, int z, EntityPlayer player, ItemStack heldItem, ItemStack workItem, AssemblySlot[] slots)
    {
        ItemStack slotItem = getItemStack();
        if(slotItem != null && heldItem == null) //Item is in slot, hand is empty.
        {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, slotItem);
            setItemStack(null);
            ((IAssemblable) workItem.getItem()).onComponentChange(workItem, slots);
            ((IAssemblable) workItem.getItem()).saveComponentInventory(workItem, slots);
        }
        else if(slotItem != null && heldItem.getItem().equals(slotItem.getItem()) && heldItem.getMaxStackSize() >= heldItem.stackSize + slotItem.stackSize) //Item is in slot, hand is not empty but has the same item.
        {
            heldItem.stackSize += slotItem.stackSize;
            setItemStack(null);
            ((IAssemblable) workItem.getItem()).onComponentChange(workItem, slots);
            ((IAssemblable) workItem.getItem()).saveComponentInventory(workItem, slots);
        }
        else if(slotItem == null && heldItem != null && isItemValid(heldItem)) //No item in slot, but the held item is valid.
        {
            ItemStack newSlot = heldItem.copy();
            newSlot.stackSize = 1;
            setItemStack(newSlot);
            heldItem.stackSize--;
            ((IAssemblable) workItem.getItem()).onComponentChange(workItem, slots);
            ((IAssemblable) workItem.getItem()).saveComponentInventory(workItem, slots);
        }
    }

    /**
     * @return The icon that will be rendered while there is no item in this slot.
     */
    public ResourceLocation getRenderIcon() {
        return defaultTexture;
    }

    public ResourceLocation getMouseOverIcon(EntityPlayer player, ItemStack workItem, ItemStack heldItem) { return null; }

    public ItemStack getItemStack() {
        return itemInSlot;
    }

    public void setItemStack(ItemStack item) {
        itemInSlot = item;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
