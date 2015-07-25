package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.Models;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TESRTelescope extends TileEntitySpecialRenderer
{
    public static final ResourceLocation TELESCOPE_1 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/telescope1.png");
    public static final ResourceLocation TELESCOPE_2 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/telescope2.png");
    public static final ResourceLocation TELESCOPE_3 = new ResourceLocation(Reference.MOD_ID, "textures/blocks/telescope3.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_)
    {
        boolean lightAlreadyActive = GL11.glIsEnabled(GL11.GL_LIGHTING);
        boolean t2;
        boolean t3;
        t2 = te.getWorldObj().getBlock(te.xCoord, te.yCoord - 1, te.zCoord).equals(ModBlocks.telescope);
        t3 = te.getWorldObj().getBlock(te.xCoord, te.yCoord - 2, te.zCoord).equals(ModBlocks.telescope) && t2;

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0F, -1F, 0F);
        if(!lightAlreadyActive)
            GL11.glEnable(GL11.GL_LIGHTING);
        if(t3)
        {
            this.bindTexture(TELESCOPE_3);
            Models.TELESCOPE_3.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);
        }
        else if(t2)
        {
            this.bindTexture(TELESCOPE_2);
            Models.TELESCOPE_2.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);
        }
        else
        {
            this.bindTexture(TELESCOPE_1);
            Models.TELESCOPE_1.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);
        }
        if(!lightAlreadyActive)
            GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
