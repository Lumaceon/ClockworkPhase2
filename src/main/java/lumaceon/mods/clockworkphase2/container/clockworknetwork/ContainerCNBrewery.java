package lumaceon.mods.clockworkphase2.container.clockworknetwork;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNBrewery extends ClockworkNetworkContainer
{
    public ContainerCNBrewery(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
    }

    @Override
    public Slot[] getSlots() {
        return new Slot[4];
    }
}
