package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child;

import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkItemStorage;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNItemStorage extends ContainerCN
{
    public ContainerCNItemStorage(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof TileClockworkItemStorage)
        {
            TileClockworkItemStorage itemStorage = (TileClockworkItemStorage) te;
            this.slots = new Slot[itemStorage.xSlots * itemStorage.ySlots];

            for(int y = 0; y < itemStorage.ySlots; y++)
                for(int x = 0; x < itemStorage.xSlots; x++)
                    this.slots[x + (y * itemStorage.xSlots)] = new Slot(itemStorage, x + (y * itemStorage.xSlots), 1+x*18, 1+y*18);
        }
    }

    @Override
    public int getUpdateCount() {
        return 0;
    }
    @Override
    public void initialCraftingUpdate(ICrafting crafting, int startingIndex, Container container) {}
    @Override
    public void detectAndSendChanges(ICrafting crafting, int startingIndex, Container container) {}
}
