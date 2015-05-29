package lumaceon.mods.clockworkphase2.client.particle.fx;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityTimeSandExtractionFX extends EntityClockworkPhaseFX
{
    public EntityTimeSandExtractionFX(World world, double x, double y, double z)
    {
        super(world, x, y, z);
        this.motionX = (-0.5F + world.rand.nextFloat()) * 0.2F;
        this.motionY = (-0.5F + world.rand.nextFloat()) * 0.2F;
        this.motionZ = (-0.5F + world.rand.nextFloat()) * 0.2F;
        this.particleMaxAge = 25;
        this.noClip = true;
    }

    public ResourceLocation getResourceLocation()
    {
        return Textures.PARTICLE.TIME_SAND;
    }
}
