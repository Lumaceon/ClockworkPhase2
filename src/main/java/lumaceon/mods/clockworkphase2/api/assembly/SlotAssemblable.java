package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAssemblable extends Slot
{
    public SlotAssemblable(IInventory inventory, int slotID, int x, int y) {
        super(inventory, slotID, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack is) {
        return is.getItem() instanceof IAssemblable;
    }
}
