package lumaceon.mods.clockworkphase2.client.render.elements.world;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class WorldRenderElementTemporalDisplacementAltar extends WorldRenderElement
{
    private Minecraft mc;
    public TileEntity te;

    public WorldRenderElementTemporalDisplacementAltar(World world, int x, int y, int z) {
        super(world, x, y, z);
        this.mc = Minecraft.getMinecraft();
        this.te = world.getTileEntity(new BlockPos(x, y, z));
    }

    @Override
    public void render(double x, double y, double z)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(-5.0F, 0.01F, -5.0F);
        GL11.glScalef(11.0F, 1.0F, 11.0F);
        //GL11.glEnable(GL11.GL_BLEND);
        mc.renderEngine.bindTexture(Textures.MISC.TDA_BOTTOM);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0, 0).tex(1, 0).endVertex();
        tessellator.draw();

        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(1, 0, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0, 0).tex(1, 0).endVertex();
        tessellator.draw();

        GL11.glTranslatef(0.0F, 0.39F, 0.0F);
        mc.renderEngine.bindTexture(Textures.MISC.TDA_GEARS);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0, 0).tex(1, 0).endVertex();
        tessellator.draw();

        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(1, 0, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0, 0).tex(1, 0).endVertex();
        tessellator.draw();

        GL11.glTranslatef(0.0F, 0.4F, 0.0F);
        mc.renderEngine.bindTexture(Textures.MISC.TDA_METAL_FRAME);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0, 0).tex(1, 0).endVertex();
        tessellator.draw();

        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(1, 0, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0, 0).tex(1, 0).endVertex();
        tessellator.draw();

        GL11.glTranslatef(0.0F, 0.2F, 0.0F);
        mc.renderEngine.bindTexture(Textures.MISC.TDA_GLASS_FRAME);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0, 0).tex(1, 0).endVertex();
        tessellator.draw();

        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(1, 0, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0, 0).tex(1, 0).endVertex();
        tessellator.draw();
        GL11.glPopMatrix();
    }

    @Override
    public boolean isFinished() {
        return world == null || te == null || world.getTileEntity(new BlockPos(xPos, yPos, zPos)) == null || !world.getTileEntity(new BlockPos(xPos, yPos, zPos)).equals(te);
    }

    @Override
    public float maxRenderDistance() {
        return 256;
    }
}
