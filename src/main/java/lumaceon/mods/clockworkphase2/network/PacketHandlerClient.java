package lumaceon.mods.clockworkphase2.network;

import lumaceon.mods.clockworkphase2.network.message.MessageCelestialCompassItemGet;
import lumaceon.mods.clockworkphase2.network.message.MessageParticleSpawn;
import lumaceon.mods.clockworkphase2.network.message.MessagePlayerDataOnWorldJoin;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerCelestialCompassItemGet;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerParticleSpawn;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerPlayerDataOnWorldJoin;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandlerClient
{
    public static void init()
    {
        PacketHandler.INSTANCE.registerMessage(HandlerCelestialCompassItemGet.class, MessageCelestialCompassItemGet.class, PacketHandler.CELESTIAL_COMPASS_ID, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(HandlerParticleSpawn.class, MessageParticleSpawn.class, PacketHandler.PARTICLE_SPAWN_ID, Side.CLIENT);
        PacketHandler.INSTANCE.registerMessage(HandlerPlayerDataOnWorldJoin.class, MessagePlayerDataOnWorldJoin.class, PacketHandler.PLAYER_DATA_CLIENT_UPDATE, Side.CLIENT);
    }
}
