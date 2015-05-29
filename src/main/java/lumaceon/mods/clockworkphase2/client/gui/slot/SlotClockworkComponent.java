package lumaceon.mods.clockworkphase2.client.gui.slot;

import lumaceon.mods.clockworkphase2.api.item.IClockworkComponent;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotClockworkComponent extends Slot
{
    public SlotClockworkComponent(IInventory inventory, int id, int x, int y)
    {
        super(inventory, id, x, y);
    }

    public boolean isItemValid(ItemStack is)
    {
        return is.getItem() instanceof IClockworkComponent;
    }
}
