package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTimezoneFluidExporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class TESRTimezoneFluidExporter extends TileEntitySpecialRenderer
{
    private FluidStack lastFluid = null;
    private ResourceLocation loc;
    private static ResourceLocation otherLoc = new ResourceLocation(Reference.MOD_ID, "textures/blocks/timezone_fluid_exporter.png");
    private static ResourceLocation empty = new ResourceLocation(Reference.MOD_ID, "textures/blocks/timezone_fluid_exporter_empty.png");

    public TESRTimezoneFluidExporter()
    {

    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_)
    {
        if(te != null && te instanceof TileTimezoneFluidExporter)
        {
            TileTimezoneFluidExporter fluidValve = (TileTimezoneFluidExporter) te;
            FluidStack fluid = fluidValve.renderStack;

            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslated(x, y, z);
            Tessellator t = Tessellator.instance;
            if(fluid != null)
            {
                IIcon icon = fluid.getFluid().getStillIcon();

                if(!fluid.equals(lastFluid))
                    loc = Minecraft.getMinecraft().renderEngine.getResourceLocation(fluid.getFluid().getSpriteNumber());
                this.bindTexture(loc);
                t.startDrawingQuads();
                t.addVertexWithUV(0, 1 - 0.01, 1, icon.getMinU(), icon.getMinV());
                t.addVertexWithUV(1, 1 - 0.01, 1, icon.getMinU(), icon.getMaxV());
                t.addVertexWithUV(1, 1 - 0.01, 0, icon.getMaxU(), icon.getMaxV());
                t.addVertexWithUV(0, 1 - 0.01, 0, icon.getMaxU(), icon.getMinV());
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(1, 0 + 0.01, 1, icon.getMinU(), icon.getMaxV());
                t.addVertexWithUV(0, 0 + 0.01, 1, icon.getMinU(), icon.getMinV());
                t.addVertexWithUV(0, 0 + 0.01, 0, icon.getMaxU(), icon.getMinV());
                t.addVertexWithUV(1, 0 + 0.01, 0, icon.getMaxU(), icon.getMaxV());
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(1 - 0.01, 1, 1, icon.getMinU(), icon.getMinV());
                t.addVertexWithUV(1 - 0.01, 0, 1, icon.getMinU(), icon.getMaxV());
                t.addVertexWithUV(1 - 0.01, 0, 0, icon.getMaxU(), icon.getMaxV());
                t.addVertexWithUV(1 - 0.01, 1, 0, icon.getMaxU(), icon.getMinV());
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(0 + 0.01, 0, 1, icon.getMaxU(), icon.getMinV());
                t.addVertexWithUV(0 + 0.01, 1, 1, icon.getMaxU(), icon.getMaxV());
                t.addVertexWithUV(0 + 0.01, 1, 0, icon.getMinU(), icon.getMaxV());
                t.addVertexWithUV(0 + 0.01, 0, 0, icon.getMinU(), icon.getMinV());
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(1, 1, 1 - 0.01, icon.getMaxU(), icon.getMaxV());
                t.addVertexWithUV(0, 1, 1 - 0.01, icon.getMinU(), icon.getMaxV());
                t.addVertexWithUV(0, 0, 1 - 0.01, icon.getMinU(), icon.getMinV());
                t.addVertexWithUV(1, 0, 1 - 0.01, icon.getMaxU(), icon.getMinV());
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(0, 1, 0 + 0.01, icon.getMaxU(), icon.getMinV());
                t.addVertexWithUV(1, 1, 0 + 0.01, icon.getMinU(), icon.getMinV());
                t.addVertexWithUV(1, 0, 0 + 0.01, icon.getMinU(), icon.getMaxV());
                t.addVertexWithUV(0, 0, 0 + 0.01, icon.getMaxU(), icon.getMaxV());
                t.draw();
                lastFluid = fluid;
            }
            else
            {
                this.bindTexture(empty);
                t.startDrawingQuads();
                t.addVertexWithUV(0, 1 - 0.01, 1, 0, 0);
                t.addVertexWithUV(1, 1 - 0.01, 1, 0, 1);
                t.addVertexWithUV(1, 1 - 0.01, 0, 1, 1);
                t.addVertexWithUV(0, 1 - 0.01, 0, 1, 0);
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(1, 0 + 0.01, 1, 0, 1);
                t.addVertexWithUV(0, 0 + 0.01, 1, 0, 0);
                t.addVertexWithUV(0, 0 + 0.01, 0, 1, 0);
                t.addVertexWithUV(1, 0 + 0.01, 0, 1, 1);
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(1 - 0.01, 1, 1, 0, 0);
                t.addVertexWithUV(1 - 0.01, 0, 1, 0, 1);
                t.addVertexWithUV(1 - 0.01, 0, 0, 1, 1);
                t.addVertexWithUV(1 - 0.01, 1, 0, 1, 0);
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(0 + 0.01, 0, 1, 1, 0);
                t.addVertexWithUV(0 + 0.01, 1, 1, 1, 1);
                t.addVertexWithUV(0 + 0.01, 1, 0, 0, 1);
                t.addVertexWithUV(0 + 0.01, 0, 0, 0, 0);
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(1, 1, 1 - 0.01, 1, 1);
                t.addVertexWithUV(0, 1, 1 - 0.01, 0, 1);
                t.addVertexWithUV(0, 0, 1 - 0.01, 0, 0);
                t.addVertexWithUV(1, 0, 1 - 0.01, 1, 0);
                t.draw();
                t.startDrawingQuads();
                t.addVertexWithUV(0, 1, 0 + 0.01, 1, 0);
                t.addVertexWithUV(1, 1, 0 + 0.01, 0, 0);
                t.addVertexWithUV(1, 0, 0 + 0.01, 0, 1);
                t.addVertexWithUV(0, 0, 0 + 0.01, 1, 1);
                t.draw();
                lastFluid = null;
            }

            this.bindTexture(otherLoc);

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