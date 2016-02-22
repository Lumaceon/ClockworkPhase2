package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotInventoryValid;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNFurnace extends ContainerCN
{
    public ContainerCNFurnace(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof IInventory)
            slots = new Slot[] { new SlotInventoryValid((IInventory) te, 0, 1, 1)};
    }
}
