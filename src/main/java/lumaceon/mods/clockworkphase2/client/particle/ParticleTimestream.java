package lumaceon.mods.clockworkphase2.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleTimestream extends Particle
{
    public boolean isChild = false;

    public ParticleTimestream(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
        double truePosX = posXIn + (rand.nextFloat() - 0.5);
        double truePosY = posYIn + (rand.nextFloat() - 0.5);
        double truePosZ = posZIn + (rand.nextFloat() - 0.5);
        this.setPosition(truePosX, truePosY, truePosZ);
        this.prevPosX = truePosX;
        this.prevPosY = truePosY;
        this.prevPosZ = truePosZ;
        this.motionX = (rand.nextFloat() - 0.5) * 0.05;
        this.motionY = (rand.nextFloat() - 0.5) * 0.05;
        this.motionZ = (rand.nextFloat() - 0.5) * 0.05;
        this.particleMaxAge = 20;
        this.particleScale = 0.3F;
        this.particleRed = 0.0F;
        this.particleGreen = 0.8F;
        this.particleBlue = 1.0F;
    }

    public ParticleTimestream(World worldIn, double posXIn, double posYIn, double posZIn, boolean isChild)
    {
        super(worldIn, posXIn, posYIn, posZIn);
        this.setPosition(posXIn, posYIn, posZIn);
        this.prevPosX = posXIn;
        this.prevPosY = posYIn;
        this.prevPosZ = posZIn;
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.particleMaxAge = 20;
        this.particleScale = 0.3F;
        this.particleRed = 0.0F;
        this.particleGreen = 0.8F;
        this.particleBlue = 1.0F;
        this.isChild = isChild;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        float f = (float)this.particleTextureIndexX / 16.0F;
        float f1 = f + 0.0624375F;
        float f2 = (float)this.particleTextureIndexY / 16.0F;
        float f3 = f2 + 0.0624375F;
        float f4 = 0.1F * this.particleScale;

        if (this.particleTexture != null)
        {
            f = this.particleTexture.getMinU();
            f1 = this.particleTexture.getMaxU();
            f2 = this.particleTexture.getMinV();
            f3 = this.particleTexture.getMaxV();
        }

        f = 0;
        f1 = 1;
        f2 = 0;
        f3 = 1;

        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};

        /*if (this.particleAngle != 0.0F)
        {
            float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
            float f9 = MathHelper.cos(f8 * 0.5F);
            float f10 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.x;
            float f11 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.y;
            float f12 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.z;
            Vec3d vec3d = new Vec3d((double)f10, (double)f11, (double)f12);

            for (int l = 0; l < 4; ++l)
            {
                avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double)(f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double)(2.0F * f9)));
            }
        }*/

        //GlStateManager.enableBlend();
        //Tessellator tessellator = Tessellator.getInstance();
        //buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        buffer.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        //tessellator.draw();
    }

    @Override
    public void onUpdate()
    {
        if(!isChild && particleAge % 4 == 0)
        {
            ModParticles.addParticle(new ParticleTimestream(world, posX, posY, posZ, true));
            //Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTimestream(world, posX, posY, posZ, true));
        }
        if(isChild)
        {
            this.particleGreen -= 0.1F;
            if(this.particleGreen < 0.0F)
                this.particleGreen = 0.0F;
        }
        super.onUpdate();
        particleAlpha -= 0.05;
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}
