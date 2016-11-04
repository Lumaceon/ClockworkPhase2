package lumaceon.mods.clockworkphase2.network;

import lumaceon.mods.clockworkphase2.network.message.MessageAchievementScore;
import lumaceon.mods.clockworkphase2.network.message.MessageCelestialCompassItemGet;
import lumaceon.mods.clockworkphase2.network.message.MessageParticleSpawn;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerAchivementScore;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerCelestialCompassItemGet;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerParticleSpawn;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandlerClient
{
    public static void init()
    {
        PacketHandler.INSTANCE.registerMessage(HandlerAchivementScore.class, MessageAchievementScore.class, PacketHandler.ACHIEVEMENT_SCORE_ID, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(HandlerCelestialCompassItemGet.class, MessageCelestialCompassItemGet.class, PacketHandler.CELESTIAL_COMPASS_ID, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(HandlerParticleSpawn.class, MessageParticleSpawn.class, PacketHandler.PARTICLE_SPAWN_ID, Side.CLIENT);
    }
}
