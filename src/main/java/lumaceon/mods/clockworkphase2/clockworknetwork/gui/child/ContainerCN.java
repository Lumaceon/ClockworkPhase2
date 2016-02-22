package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public abstract class ContainerCN extends ClockworkNetworkContainer
{
    protected Slot[] slots;
    protected TileClockworkNetworkMachine machine;

    protected int previousProgress;

    public ContainerCN(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof TileClockworkNetworkMachine)
            machine = (TileClockworkNetworkMachine) te;
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
        crafting.sendProgressBarUpdate(container, startingIndex, machine.getProgress());
    }

    @Override
    public void detectAndSendChanges(ICrafting crafting, int startingIndex, Container container) {
        if(machine.getProgress() != previousProgress)
            crafting.sendProgressBarUpdate(container, startingIndex, machine.getProgress());
        previousProgress = machine.getProgress();
    }
}
