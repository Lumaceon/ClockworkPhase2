package lumaceon.mods.clockworkphase2.client.particle;

import lumaceon.mods.clockworkphase2.client.particle.fx.EntityClockworkPhaseFX;
import lumaceon.mods.clockworkphase2.client.particle.fx.EntityDustFX;
import lumaceon.mods.clockworkphase2.client.particle.sequence.ParticleSequence;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ParticleSpawn
{
    public static EntityFX spawnDustParticle(World world, double x, double y, double z, float size, float red, float green, float blue)
    {
        EntityClockworkPhaseFX entityFX = new EntityDustFX(world, x, y, z, size, red, green, blue);
        ParticleSequence.addParticle(entityFX);
        return entityFX;
    }
}
