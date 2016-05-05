package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TESRTimezoneController extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(-5.0F, 0.0F, -5.0F);
        GL11.glScalef(11.0F, 1.0F, 11.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_TOP);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.98, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.98, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.98, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.98, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.98, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.98, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.98, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.98, 0).tex(1, 0).endVertex();
        tessellator.draw();

        bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_BOTTOM);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.01, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.01, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.01, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.01, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.01, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.01, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.01, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.01, 0).tex(1, 0).endVertex();
        tessellator.draw();

        bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_METAL);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.8, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.8, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.8, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.8, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.8, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.8, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.8, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.8, 0).tex(1, 0).endVertex();
        tessellator.draw();

        GL11.glScalef(1F/11.0F, 1.0F, 1F/11.0F);
        GL11.glTranslatef(5.5F, 0.0F, 5.5F);
        GL11.glRotatef((System.currentTimeMillis() / 10) % 360, 0.0F, -1.0F, 0.0F);
        GL11.glTranslatef(-5.5F, 0.0F, -5.5F);
        GL11.glScalef(11.0F, 1.0F, 11.0F);
        bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_LHAND);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.999, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.999, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.999, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.999, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.999, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.999, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.999, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.999, 0).tex(1, 0).endVertex();
        tessellator.draw();

        GL11.glScalef(1F/11.0F, 1.0F, 1F/11.0F);
        GL11.glTranslatef(5.5F, 0.0F, 5.5F);
        GL11.glRotatef(-((System.currentTimeMillis() / 10) % 360), 0.0F, -1.0F, 0.0F); //Negate old translation.
        GL11.glRotatef(((System.currentTimeMillis() / 600) % 360), 0.0F, -1.0F, 0.0F);
        GL11.glTranslatef(-5.5F, 0.0F, -5.5F);
        GL11.glScalef(11.0F, 1.0F, 11.0F);
        bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_SHAND);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.99, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.99, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.99, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.99, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.99, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.99, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.99, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.99, 0).tex(1, 0).endVertex();
        tessellator.draw();

        GL11.glPopMatrix();
    }
}
