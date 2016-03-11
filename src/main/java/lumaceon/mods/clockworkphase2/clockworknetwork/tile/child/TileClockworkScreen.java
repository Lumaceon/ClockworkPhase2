package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileClockworkScreen extends TileClockworkNetworkMachine
{
    @Override
    public boolean canWork() {
        return false;
    }

    @Override
    public void work() {

    }

    @Override
    public ClockworkNetworkContainer getGui() {
        return null;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }
}
