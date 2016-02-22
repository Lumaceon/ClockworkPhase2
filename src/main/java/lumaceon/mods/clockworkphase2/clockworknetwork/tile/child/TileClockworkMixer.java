package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileClockworkMixer extends TileClockworkNetworkMachine
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
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 2);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, EnumFacing side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, EnumFacing side) {
        return false;
    }
}
