package lumaceon.mods.clockworkphase2.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerTESR()
    {

    }

    @Override
    public void registerModels()
    {

    }

    @Override
    public void registerKeybindings()
    {

    }

    @Override
    public void initSideHandlers()
    {
        RenderHandler RH = new RenderHandler();

        MinecraftForge.EVENT_BUS.register(RH);
        FMLCommonHandler.instance().bus().register(RH);
    }
}
