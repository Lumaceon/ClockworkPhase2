package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNSuperAlloyFurnace extends ClockworkNetworkContainer
{
    protected Slot[] slots;

    public ContainerCNSuperAlloyFurnace(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof IInventory)
        {
            slots = new Slot[] {
                    new Slot((IInventory) te, 0, 0, 0),
                    new Slot((IInventory) te, 1, 20, 0),
                    new Slot((IInventory) te, 2, 40, 0),
                    new Slot((IInventory) te, 3, 100, 0),
                    new Slot((IInventory) te, 4, 120, 0),
                    new Slot((IInventory) te, 5, 140, 0),
                    new Slot((IInventory) te, 6, 70, 0)
            };
        }
    }

    @Override
    public Slot[] getSlots() {
        return slots;
    }
}
