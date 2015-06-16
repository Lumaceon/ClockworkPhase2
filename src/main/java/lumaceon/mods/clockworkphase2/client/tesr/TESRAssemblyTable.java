package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTable;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TESRAssemblyTable extends TileEntitySpecialRenderer
{
    private ResourceLocation mainTexture = new ResourceLocation(Reference.MOD_ID, "textures/blocks/assembly_table.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_)
    {
        if(te != null && te instanceof TileAssemblyTable)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            Tessellator t = Tessellator.instance;
            this.bindTexture(mainTexture);
            t.startDrawingQuads();
            t.addVertexWithUV(0, 1, 1, 0, 0);
            t.addVertexWithUV(1, 1, 1, 0, 1);
            t.addVertexWithUV(1, 1, 0, 1, 1);
            t.addVertexWithUV(0, 1, 0, 1, 0);
            t.draw();
            t.startDrawingQuads();
            t.addVertexWithUV(1, 0, 1, 0, 1);
            t.addVertexWithUV(0, 0, 1, 0, 0);
            t.addVertexWithUV(0, 0, 0, 1, 0);
            t.addVertexWithUV(1, 0, 0, 1, 1);
            t.draw();
            t.startDrawingQuads();
            t.addVertexWithUV(1, 1, 1, 0, 0);
            t.addVertexWithUV(1, 0, 1, 0, 1);
            t.addVertexWithUV(1, 0, 0, 1, 1);
            t.addVertexWithUV(1, 1, 0, 1, 0);
            t.draw();
            t.startDrawingQuads();
            t.addVertexWithUV(0, 0, 1, 1, 0);
            t.addVertexWithUV(0, 1, 1, 1, 1);
            t.addVertexWithUV(0, 1, 0, 0, 1);
            t.addVertexWithUV(0, 0, 0, 0, 0);
            t.draw();
            t.startDrawingQuads();
            t.addVertexWithUV(1, 1, 1, 1, 1);
            t.addVertexWithUV(0, 1, 1, 0, 1);
            t.addVertexWithUV(0, 0, 1, 0, 0);
            t.addVertexWithUV(1, 0, 1, 1, 0);
            t.draw();
            t.startDrawingQuads();
            t.addVertexWithUV(0, 1, 0, 1, 0);
            t.addVertexWithUV(1, 1, 0, 0, 0);
            t.addVertexWithUV(1, 0, 0, 0, 1);
            t.addVertexWithUV(0, 0, 0, 1, 1);
            t.draw();
            GL11.glPopMatrix();
        }
    }
}
