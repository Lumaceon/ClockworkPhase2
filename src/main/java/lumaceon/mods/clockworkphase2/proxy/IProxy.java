package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;

import java.io.File;
import java.util.List;

public interface IProxy
{
    public World getClientWorld();
    public void registerTESR();
    public void registerModels();
    public void registerKeybindings();
    public void initSideHandlers();
    public void addWorldRenderer(World world, int x, int y, int z, int ID);
    public void clearWorldRenderers(World world, int x, int y, int z);
    public IRenderHandler getSkyRendererForWorld(WorldProvider worldProvider);
    public void initializeButtonsViaProxy(int id, List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop);
    public File getMinecraftDataDirectory();
}
