package lumaceon.mods.clockworkphase2.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;

public class ParticleGenerator
{
    public static final ParticleGenerator INSTANCE = new ParticleGenerator();
    private Minecraft mc = Minecraft.getMinecraft();

    public EntityFX spawnParticle(EntityFX particle, double cutoff)
    {
        if(canSpawnParticle(particle.posX, particle.posY, particle.posZ, cutoff))
        {
            mc.effectRenderer.addEffect(particle);
            return particle;
        }
        else
            return null;
    }

    private boolean canSpawnParticle(double x, double y, double z, double maxDistance)
    {
        if(mc == null)
            mc = Minecraft.getMinecraft();
        if(mc == null || mc.theWorld == null || mc.renderViewEntity == null || mc.effectRenderer == null)
            return false;

        //Turns off these particles if particles are set to be minimal, and reduces them if needed.
        int userSettings = mc.gameSettings.particleSetting;
        if((userSettings == 1 && mc.theWorld.rand.nextInt(3) == 0) || userSettings == 2)
            return false;

        //Calculates distance to the particle generation, and returns false if it's too far away.
        double xDistance = mc.renderViewEntity.posX - x;
        double yDistance = mc.renderViewEntity.posY - y;
        double zDistance = mc.renderViewEntity.posZ - z;

        if(xDistance * xDistance + yDistance * yDistance + zDistance * zDistance > maxDistance * maxDistance)
            return false;
        return true;
    }
}
