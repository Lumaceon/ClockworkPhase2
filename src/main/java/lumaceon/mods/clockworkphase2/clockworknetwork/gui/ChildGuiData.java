package lumaceon.mods.clockworkphase2.clockworknetwork.gui;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;

public class ChildGuiData
{
    public IClockworkNetworkMachine machine;
    public ClockworkNetworkContainer gui;
    private int x, y; //Position is relative to the bottom-center of the screen.

    public ChildGuiData(IClockworkNetworkMachine machine, ClockworkNetworkContainer gui, int x, int y, int guiX, int guiY) {
        this.machine = machine;
        this.gui = gui;
        setLocation(x, y, guiX, guiY);
    }

    public void setLocation(int x, int y, int guiX, int guiY) {
        this.x = x - guiX / 2;
        this.y = y - guiY;
    }

    /**
     * Calculates the desired x coordinate from the left of the screen.
     * @param guiX The xSize of the gui.
     * @return The distance of this child gui's left coordinate from the left of the parent gui.
     */
    public int getX(int guiX) {
        return x + guiX / 2;
    }

    /**
     * Calculates the desired y coordinate from the top of the screen.
     * @param guiY The ySize of the gui.
     * @return The distance of this child gui's top coordinate from the top of the parent gui.
     */
    public int getY(int guiY) {
        return y + guiY;
    }

    public int getRawX() {
        return x;
    }

    public int getRawY() {
        return y;
    }
}