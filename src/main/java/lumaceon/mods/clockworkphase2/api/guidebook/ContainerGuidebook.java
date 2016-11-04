package lumaceon.mods.clockworkphase2.api.guidebook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerGuidebook extends Container
{
    public void updateContents(InventoryGuidebook[] guideInventories)
    {
        if(guideInventories != null && guideInventories.length > 0)
        {
            this.inventoryItemStacks.clear();
            this.inventorySlots.clear();
            for(int i = 0; i < guideInventories.length; i++)
            {
                InventoryGuidebook inventory = guideInventories[i];
                for(int n = 0; n < inventory.getSizeInventory(); n++)
                {
                    int x = n == inventory.getSizeInventory() - 1 ? 90 : (n % 3) * 20;
                    int y = n == inventory.getSizeInventory() - 1 ? 20 : ((int) Math.floor(n / 3.0F)) * 20;
                    Slot s = new Slot(inventory, n, x, y);
                    addSlotToContainer(s);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return false;
    }
}
