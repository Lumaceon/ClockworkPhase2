package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.lwjgl.opengl.GL11;

public class TESRTemporalZoningMachine extends TileEntitySpecialRenderer
{
    @CapabilityInject(ITimezone.class)
    static final Capability<ITimezone> TIMEZONE = null;

    Minecraft mc = Minecraft.getMinecraft();

    @Override
    public boolean isGlobalRenderer(TileEntity te) {
        return true;
    }

    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GL11.glPushMatrix();

        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        setLightmapDisabled(true);

        ITimezone timezone = te.getCapability(TIMEZONE, null);
        renderGlyph(te.getPos(), x, y, z, timezone);

        GL11.glPopMatrix();
    }

    public void renderGlyph(BlockPos tilePosition, double x, double y, double z, ITimezone timezone)
    {
        if(mc.getRenderViewEntity() != null)
        {
            double dist = Math.sqrt(Math.pow(mc.getRenderViewEntity().posX - tilePosition.getX(), 2) + Math.pow(mc.getRenderViewEntity().posZ - tilePosition.getZ(), 2));
            if(dist > 128 * 2)
                return;
        }
        mc.renderEngine.bindTexture(Textures.MISC.GLYPH_MAIN);
        double low = -1 - 128;
        double high = 128;

        GL11.glPushMatrix();
        GL11.glTranslated(x + 1, y + 254.99 - tilePosition.getY(), z + 1);
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
        GL11.glRotatef((Minecraft.getSystemTime() % 115200.0F) / 320, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.5F, 0.0F, 0.5F);

        Tessellator tessy = Tessellator.getInstance();
        BufferBuilder renderer = tessy.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(low, 0, low).tex(0, 0).endVertex();
        renderer.pos(low, 0, high).tex(0, 1).endVertex();
        renderer.pos(high, 0, high).tex(1, 1).endVertex();
        renderer.pos(high, 0, low).tex(1, 0).endVertex();
        tessy.draw();

        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(high, 0, low).tex(0, 0).endVertex();
        renderer.pos(high, 0, high).tex(0, 1).endVertex();
        renderer.pos(low, 0, high).tex(1, 1).endVertex();
        renderer.pos(low, 0, low).tex(1, 0).endVertex();
        tessy.draw();
        GL11.glPopMatrix();
    }
}
