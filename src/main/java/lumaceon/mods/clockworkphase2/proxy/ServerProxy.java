package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.client.gui.GuiHandler;
import lumaceon.mods.clockworkphase2.handler.ChunkLoadingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.util.List;

public class ServerProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        new GuiHandler();
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
    public void registerBlockModel(Block block, String unlocalizedName) {}
    @Override
    public void registerItemModel(Item item, String unlocalizedName) {}
    @Override
    public void registerCustomModels() {}
    @Override
    public void registerFluidModels() {}
    @Override
    public void registerKeybindings() {}
    @Override
    public void initSideHandlers() {
        MinecraftForge.EVENT_BUS.register(new ChunkLoadingHandler());
        //MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenHandler());
    }

    @Override
    public void spawnParticle(int modParticleID, double x, double y, double z) {}
    @Override
    public void clearWorldRenderers(World world, int x, int y, int z) {}
    @Override
    public IRenderHandler getSkyRendererForWorld(WorldProvider worldProvider) { return null; }
    @Override
    public void initializeButtonsViaProxy(int id, List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop) {}
    @Override
    public File getMinecraftDataDirectory() { return null; }

    @Override
    public void sendBlockDestroyPacket(BlockPos pos) {

    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        //NOOP
    }
}
