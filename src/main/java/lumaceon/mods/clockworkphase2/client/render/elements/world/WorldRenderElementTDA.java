package lumaceon.mods.clockworkphase2.client.render.elements.world;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class WorldRenderElementTDA extends WorldRenderElement
{
    private Minecraft mc;
    public TileEntity te;

    public WorldRenderElementTDA(World world, int x, int y, int z) {
        super(world, x, y, z);
        this.mc = Minecraft.getMinecraft();
        this.te = world.getTileEntity(x, y, z);
    }

    @Override
    public void render(double x, double y, double z) {
        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z);
        if(te.blockMetadata == ForgeDirection.EAST.ordinal() || te.blockMetadata == ForgeDirection.WEST.ordinal())
        {
            GL11.glTranslatef(0F, 0F, 1F);
            GL11.glRotatef(90, 0, 1, 0);
        }
        else if(te.blockMetadata == ForgeDirection.DOWN.ordinal() || te.blockMetadata == ForgeDirection.UP.ordinal())
        {
            GL11.glTranslatef(0F, 1F, 0F);
            GL11.glRotatef(90, 1, 0, 0);
        }
        GL11.glTranslatef(-7.0F, 0.0F, -0.0F);
        GL11.glScalef(15.0F, 15.0F, 1.0F);
        mc.renderEngine.bindTexture(Textures.MISC.TDA);
        Tessellator tessy = Tessellator.instance;
        tessy.startDrawingQuads();
        tessy.addVertexWithUV(0, 0, 0, 0, 0);
        tessy.addVertexWithUV(0, 1, 0, 0, 1);
        tessy.addVertexWithUV(1, 1, 0, 1, 1);
        tessy.addVertexWithUV(1, 0, 0, 1, 0);

        tessy.addVertexWithUV(1, 0, 1, 1, 0);
        tessy.addVertexWithUV(1, 1, 1, 1, 1);
        tessy.addVertexWithUV(0, 1, 1, 0, 1);
        tessy.addVertexWithUV(0, 0, 1, 0, 0);
        tessy.draw();

        GL11.glPopMatrix();


        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z);
        if(te.blockMetadata == ForgeDirection.EAST.ordinal() || te.blockMetadata == ForgeDirection.WEST.ordinal())
        {
            GL11.glTranslatef(0F, 0F, 1F);
            GL11.glRotatef(90, 0, 1, 0);
        }
        else if(te.blockMetadata == ForgeDirection.DOWN.ordinal() || te.blockMetadata == ForgeDirection.UP.ordinal())
        {
            GL11.glTranslatef(0F, 1F, 0F);
            GL11.glRotatef(90, 1, 0, 0);
        }
        mc.renderEngine.bindTexture(Textures.MISC.TDA_SIDES);
        tessy.startDrawingQuads();
        //TOPS
        tessy.addVertexWithUV(0, 1, 0, 0, 0);
        tessy.addVertexWithUV(0, 1, 1, 0, 1);
        tessy.addVertexWithUV(1, 1, 1, 1, 1);
        tessy.addVertexWithUV(1, 1, 0, 1, 0);

        tessy.addVertexWithUV(0+1, 1, 0, 0, 0);
        tessy.addVertexWithUV(0+1, 1, 1, 0, 1);
        tessy.addVertexWithUV(1+1, 1, 1, 1, 1);
        tessy.addVertexWithUV(1+1, 1, 0, 1, 0);

        tessy.addVertexWithUV(0+2, 1+1, 0, 0, 0);
        tessy.addVertexWithUV(0+2, 1+1, 1, 0, 1);
        tessy.addVertexWithUV(1+2, 1+1, 1, 1, 1);
        tessy.addVertexWithUV(1+2, 1+1, 0, 1, 0);

        tessy.addVertexWithUV(0+3, 1+1, 0, 0, 0);
        tessy.addVertexWithUV(0+3, 1+1, 1, 0, 1);
        tessy.addVertexWithUV(1+3, 1+1, 1, 1, 1);
        tessy.addVertexWithUV(1+3, 1+1, 0, 1, 0);

        tessy.addVertexWithUV(0+4, 1+2, 0, 0, 0);
        tessy.addVertexWithUV(0+4, 1+2, 1, 0, 1);
        tessy.addVertexWithUV(1+4, 1+2, 1, 1, 1);
        tessy.addVertexWithUV(1+4, 1+2, 0, 1, 0);

        tessy.addVertexWithUV(0+5, 1+3, 0, 0, 0);
        tessy.addVertexWithUV(0+5, 1+3, 1, 0, 1);
        tessy.addVertexWithUV(1+5, 1+3, 1, 1, 1);
        tessy.addVertexWithUV(1+5, 1+3, 0, 1, 0);

        tessy.addVertexWithUV(0+6, 1+5, 0, 0, 0);
        tessy.addVertexWithUV(0+6, 1+5, 1, 0, 1);
        tessy.addVertexWithUV(1+6, 1+5, 1, 1, 1);
        tessy.addVertexWithUV(1+6, 1+5, 0, 1, 0);

        tessy.addVertexWithUV(0+7, 1+9, 0, 0, 0);
        tessy.addVertexWithUV(0+7, 1+9, 1, 0, 1);
        tessy.addVertexWithUV(1+7, 1+9, 1, 1, 1);
        tessy.addVertexWithUV(1+7, 1+9, 0, 1, 0);

        tessy.addVertexWithUV(0+6, 1+11, 0, 0, 0);
        tessy.addVertexWithUV(0+6, 1+11, 1, 0, 1);
        tessy.addVertexWithUV(1+6, 1+11, 1, 1, 1);
        tessy.addVertexWithUV(1+6, 1+11, 0, 1, 0);

        tessy.addVertexWithUV(0+5, 1+12, 0, 0, 0);
        tessy.addVertexWithUV(0+5, 1+12, 1, 0, 1);
        tessy.addVertexWithUV(1+5, 1+12, 1, 1, 1);
        tessy.addVertexWithUV(1+5, 1+12, 0, 1, 0);

        tessy.addVertexWithUV(0+4, 1+13, 0, 0, 0);
        tessy.addVertexWithUV(0+4, 1+13, 1, 0, 1);
        tessy.addVertexWithUV(1+4, 1+13, 1, 1, 1);
        tessy.addVertexWithUV(1+4, 1+13, 0, 1, 0);

        tessy.addVertexWithUV(0+3, 1+13, 0, 0, 0);
        tessy.addVertexWithUV(0+3, 1+13, 1, 0, 1);
        tessy.addVertexWithUV(1+3, 1+13, 1, 1, 1);
        tessy.addVertexWithUV(1+3, 1+13, 0, 1, 0);

        tessy.addVertexWithUV(0+2, 1+14, 0, 0, 0);
        tessy.addVertexWithUV(0+2, 1+14, 1, 0, 1);
        tessy.addVertexWithUV(1+2, 1+14, 1, 1, 1);
        tessy.addVertexWithUV(1+2, 1+14, 0, 1, 0);

        tessy.addVertexWithUV(0+1, 1+14, 0, 0, 0);
        tessy.addVertexWithUV(0+1, 1+14, 1, 0, 1);
        tessy.addVertexWithUV(1+1, 1+14, 1, 1, 1);
        tessy.addVertexWithUV(1+1, 1+14, 0, 1, 0);

        tessy.addVertexWithUV(0, 1+14, 0, 0, 0);
        tessy.addVertexWithUV(0, 1+14, 1, 0, 1);
        tessy.addVertexWithUV(1, 1+14, 1, 1, 1);
        tessy.addVertexWithUV(1, 1+14, 0, 1, 0);

        tessy.addVertexWithUV(0-1, 1+14, 0, 0, 0);
        tessy.addVertexWithUV(0-1, 1+14, 1, 0, 1);
        tessy.addVertexWithUV(1-1, 1+14, 1, 1, 1);
        tessy.addVertexWithUV(1-1, 1+14, 0, 1, 0);

        tessy.addVertexWithUV(0-2, 1+14, 0, 0, 0);
        tessy.addVertexWithUV(0-2, 1+14, 1, 0, 1);
        tessy.addVertexWithUV(1-2, 1+14, 1, 1, 1);
        tessy.addVertexWithUV(1-2, 1+14, 0, 1, 0);

        tessy.addVertexWithUV(0-3, 1+13, 0, 0, 0);
        tessy.addVertexWithUV(0-3, 1+13, 1, 0, 1);
        tessy.addVertexWithUV(1-3, 1+13, 1, 1, 1);
        tessy.addVertexWithUV(1-3, 1+13, 0, 1, 0);

        tessy.addVertexWithUV(0-4, 1+13, 0, 0, 0);
        tessy.addVertexWithUV(0-4, 1+13, 1, 0, 1);
        tessy.addVertexWithUV(1-4, 1+13, 1, 1, 1);
        tessy.addVertexWithUV(1-4, 1+13, 0, 1, 0);

        tessy.addVertexWithUV(0-5, 1+12, 0, 0, 0);
        tessy.addVertexWithUV(0-5, 1+12, 1, 0, 1);
        tessy.addVertexWithUV(1-5, 1+12, 1, 1, 1);
        tessy.addVertexWithUV(1-5, 1+12, 0, 1, 0);

        tessy.addVertexWithUV(0-6, 1+11, 0, 0, 0);
        tessy.addVertexWithUV(0-6, 1+11, 1, 0, 1);
        tessy.addVertexWithUV(1-6, 1+11, 1, 1, 1);
        tessy.addVertexWithUV(1-6, 1+11, 0, 1, 0);

        tessy.addVertexWithUV(0-7, 1+9, 0, 0, 0);
        tessy.addVertexWithUV(0-7, 1+9, 1, 0, 1);
        tessy.addVertexWithUV(1-7, 1+9, 1, 1, 1);
        tessy.addVertexWithUV(1-7, 1+9, 0, 1, 0);

        tessy.addVertexWithUV(0-1, 1, 0, 0, 0);
        tessy.addVertexWithUV(0-1, 1, 1, 0, 1);
        tessy.addVertexWithUV(1-1, 1, 1, 1, 1);
        tessy.addVertexWithUV(1-1, 1, 0, 1, 0);

        tessy.addVertexWithUV(0-2, 1+1, 0, 0, 0);
        tessy.addVertexWithUV(0-2, 1+1, 1, 0, 1);
        tessy.addVertexWithUV(1-2, 1+1, 1, 1, 1);
        tessy.addVertexWithUV(1-2, 1+1, 0, 1, 0);

        tessy.addVertexWithUV(0 - 3, 1 + 1, 0, 0, 0);
        tessy.addVertexWithUV(0-3, 1+1, 1, 0, 1);
        tessy.addVertexWithUV(1-3, 1+1, 1, 1, 1);
        tessy.addVertexWithUV(1 - 3, 1 + 1, 0, 1, 0);

        tessy.addVertexWithUV(0-4, 1+2, 0, 0, 0);
        tessy.addVertexWithUV(0-4, 1+2, 1, 0, 1);
        tessy.addVertexWithUV(1-4, 1+2, 1, 1, 1);
        tessy.addVertexWithUV(1-4, 1+2, 0, 1, 0);

        tessy.addVertexWithUV(0-5, 1+3, 0, 0, 0);
        tessy.addVertexWithUV(0-5, 1+3, 1, 0, 1);
        tessy.addVertexWithUV(1-5, 1+3, 1, 1, 1);
        tessy.addVertexWithUV(1-5, 1+3, 0, 1, 0);

        tessy.addVertexWithUV(0-6, 1+5, 0, 0, 0);
        tessy.addVertexWithUV(0-6, 1+5, 1, 0, 1);
        tessy.addVertexWithUV(1-6, 1+5, 1, 1, 1);
        tessy.addVertexWithUV(1-6, 1+5, 0, 1, 0);
        //TOPS

        //BOTTOMS
        tessy.addVertexWithUV(1, 0, 0, 1, 0);
        tessy.addVertexWithUV(1, 0, 1, 1, 1);
        tessy.addVertexWithUV(0, 0, 1, 0, 1);
        tessy.addVertexWithUV(0, 0, 0, 0, 0);

        tessy.addVertexWithUV(1+1, 0, 0, 1, 0);
        tessy.addVertexWithUV(1+1, 0, 1, 1, 1);
        tessy.addVertexWithUV(0+1, 0, 1, 0, 1);
        tessy.addVertexWithUV(0+1, 0, 0, 0, 0);

        tessy.addVertexWithUV(1+2, 0, 0, 1, 0);
        tessy.addVertexWithUV(1+2, 0, 1, 1, 1);
        tessy.addVertexWithUV(0+2, 0, 1, 0, 1);
        tessy.addVertexWithUV(0+2, 0, 0, 0, 0);

        tessy.addVertexWithUV(1+3, 0+1, 0, 1, 0);
        tessy.addVertexWithUV(1+3, 0+1, 1, 1, 1);
        tessy.addVertexWithUV(0+3, 0+1, 1, 0, 1);
        tessy.addVertexWithUV(0+3, 0+1, 0, 0, 0);

        tessy.addVertexWithUV(1+4, 0+1, 0, 1, 0);
        tessy.addVertexWithUV(1+4, 0+1, 1, 1, 1);
        tessy.addVertexWithUV(0+4, 0+1, 1, 0, 1);
        tessy.addVertexWithUV(0+4, 0+1, 0, 0, 0);

        tessy.addVertexWithUV(1+5, 0+2, 0, 1, 0);
        tessy.addVertexWithUV(1+5, 0+2, 1, 1, 1);
        tessy.addVertexWithUV(0+5, 0+2, 1, 0, 1);
        tessy.addVertexWithUV(0+5, 0+2, 0, 0, 0);

        tessy.addVertexWithUV(1+6, 0+3, 0, 1, 0);
        tessy.addVertexWithUV(1+6, 0+3, 1, 1, 1);
        tessy.addVertexWithUV(0+6, 0+3, 1, 0, 1);
        tessy.addVertexWithUV(0+6, 0+3, 0, 0, 0);

        tessy.addVertexWithUV(1+7, 0+5, 0, 1, 0);
        tessy.addVertexWithUV(1+7, 0+5, 1, 1, 1);
        tessy.addVertexWithUV(0+7, 0+5, 1, 0, 1);
        tessy.addVertexWithUV(0+7, 0+5, 0, 0, 0);

        tessy.addVertexWithUV(1+6, 0+9, 0, 1, 0);
        tessy.addVertexWithUV(1+6, 0+9, 1, 1, 1);
        tessy.addVertexWithUV(0+6, 0+9, 1, 0, 1);
        tessy.addVertexWithUV(0+6, 0+9, 0, 0, 0);

        tessy.addVertexWithUV(1+5, 0+11, 0, 1, 0);
        tessy.addVertexWithUV(1+5, 0+11, 1, 1, 1);
        tessy.addVertexWithUV(0+5, 0+11, 1, 0, 1);
        tessy.addVertexWithUV(0+5, 0+11, 0, 0, 0);

        tessy.addVertexWithUV(1+4, 0+12, 0, 1, 0);
        tessy.addVertexWithUV(1+4, 0+12, 1, 1, 1);
        tessy.addVertexWithUV(0+4, 0+12, 1, 0, 1);
        tessy.addVertexWithUV(0+4, 0+12, 0, 0, 0);

        tessy.addVertexWithUV(1+3, 0+13, 0, 1, 0);
        tessy.addVertexWithUV(1+3, 0+13, 1, 1, 1);
        tessy.addVertexWithUV(0+3, 0+13, 1, 0, 1);
        tessy.addVertexWithUV(0+3, 0+13, 0, 0, 0);

        tessy.addVertexWithUV(1+2, 0+13, 0, 1, 0);
        tessy.addVertexWithUV(1+2, 0+13, 1, 1, 1);
        tessy.addVertexWithUV(0+2, 0+13, 1, 0, 1);
        tessy.addVertexWithUV(0+2, 0+13, 0, 0, 0);

        tessy.addVertexWithUV(1+1, 0+14, 0, 1, 0);
        tessy.addVertexWithUV(1+1, 0+14, 1, 1, 1);
        tessy.addVertexWithUV(0+1, 0+14, 1, 0, 1);
        tessy.addVertexWithUV(0+1, 0+14, 0, 0, 0);

        tessy.addVertexWithUV(1, 0+14, 0, 1, 0);
        tessy.addVertexWithUV(1, 0+14, 1, 1, 1);
        tessy.addVertexWithUV(0, 0+14, 1, 0, 1);
        tessy.addVertexWithUV(0, 0+14, 0, 0, 0);

        tessy.addVertexWithUV(1-1, 0+14, 0, 1, 0);
        tessy.addVertexWithUV(1-1, 0+14, 1, 1, 1);
        tessy.addVertexWithUV(0-1, 0+14, 1, 0, 1);
        tessy.addVertexWithUV(0-1, 0+14, 0, 0, 0);

        tessy.addVertexWithUV(1-1, 0, 0, 1, 0);
        tessy.addVertexWithUV(1-1, 0, 1, 1, 1);
        tessy.addVertexWithUV(0-1, 0, 1, 0, 1);
        tessy.addVertexWithUV(0-1, 0, 0, 0, 0);

        tessy.addVertexWithUV(1-2, 0, 0, 1, 0);
        tessy.addVertexWithUV(1-2, 0, 1, 1, 1);
        tessy.addVertexWithUV(0-2, 0, 1, 0, 1);
        tessy.addVertexWithUV(0-2, 0, 0, 0, 0);

        tessy.addVertexWithUV(1-3, 0+1, 0, 1, 0);
        tessy.addVertexWithUV(1-3, 0+1, 1, 1, 1);
        tessy.addVertexWithUV(0-3, 0+1, 1, 0, 1);
        tessy.addVertexWithUV(0-3, 0+1, 0, 0, 0);

        tessy.addVertexWithUV(1-4, 0+1, 0, 1, 0);
        tessy.addVertexWithUV(1-4, 0+1, 1, 1, 1);
        tessy.addVertexWithUV(0-4, 0+1, 1, 0, 1);
        tessy.addVertexWithUV(0-4, 0+1, 0, 0, 0);

        tessy.addVertexWithUV(1-5, 0+2, 0, 1, 0);
        tessy.addVertexWithUV(1-5, 0+2, 1, 1, 1);
        tessy.addVertexWithUV(0-5, 0+2, 1, 0, 1);
        tessy.addVertexWithUV(0-5, 0+2, 0, 0, 0);

        tessy.addVertexWithUV(1-6, 0+3, 0, 1, 0);
        tessy.addVertexWithUV(1-6, 0+3, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 0+3, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 0+3, 0, 0, 0);

        tessy.addVertexWithUV(1-7, 0+5, 0, 1, 0);
        tessy.addVertexWithUV(1-7, 0+5, 1, 1, 1);
        tessy.addVertexWithUV(0-7, 0+5, 1, 0, 1);
        tessy.addVertexWithUV(0-7, 0+5, 0, 0, 0);

        tessy.addVertexWithUV(1-6, 0+9, 0, 1, 0);
        tessy.addVertexWithUV(1-6, 0+9, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 0+9, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 0+9, 0, 0, 0);

        tessy.addVertexWithUV(1-5, 0+11, 0, 1, 0);
        tessy.addVertexWithUV(1-5, 0+11, 1, 1, 1);
        tessy.addVertexWithUV(0-5, 0+11, 1, 0, 1);
        tessy.addVertexWithUV(0-5, 0+11, 0, 0, 0);

        tessy.addVertexWithUV(1-4, 0+12, 0, 1, 0);
        tessy.addVertexWithUV(1-4, 0+12, 1, 1, 1);
        tessy.addVertexWithUV(0-4, 0+12, 1, 0, 1);
        tessy.addVertexWithUV(0-4, 0+12, 0, 0, 0);

        tessy.addVertexWithUV(1-3, 0+13, 0, 1, 0);
        tessy.addVertexWithUV(1-3, 0+13, 1, 1, 1);
        tessy.addVertexWithUV(0-3, 0+13, 1, 0, 1);
        tessy.addVertexWithUV(0-3, 0+13, 0, 0, 0);

        tessy.addVertexWithUV(1-2, 0+13, 0, 1, 0);
        tessy.addVertexWithUV(1-2, 0+13, 1, 1, 1);
        tessy.addVertexWithUV(0-2, 0+13, 1, 0, 1);
        tessy.addVertexWithUV(0-2, 0+13, 0, 0, 0);
        //BOTTOMS

        //WESTS (seen by facing EAST)
        tessy.addVertexWithUV(0-2, 0, 0, 0, 0);
        tessy.addVertexWithUV(0-2, 0, 1, 0, 1);
        tessy.addVertexWithUV(0-2, 1, 1, 1, 1);
        tessy.addVertexWithUV(0-2, 1, 0, 1, 0);

        tessy.addVertexWithUV(0-4, 0+1, 0, 0, 0);
        tessy.addVertexWithUV(0-4, 0+1, 1, 0, 1);
        tessy.addVertexWithUV(0-4, 1+1, 1, 1, 1);
        tessy.addVertexWithUV(0-4, 1+1, 0, 1, 0);

        tessy.addVertexWithUV(0-5, 0+2, 0, 0, 0);
        tessy.addVertexWithUV(0-5, 0+2, 1, 0, 1);
        tessy.addVertexWithUV(0-5, 1+2, 1, 1, 1);
        tessy.addVertexWithUV(0-5, 1+2, 0, 1, 0);

        tessy.addVertexWithUV(0-6, 0+3, 0, 0, 0);
        tessy.addVertexWithUV(0-6, 0+3, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 1+3, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 1+3, 0, 1, 0);

        tessy.addVertexWithUV(0-6, 0+4, 0, 0, 0);
        tessy.addVertexWithUV(0-6, 0+4, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 1+4, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 1+4, 0, 1, 0);

        tessy.addVertexWithUV(0-7, 0+5, 0, 0, 0);
        tessy.addVertexWithUV(0-7, 0+5, 1, 0, 1);
        tessy.addVertexWithUV(0-7, 1+5, 1, 1, 1);
        tessy.addVertexWithUV(0-7, 1+5, 0, 1, 0);

        tessy.addVertexWithUV(0-7, 0+6, 0, 0, 0);
        tessy.addVertexWithUV(0-7, 0+6, 1, 0, 1);
        tessy.addVertexWithUV(0-7, 1+6, 1, 1, 1);
        tessy.addVertexWithUV(0-7, 1+6, 0, 1, 0);

        tessy.addVertexWithUV(0-7, 0+7, 0, 0, 0);
        tessy.addVertexWithUV(0-7, 0+7, 1, 0, 1);
        tessy.addVertexWithUV(0-7, 1+7, 1, 1, 1);
        tessy.addVertexWithUV(0-7, 1+7, 0, 1, 0);

        tessy.addVertexWithUV(0-7, 0+8, 0, 0, 0);
        tessy.addVertexWithUV(0-7, 0+8, 1, 0, 1);
        tessy.addVertexWithUV(0-7, 1+8, 1, 1, 1);
        tessy.addVertexWithUV(0-7, 1+8, 0, 1, 0);

        tessy.addVertexWithUV(0-7, 0+9, 0, 0, 0);
        tessy.addVertexWithUV(0-7, 0+9, 1, 0, 1);
        tessy.addVertexWithUV(0-7, 1+9, 1, 1, 1);
        tessy.addVertexWithUV(0-7, 1+9, 0, 1, 0);

        tessy.addVertexWithUV(0-6, 0+10, 0, 0, 0);
        tessy.addVertexWithUV(0-6, 0+10, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 1+10, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 1+10, 0, 1, 0);

        tessy.addVertexWithUV(0-6, 0+11, 0, 0, 0);
        tessy.addVertexWithUV(0-6, 0+11, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 1+11, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 1+11, 0, 1, 0);

        tessy.addVertexWithUV(0-5, 0+12, 0, 0, 0);
        tessy.addVertexWithUV(0-5, 0+12, 1, 0, 1);
        tessy.addVertexWithUV(0-5, 1+12, 1, 1, 1);
        tessy.addVertexWithUV(0-5, 1+12, 0, 1, 0);

        tessy.addVertexWithUV(0-4, 0+13, 0, 0, 0);
        tessy.addVertexWithUV(0-4, 0+13, 1, 0, 1);
        tessy.addVertexWithUV(0-4, 1+13, 1, 1, 1);
        tessy.addVertexWithUV(0-4, 1+13, 0, 1, 0);

        tessy.addVertexWithUV(0-2, 0+14, 0, 0, 0);
        tessy.addVertexWithUV(0-2, 0+14, 1, 0, 1);
        tessy.addVertexWithUV(0-2, 1+14, 1, 1, 1);
        tessy.addVertexWithUV(0-2, 1+14, 0, 1, 0);

        tessy.addVertexWithUV(0+2, 0+13, 0, 0, 0);
        tessy.addVertexWithUV(0+2, 0+13, 1, 0, 1);
        tessy.addVertexWithUV(0+2, 1+13, 1, 1, 1);
        tessy.addVertexWithUV(0+2, 1+13, 0, 1, 0);

        tessy.addVertexWithUV(0+4, 0+12, 0, 0, 0);
        tessy.addVertexWithUV(0+4, 0+12, 1, 0, 1);
        tessy.addVertexWithUV(0+4, 1+12, 1, 1, 1);
        tessy.addVertexWithUV(0+4, 1+12, 0, 1, 0);

        tessy.addVertexWithUV(0+5, 0+11, 0, 0, 0);
        tessy.addVertexWithUV(0+5, 0+11, 1, 0, 1);
        tessy.addVertexWithUV(0+5, 1+11, 1, 1, 1);
        tessy.addVertexWithUV(0+5, 1+11, 0, 1, 0);

        tessy.addVertexWithUV(0+6, 0+10, 0, 0, 0);
        tessy.addVertexWithUV(0+6, 0+10, 1, 0, 1);
        tessy.addVertexWithUV(0+6, 1+10, 1, 1, 1);
        tessy.addVertexWithUV(0+6, 1+10, 0, 1, 0);

        tessy.addVertexWithUV(0+6, 0+9, 0, 0, 0);
        tessy.addVertexWithUV(0+6, 0+9, 1, 0, 1);
        tessy.addVertexWithUV(0+6, 1+9, 1, 1, 1);
        tessy.addVertexWithUV(0+6, 1+9, 0, 1, 0);

        tessy.addVertexWithUV(0+7, 0+8, 0, 0, 0);
        tessy.addVertexWithUV(0+7, 0+8, 1, 0, 1);
        tessy.addVertexWithUV(0+7, 1+8, 1, 1, 1);
        tessy.addVertexWithUV(0+7, 1+8, 0, 1, 0);

        tessy.addVertexWithUV(0+7, 0+7, 0, 0, 0);
        tessy.addVertexWithUV(0+7, 0+7, 1, 0, 1);
        tessy.addVertexWithUV(0+7, 1+7, 1, 1, 1);
        tessy.addVertexWithUV(0+7, 1+7, 0, 1, 0);

        tessy.addVertexWithUV(0+7, 0+6, 0, 0, 0);
        tessy.addVertexWithUV(0+7, 0+6, 1, 0, 1);
        tessy.addVertexWithUV(0+7, 1+6, 1, 1, 1);
        tessy.addVertexWithUV(0+7, 1+6, 0, 1, 0);

        tessy.addVertexWithUV(0+6, 0+5, 0, 0, 0);
        tessy.addVertexWithUV(0+6, 0+5, 1, 0, 1);
        tessy.addVertexWithUV(0+6, 1+5, 1, 1, 1);
        tessy.addVertexWithUV(0+6, 1+5, 0, 1, 0);

        tessy.addVertexWithUV(0+6, 0+4, 0, 0, 0);
        tessy.addVertexWithUV(0+6, 0+4, 1, 0, 1);
        tessy.addVertexWithUV(0+6, 1+4, 1, 1, 1);
        tessy.addVertexWithUV(0+6, 1+4, 0, 1, 0);

        tessy.addVertexWithUV(0+5, 0+3, 0, 0, 0);
        tessy.addVertexWithUV(0+5, 0+3, 1, 0, 1);
        tessy.addVertexWithUV(0+5, 1+3, 1, 1, 1);
        tessy.addVertexWithUV(0+5, 1+3, 0, 1, 0);

        tessy.addVertexWithUV(0+4, 0+2, 0, 0, 0);
        tessy.addVertexWithUV(0+4, 0+2, 1, 0, 1);
        tessy.addVertexWithUV(0+4, 1+2, 1, 1, 1);
        tessy.addVertexWithUV(0+4, 1+2, 0, 1, 0);

        tessy.addVertexWithUV(0+2, 0+1, 0, 0, 0);
        tessy.addVertexWithUV(0+2, 0+1, 1, 0, 1);
        tessy.addVertexWithUV(0+2, 1+1, 1, 1, 1);
        tessy.addVertexWithUV(0+2, 1+1, 0, 1, 0);
        //WESTS (seen by facing EAST)


        //EASTS (seen by facing WEST)
        tessy.addVertexWithUV(0+3, 1, 0, 1, 0);
        tessy.addVertexWithUV(0+3, 1, 1, 1, 1);
        tessy.addVertexWithUV(0+3, 0, 1, 0, 1);
        tessy.addVertexWithUV(0+3, 0, 0, 0, 0);

        tessy.addVertexWithUV(0+5, 1+1, 0, 1, 0);
        tessy.addVertexWithUV(0+5, 1+1, 1, 1, 1);
        tessy.addVertexWithUV(0+5, 0+1, 1, 0, 1);
        tessy.addVertexWithUV(0+5, 0+1, 0, 0, 0);

        tessy.addVertexWithUV(0+6, 1+2, 0, 1, 0);
        tessy.addVertexWithUV(0+6, 1+2, 1, 1, 1);
        tessy.addVertexWithUV(0+6, 0+2, 1, 0, 1);
        tessy.addVertexWithUV(0+6, 0+2, 0, 0, 0);

        tessy.addVertexWithUV(0+7, 1+3, 0, 1, 0);
        tessy.addVertexWithUV(0+7, 1+3, 1, 1, 1);
        tessy.addVertexWithUV(0+7, 0+3, 1, 0, 1);
        tessy.addVertexWithUV(0+7, 0+3, 0, 0, 0);

        tessy.addVertexWithUV(0+7, 1+4, 0, 1, 0);
        tessy.addVertexWithUV(0+7, 1+4, 1, 1, 1);
        tessy.addVertexWithUV(0+7, 0+4, 1, 0, 1);
        tessy.addVertexWithUV(0+7, 0+4, 0, 0, 0);

        tessy.addVertexWithUV(0+8, 1+5, 0, 1, 0);
        tessy.addVertexWithUV(0+8, 1+5, 1, 1, 1);
        tessy.addVertexWithUV(0+8, 0+5, 1, 0, 1);
        tessy.addVertexWithUV(0+8, 0+5, 0, 0, 0);

        tessy.addVertexWithUV(0+8, 1+6, 0, 1, 0);
        tessy.addVertexWithUV(0+8, 1+6, 1, 1, 1);
        tessy.addVertexWithUV(0+8, 0+6, 1, 0, 1);
        tessy.addVertexWithUV(0+8, 0+6, 0, 0, 0);

        tessy.addVertexWithUV(0+8, 1+7, 0, 1, 0);
        tessy.addVertexWithUV(0+8, 1+7, 1, 1, 1);
        tessy.addVertexWithUV(0+8, 0+7, 1, 0, 1);
        tessy.addVertexWithUV(0+8, 0+7, 0, 0, 0);

        tessy.addVertexWithUV(0+8, 1+8, 0, 1, 0);
        tessy.addVertexWithUV(0+8, 1+8, 1, 1, 1);
        tessy.addVertexWithUV(0+8, 0+8, 1, 0, 1);
        tessy.addVertexWithUV(0+8, 0+8, 0, 0, 0);

        tessy.addVertexWithUV(0+8, 1+9, 0, 1, 0);
        tessy.addVertexWithUV(0+8, 1+9, 1, 1, 1);
        tessy.addVertexWithUV(0+8, 0+9, 1, 0, 1);
        tessy.addVertexWithUV(0+8, 0+9, 0, 0, 0);

        tessy.addVertexWithUV(0+7, 1+10, 0, 1, 0);
        tessy.addVertexWithUV(0+7, 1+10, 1, 1, 1);
        tessy.addVertexWithUV(0+7, 0+10, 1, 0, 1);
        tessy.addVertexWithUV(0+7, 0+10, 0, 0, 0);

        tessy.addVertexWithUV(0+7, 1+11, 0, 1, 0);
        tessy.addVertexWithUV(0+7, 1+11, 1, 1, 1);
        tessy.addVertexWithUV(0+7, 0+11, 1, 0, 1);
        tessy.addVertexWithUV(0+7, 0+11, 0, 0, 0);

        tessy.addVertexWithUV(0+6, 1+12, 0, 1, 0);
        tessy.addVertexWithUV(0+6, 1+12, 1, 1, 1);
        tessy.addVertexWithUV(0+6, 0+12, 1, 0, 1);
        tessy.addVertexWithUV(0+6, 0+12, 0, 0, 0);

        tessy.addVertexWithUV(0+5, 1+13, 0, 1, 0);
        tessy.addVertexWithUV(0+5, 1+13, 1, 1, 1);
        tessy.addVertexWithUV(0+5, 0+13, 1, 0, 1);
        tessy.addVertexWithUV(0+5, 0+13, 0, 0, 0);

        tessy.addVertexWithUV(0+3, 1+14, 0, 1, 0);
        tessy.addVertexWithUV(0+3, 1+14, 1, 1, 1);
        tessy.addVertexWithUV(0+3, 0+14, 1, 0, 1);
        tessy.addVertexWithUV(0+3, 0+14, 0, 0, 0);

        tessy.addVertexWithUV(0-1, 1+13, 0, 1, 0);
        tessy.addVertexWithUV(0-1, 1+13, 1, 1, 1);
        tessy.addVertexWithUV(0-1, 0+13, 1, 0, 1);
        tessy.addVertexWithUV(0-1, 0+13, 0, 0, 0);

        tessy.addVertexWithUV(0-3, 1+12, 0, 1, 0);
        tessy.addVertexWithUV(0-3, 1+12, 1, 1, 1);
        tessy.addVertexWithUV(0-3, 0+12, 1, 0, 1);
        tessy.addVertexWithUV(0-3, 0+12, 0, 0, 0);

        tessy.addVertexWithUV(0-4, 1+11, 0, 1, 0);
        tessy.addVertexWithUV(0-4, 1+11, 1, 1, 1);
        tessy.addVertexWithUV(0-4, 0+11, 1, 0, 1);
        tessy.addVertexWithUV(0-4, 0+11, 0, 0, 0);

        tessy.addVertexWithUV(0-5, 1+10, 0, 1, 0);
        tessy.addVertexWithUV(0-5, 1+10, 1, 1, 1);
        tessy.addVertexWithUV(0-5, 0+10, 1, 0, 1);
        tessy.addVertexWithUV(0-5, 0+10, 0, 0, 0);

        tessy.addVertexWithUV(0-5, 1+9, 0, 1, 0);
        tessy.addVertexWithUV(0-5, 1+9, 1, 1, 1);
        tessy.addVertexWithUV(0-5, 0+9, 1, 0, 1);
        tessy.addVertexWithUV(0-5, 0+9, 0, 0, 0);

        tessy.addVertexWithUV(0-6, 1+8, 0, 1, 0);
        tessy.addVertexWithUV(0-6, 1+8, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 0+8, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 0+8, 0, 0, 0);

        tessy.addVertexWithUV(0-6, 1+7, 0, 1, 0);
        tessy.addVertexWithUV(0-6, 1+7, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 0+7, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 0+7, 0, 0, 0);

        tessy.addVertexWithUV(0-6, 1+6, 0, 1, 0);
        tessy.addVertexWithUV(0-6, 1+6, 1, 1, 1);
        tessy.addVertexWithUV(0-6, 0+6, 1, 0, 1);
        tessy.addVertexWithUV(0-6, 0+6, 0, 0, 0);

        tessy.addVertexWithUV(0-5, 1+5, 0, 1, 0);
        tessy.addVertexWithUV(0-5, 1+5, 1, 1, 1);
        tessy.addVertexWithUV(0-5, 0+5, 1, 0, 1);
        tessy.addVertexWithUV(0-5, 0+5, 0, 0, 0);

        tessy.addVertexWithUV(0-5, 1+4, 0, 1, 0);
        tessy.addVertexWithUV(0-5, 1+4, 1, 1, 1);
        tessy.addVertexWithUV(0-5, 0+4, 1, 0, 1);
        tessy.addVertexWithUV(0-5, 0+4, 0, 0, 0);

        tessy.addVertexWithUV(0-4, 1+3, 0, 1, 0);
        tessy.addVertexWithUV(0-4, 1+3, 1, 1, 1);
        tessy.addVertexWithUV(0-4, 0+3, 1, 0, 1);
        tessy.addVertexWithUV(0-4, 0+3, 0, 0, 0);

        tessy.addVertexWithUV(0-3, 1+2, 0, 1, 0);
        tessy.addVertexWithUV(0-3, 1+2, 1, 1, 1);
        tessy.addVertexWithUV(0-3, 0+2, 1, 0, 1);
        tessy.addVertexWithUV(0-3, 0+2, 0, 0, 0);

        tessy.addVertexWithUV(0-1, 1+1, 0, 1, 0);
        tessy.addVertexWithUV(0-1, 1+1, 1, 1, 1);
        tessy.addVertexWithUV(0-1, 0+1, 1, 0, 1);
        tessy.addVertexWithUV(0-1, 0+1, 0, 0, 0);
        //EASTS (seen by facing WEST)
        tessy.draw();

        GL11.glPopMatrix();
    }

    @Override
    public boolean isFinished() {
        return world == null || te == null || world.getTileEntity(xPos, yPos, zPos) == null || !world.getTileEntity(xPos, yPos, zPos).equals(te);
    }
}
