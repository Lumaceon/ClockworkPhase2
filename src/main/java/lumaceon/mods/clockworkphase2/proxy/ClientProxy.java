package lumaceon.mods.clockworkphase2.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.client.particle.ParticleGenerator;
import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

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
        RenderHandler renderer = new RenderHandler();

        MinecraftForge.EVENT_BUS.register(renderer);
        FMLCommonHandler.instance().bus().register(renderer);
    }

    @Override
    public void initButtons(int id, List buttonList, IAssemblyContainer container, int guiLeft, int guiTop)
    {
        switch (id)
        {
            case 0: //Mainspring
                buttonList.add(new GuiButton(0, guiLeft + 50, guiTop + 50, 100, 20, "Add Metal"));
                break;
        }
    }
}
