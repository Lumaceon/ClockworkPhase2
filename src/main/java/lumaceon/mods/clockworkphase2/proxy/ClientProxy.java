package lumaceon.mods.clockworkphase2.proxy;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.client.gui.ButtonInitializer;
import lumaceon.mods.clockworkphase2.client.gui.GuiHandler;
import lumaceon.mods.clockworkphase2.client.handler.TooltipModificationHandler;
import lumaceon.mods.clockworkphase2.client.particle.ModParticles;
import lumaceon.mods.clockworkphase2.client.particle.ParticleTimestream;
import lumaceon.mods.clockworkphase2.client.particle.ParticleTimezone;
import lumaceon.mods.clockworkphase2.client.render.ModelRegistry;
import lumaceon.mods.clockworkphase2.client.handler.keybind.KeyHandler;
import lumaceon.mods.clockworkphase2.client.handler.keybind.Keybindings;
import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import lumaceon.mods.clockworkphase2.client.tesr.*;
import lumaceon.mods.clockworkphase2.client.handler.MouseInputHandler;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTemporalToolbeltSwap;
import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import lumaceon.mods.clockworkphase2.tile.temporal.TileArmillaryRing;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalZoningMachine;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneModulator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ClientProxy extends CommonProxy
{
    public static int toolbeltRowSelectIndex = 0; //0 to (0 + rows)

    @Override
    public void preInit() {
        OBJLoader.INSTANCE.addDomain(Reference.MOD_ID);
        new GuiHandler();
        ModelRegistry.registerFluidModels(); //Must be called in pre-initialization phase.
    }

    @Override
    public void init()
    {

    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft() == null ? null : Minecraft.getMinecraft().world;
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft() == null ? null : Minecraft.getMinecraft().player;
    }

    @Override
    public IThreadListener getThreadListener(MessageContext context)
    {
        if(context.side.isClient())
        {
            return Minecraft.getMinecraft();
        }
        return null;
    }

    @Override
    public EntityPlayer getPlayer(MessageContext context)
    {
        if(context.side.isClient())
        {
            return Minecraft.getMinecraft().player;
        }

        return null;
    }

    @Override
    public void registerTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileArmillaryRing.class, new TESRArmillaryRing());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCelestialCompass.class, new TESRTimezoneController());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTemporalZoningMachine.class, new TESRTemporalZoningMachine());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTimezoneModulator.class, new TESRTimezoneModulator());
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
        ClientRegistry.registerKeyBinding(Keybindings.toolbelt);
    }

    @Override
    public void initSideHandlers()
    {
        RenderHandler renderer = new RenderHandler();

        MinecraftForge.EVENT_BUS.register(renderer);
        MinecraftForge.EVENT_BUS.register(renderer);
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
        MinecraftForge.EVENT_BUS.register(new MouseInputHandler());
        MinecraftForge.EVENT_BUS.register(new TooltipModificationHandler());
    }

    @Override
    public void spawnParticle(int modParticleID, double x, double y, double z)
    {
        switch(modParticleID)
        {
            case 0:
                if(ModParticles.canSpawnParticle(x, y, z, 20))
                {
                    ModParticles.addParticle(new ParticleTimestream(Minecraft.getMinecraft().world, x, y, z));
                }
                break;
            case 1:
                if(ModParticles.canSpawnParticle(x, y, z, 500))
                {
                    ModParticles.addParticle(new ParticleTimezone(Minecraft.getMinecraft().world, x, 254.59, z));
                }
                break;
        }

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

    @Override
    public void sendBlockDestroyPacket(BlockPos pos) {
        //noinspection ConstantConditions
        Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase.equals(TickEvent.Phase.END))
        {
            Minecraft mc = Minecraft.getMinecraft();

            //Toolbelt updates.
            if(toolbeltRowSelectIndex > 0 && !Keybindings.toolbelt.isKeyDown())
            {
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                if(player != null)
                {
                    ITemporalToolbeltHandler toolbelt = player.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
                    if(toolbelt != null && toolbeltRowSelectIndex <= toolbelt.getRowCount())
                    {
                        PacketHandler.INSTANCE.sendToServer(new MessageTemporalToolbeltSwap(toolbeltRowSelectIndex - 1));
                        swapHotbarsClientside(player);
                        toolbeltRowSelectIndex = 0;
                    }
                }
            }

            if(mc.player != null)
            {
                //Spawn particles for nearby timezone glyphs.
                double playerPosX = mc.player.posX;
                double playerPosZ = mc.player.posZ;
                for(Map.Entry<int[], ITimezone> entry : TimezoneHandler.timezones.entrySet())
                {
                    int[] pos = entry.getKey();
                    double disX = playerPosX - pos[0];
                    double disZ = playerPosZ - pos[2];

                    //Is within range and in the same dimension?
                    if(Math.sqrt(disX*disX + disZ*disZ) < 400 && pos[3] == mc.world.provider.getDimension())
                    {
                        this.spawnParticle(1, pos[0] + 0.5, 255, pos[2] + 0.5);
                    }
                }
            }

            //Update custom particles.
            ModParticles.updateParticleList();
        }
    }

    private void swapHotbarsClientside(EntityPlayer player)
    {
        ITemporalToolbeltHandler toolbelt = player.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
        if(toolbelt != null && toolbelt.getRowCount() > toolbeltRowSelectIndex - 1)
        {
            NonNullList<ItemStack> newHotbar = toolbelt.getRow(toolbeltRowSelectIndex - 1);
            NonNullList<ItemStack> currentHotbar = NonNullList.withSize(9, ItemStack.EMPTY);
            for(int i = 0; i < 9; i++)
            {
                currentHotbar.set(i, player.inventory.getStackInSlot(i));
            }

            for(int i = 0; i < 9; i++)
            {
                //player.inventory.setInventorySlotContents(i, newHotbar.get(i));
            }

            toolbelt.setRow(toolbeltRowSelectIndex - 1, currentHotbar);
        }
    }
}
