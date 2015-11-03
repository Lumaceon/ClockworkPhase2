package lumaceon.mods.clockworkphase2.client.render.elements.world;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class WorldRenderElementTemporalDisplacementAltar extends WorldRenderElement
{
    private Minecraft mc;
    public TileEntity te;

    public WorldRenderElementTemporalDisplacementAltar(World world, int x, int y, int z) {
        super(world, x, y, z);
        this.mc = Minecraft.getMinecraft();
        this.te = world.getTileEntity(x, y, z);
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
        Tessellator tessy = Tessellator.instance;
        tessy.startDrawingQuads();
        tessy.addVertexWithUV(0, 0, 0, 0, 0);
        tessy.addVertexWithUV(0, 0, 1, 0, 1);
        tessy.addVertexWithUV(1, 0, 1, 1, 1);
        tessy.addVertexWithUV(1, 0, 0, 1, 0);

        tessy.addVertexWithUV(1, 0, 0, 0, 0);
        tessy.addVertexWithUV(1, 0, 1, 0, 1);
        tessy.addVertexWithUV(0, 0, 1, 1, 1);
        tessy.addVertexWithUV(0, 0, 0, 1, 0);
        tessy.draw();

        GL11.glTranslatef(0.0F, 0.39F, 0.0F);
        mc.renderEngine.bindTexture(Textures.MISC.TDA_GEARS);
        tessy.startDrawingQuads();
        tessy.addVertexWithUV(0, 0, 0, 0, 0);
        tessy.addVertexWithUV(0, 0, 1, 0, 1);
        tessy.addVertexWithUV(1, 0, 1, 1, 1);
        tessy.addVertexWithUV(1, 0, 0, 1, 0);

        tessy.addVertexWithUV(1, 0, 0, 0, 0);
        tessy.addVertexWithUV(1, 0, 1, 0, 1);
        tessy.addVertexWithUV(0, 0, 1, 1, 1);
        tessy.addVertexWithUV(0, 0, 0, 1, 0);
        tessy.draw();

        GL11.glTranslatef(0.0F, 0.4F, 0.0F);
        mc.renderEngine.bindTexture(Textures.MISC.TDA_METAL_FRAME);
        tessy.startDrawingQuads();
        tessy.addVertexWithUV(0, 0, 0, 0, 0);
        tessy.addVertexWithUV(0, 0, 1, 0, 1);
        tessy.addVertexWithUV(1, 0, 1, 1, 1);
        tessy.addVertexWithUV(1, 0, 0, 1, 0);

        tessy.addVertexWithUV(1, 0, 0, 0, 0);
        tessy.addVertexWithUV(1, 0, 1, 0, 1);
        tessy.addVertexWithUV(0, 0, 1, 1, 1);
        tessy.addVertexWithUV(0, 0, 0, 1, 0);
        tessy.draw();

        GL11.glTranslatef(0.0F, 0.2F, 0.0F);
        mc.renderEngine.bindTexture(Textures.MISC.TDA_GLASS_FRAME);
        tessy.startDrawingQuads();
        tessy.addVertexWithUV(0, 0, 0, 0, 0);
        tessy.addVertexWithUV(0, 0, 1, 0, 1);
        tessy.addVertexWithUV(1, 0, 1, 1, 1);
        tessy.addVertexWithUV(1, 0, 0, 1, 0);

        tessy.addVertexWithUV(1, 0, 0, 0, 0);
        tessy.addVertexWithUV(1, 0, 1, 0, 1);
        tessy.addVertexWithUV(0, 0, 1, 1, 1);
        tessy.addVertexWithUV(0, 0, 0, 1, 0);
        tessy.draw();
        GL11.glPopMatrix();
    }

    @Override
    public boolean isFinished() {
        return world == null || te == null || world.getTileEntity(xPos, yPos, zPos) == null || !world.getTileEntity(xPos, yPos, zPos).equals(te);
    }

    @Override
    public float maxRenderDistance() {
        return 256;
    }
}
