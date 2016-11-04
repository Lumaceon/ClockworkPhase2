package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;

import java.io.File;
import java.util.List;

public class ServerProxy extends CommonProxy
{
    @Override
    public void preInit()
    {

    }

    @Override
    public void init()
    {

    }

    @Override
    public World getClientWorld() { return null; }
    @Override
    public void registerTESR() {}
    @Override
    public void registerBlockModel(Block block, String unlocalizedName, boolean isCustomModel) {}
    @Override
    public void registerItemModel(Item item, String unlocalizedName) {}
    @Override
    public void registerCustomModels() {}
    @Override
    public void registerFluidModels() {}
    @Override
    public void registerKeybindings() {}
    @Override
    public void initSideHandlers() {}
    @Override
    public void clearWorldRenderers(World world, int x, int y, int z) {}
    @Override
    public IRenderHandler getSkyRendererForWorld(WorldProvider worldProvider) { return null; }
    @Override
    public void initializeButtonsViaProxy(int id, List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop) {}
    @Override
    public File getMinecraftDataDirectory() { return null; }
}
