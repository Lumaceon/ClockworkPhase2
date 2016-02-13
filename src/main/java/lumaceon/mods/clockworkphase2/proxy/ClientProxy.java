package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.client.ClientTickHandler;
import lumaceon.mods.clockworkphase2.client.gui.ButtonInitializer;
import lumaceon.mods.clockworkphase2.client.render.ModelRegistry;
import lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client.*;
import lumaceon.mods.clockworkphase2.client.keybind.KeyHandler;
import lumaceon.mods.clockworkphase2.client.keybind.Keybindings;
import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import lumaceon.mods.clockworkphase2.client.render.elements.world.WorldRenderElement;
import lumaceon.mods.clockworkphase2.client.render.elements.world.WorldRenderElementTDA;
import lumaceon.mods.clockworkphase2.client.render.elements.world.WorldRenderElementTemporalClock;
import lumaceon.mods.clockworkphase2.client.render.elements.world.WorldRenderElementTemporalDisplacementAltar;
import lumaceon.mods.clockworkphase2.client.render.sky.SkyRendererForthAge;
import lumaceon.mods.clockworkphase2.client.tesr.*;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTable;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidExporter;
import lumaceon.mods.clockworkphase2.timetravel.third.world.WorldProviderThirdAge;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;
import java.util.List;

public class ClientProxy extends CommonProxy
{
    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft() == null ? null : Minecraft.getMinecraft().theWorld;
    }

    @Override
    public void registerTESR()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAssemblyTable.class, new TESRAssemblyTable());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTimezoneFluidExporter.class, new TESRTimezoneFluidExporter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTemporalFurnace.class, new TESRTemporalFurnace());
    }

    @Override
    public void registerBlockModel(Block block, String unlocalizedName) {
        ModelRegistry.registerItemBlockModel(block, unlocalizedName);
    }

    @Override
    public void registerItemModel(Item item, String unlocalizedName) {
        ModelRegistry.registerItemModel(item, unlocalizedName);
    }

    @Override
    public void registerKeybindings() {
        ClientRegistry.registerKeyBinding(Keybindings.activate);
    }

    @Override
    public void initSideHandlers()
    {
        RenderHandler renderer = new RenderHandler();

        MinecraftForge.EVENT_BUS.register(renderer);
        FMLCommonHandler.instance().bus().register(renderer);
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());
        FMLCommonHandler.instance().bus().register(new KeyHandler());
    }

    @Override
    public void addWorldRenderer(World world, int x, int y, int z, int ID)
    {
        switch(ID)
        {
            case 0: //Temporal machine clock renderer.
                RenderHandler.registerWorldRenderElement(new WorldRenderElementTemporalClock(world, x, y, z));
                break;
            case 1: //Temporal displacement altar renderer.
                RenderHandler.registerWorldRenderElement(new WorldRenderElementTemporalDisplacementAltar(world, x, y, z));
                break;
            case 2://TDA Stargate style
                RenderHandler.registerWorldRenderElement(new WorldRenderElementTDA(world, x, y, z));
                break;
        }
    }

    @Override
    public void clearWorldRenderers(World world, int x, int y, int z)
    {
        for(int n = 0; n < RenderHandler.worldRenderList.size(); n++)
        {
            WorldRenderElement wre = RenderHandler.worldRenderList.get(n);
            if(wre.isFinished())
            {
                RenderHandler.worldRenderList.remove(n);
                --n;
            }
        }
    }

    @Override
    public IRenderHandler getSkyRendererForWorld(WorldProvider worldProvider) {
        if(worldProvider instanceof WorldProviderThirdAge)
            return new SkyRendererForthAge();
        return null;
    }

    @Override
    public void initializeButtonsViaProxy(int id, List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop)
    {
        switch (id)
        {
            case 0: //Mainspring
                ButtonInitializer.initializeMainspringButtons(buttonList, container, guiLeft, guiTop);
                break;
        }
    }

    @Override
    public File getMinecraftDataDirectory() {
        return Minecraft.getMinecraft().mcDataDir;
    }

    @Override
    public ClockworkNetworkContainer getClockworkNetworkGui(TileEntity te, int id)
    {
        switch(id)
        {
            case 0: //Clockwork Furnace
                return new GuiClockworkFurnaceClient(te, 168, 18);
            case 1: //Clockwork Brewery
                return new GuiClockworkBreweryClient(te, 80, 74);
            case 2: //Clockwork Mixer
                return new GuiClockworkMixerClient(te, 172, 80);
            case 3: //Clockwork Melter
                return new GuiClockworkMelterClient(te, 172, 80);
            case 4: //Clockwork Super Alloy Furnace
                return new GuiClockworkSuperAlloyFurnace(te, 200, 18);
            case 5: //Clockwork Alloy Furnace
                return new GuiClockworkAlloyFurnace(te, 100, 18);
            case 6: //Clockwork Crafting Table
                return new GuiClockworkCraftingTable(te, 100, 60);
        }
        return null;
    }
}
