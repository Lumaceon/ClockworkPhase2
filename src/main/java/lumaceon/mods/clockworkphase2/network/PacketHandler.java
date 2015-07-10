package lumaceon.mods.clockworkphase2.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.message.*;
import lumaceon.mods.clockworkphase2.network.message.handler.*;

public class PacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static void init()
    {
        //Note: the side passed in is the RECEIVING side.
        int nextID = 0;
        INSTANCE.registerMessage(HandlerStandardParticleSpawn.class, MessageStandardParticleSpawn.class, nextID, Side.CLIENT);
        nextID++;
        INSTANCE.registerMessage(HandlerTileStateChange.class, MessageTileStateChange.class, nextID, Side.CLIENT);
        nextID++;
        INSTANCE.registerMessage(HandlerTemporalInfluence.class, MessageTemporalInfluence.class, nextID, Side.CLIENT);
        nextID++;
        INSTANCE.registerMessage(HandlerLightningSwordActivate.class, MessageLightningSwordActivate.class, nextID, Side.SERVER);
        nextID++;
        INSTANCE.registerMessage(HandlerTimestreamRecipe.class, MessageTimestreamRecipe.class, nextID, Side.SERVER);
    }
}
