package lumaceon.mods.clockworkphase2.network;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.message.*;
import lumaceon.mods.clockworkphase2.network.message.handler.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
    private static int messageID = 1;

    public static void init()
    {
        //Note: the side passed in is the RECEIVING side.
        registerMessage(HandlerParticleSpawn.class, MessageParticleSpawn.class, Side.CLIENT);
        registerMessage(HandlerMainspringButton.class, MessageMainspringButton.class, Side.SERVER);
        registerMessage(HandlerToolUpgradeActivate.class, MessageToolUpgradeActivate.class, Side.SERVER);
        registerMessage(HandlerTileMachineConfiguration.class, MessageTileMachineConfiguration.class, Side.SERVER);
        registerMessage(HandlerTileMachineConfigurationTank.class, MessageTileMachineConfigurationTank.class, Side.SERVER);
        registerMessage(HandlerMachineModeActivate.class, MessageMachineModeActivate.class, Side.SERVER);
        registerMessage(HandlerTemporalToolbeltSwap.class, MessageTemporalToolbeltSwap.class, Side.SERVER);
        registerMessage(HandlerPlayerDataOnWorldJoin.class, MessagePlayerDataOnWorldJoin.class, Side.CLIENT);
        registerMessage(HandlerEntityConstructorSetRecipe.class, MessageEntityConstructorSetRecipe.class, Side.SERVER);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        INSTANCE.registerMessage(messageHandler, requestMessageType, messageID++, side);
    }
}
