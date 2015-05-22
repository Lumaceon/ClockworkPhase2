package lumaceon.mods.clockworkphase2.client.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotItemSpecific extends Slot
{
    public Item item;

    public SlotItemSpecific(IInventory inventory, int id, int x, int y, Item item)
    {
        super(inventory, id, x, y);
        this.item = item;
    }

    public boolean isItemValid(ItemStack is)
    {
        return is.getItem().equals(this.item);
    }
}
