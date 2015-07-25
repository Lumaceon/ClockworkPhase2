package lumaceon.mods.clockworkphase2.client.particle.sequence;

import net.minecraft.client.particle.EntityFX;

import java.util.*;

public class ParticleSequence
{
    private static ArrayList<ParticleSequence> sequences = new ArrayList<ParticleSequence>();
    public static ArrayList<EntityFX> particles = new ArrayList<EntityFX>(8000);

    public static ParticleSequence spawnParticleSequence(ParticleSequence sequence) {
        if(sequence != null)
            sequences.add(sequence);
        return sequence;
    }

    public static void addParticle(EntityFX fx) {
        if(particles.size() >= 8000)
            particles.remove(0);
        particles.add(fx);
    }

    //TODO - only update when not paused.
    public static void updateSequences()
    {
        for(int n = 0; n < sequences.size(); n++)
        {
            ParticleSequence sequence = sequences.get(n);
            if(sequence != null)
            {
                if(!sequence.update())
                {
                    sequences.remove(n);
                    --n;
                }
            }
        }
    }


    public double xPos, yPos, zPos;

    public ParticleSequence(double x, double y, double z)
    {
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }

    /**
     * Updates this sequence, usually spawning more particles.
     * @return True if this has more to do, false to remove it from the sequence list.
     */
    public boolean update() {
        return false;
    }

    /*public EntityFX spawnParticle(EntityFX particle, double cutoff)
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
    }*/
}
