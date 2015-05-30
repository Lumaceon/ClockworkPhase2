package lumaceon.mods.clockworkphase2.inventory.slot;

import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalToolFunction;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotTemporalFunction extends Slot
{
    private boolean active;

    public SlotTemporalFunction(IInventory inventory, int slotId, int x, int y, boolean active)
    {
        super(inventory, slotId, x, y);
        this.active = active;
    }

    @Override
    public boolean isItemValid(ItemStack is)
    {
        if(is != null && is.getItem() instanceof ITemporalToolFunction)
        {
            ITemporalToolFunction function = (ITemporalToolFunction) is.getItem();
            return active == function.isActive(is);
        }
        return false;
    }
}
