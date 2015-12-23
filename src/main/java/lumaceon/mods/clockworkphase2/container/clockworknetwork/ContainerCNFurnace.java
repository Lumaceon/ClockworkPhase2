package lumaceon.mods.clockworkphase2.container.clockworknetwork;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotInventoryValid;
import lumaceon.mods.clockworkphase2.tile.clockwork.TileClockworkFurnace;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCNFurnace extends ClockworkNetworkContainer
{
    protected Slot[] slots;
    private TileClockworkFurnace furnace;

    private int previousCookTime;

    public ContainerCNFurnace(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof IInventory)
        {
            slots = new Slot[] { new SlotInventoryValid((IInventory) te, 0, 3, 3), new SlotInventoryValid((IInventory) te, 1, 154, 3)};
            furnace = (TileClockworkFurnace) te;
        }
    }

    @Override
    public Slot[] getSlots() {
        return slots;
    }

    @Override
    public int getUpdateCount() {
        return 1;
    }

    @Override
    public void initialCraftingUpdate(ICrafting crafting, int startingIndex, Container container) {
        crafting.sendProgressBarUpdate(container, startingIndex, furnace.furnaceCookTime);
    }

    @Override
    public void detectAndSendChanges(ICrafting crafting, int startingIndex, Container container) {
        if(furnace.furnaceCookTime != previousCookTime)
            crafting.sendProgressBarUpdate(container, startingIndex, furnace.furnaceCookTime);
        previousCookTime = furnace.furnaceCookTime;
    }
}
