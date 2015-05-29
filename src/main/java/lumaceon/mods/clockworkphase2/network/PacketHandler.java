package lumaceon.mods.clockworkphase2.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.message.MessageMainspringButton;
import lumaceon.mods.clockworkphase2.network.message.MessageStandardParticleSpawn;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerMainspringButton;
import lumaceon.mods.clockworkphase2.network.message.handler.HandlerStandardParticleSpawn;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static void init()
    {
        //Note: the side passed in is the RECEIVING side.
        int nextID = 0;
        INSTANCE.registerMessage(HandlerMainspringButton.class, MessageMainspringButton.class, nextID, Side.SERVER);
        nextID++;
        INSTANCE.registerMessage(HandlerStandardParticleSpawn.class, MessageStandardParticleSpawn.class, nextID, Side.CLIENT);
    }
}
