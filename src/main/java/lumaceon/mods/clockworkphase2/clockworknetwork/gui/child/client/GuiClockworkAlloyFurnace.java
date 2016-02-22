package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class GuiClockworkAlloyFurnace extends GuiCN
{
    public GuiClockworkAlloyFurnace(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof IInventory)
        {
            slots = new Slot[] {
                    new Slot((IInventory) te, 0, 1, 1),
                    new Slot((IInventory) te, 1, 23, 1)
            };
        }
    }

    @Override
    public void drawBackground(int left, int top, float zLevel) {
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.ALLOY_FURNACE);
        this.drawTexturedModalRect(left, top, xSize, ySize, zLevel);
    }

    @Override
    public void drawForeground(int left, int top, float zLevel) {}
}
