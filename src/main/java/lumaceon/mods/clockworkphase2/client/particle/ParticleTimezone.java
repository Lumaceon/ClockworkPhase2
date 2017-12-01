package lumaceon.mods.clockworkphase2.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleTimezone extends Particle
{
    public boolean isChild = false;
    public double startX, startZ;

    public ParticleTimezone(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
        this.setPosition(posXIn, posYIn, posZIn);
        this.startX = posXIn;
        this.startZ = posZIn;
        this.prevPosX = posXIn;
        this.prevPosY = posYIn;
        this.prevPosZ = posZIn;
        this.motionX = (rand.nextFloat() - 0.5) * 10;
        this.motionZ = (rand.nextFloat() - 0.5) * 10;
        this.particleMaxAge = 40;
        this.particleScale = 10.0F;
        this.particleRed = 0.0F;
        this.particleGreen = 0.8F;
        this.particleBlue = 1.0F;
        onUpdate();
    }

    public ParticleTimezone(World worldIn, double posXIn, double posYIn, double posZIn, boolean isChild)
    {
        super(worldIn, posXIn, posYIn, posZIn);
        this.setPosition(posXIn, posYIn, posZIn);
        this.prevPosX = posXIn;
        this.prevPosY = posYIn;
        this.prevPosZ = posZIn;
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.particleMaxAge = 40;
        this.particleScale = 10.0F;
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

        buffer.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
    }

    @Override
    public void onUpdate()
    {
        if(!isChild)
        {
            double xLength = this.posX - this.startX;
            double zLength = this.posZ - this.startZ;
            if(Math.sqrt(xLength*xLength + zLength*zLength) > 125)
            {
                this.setExpired();
            }
            else if(this.particleAge != 0)
            {
                ModParticles.addParticle(new ParticleTimezone(world, posX, posY, posZ, true));
            }
        }
        else
        {
            this.particleGreen -= 0.05F;
            if(this.particleGreen < 0.0F)
                this.particleGreen = 0.0F;
        }
        super.onUpdate();
        particleAlpha -= 0.025;
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}
