package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class GuiClockworkSuperAlloyFurnace extends GuiCN
{
    public GuiClockworkSuperAlloyFurnace(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        if(te != null && te instanceof IInventory)
        {
            slots = new Slot[] {
                    new Slot((IInventory) te, 0, 1, 1),
                    new Slot((IInventory) te, 1, 23, 1),
                    new Slot((IInventory) te, 2, 45, 1),
                    new Slot((IInventory) te, 3, 67, 1),
                    new Slot((IInventory) te, 4, 89, 1),
                    new Slot((IInventory) te, 5, 111, 1)
            };
        }
    }

    @Override
    public void drawBackground(int left, int top, float zLevel) {
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.SUPER_ALLOY_FURNACE);
        this.drawTexturedModalRect(left, top, xSize, ySize, zLevel);
    }

    @Override
    public void drawForeground(int left, int top, float zLevel) {}
}
