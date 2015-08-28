package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalConduit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class TESRTemporalConduit extends TileEntitySpecialRenderer
{
    public static final ResourceLocation CONDUIT = new ResourceLocation(Reference.MOD_ID, "textures/blocks/temporal_conduit.png");
    public static final ResourceLocation CONDUIT_SIDE = new ResourceLocation(Reference.MOD_ID, "textures/blocks/temporal_conduit_side.png");

    private Minecraft mc;
    private boolean renderInside = true;

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_)
    {
        if(mc == null)
            mc = Minecraft.getMinecraft();

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(x, y, z);
        bindTexture(CONDUIT);
        Tessellator tessellator = Tessellator.instance;
        renderCenter(tessellator);
        boolean[] connections = ((TileTemporalConduit)te).connections;
        for(int n = 0; n < 6; n++)
            if(connections[n])
            {
                renderCenterConnection(tessellator, ForgeDirection.getOrientation(n));
                bindTexture(CONDUIT_SIDE);
                renderSide(tessellator, ForgeDirection.getOrientation(n));
                bindTexture(CONDUIT);
            }
        GL11.glPopMatrix();
    }

    private void renderSide(Tessellator tessellator, ForgeDirection direction)
    {
        float worldPixel = 1F/16F;
        float texturePixel = 1F/32F;
        float stutterFraction = 1F/300F;

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        if(direction.equals(ForgeDirection.DOWN))
            GL11.glRotatef(180, 1, 0, 0);
        else if(direction.equals(ForgeDirection.NORTH))
            GL11.glRotatef(270, 1, 0, 0);
        else if(direction.equals(ForgeDirection.SOUTH))
            GL11.glRotatef(90, 1, 0, 0);
        else if(direction.equals(ForgeDirection.WEST))
            GL11.glRotatef(90, 0, 0, 1);
        else if(direction.equals(ForgeDirection.EAST))
            GL11.glRotatef(270, 0, 0, 1);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        //BOTTOM
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0 + stutterFraction, 1.0F - worldPixel, 1 - stutterFraction, 0, texturePixel);
        tessellator.addVertexWithUV(0 + stutterFraction, 1.0F - worldPixel, 0 + stutterFraction, 0, 0);
        tessellator.addVertexWithUV(1 - stutterFraction, 1.0F - worldPixel, 0 + stutterFraction, texturePixel * 16, 0);
        tessellator.addVertexWithUV(1 - stutterFraction, 1.0F - worldPixel, 1 - stutterFraction, texturePixel * 16, texturePixel);
        tessellator.draw();

        //TOP
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(1 - stutterFraction, 1 - stutterFraction, 1 - stutterFraction, texturePixel * 16, texturePixel * 17);
        tessellator.addVertexWithUV(1 - stutterFraction, 1 - stutterFraction, 0 + stutterFraction, texturePixel * 16, texturePixel);
        tessellator.addVertexWithUV(0 + stutterFraction, 1 - stutterFraction, 0 + stutterFraction, 0, texturePixel);
        tessellator.addVertexWithUV(0 + stutterFraction, 1 - stutterFraction, 1 - stutterFraction, 0, texturePixel * 17);
        tessellator.draw();

        //NORTH
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0 + stutterFraction, 1.0F - worldPixel, 0 + stutterFraction, texturePixel * 16, 0);
        tessellator.addVertexWithUV(0 + stutterFraction, 1 - stutterFraction, 0 + stutterFraction, 0, 0);
        tessellator.addVertexWithUV(1 - stutterFraction, 1 - stutterFraction, 0 + stutterFraction, 0, texturePixel);
        tessellator.addVertexWithUV(1 - stutterFraction, 1.0F - worldPixel, 0 + stutterFraction, texturePixel * 16, texturePixel);
        tessellator.draw();

        //SOUTH
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0 + stutterFraction, 1 - stutterFraction, 1 - stutterFraction, texturePixel * 16, 0);
        tessellator.addVertexWithUV(0 + stutterFraction, 1.0F - worldPixel, 1 - stutterFraction, 0, 0);
        tessellator.addVertexWithUV(1 - stutterFraction, 1.0F - worldPixel, 1 - stutterFraction, 0, texturePixel);
        tessellator.addVertexWithUV(1 - stutterFraction, 1 - stutterFraction, 1 - stutterFraction, texturePixel * 16, texturePixel);
        tessellator.draw();

        //WEST
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0 + stutterFraction, 1 - stutterFraction, 0 + stutterFraction, texturePixel * 16, 0);
        tessellator.addVertexWithUV(0 + stutterFraction, 1.0F - worldPixel, 0 + stutterFraction, 0, 0);
        tessellator.addVertexWithUV(0 + stutterFraction, 1.0F - worldPixel, 1 - stutterFraction, 0, texturePixel);
        tessellator.addVertexWithUV(0 + stutterFraction, 1 - stutterFraction, 1 - stutterFraction, texturePixel * 16, texturePixel);
        tessellator.draw();

        //EAST
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(1 - stutterFraction, 1 - stutterFraction, 1 - stutterFraction, texturePixel * 16, 0);
        tessellator.addVertexWithUV(1 - stutterFraction, 1.0F - worldPixel, 1 - stutterFraction, 0, 0);
        tessellator.addVertexWithUV(1 - stutterFraction, 1.0F - worldPixel, 0 + stutterFraction, 0, texturePixel);
        tessellator.addVertexWithUV(1 - stutterFraction, 1 - stutterFraction, 0 + stutterFraction, texturePixel * 16, texturePixel);
        tessellator.draw();

        if(false)
        {
            //NORTH
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(1, 1.0F - worldPixel, 0, texturePixel * 10, 0);
            tessellator.addVertexWithUV(1, 1, 0, texturePixel * 4, 0);
            tessellator.addVertexWithUV(0, 1, 0, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(0, 1.0F - worldPixel, 0, texturePixel * 10, texturePixel * 4);
            tessellator.draw();

            //SOUTH
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(1, 1, 1, texturePixel * 10, texturePixel * 4);
            tessellator.addVertexWithUV(1, 1.0F - worldPixel, 1, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(0, 1.0F - worldPixel, 1, texturePixel * 4, 0);
            tessellator.addVertexWithUV(0, 1, 1, texturePixel * 10, 0);
            tessellator.draw();

            //WEST
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 1, 1, texturePixel * 10, texturePixel * 4);
            tessellator.addVertexWithUV(0, 1.0F - worldPixel, 1, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(0, 1.0F - worldPixel, 0, texturePixel * 4, 0);
            tessellator.addVertexWithUV(0, 1, 0, texturePixel * 10, 0);
            tessellator.draw();

            //EAST
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(1, 1, 0, texturePixel * 10, texturePixel * 4);
            tessellator.addVertexWithUV(1, 1.0F - worldPixel, 0, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(1, 1.0F - worldPixel, 1, texturePixel * 4, 0);
            tessellator.addVertexWithUV(1, 1, 1, texturePixel * 10, 0);
            tessellator.draw();
        }
        GL11.glPopMatrix();
    }

    private void renderCenterConnection(Tessellator tessellator, ForgeDirection direction)
    {
        float worldPixel = 1F/16F;
        float texturePixel = 1F/16F;

        float lowPoint = worldPixel * 6;
        float highPoint = 1 - worldPixel * 6;

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        if(direction.equals(ForgeDirection.DOWN))
        {
            GL11.glRotatef(180, 1, 0, 0);
        }
        else if(direction.equals(ForgeDirection.NORTH))
            GL11.glRotatef(270, 1, 0, 0);
        else if(direction.equals(ForgeDirection.SOUTH))
            GL11.glRotatef(90, 1, 0, 0);
        else if(direction.equals(ForgeDirection.WEST))
            GL11.glRotatef(90, 0, 0, 1);
        else if(direction.equals(ForgeDirection.EAST))
            GL11.glRotatef(270, 0, 0, 1);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        //NORTH
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(highPoint, 1, lowPoint, texturePixel * 10, 0);
        tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, texturePixel * 4, 0);
        tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(lowPoint, 1, lowPoint, texturePixel * 10, texturePixel * 4);
        tessellator.draw();

        //SOUTH
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(lowPoint, 1, highPoint, texturePixel * 10, 0);
        tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, texturePixel * 4, 0);
        tessellator.addVertexWithUV(highPoint, highPoint, highPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(highPoint, 1, highPoint, texturePixel * 10, texturePixel * 4);
        tessellator.draw();

        //WEST
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(lowPoint, 1, lowPoint, texturePixel * 10, 0);
        tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, texturePixel * 4, 0);
        tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(lowPoint, 1, highPoint, texturePixel * 10, texturePixel * 4);
        tessellator.draw();

        //EAST
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(highPoint, 1, highPoint, texturePixel * 10, 0);
        tessellator.addVertexWithUV(highPoint, highPoint, highPoint, texturePixel * 4, 0);
        tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(highPoint, 1, lowPoint, texturePixel * 10, texturePixel * 4);
        tessellator.draw();

        if(renderInside)
        {
            //NORTH-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(lowPoint, 1, lowPoint, texturePixel * 10, texturePixel * 4);
            tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(highPoint, 1, lowPoint, texturePixel * 10, 0);
            tessellator.draw();

            //SOUTH-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(highPoint, 1, highPoint, texturePixel * 10, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, highPoint, highPoint, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(lowPoint, 1, highPoint, texturePixel * 10, 0);
            tessellator.draw();

            //WEST-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(lowPoint, 1, highPoint, texturePixel * 10, texturePixel * 4);
            tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(lowPoint, 1, lowPoint, texturePixel * 10, 0);
            tessellator.draw();

            //EAST-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(highPoint, 1, lowPoint, texturePixel * 10, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, highPoint, highPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(highPoint, 1, highPoint, texturePixel * 10, 0);
            tessellator.draw();
        }
        GL11.glPopMatrix();
    }

    private void renderCenter(Tessellator tessellator)
    {
        float worldPixel = 1F/16F;
        float texturePixel = 1F/16F;

        float lowPoint = worldPixel * 6;
        float highPoint = 1 - worldPixel * 6;

        //BOTTOM
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(lowPoint, lowPoint, highPoint, 0, texturePixel * 4);
        tessellator.addVertexWithUV(lowPoint, lowPoint, lowPoint, 0, 0);
        tessellator.addVertexWithUV(highPoint, lowPoint, lowPoint, texturePixel * 4, 0);
        tessellator.addVertexWithUV(highPoint, lowPoint, highPoint, texturePixel * 4, texturePixel * 4);
        tessellator.draw();

        //TOP
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(highPoint, highPoint, highPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, texturePixel * 4, 0);
        tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, 0, 0);
        tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, 0, texturePixel * 4);
        tessellator.draw();

        //NORTH
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, 0, 0);
        tessellator.addVertexWithUV(highPoint, lowPoint, lowPoint, 0, texturePixel * 4);
        tessellator.addVertexWithUV(lowPoint, lowPoint, lowPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, texturePixel * 4, 0);
        tessellator.draw();

        //SOUTH
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(lowPoint, lowPoint, highPoint, 0, texturePixel * 4);
        tessellator.addVertexWithUV(highPoint, lowPoint, highPoint, 0, 0);
        tessellator.addVertexWithUV(highPoint, highPoint, highPoint, texturePixel * 4, 0);
        tessellator.draw();

        //WEST
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(lowPoint, lowPoint, lowPoint, 0, texturePixel * 4);
        tessellator.addVertexWithUV(lowPoint, lowPoint, highPoint, 0, 0);
        tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, texturePixel * 4, 0);
        tessellator.draw();

        //EAST
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(highPoint, highPoint, highPoint, 0, 0);
        tessellator.addVertexWithUV(highPoint, lowPoint, highPoint, 0, texturePixel * 4);
        tessellator.addVertexWithUV(highPoint, lowPoint, lowPoint, texturePixel * 4, texturePixel * 4);
        tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, texturePixel * 4, 0);
        tessellator.draw();

        if(renderInside)
        {
            //BOTTOM-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(highPoint, lowPoint, highPoint, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, lowPoint, lowPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(lowPoint, lowPoint, lowPoint, 0, 0);
            tessellator.addVertexWithUV(lowPoint, lowPoint, highPoint, 0, texturePixel * 4);
            tessellator.draw();

            //TOP-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, 0, texturePixel * 4);
            tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, 0, 0);
            tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(highPoint, highPoint, highPoint, texturePixel * 4, texturePixel * 4);
            tessellator.draw();

            //NORTH-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(lowPoint, lowPoint, lowPoint, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, lowPoint, lowPoint, 0, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, 0, 0);
            tessellator.draw();

            //SOUTH-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(highPoint, highPoint, highPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(highPoint, lowPoint, highPoint, 0, 0);
            tessellator.addVertexWithUV(lowPoint, lowPoint, highPoint, 0, texturePixel * 4);
            tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, texturePixel * 4, texturePixel * 4);
            tessellator.draw();

            //WEST-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(lowPoint, highPoint, highPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(lowPoint, lowPoint, highPoint, 0, 0);
            tessellator.addVertexWithUV(lowPoint, lowPoint, lowPoint, 0, texturePixel * 4);
            tessellator.addVertexWithUV(lowPoint, highPoint, lowPoint, texturePixel * 4, texturePixel * 4);
            tessellator.draw();

            //EAST-FLIPPED
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(highPoint, highPoint, lowPoint, texturePixel * 4, 0);
            tessellator.addVertexWithUV(highPoint, lowPoint, lowPoint, texturePixel * 4, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, lowPoint, highPoint, 0, texturePixel * 4);
            tessellator.addVertexWithUV(highPoint, highPoint, highPoint, 0, 0);
            tessellator.draw();
        }
    }
}
