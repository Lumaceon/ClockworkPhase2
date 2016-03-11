package lumaceon.mods.clockworkphase2.inventory.slot;

import lumaceon.mods.clockworkphase2.api.item.IWeaponUpgrade;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWeaponUpgrade extends Slot
{
    public SlotWeaponUpgrade(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack is) {
        return is != null && is.getItem() instanceof IWeaponUpgrade && !alreadyExists(is);
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
