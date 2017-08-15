package lumaceon.mods.clockworkphase2.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public class ModParticles
{
    private static final ArrayList<Particle> PARTICLES = new ArrayList<>(10000);
    private static Minecraft mc;

    public static void addParticle(Particle fx) {
        if(PARTICLES.size() >= 10000)
            PARTICLES.remove(0);
        PARTICLES.add(fx);
    }

    public static ArrayList<Particle> getParticleList() {
        return PARTICLES;
    }

    public static void updateParticleList()
    {
        for(int i = 0; i < PARTICLES.size(); i++) //For-each results in a ConcurrentModificationException
        {
            Particle particle = PARTICLES.get(i);
            particle.onUpdate();
        }
    }

    public static boolean canSpawnParticle(double x, double y, double z, double maxDistance)
    {
        if(mc == null)
            mc = Minecraft.getMinecraft();

        Entity renderViewEntity = mc.getRenderViewEntity();

        if(mc.world == null || renderViewEntity == null || mc.effectRenderer == null)
            return false;

        //Turns off these particles if particles are set to be minimal, and reduces them if needed.
        int userSettings = mc.gameSettings.particleSetting;
        if((userSettings == 1 && mc.world.rand.nextInt(3) == 0) || userSettings == 2)
            return false;
        //Calculates distance to the particle generation, and returns false if it's too far away.
        double xDistance = renderViewEntity.posX - x;
        double yDistance = renderViewEntity.posY - y;
        double zDistance = renderViewEntity.posZ - z;

        return xDistance * xDistance + yDistance * yDistance + zDistance * zDistance <= maxDistance * maxDistance;

    }
}
