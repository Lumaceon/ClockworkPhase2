package lumaceon.mods.clockworkphase2.client.particle.fx;

import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class EntityClockworkPhaseFX extends EntityFX
{
    boolean firstTick = true;
    public EntityClockworkPhaseFX(World world, double x, double y, double z, float size, float red, float green, float blue)
    {
        super(world, x - 0.5, y, z - 0.5);

        this.particleAlpha = 1.0F;
        this.setPosition(x - 0.5, y, z - 0.5);
        this.lastTickPosX = this.prevPosX = this.posX;
        this.lastTickPosY = this.prevPosY = this.posY;
        this.lastTickPosZ = this.prevPosZ = this.posZ;

        this.multipleParticleScaleBy(size);
        this.setRBGColorF(red, green, blue);
    }

    @Override
    public void renderParticle(Tessellator t, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        if(firstTick)
            return;
        //Minecraft.getMinecraft().renderEngine.bindTexture(getResourceLocation());

        float f10 = 0.1F * this.particleScale;

        float f11 = (float)(prevPosX + (posX - prevPosX) * (double)par2 - RenderHandler.interpolatedPosX);
        float f12 = (float)(prevPosY + (posY - prevPosY) * (double)par2 - RenderHandler.interpolatedPosY);
        float f13 = (float)(prevPosZ + (posZ - prevPosZ) * (double)par2 - RenderHandler.interpolatedPosZ);
        float f14 = 1.0F;

        t.startDrawingQuads();
        t.setColorRGBA_F(particleRed * f14, particleGreen * f14, particleBlue * f14, particleAlpha);
        t.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), 1, 1);
        t.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), 1, 0);
        t.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), 0, 0);
        t.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), 0, 1);
        t.draw();
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

    @Override
    public void onUpdate()
    {
        if(firstTick)
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if(this.particleAge++ >= this.particleMaxAge)
            this.setDead();

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        firstTick = false;
    }
}
