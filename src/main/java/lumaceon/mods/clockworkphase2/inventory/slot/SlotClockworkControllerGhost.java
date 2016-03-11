package lumaceon.mods.clockworkphase2.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotClockworkControllerGhost extends Slot
{
    protected Slot ghostSlot;

    public SlotClockworkControllerGhost(IInventory inventoryIn, int index, int xPosition, int yPosition, Slot ghostSlot) {
        super(inventoryIn, index, xPosition, yPosition);
        this.ghostSlot = ghostSlot;
    }

    @Override
    public boolean isItemValid(ItemStack p_75214_1_) {
        return ghostSlot.isItemValid(p_75214_1_);
    }
}
