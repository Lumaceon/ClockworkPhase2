package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkGuiClient;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkMachine;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class GuiCN extends ClockworkNetworkGuiClient
{
    protected Slot[] slots;
    protected TileClockworkNetworkMachine machine;

    protected int previousProgress;

    public GuiCN(TileEntity te, int xSize, int ySize) {
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

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if(id == 0)
            machine.setProgress(value);
    }
}
