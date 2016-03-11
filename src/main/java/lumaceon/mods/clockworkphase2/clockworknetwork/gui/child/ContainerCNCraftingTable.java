package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkCraftingTable;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNCraftingTable extends ClockworkNetworkContainer
{
    protected Slot[] slots;
    protected TileClockworkCraftingTable craftingTable;
    private InventoryCrafting craftingInventory;

    public ContainerCNCraftingTable(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        slots = new Slot[10];
        if(te != null && te instanceof TileClockworkCraftingTable)
        {
            craftingTable = (TileClockworkCraftingTable) te;

            //From the ContainerWorkbench class.
            int l;
            int i1;
            for (l = 0; l < 3; ++l)
                for (i1 = 0; i1 < 3; ++i1)
                    slots[i1 + l * 3] = new Slot(craftingTable, i1 + l * 3, 1 + i1 * 18, 1 + l * 18);

            slots[9] = new SlotNever(craftingTable, 9, 81, 19);
        }
    }

    @Override
    public Slot[] getSlots() {
        return slots;
    }
}
