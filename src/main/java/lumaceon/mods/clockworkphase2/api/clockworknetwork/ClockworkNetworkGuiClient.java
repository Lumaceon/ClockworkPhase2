package lumaceon.mods.clockworkphase2.api.clockworknetwork;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public abstract class ClockworkNetworkGuiClient extends ClockworkNetworkContainer
{
    protected float[] colors = new float[] { 1.0F, 1.0F, 1.0F };

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

    public void drawTexturedModalRect(int x, int y, int width, int height, float zLevel)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)(x + 0), (double)(y + height), (double)zLevel).tex(0, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), (double)zLevel).tex(1, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), (double)zLevel).tex(1, 0).endVertex();
        renderer.pos((double)(x + 0), (double)(y + 0), (double)zLevel).tex(0, 0).endVertex();
        tessellator.draw();
    }

    /**
     * Get the colors to apply to the controller's output line. It will automatically look semi-transparent, so you
     * should keep that in mind when choosing a color (typically brighter colors are better).
     */
    public float[] getColorRGB() {
        return colors;
    }
}
