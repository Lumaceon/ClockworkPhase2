package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkGuiClient;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class GuiClockworkBreweryClient extends ClockworkNetworkGuiClient
{
    protected Slot[] slots;

    public GuiClockworkBreweryClient(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof IInventory)
        {
            slots = new Slot[] {
                    new Slot((IInventory) te, 0, 1, 1),
                    new Slot((IInventory) te, 1, 21, 1),
                    new Slot((IInventory) te, 2, 41, 1),
                    new Slot((IInventory) te, 3, 61, 1)
            };
        }
    }

    @Override
    public void drawBackground(int left, int top, float zLevel)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.BREWERY);
        this.drawTexturedModalRect(left, top, xSize, ySize, zLevel);
    }

    @Override
    public void drawForeground(int left, int top, float zLevel) {}

    @Override
    public Slot[] getSlots() {
        return slots;
    }
}
