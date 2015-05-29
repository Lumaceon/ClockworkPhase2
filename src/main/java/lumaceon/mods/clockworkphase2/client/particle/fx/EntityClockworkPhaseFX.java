package lumaceon.mods.clockworkphase2.client.particle.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class EntityClockworkPhaseFX extends EntityFX
{
    public EntityClockworkPhaseFX(World world, double x, double y, double z)
    {
        super(world, x, y, z);

        this.particleAlpha = 1.0F;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
    }

    public EntityClockworkPhaseFX(World world, double x, double y, double z, double xMotion, double yMotion, double zMotion)
    {
        super(world, x, y, z, xMotion, yMotion, zMotion);

        this.particleAlpha = 1.0F;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
    }

    @Override
    public void renderParticle(Tessellator t, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        t.draw();
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(getResourceLocation());

        float f10 = 0.1F * this.particleScale;

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float f14 = 1.0F;

        t.startDrawingQuads();
        t.setColorRGBA_F(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha);
        t.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), 1, 1);
        t.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), 1, 0);
        t.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), 0, 0);
        t.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), 0, 1);
        t.draw();
        GL11.glPopMatrix();
        t.startDrawingQuads();
    }

    public ResourceLocation getResourceLocation()
    {
        return null;
    }

    @Override
    public int getBrightnessForRender(float p_70070_1_)
    {
        return 255;
    }
}
