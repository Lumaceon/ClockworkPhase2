package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockwork;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkMachine;
import net.minecraft.item.ItemStack;

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
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return false;
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
