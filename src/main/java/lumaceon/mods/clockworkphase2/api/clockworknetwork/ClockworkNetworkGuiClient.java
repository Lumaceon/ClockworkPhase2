package lumaceon.mods.clockworkphase2.api.clockworknetwork;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public abstract class ClockworkNetworkGuiClient extends ClockworkNetworkContainer
{
    public ClockworkNetworkGuiClient(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
    }

    /**
     * Called to get buttons to initialize every time the master gui is initialized. Buttons should be created with
     * local IDs starting from 0 and going up like a standard gui.
     * @param guiLeft Left coordinate of this gui.
     * @param guiTop Top coordinate of this gui.
     * @return A list of buttons which will be added to the master gui.
     */
    public List<GuiButton> getButtonsToAdd(int guiLeft, int guiTop) { return null; }

    /**
     * Similar to actionPerformed in GuiScreen, except that the id parameter passed in should be used rather than the
     * id of the button itself.
     * @param button The button clicked.
     * @param id The id of the button in relation to this gui.
     */
    public void actionPerformed(GuiButton button, int id) {}

    public abstract void drawBackground(int left, int top, float zLevel);
    public abstract void drawForeground(int left, int top, float zLevel);

    public void drawTexturedModalRect(int left, int top, int xSize, int ySize, float zLevel)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) left, (double)(top + ySize), (double) zLevel, 0, 1);
        tessellator.addVertexWithUV((double)(left + xSize), (double)(top + ySize), (double) zLevel, 1, 1);
        tessellator.addVertexWithUV((double) (left + xSize), (double) top, (double) zLevel, 1, 0);
        tessellator.addVertexWithUV((double) left, (double) top, (double) zLevel, 0, 0);
        tessellator.draw();
    }
}
