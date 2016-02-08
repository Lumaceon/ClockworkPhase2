package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;

import java.io.File;
import java.util.List;

public class ServerProxy extends CommonProxy
{
    @Override
    public World getClientWorld() { return null; }
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
    @Override
    public void initializeButtonsViaProxy(int id, List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop) {}
    @Override
    public File getMinecraftDataDirectory() { return null; }

    @Override
    public ClockworkNetworkContainer getClockworkNetworkGui(TileEntity te, int id)
    {
        switch(id)
        {
            case 0: //Clockwork Furnace
                return new ContainerCNFurnace(te, 168, 18);
            case 1: //Clockwork Brewery
                return new ContainerCNBrewery(te, 80, 76);
            case 2: //Clockwork Mixer
                return new ContainerCNMixer(te, 172, 80);
            case 3: //Clockwork Melter
                return new ContainerCNMelter(te, 172, 80);
            case 4: //Clockwork Super Alloy Furnace
                return new ContainerCNSuperAlloyFurnace(te, 200, 18);
            case 5: //Clockwork Alloy Furnace
                return new ContainerCNAlloyFurnace(te, 100, 18);
            case 6: //Clockwork Crafting Table
                return new ContainerCNCraftingTable(te, 100, 60);
        }
        return null;
    }
}
