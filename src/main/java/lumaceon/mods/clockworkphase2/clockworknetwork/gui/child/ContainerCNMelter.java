package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNMelter extends ClockworkNetworkContainer
{
    public ContainerCNMelter(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);


    }

    @Override
    public Slot[] getSlots() {
        return new Slot[1];
    }
}