package lumaceon.mods.clockworkphase2.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import lumaceon.mods.clockworkphase2.api.phase.Phase;
import lumaceon.mods.clockworkphase2.api.phase.Phases;
import lumaceon.mods.clockworkphase2.client.particle.sequence.ParticleSequence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;

public class ClientTickHandler
{
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(!Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().theWorld != null)
        {
            ParticleSequence.updateSequences();
            for(EntityFX fx : ParticleSequence.particles)
                fx.onUpdate();

            Phase[] phases = Phases.getPhases(Minecraft.getMinecraft().theWorld);
            for(Phase phase : phases)
                phase.update(Side.CLIENT);
        }
    }
}
