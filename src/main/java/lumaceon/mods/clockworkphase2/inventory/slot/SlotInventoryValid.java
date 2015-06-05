package lumaceon.mods.clockworkphase2.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInventoryValid extends Slot
{
    protected int slotIndex;

    public SlotInventoryValid(IInventory inventory, int slotID, int x, int y) {
        super(inventory, slotID, x, y);
        slotIndex = slotID;
    }

    @Override
    public boolean isItemValid(ItemStack is) {
        return inventory.isItemValidForSlot(slotIndex, is);
    }
}
