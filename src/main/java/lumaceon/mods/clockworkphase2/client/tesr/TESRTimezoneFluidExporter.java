package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidExporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class TESRTimezoneFluidExporter extends TileEntitySpecialRenderer
{
    private FluidStack lastFluid = null;
    private TextureMap textureMap;
    private TextureAtlasSprite activeAtlasSprite;
    private static ResourceLocation otherLoc = new ResourceLocation(Reference.MOD_ID, "textures/blocks/timezone_fluid_exporter.png");
    private static ResourceLocation empty = new ResourceLocation(Reference.MOD_ID, "textures/blocks/timezone_fluid_exporter_empty.png");

    public TESRTimezoneFluidExporter() {
        textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if(te != null && te instanceof TileTimezoneFluidExporter)
        {
            TileTimezoneFluidExporter fluidValve = (TileTimezoneFluidExporter) te;
            FluidStack fluid = fluidValve.renderStack;

            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glTranslated(x, y, z);
            Tessellator t = Tessellator.getInstance();
            WorldRenderer wr = t.getWorldRenderer();
            if(fluid != null)
            {
                if(lastFluid == null || !lastFluid.getFluid().equals(fluid.getFluid()))
                    activeAtlasSprite = textureMap.getAtlasSprite(fluid.getFluid().getStill().toString());

                this.bindTexture(TextureMap.locationBlocksTexture);
                float minU = activeAtlasSprite.getMinU();
                float maxU = activeAtlasSprite.getMaxU();
                float minV = activeAtlasSprite.getMinV();
                float maxV = activeAtlasSprite.getMaxV();

                wr.begin(7, DefaultVertexFormats.POSITION_TEX);

                wr.pos(0, 1 - 0.01, 1).tex(minU, minV).endVertex();
                wr.pos(1, 1 - 0.01, 1).tex(minU, maxV).endVertex();
                wr.pos(1, 1 - 0.01, 0).tex(maxU, maxV).endVertex();
                wr.pos(0, 1 - 0.01, 0).tex(maxU, minV).endVertex();

                wr.pos(1, 0 + 0.01, 1).tex(minU, maxV).endVertex();
                wr.pos(0, 0 + 0.01, 1).tex(minU, minV).endVertex();
                wr.pos(0, 0 + 0.01, 0).tex(maxU, minV).endVertex();
                wr.pos(1, 0 + 0.01, 0).tex(maxU, maxV).endVertex();

                wr.pos(1 - 0.01, 1, 1).tex(minU, minV).endVertex();
                wr.pos(1 - 0.01, 0, 1).tex(minU, maxV).endVertex();
                wr.pos(1 - 0.01, 0, 0).tex(maxU, maxV).endVertex();
                wr.pos(1 - 0.01, 1, 0).tex(maxU, minV).endVertex();

                wr.pos(0 + 0.01, 0, 1).tex(maxU, minV).endVertex();
                wr.pos(0 + 0.01, 1, 1).tex(maxU, maxV).endVertex();
                wr.pos(0 + 0.01, 1, 0).tex(minU, maxV).endVertex();
                wr.pos(0 + 0.01, 0, 0).tex(minU, minV).endVertex();

                wr.pos(1, 1, 1 - 0.01).tex(maxU, maxV).endVertex();
                wr.pos(0, 1, 1 - 0.01).tex(minU, maxV).endVertex();
                wr.pos(0, 0, 1 - 0.01).tex(minU, minV).endVertex();
                wr.pos(1, 0, 1 - 0.01).tex(maxU, minV).endVertex();

                wr.pos(0, 1, 0 + 0.01).tex(maxU, minV).endVertex();
                wr.pos(1, 1, 0 + 0.01).tex(minU, minV).endVertex();
                wr.pos(1, 0, 0 + 0.01).tex(minU, maxV).endVertex();
                wr.pos(0, 0, 0 + 0.01).tex(maxU, maxV).endVertex();

                t.draw();

                lastFluid = fluid;
            }
            else
            {
                this.bindTexture(empty);

                wr.begin(7, DefaultVertexFormats.POSITION_TEX);

                wr.pos(0, 1 - 0.01, 1).tex(0, 0).endVertex();
                wr.pos(1, 1 - 0.01, 1).tex(0, 1).endVertex();
                wr.pos(1, 1 - 0.01, 0).tex(1, 1).endVertex();
                wr.pos(0, 1 - 0.01, 0).tex(1, 0).endVertex();

                wr.pos(1, 0 + 0.01, 1).tex(0, 1).endVertex();
                wr.pos(0, 0 + 0.01, 1).tex(0, 0).endVertex();
                wr.pos(0, 0 + 0.01, 0).tex(1, 0).endVertex();
                wr.pos(1, 0 + 0.01, 0).tex(1, 1).endVertex();

                wr.pos(1 - 0.01, 1, 1).tex(0, 0).endVertex();
                wr.pos(1 - 0.01, 0, 1).tex(0, 1).endVertex();
                wr.pos(1 - 0.01, 0, 0).tex(1, 1).endVertex();
                wr.pos(1 - 0.01, 1, 0).tex(1, 0).endVertex();

                wr.pos(0 + 0.01, 0, 1).tex(1, 0).endVertex();
                wr.pos(0 + 0.01, 1, 1).tex(1, 1).endVertex();
                wr.pos(0 + 0.01, 1, 0).tex(0, 1).endVertex();
                wr.pos(0 + 0.01, 0, 0).tex(0, 0).endVertex();

                wr.pos(1, 1, 1 - 0.01).tex(1, 1).endVertex();
                wr.pos(0, 1, 1 - 0.01).tex(0, 1).endVertex();
                wr.pos(0, 0, 1 - 0.01).tex(0, 0).endVertex();
                wr.pos(1, 0, 1 - 0.01).tex(1, 0).endVertex();

                wr.pos(0, 1, 0 + 0.01).tex(1, 0).endVertex();
                wr.pos(1, 1, 0 + 0.01).tex(0, 0).endVertex();
                wr.pos(1, 0, 0 + 0.01).tex(0, 1).endVertex();
                wr.pos(0, 0, 0 + 0.01).tex(1, 1).endVertex();

                t.draw();

                lastFluid = null;
            }

            this.bindTexture(otherLoc);

            wr.begin(7, DefaultVertexFormats.POSITION_TEX);

            wr.pos(0, 1, 1).tex(0, 0).endVertex();
            wr.pos(1, 1, 1).tex(0, 1).endVertex();
            wr.pos(1, 1, 0).tex(1, 1).endVertex();
            wr.pos(0, 1, 0).tex(1, 0).endVertex();

            wr.pos(1, 0, 1).tex(0, 1).endVertex();
            wr.pos(0, 0, 1).tex(0, 0).endVertex();
            wr.pos(0, 0, 0).tex(1, 0).endVertex();
            wr.pos(1, 0, 0).tex(1, 1).endVertex();

            wr.pos(1, 1, 1).tex(0, 0).endVertex();
            wr.pos(1, 0, 1).tex(0, 1).endVertex();
            wr.pos(1, 0, 0).tex(1, 1).endVertex();
            wr.pos(1, 1, 0).tex(1, 0).endVertex();

            wr.pos(0, 0, 1).tex(1, 0).endVertex();
            wr.pos(0, 1, 1).tex(1, 1).endVertex();
            wr.pos(0, 1, 0).tex(0, 1).endVertex();
            wr.pos(0, 0, 0).tex(0, 0).endVertex();

            wr.pos(1, 1, 1).tex(1, 1).endVertex();
            wr.pos(0, 1, 1).tex(0, 1).endVertex();
            wr.pos(0, 0, 1).tex(0, 0).endVertex();
            wr.pos(1, 0, 1).tex(1, 0).endVertex();

            wr.pos(0, 1, 0).tex(1, 0).endVertex();
            wr.pos(1, 1, 0).tex(0, 0).endVertex();
            wr.pos(1, 0, 0).tex(0, 1).endVertex();
            wr.pos(0, 0, 0).tex(1, 1).endVertex();

            t.draw();

            GL11.glPopMatrix();
        }
    }
}