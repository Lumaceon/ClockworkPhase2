package lumaceon.mods.clockworkphase2.client.gui.slot;

import lumaceon.mods.clockworkphase2.api.item.IClockwork;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotClockwork extends Slot
{
    public SlotClockwork(IInventory inventory, int slotId, int x, int y) {
        super(inventory, slotId, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack is) {
        return is.getItem() instanceof IClockwork;
    }
}
