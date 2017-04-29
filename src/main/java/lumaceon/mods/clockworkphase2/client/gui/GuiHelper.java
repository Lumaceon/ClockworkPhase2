package lumaceon.mods.clockworkphase2.client.gui;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiHelper
{
    /**
     * Draws the bound texture by stretching it over the specified width and height.
     */
    public static void drawTexturedModalRectStretched(int x, int y, double zLevel, int width, int height)
    {
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)(x), (double)(y + height), zLevel).tex(0, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), zLevel).tex(1, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y), zLevel).tex(1, 0).endVertex();
        renderer.pos((double)(x), (double)(y), zLevel).tex(0, 0).endVertex();
        tessellator.draw();
    }
}
