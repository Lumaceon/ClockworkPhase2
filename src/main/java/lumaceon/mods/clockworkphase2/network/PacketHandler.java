package lumaceon.mods.clockworkphase2.network;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.message.*;
import lumaceon.mods.clockworkphase2.network.message.handler.*;
import lumaceon.mods.clockworkphase2.network.message.handler.dummy.DummyHandlerCelestialCompassItemGet;
import lumaceon.mods.clockworkphase2.network.message.handler.dummy.DummyHandlerParticleSpawn;
import lumaceon.mods.clockworkphase2.network.message.handler.dummy.DummyHandlerPlayerDataOnWorldJoin;
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
    public static final int TILE_MACHINE_CONFIGURATION = 55;
    public static final int TILE_MACHINE_CONFIGURATION_TANK = 56;
    public static final int MACHINE_MODE_ACTIVATE = 57;
    public static final int TEMPORAL_TOOLBELT_SWAP = 58;
    public static final int PLAYER_DATA_CLIENT_UPDATE = 59;

    public static void init()
    {
        //Note: the side passed in is the RECEIVING side.
        INSTANCE.registerMessage(DummyHandlerCelestialCompassItemGet.class, MessageCelestialCompassItemGet.class, CELESTIAL_COMPASS_ID, Side.SERVER);
        INSTANCE.registerMessage(DummyHandlerParticleSpawn.class, MessageParticleSpawn.class, PARTICLE_SPAWN_ID, Side.SERVER);
        INSTANCE.registerMessage(HandlerMainspringButton.class, MessageMainspringButton.class, MAINSPRING_BUTTON_ID, Side.SERVER);
        INSTANCE.registerMessage(HandlerToolUpgradeActivate.class, MessageToolUpgradeActivate.class, TOOL_UPGRADE_ACTIVATE_ID, Side.SERVER);
        INSTANCE.registerMessage(HandlerTileMachineConfiguration.class, MessageTileMachineConfiguration.class, TILE_MACHINE_CONFIGURATION, Side.SERVER);
        INSTANCE.registerMessage(HandlerTileMachineConfigurationTank.class, MessageTileMachineConfigurationTank.class, TILE_MACHINE_CONFIGURATION_TANK, Side.SERVER);
        INSTANCE.registerMessage(HandlerMachineModeActivate.class, MessageMachineModeActivate.class, MACHINE_MODE_ACTIVATE, Side.SERVER);
        INSTANCE.registerMessage(HandlerTemporalToolbeltSwap.class, MessageTemporalToolbeltSwap.class, TEMPORAL_TOOLBELT_SWAP, Side.SERVER);
        INSTANCE.registerMessage(DummyHandlerPlayerDataOnWorldJoin.class, MessagePlayerDataOnWorldJoin.class, PLAYER_DATA_CLIENT_UPDATE, Side.SERVER);
    }
}
