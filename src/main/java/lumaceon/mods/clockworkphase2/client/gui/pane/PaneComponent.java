package lumaceon.mods.clockworkphase2.client.gui.pane;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class PaneComponent
{
    public Minecraft mc;
    public ResourceLocation texture = null;

    public EnumRenderType RENDER_TYPE = EnumRenderType.STRETCH;
    public EnumSide ANCHOR = EnumSide.CENTER;
    public int textureWidth = 1;
    public int textureHeight = 1;
    public float alpha = 1;

    //Rendered from the center, which is where these coordinates represent.
    public float xCenter = 0.5F;
    public float yCenter = 0.5F;

    //Size represents the entire area this component has, not the actual rendering area, 1 being the whole screen.
    public float xSize = 1.0F;
    public float ySize = 1.0F;

    public float localSizeX = 1.0F;
    public float localSizeY = 1.0F;

    public PaneComponent(Minecraft mc) {
        this.mc = mc;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    public void setRenderType(EnumRenderType renderType) {
        this.RENDER_TYPE = renderType;
    }

    public void setTextureProportions(int width, int height) {
        textureWidth = width;
        textureHeight = height;
    }

    public void setPosition(float x, float y) {
        xCenter = x;
        yCenter = y;
    }

    public void setLocalPosition(float x, float y) {
        localSizeX = x;
        localSizeY = y;
    }

    public void update(int screenWidth, int screenHeight) {
        if(mc == null)
            mc = Minecraft.getMinecraft();
    }

    public void draw(int screenWidth, int screenHeight, double zLevel)
    {
        if(alpha <= 0 || texture == null || mc == null || mc.renderEngine == null)
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

        mc.renderEngine.bindTexture(texture);
        Tessellator tessellator = Tessellator.instance;
        switch(RENDER_TYPE)
        {
            case STRETCH:
                left = screenWidth * xCenter - (xSize * screenWidth) / 2;
                right = screenWidth * xCenter + (xSize * screenWidth) / 2;
                top = screenHeight * yCenter - (ySize * screenHeight) / 2;
                bottom = screenHeight * yCenter + (ySize * screenHeight) / 2;

                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1, 1, 1, alpha);
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
    }
}
