package lumaceon.mods.clockworkphase2.client.gui.pane;

import lumaceon.mods.clockworkphase2.client.gui.GuiPane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

import java.awt.*;

public class PaneComponentTitled extends PaneComponent
{
    public String componentTitle;
    public int titleColor = Color.WHITE.getRGB();
    public boolean shouldRenderString = true;

    public PaneComponentTitled(Minecraft mc, GuiPane guiPane) {
        super(mc, guiPane);
    }

    public void setTitle(String title) {
        componentTitle = title;
    }

    public void setTitleColor(int color) {
        titleColor = color;
    }

    public void setShouldRenderString(boolean shouldRenderString) {
        this.shouldRenderString = shouldRenderString;
    }

    @Override
    public void draw(int screenWidth, int screenHeight, double zLevel)
    {
        if(mouseIsHoveringOverThis)
            mouseOverTicks = Math.min(mouseOverTicks + 1, maxMouseOverTicks);
        else
            mouseOverTicks = Math.max(mouseOverTicks - 1, 0);

        if(mouseOverEnlarge && maxMouseOverTicks > 0)
            mouseOverScale = 1.0F + (((float) mouseOverTicks / maxMouseOverTicks) * 0.2F);
        else
            mouseOverScale = 1.0F;

        if(alpha <= 0 || texture == null || mc == null || mc.renderEngine == null)
            return;

        double left = 0;
        double right = 1;
        double top = 0;
        double bottom = 1;
        double textureRatio;
        double paneRatio;
        double finalRatio;
        double minU;
        double maxU;
        double minV;
        double maxV;

        mc.renderEngine.bindTexture(texture);
        Tessellator tessellator = Tessellator.instance;
        switch(RENDER_TYPE)
        {
            case STRETCH:
                left = screenWidth * xCenter - (mouseOverScale * localSizeX * xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (mouseOverScale * localSizeX * xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (mouseOverScale * localSizeY * ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (mouseOverScale * localSizeY * ySize * screenHeight) / 2;

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha);
                tessellator.addVertexWithUV(left, bottom, zLevel, 0, 1);
                tessellator.addVertexWithUV(right, bottom, zLevel, 1, 1);
                tessellator.addVertexWithUV(right, top, zLevel, 1, 0);
                tessellator.addVertexWithUV(left, top, zLevel, 0, 0);
                tessellator.draw();
                break;
            case FILL:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2; //TODO - check for localSize changes?
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                textureRatio = (float) textureWidth / (float) textureHeight;
                paneRatio = top - bottom == 0 ? 1 : Math.abs((right - left) / (top - bottom));
                finalRatio = paneRatio / textureRatio;
                if(finalRatio == 1) // 1:1 ratio.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0;
                    maxV = 1;
                }
                else if(finalRatio > 1.0) //WIDE, cutoff on the top and bottom.
                {
                    minU = 0;
                    maxU = 1;
                    minV = ((finalRatio - 1.0) / finalRatio) * 0.5;
                    maxV = 1 - ((finalRatio - 1.0) / finalRatio) * 0.5;
                }
                else //TALL, cutoff on the sides.
                {
                    minU = (1 - finalRatio) * 0.5;
                    maxU = 1 - (1 - finalRatio) * 0.5;
                    minV = 0;
                    maxV = 1;
                }

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha);
                tessellator.addVertexWithUV(left, bottom, zLevel, minU, maxV);
                tessellator.addVertexWithUV(right, bottom, zLevel, maxU, maxV);
                tessellator.addVertexWithUV(right, top, zLevel, maxU, minV);
                tessellator.addVertexWithUV(left, top, zLevel, minU, minV);
                tessellator.draw();
                break;
            case TILE:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                textureRatio = (float) textureWidth / (float) textureHeight;
                paneRatio = top - bottom == 0 ? 1 : Math.abs((right - left) / (top - bottom));
                finalRatio = paneRatio / textureRatio;
                if(finalRatio == 1) // 1:1 ratio.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0;
                    maxV = 1;
                }
                else if(finalRatio > 1.0) //WIDE, continue to the sides.
                {
                    minU = 0 - 0.5 * (finalRatio - 1);
                    maxU = 1 + 0.5 * (finalRatio - 1);
                    minV = 0;
                    maxV = 1;
                }
                else //TALL, continue to the top and bottom.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0 - 0.5 * (1 / finalRatio - 1);
                    maxV = 1 + 0.5 * (1 / finalRatio - 1);
                }

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha);
                tessellator.addVertexWithUV(left, bottom, zLevel, minU, maxV);
                tessellator.addVertexWithUV(right, bottom, zLevel, maxU, maxV);
                tessellator.addVertexWithUV(right, top, zLevel, maxU, minV);
                tessellator.addVertexWithUV(left, top, zLevel, minU, minV);
                tessellator.draw();
                break;
            case TILE_HORIZONTAL:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                textureRatio = (float) textureWidth / (float) textureHeight;
                paneRatio = top - bottom == 0 ? 1 : Math.abs((right - left) / (top - bottom));
                finalRatio = paneRatio / textureRatio;
                if(finalRatio == 1) // 1:1 ratio.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0;
                    maxV = 1;
                }
                else if(finalRatio > 1.0) //WIDE, continue to the sides.
                {
                    minU = 0 - 0.5 * (finalRatio - 1);
                    maxU = 1 + 0.5 * (finalRatio - 1);
                    minV = 0;
                    maxV = 1;
                }
                else //TALL, cutoff to the sides.
                {
                    minU = (1 - finalRatio) * 0.5;
                    maxU = 1 - (1 - finalRatio) * 0.5;
                    minV = 0;
                    maxV = 1;
                }

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha);
                tessellator.addVertexWithUV(left, bottom, zLevel, minU, maxV);
                tessellator.addVertexWithUV(right, bottom, zLevel, maxU, maxV);
                tessellator.addVertexWithUV(right, top, zLevel, maxU, minV);
                tessellator.addVertexWithUV(left, top, zLevel, minU, minV);
                tessellator.draw();
                break;
            case TILE_VERTICAL:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                textureRatio = (float) textureWidth / (float) textureHeight;
                paneRatio = top - bottom == 0 ? 1 : Math.abs((right - left) / (top - bottom));
                finalRatio = paneRatio / textureRatio;
                if(finalRatio == 1) // 1:1 ratio.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0;
                    maxV = 1;
                }
                else if(finalRatio > 1.0) //WIDE, continue to the sides.
                {
                    minU = 0;
                    maxU = 1;
                    minV = ((finalRatio - 1.0) / finalRatio) * 0.5;
                    maxV = 1 - ((finalRatio - 1.0) / finalRatio) * 0.5;
                }
                else //TALL, continue to the top and bottom.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0 - 0.5 * (1 / finalRatio - 1);
                    maxV = 1 + 0.5 * (1 / finalRatio - 1);
                }

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha);
                tessellator.addVertexWithUV(left, bottom, zLevel, minU, maxV);
                tessellator.addVertexWithUV(right, bottom, zLevel, maxU, maxV);
                tessellator.addVertexWithUV(right, top, zLevel, maxU, minV);
                tessellator.addVertexWithUV(left, top, zLevel, minU, minV);
                tessellator.draw();
                break;
        }

        if(mouseOverTicks > 0 && mouseOverTexture != null)
            drawMouseOverTexture(screenWidth, screenHeight, zLevel);
        if(shouldRenderString)
            mc.fontRenderer.drawString(componentTitle, (int) ((left + (right - left) / 2.0) - mc.fontRenderer.getStringWidth(componentTitle) / 2.0), (int) (bottom + (xSize * localSizeX * 0.2F) * screenHeight), titleColor);
    }

    private void drawMouseOverTexture(int screenWidth, int screenHeight, double zLevel)
    {
        if(alpha <= 0 || mouseOverTexture == null || mc == null || mc.renderEngine == null || mouseOverTicks <= 0)
            return;

        double left;
        double right;
        double top;
        double bottom;
        double textureRatio;
        double paneRatio;
        double finalRatio;
        double minU;
        double maxU;
        double minV;
        double maxV;

        mc.renderEngine.bindTexture(mouseOverTexture);
        Tessellator tessellator = Tessellator.instance;
        switch(RENDER_TYPE)
        {
            case STRETCH:
                left = screenWidth * xCenter - (localSizeX * xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (localSizeX * xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (localSizeY * ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (localSizeY * ySize * screenHeight) / 2;

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha / (mouseOverTicks / 20.0F));
                tessellator.addVertexWithUV(left, bottom, zLevel, 0, 1);
                tessellator.addVertexWithUV(right, bottom, zLevel, 1, 1);
                tessellator.addVertexWithUV(right, top, zLevel, 1, 0);
                tessellator.addVertexWithUV(left, top, zLevel, 0, 0);
                tessellator.draw();
                break;
            case FILL:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                textureRatio = (float) textureWidth / (float) textureHeight;
                paneRatio = top - bottom == 0 ? 1 : Math.abs((right - left) / (top - bottom));
                finalRatio = paneRatio / textureRatio;
                if(finalRatio == 1) // 1:1 ratio.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0;
                    maxV = 1;
                }
                else if(finalRatio > 1.0) //WIDE, cutoff on the top and bottom.
                {
                    minU = 0;
                    maxU = 1;
                    minV = ((finalRatio - 1.0) / finalRatio) * 0.5;
                    maxV = 1 - ((finalRatio - 1.0) / finalRatio) * 0.5;
                }
                else //TALL, cutoff on the sides.
                {
                    minU = (1 - finalRatio) * 0.5;
                    maxU = 1 - (1 - finalRatio) * 0.5;
                    minV = 0;
                    maxV = 1;
                }

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha / (mouseOverTicks / 20.0F));
                tessellator.addVertexWithUV(left, bottom, zLevel, minU, maxV);
                tessellator.addVertexWithUV(right, bottom, zLevel, maxU, maxV);
                tessellator.addVertexWithUV(right, top, zLevel, maxU, minV);
                tessellator.addVertexWithUV(left, top, zLevel, minU, minV);
                tessellator.draw();
                break;
            case TILE:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                textureRatio = (float) textureWidth / (float) textureHeight;
                paneRatio = top - bottom == 0 ? 1 : Math.abs((right - left) / (top - bottom));
                finalRatio = paneRatio / textureRatio;
                if(finalRatio == 1) // 1:1 ratio.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0;
                    maxV = 1;
                }
                else if(finalRatio > 1.0) //WIDE, continue to the sides.
                {
                    minU = 0 - 0.5 * (finalRatio - 1);
                    maxU = 1 + 0.5 * (finalRatio - 1);
                    minV = 0;
                    maxV = 1;
                }
                else //TALL, continue to the top and bottom.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0 - 0.5 * (1 / finalRatio - 1);
                    maxV = 1 + 0.5 * (1 / finalRatio - 1);
                }

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha / (mouseOverTicks / 20.0F));
                tessellator.addVertexWithUV(left, bottom, zLevel, minU, maxV);
                tessellator.addVertexWithUV(right, bottom, zLevel, maxU, maxV);
                tessellator.addVertexWithUV(right, top, zLevel, maxU, minV);
                tessellator.addVertexWithUV(left, top, zLevel, minU, minV);
                tessellator.draw();
                break;
            case TILE_HORIZONTAL:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                textureRatio = (float) textureWidth / (float) textureHeight;
                paneRatio = top - bottom == 0 ? 1 : Math.abs((right - left) / (top - bottom));
                finalRatio = paneRatio / textureRatio;
                if(finalRatio == 1) // 1:1 ratio.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0;
                    maxV = 1;
                }
                else if(finalRatio > 1.0) //WIDE, continue to the sides.
                {
                    minU = 0 - 0.5 * (finalRatio - 1);
                    maxU = 1 + 0.5 * (finalRatio - 1);
                    minV = 0;
                    maxV = 1;
                }
                else //TALL, cutoff to the sides.
                {
                    minU = (1 - finalRatio) * 0.5;
                    maxU = 1 - (1 - finalRatio) * 0.5;
                    minV = 0;
                    maxV = 1;
                }

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha / (mouseOverTicks / 20.0F));
                tessellator.addVertexWithUV(left, bottom, zLevel, minU, maxV);
                tessellator.addVertexWithUV(right, bottom, zLevel, maxU, maxV);
                tessellator.addVertexWithUV(right, top, zLevel, maxU, minV);
                tessellator.addVertexWithUV(left, top, zLevel, minU, minV);
                tessellator.draw();
                break;
            case TILE_VERTICAL:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                textureRatio = (float) textureWidth / (float) textureHeight;
                paneRatio = top - bottom == 0 ? 1 : Math.abs((right - left) / (top - bottom));
                finalRatio = paneRatio / textureRatio;
                if(finalRatio == 1) // 1:1 ratio.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0;
                    maxV = 1;
                }
                else if(finalRatio > 1.0) //WIDE, continue to the sides.
                {
                    minU = 0;
                    maxU = 1;
                    minV = ((finalRatio - 1.0) / finalRatio) * 0.5;
                    maxV = 1 - ((finalRatio - 1.0) / finalRatio) * 0.5;
                }
                else //TALL, continue to the top and bottom.
                {
                    minU = 0;
                    maxU = 1;
                    minV = 0 - 0.5 * (1 / finalRatio - 1);
                    maxV = 1 + 0.5 * (1 / finalRatio - 1);
                }

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha / (mouseOverTicks / 20.0F));
                tessellator.addVertexWithUV(left, bottom, zLevel, minU, maxV);
                tessellator.addVertexWithUV(right, bottom, zLevel, maxU, maxV);
                tessellator.addVertexWithUV(right, top, zLevel, maxU, minV);
                tessellator.addVertexWithUV(left, top, zLevel, minU, minV);
                tessellator.draw();
                break;
        }
    }
}
