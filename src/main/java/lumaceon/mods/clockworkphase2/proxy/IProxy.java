package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.inventory.ContainerAssemblyTableClient;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.File;
import java.util.List;

public interface IProxy
{
    public void preInit();
    public void init();
    public World getClientWorld();
    public EntityPlayer getClientPlayer();
    public IThreadListener getThreadListener(MessageContext context);
    public EntityPlayer getPlayer(MessageContext context);
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
    public void initializeButtonsViaProxy(int id, List buttonList, ContainerAssemblyTableClient container, int guiLeft, int guiTop);
    public File getMinecraftDataDirectory();
    public void sendBlockDestroyPacket(BlockPos pos);
    public void onClientTick(TickEvent.ClientTickEvent event);
}
