package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNBrewery extends ContainerCN
{
    public ContainerCNBrewery(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof IInventory)
        {
            slots = new Slot[] {
                    new Slot((IInventory) te, 0, 1, 1),
                    new Slot((IInventory) te, 1, 21, 1),
                    new Slot((IInventory) te, 2, 41, 1),
                    new Slot((IInventory) te, 3, 61, 1)
            };
        }
    }
}
