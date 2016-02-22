package lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;

/**
 * To be implemented by tile entities that wish to display a gui in the clockwork network controller.
 */
public interface IClockworkNetworkMachine extends IClockworkNetworkTile
{
    /**
     * Go through a proxy to separate client and server code here.
     * @return A Clockwork Network GUI class representing this gui.
     */
    public ClockworkNetworkContainer getGui();

    /**
     * @return True to allow the controller to set a target inventory for this tile, false to disable said ability.
     */
    public boolean canExportToTargetInventory();

    public boolean isValidTargetInventory();

    /**
     * Used to get a targeted inventory to export this tile's result items into.
     */
    public IClockworkNetworkTile getTargetInventory();

    public void setTargetInventory(IClockworkNetworkTile targetInventory);
}
