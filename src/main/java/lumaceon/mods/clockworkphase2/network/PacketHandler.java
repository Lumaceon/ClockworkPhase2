package lumaceon.mods.clockworkphase2.network;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.message.*;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerMainspringButton;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerToolUpgradeActivate;
import lumaceon.mods.clockworkphase2.network.message.handler.dummy.DummyHandlerCelestialCompassItemGet;
import lumaceon.mods.clockworkphase2.network.message.handler.dummy.DummyHandlerParticleSpawn;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
    public static final int ACHIEVEMENT_SCORE_ID = 50;
    public static final int CELESTIAL_COMPASS_ID = 51;
    public static final int PARTICLE_SPAWN_ID = 52;
    public static final int MAINSPRING_BUTTON_ID = 53;
    public static final int TOOL_UPGRADE_ACTIVATE_ID = 54;

    public static void init()
    {
        //Note: the side passed in is the RECEIVING side.
        INSTANCE.registerMessage(DummyHandlerCelestialCompassItemGet.class, MessageCelestialCompassItemGet.class, CELESTIAL_COMPASS_ID, Side.SERVER);
        INSTANCE.registerMessage(DummyHandlerParticleSpawn.class, MessageParticleSpawn.class, PARTICLE_SPAWN_ID, Side.SERVER);
        INSTANCE.registerMessage(HandlerMainspringButton.class, MessageMainspringButton.class, MAINSPRING_BUTTON_ID, Side.SERVER);
        INSTANCE.registerMessage(HandlerToolUpgradeActivate.class, MessageToolUpgradeActivate.class, TOOL_UPGRADE_ACTIVATE_ID, Side.SERVER);
    }
}
