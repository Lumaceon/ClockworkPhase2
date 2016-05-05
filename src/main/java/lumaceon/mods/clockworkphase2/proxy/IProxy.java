package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;

import java.io.File;
import java.util.List;

public interface IProxy
{
    public World getClientWorld();
    public void registerTESR();
    public void registerBlockModel(Block block, String unlocalizedName);
    public void registerItemModel(Item item, String unlocalizedName);
    public void registerFluidModels();
    public void registerKeybindings();
    public void initSideHandlers();
    public void clearWorldRenderers(World world, int x, int y, int z);
    public IRenderHandler getSkyRendererForWorld(WorldProvider worldProvider);
    public void initializeButtonsViaProxy(int id, List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop);
    public File getMinecraftDataDirectory();
    public ClockworkNetworkContainer getClockworkNetworkGui(TileEntity te, int id);
    public ClockworkNetworkContainer getClockworkNetworkItemStorage(TileEntity te, int xSlots, int ySlots);
}
