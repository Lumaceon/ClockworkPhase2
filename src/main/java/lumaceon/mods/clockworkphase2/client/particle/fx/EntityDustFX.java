package lumaceon.mods.clockworkphase2.client.particle.fx;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityDustFX extends EntityClockworkPhaseFX
{
    public EntityDustFX(World world, double x, double y, double z, float size, float red, float green, float blue)
    {
        super(world, x, y, z, size, red, green, blue);
        this.motionX = 0F;
        this.motionY = 0.2F;
        this.motionZ = 0F;
        this.noClip = true;
        this.particleMaxAge = 60;
    }

    @Override
    public ResourceLocation getResourceLocation()
    {
        return Textures.PARTICLE.TEST;
    }

    @Override
    public void onUpdate()
    {
        float par1 = Math.abs(particleAge - particleMaxAge * 0.5F);
        particleAlpha = Math.abs(par1 - particleMaxAge * 0.5F) * 0.05F;
        super.onUpdate();
    }
}
