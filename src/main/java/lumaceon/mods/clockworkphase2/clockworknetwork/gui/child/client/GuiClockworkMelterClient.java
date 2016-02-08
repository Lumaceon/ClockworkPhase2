package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotInventoryValid;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class GuiClockworkMelterClient extends GuiCN
{
    private static Minecraft mc;

    public GuiClockworkMelterClient(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        mc = Minecraft.getMinecraft();
        if(te != null && te instanceof IInventory)
        {
            Slot input = new SlotInventoryValid((IInventory) te, 0, 3, 3);
            Slot output = new SlotInventoryValid((IInventory) te, 1, 154, 3);

            slots = new Slot[] { input, output };
        }
    }

    @Override
    public void drawBackground(int left, int top, float zLevel) {
        mc.renderEngine.bindTexture(Textures.GUI.MELTER);
        this.drawTexturedModalRect(left, top, xSize, ySize, zLevel);
    }

    @Override
    public void drawForeground(int left, int top, float zLevel) {}
}
