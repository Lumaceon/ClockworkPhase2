package lumaceon.mods.clockworkphase2.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.client.ClientTickHandler;
import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import lumaceon.mods.clockworkphase2.client.tesr.TESRAssemblyTable;
import lumaceon.mods.clockworkphase2.client.tesr.TESRAssemblyTableSB;
import lumaceon.mods.clockworkphase2.client.tesr.TESRTelescope;
import lumaceon.mods.clockworkphase2.client.tesr.TESRTimezoneFluidExporter;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTable;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTableSB;
import lumaceon.mods.clockworkphase2.tile.TileTelescope;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTimezoneFluidExporter;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerTESR()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAssemblyTable.class, new TESRAssemblyTable());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAssemblyTableSB.class, new TESRAssemblyTableSB());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTimezoneFluidExporter.class, new TESRTimezoneFluidExporter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTelescope.class, new TESRTelescope());
    }

    @Override
    public void registerModels() {}

    @Override
    public void registerKeybindings() {}

    @Override
    public void initSideHandlers()
    {
        RenderHandler renderer = new RenderHandler();

        MinecraftForge.EVENT_BUS.register(renderer);
        FMLCommonHandler.instance().bus().register(renderer);
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());
    }
}
