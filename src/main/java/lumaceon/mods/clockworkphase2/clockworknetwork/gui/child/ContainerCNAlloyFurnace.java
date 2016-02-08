package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNAlloyFurnace extends ContainerCN
{
    public ContainerCNAlloyFurnace(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof IInventory)
        {
            slots = new Slot[] {
                    new Slot((IInventory) te, 0, 0, 0),
                    new Slot((IInventory) te, 1, 20, 0),
                    new Slot((IInventory) te, 2, 80, 0),
            };
        }
    }
}
