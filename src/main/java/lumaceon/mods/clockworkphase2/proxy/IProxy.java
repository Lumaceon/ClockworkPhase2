package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import net.minecraft.block.Block;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.util.List;

public interface IProxy
{
    public void preInit();
    public void init();
    public World getClientWorld();
    public void registerTESR();
    public void registerBlockModel(Block block, String unlocalizedName);
    public void registerItemModel(Item item, String unlocalizedName);
    public void registerCustomModels();
    public void registerFluidModels();
    public void registerKeybindings();
    public void initSideHandlers();
    public void spawnParticle(int modParticleID, double x, double y, double z);
    public void clearWorldRenderers(World world, int x, int y, int z);
    public IRenderHandler getSkyRendererForWorld(WorldProvider worldProvider);
    public void initializeButtonsViaProxy(int id, List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop);
    public File getMinecraftDataDirectory();
    public void onClientTick(TickEvent.ClientTickEvent event);
}
