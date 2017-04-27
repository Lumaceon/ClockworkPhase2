package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.client.gui.ButtonInitializer;
import lumaceon.mods.clockworkphase2.client.gui.GuiHandler;
import lumaceon.mods.clockworkphase2.client.model.CustomModelLoaderClockworkPhase;
import lumaceon.mods.clockworkphase2.client.render.ModelRegistry;
import lumaceon.mods.clockworkphase2.client.keybind.KeyHandler;
import lumaceon.mods.clockworkphase2.client.keybind.Keybindings;
import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import lumaceon.mods.clockworkphase2.client.tesr.*;
import lumaceon.mods.clockworkphase2.handler.InputHandler;
import lumaceon.mods.clockworkphase2.handler.ModelBakeHandler;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandlerClient;
import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.io.File;
import java.util.List;

public class ClientProxy extends CommonProxy
{
    public static ModelResourceLocation LEXICON_MODEL;
    public static ModelResourceLocation MULTITOOL_MODEL;

    @Override
    public void preInit() {
        OBJLoader.INSTANCE.addDomain(Reference.MOD_ID);
        ModelLoaderRegistry.registerLoader(new CustomModelLoaderClockworkPhase());
        new GuiHandler();
    }

    @Override
    public void init()
    {
        PacketHandlerClient.init();
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft() == null ? null : Minecraft.getMinecraft().theWorld;
    }

    @Override
    public void registerTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileCelestialCompass.class, new TESRTimezoneController());
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
    public void registerCustomModels() {
    }

    @Override
    public void registerFluidModels() {
        //ModelRegistry.registerFluidModel(ModBlocks.liquidTemporium.getBlock(), "temporium");
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
        MinecraftForge.EVENT_BUS.register(renderer);
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
        MinecraftForge.EVENT_BUS.register(new InputHandler());
        MinecraftForge.EVENT_BUS.register(new ModelBakeHandler());
    }

    @Override
    public void clearWorldRenderers(World world, int x, int y, int z) {

    }

    @Override
    public IRenderHandler getSkyRendererForWorld(WorldProvider worldProvider) {
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
}
