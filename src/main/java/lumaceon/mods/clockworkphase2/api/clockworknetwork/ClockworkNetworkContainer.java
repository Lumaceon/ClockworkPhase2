package lumaceon.mods.clockworkphase2.api.clockworknetwork;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public abstract class ClockworkNetworkContainer
{
    public TileEntity te;
    protected int xSize, ySize;

    public ClockworkNetworkContainer(TileEntity te, int xSize, int ySize) {
        this.te = te;
        this.xSize = xSize;
        this.ySize = ySize;
    }
    /**
     * Gets a list of slots for the corresponding container, coordinates should be local (based on this GUI).
     * @return A list of slots or null if the gui has none.
     */
    public abstract Slot[] getSlots();

    public int getSizeX() { return xSize; }
    public int getSizeY() { return ySize; }

    /**
     * @return The number of values this container needs to update.
     */
    public int getUpdateCount() {
        return 0;
    }

    /**
     * Called for initial GUI parameter updates.
     */
    public void initialCraftingUpdate(ICrafting crafting, int startingIndex, Container container) {}

    public void detectAndSendChanges(ICrafting crafting, int startingIndex, Container container) {}

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {}
}
