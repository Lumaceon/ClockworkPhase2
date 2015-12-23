package lumaceon.mods.clockworkphase2.inventory.slot;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotToolUpgrade extends Slot
{
    public SlotToolUpgrade(IInventory inventory, int slotId, int x, int y) {
        super(inventory, slotId, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack is) {
        return is != null && is.getItem() instanceof IToolUpgrade && !alreadyExists(is);
    }

    private boolean alreadyExists(ItemStack is)
    {
        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            if(i == getSlotIndex())
                continue;
            ItemStack item = inventory.getStackInSlot(i);
            if(item != null && item.getItem().equals(is.getItem()))
                return true;
        }
        return false;
    }
}