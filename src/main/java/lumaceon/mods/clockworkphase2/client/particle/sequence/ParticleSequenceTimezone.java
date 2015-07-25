package lumaceon.mods.clockworkphase2.client.particle.sequence;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.client.particle.ParticleSpawn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;

import java.util.Random;

public class ParticleSequenceTimezone extends ParticleSequence
{
    public Random rand = new Random();
    public ITimezone timezone;
    public EntityFX entityFX;

    public ParticleSequenceTimezone(ITimezone timezone, double x, double y, double z) {
        super(x, y, z);
        this.timezone = timezone;
    }

    @Override
    public boolean update()
    {
        if(timezone == null)
            return false;
        int area = 128;
        for(int n = 0; n < 20; n++)
            ParticleSpawn.spawnDustParticle(Minecraft.getMinecraft().theWorld, xPos - area + rand.nextInt(area * 2) + rand.nextFloat(), rand.nextInt(190) + 10, zPos - area + rand.nextInt(area * 2) + rand.nextFloat(), 1.5F, 1.0F, 1.0F, 1.0F);
        return true;
    }
}
