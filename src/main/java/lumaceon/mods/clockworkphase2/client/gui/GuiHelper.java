package lumaceon.mods.clockworkphase2.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class GuiHelper
{
    /**
     * Renders similar to the vanilla drawTexturedModalRect from the Gui class, except this allows you to specify the
     * size of the texture file, so you aren't forced to make massive 256x256 files when you only need 64x64.
     *
     * @param x X location of rectangle to draw.
     * @param y Y location of rectangle to draw.
     * @param textureX X coordinate in the texture.
     * @param textureY Y coordinate in the texture.
     * @param width Width of the rectangle in pixels (also applies to the texture).
     * @param height Height of the tectangle in pixels (also applies to the texture).
     * @param textureFileSize The size of the entire texture file we have bound to the renderer (must be square).
     */
    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, int textureFileSize, double zLevel)
    {
        float f = 1.0F / textureFileSize;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + height) * f)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), zLevel).tex((double)((float)(textureX + width) * f), (double)((float)(textureY + 0) * f)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), zLevel).tex((double)((float)(textureX + 0) * f), (double)((float)(textureY + 0) * f)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws the bound texture by stretching it over the specified width and height.
     */
    public static void drawTexturedModalRectStretched(int x, int y, double zLevel, int width, int height)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)(x), (double)(y + height), zLevel).tex(0, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), zLevel).tex(1, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y), zLevel).tex(1, 0).endVertex();
        renderer.pos((double)(x), (double)(y), zLevel).tex(0, 0).endVertex();
        tessellator.draw();
    }

    public static void drawTexturedModalRectCutTop(int x, int y, double zLevel, int width, int fullHeight, int currentHeight)
    {
        float height = fullHeight - (currentHeight);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x), (double)(y+fullHeight), zLevel).tex(0, 0).endVertex();
        vertexbuffer.pos((double)(x+width), (double)(y+fullHeight), zLevel).tex(1, 0).endVertex();
        vertexbuffer.pos((double)(x+width), (double)(y+height), zLevel).tex(1, (float) -(1 +(currentHeight))/fullHeight).endVertex();
        vertexbuffer.pos((double)(x), (double)(y+height), zLevel).tex(0, (float) -(1 +(currentHeight))/fullHeight).endVertex();
        tessellator.draw();
    }

    public static void drawTexturedModalRectStretchedWithUVOffset(int x, int y, double zLevel, int width, int height, float uOffset, float vOffset)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)(x), (double)(y + height), zLevel).tex(0 + uOffset, 1 + vOffset).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), zLevel).tex(1 + uOffset, 1 + vOffset).endVertex();
        renderer.pos((double)(x + width), (double)(y), zLevel).tex(1 + uOffset, 0 + vOffset).endVertex();
        renderer.pos((double)(x), (double)(y), zLevel).tex(0 + uOffset, 0 + vOffset).endVertex();
        tessellator.draw();
    }

    /**
     * Draws fluid top to bottom, based on the current amount of fluid vs the given max capacity.
     * Width also determines the effective size of the fluid (as fluid pixels are drawn square).
     */
    public static void drawFluidBar(int x, int y, double zLevel, int width, int height, int maxCapacity, int currentAmount, FluidStack fluidStack, Minecraft mc)
    {
        Fluid fluid = fluidStack.getFluid();
        TextureAtlasSprite textureUV = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill(fluidStack).toString());
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if(maxCapacity <= 0)
            return;

        float fluidPercentage = (float) currentAmount / (float) maxCapacity;
        int actualHeight = (int) (height * fluidPercentage);

        if(actualHeight <= 0)
            return;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        int pass = 0;
        int heightToRender;
        float maxV;
        while(actualHeight > 0)
        {
            heightToRender = actualHeight > width ? width : actualHeight;
            maxV = ((textureUV.getMaxV() - textureUV.getMinV()) * (float) heightToRender / (float) width) + textureUV.getMinV();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos((double)(x), (double)(y+height-(pass*width)), zLevel).tex(textureUV.getMinU(), textureUV.getMinV()).endVertex(); //Bottom-left
            vertexbuffer.pos((double)(x+width), (double)(y+height-(pass*width)), zLevel).tex(textureUV.getMaxU(), textureUV.getMinV()).endVertex(); //Bottom-right
            vertexbuffer.pos((double)(x+width), (double)(((y+height)-heightToRender)-(pass*width)), zLevel).tex(textureUV.getMaxU(), maxV ).endVertex(); //Top-right
            vertexbuffer.pos((double)(x), (double)(((y+height)-heightToRender)-(pass*width)), zLevel).tex(textureUV.getMinU(), maxV ).endVertex(); //Top-left
            tessellator.draw();

            actualHeight = actualHeight - width;
            ++pass;
        }
    }
}
