package lumaceon.mods.clockworkphase2.client.render.elements.world;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class WorldRenderElementTemporalClock extends WorldRenderElement
{
    private Minecraft mc;
    public TileEntity te;

    public WorldRenderElementTemporalClock(World world, int x, int y, int z) {
        super(world, x, y, z);
        this.mc = Minecraft.getMinecraft();
        this.te = world.getTileEntity(new BlockPos(x, y, z));
    }

    @Override
    public void render(double x, double y, double z)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0, 1.02F, 0);
        GL11.glEnable(GL11.GL_BLEND);
        //mc.renderEngine.bindTexture(Textures.GUI.CLOCK);
        /*Tessellator tessy = Tessellator.instance;
        tessy.startDrawingQuads();
        tessy.addVertexWithUV(0, 0, 0, 0, 0);
        tessy.addVertexWithUV(0, 0, 1, 0, 1);
        tessy.addVertexWithUV(1, 0, 1, 1, 1);
        tessy.addVertexWithUV(1, 0, 0, 1, 0);

        tessy.addVertexWithUV(1, 0, 0, 0, 0);
        tessy.addVertexWithUV(1, 0, 1, 0, 1);
        tessy.addVertexWithUV(0, 0, 1, 1, 1);
        tessy.addVertexWithUV(0, 0, 0, 1, 0);
        tessy.draw();*/
        GL11.glPopMatrix();
    }
}
