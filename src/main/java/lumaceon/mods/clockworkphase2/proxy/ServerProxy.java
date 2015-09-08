package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;

import java.util.List;

public class ServerProxy extends CommonProxy
{
    @Override
    public void registerTESR() {}
    @Override
    public void registerModels() {}
    @Override
    public void registerKeybindings() {}
    @Override
    public void initSideHandlers() {}
    @Override
    public void addWorldRenderer(World world, int x, int y, int z, int ID) {}
    @Override
    public void clearWorldRenderers(World world, int x, int y, int z) {}
    @Override
    public IRenderHandler getSkyRendererForWorld(WorldProvider worldProvider) { return null; }
}
