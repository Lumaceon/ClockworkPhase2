package lumaceon.mods.clockworkphase2.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMultitoolAccepts extends Slot
{
    public SlotMultitoolAccepts(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack is) {
        return !is.isStackable();
    }
}
