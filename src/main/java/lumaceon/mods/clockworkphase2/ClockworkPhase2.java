package lumaceon.mods.clockworkphase2;

import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import lumaceon.mods.clockworkphase2.api.block.CustomProperties;
import lumaceon.mods.clockworkphase2.config.ConfigurationHandler;
import lumaceon.mods.clockworkphase2.creativetab.CreativeTabClockworkPhase2;
import lumaceon.mods.clockworkphase2.handler.*;
import lumaceon.mods.clockworkphase2.init.*;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.proxy.IProxy;
import lumaceon.mods.clockworkphase2.recipe.Recipes;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import lumaceon.mods.clockworkphase2.world.gen.WorldGeneratorOres;
import lumaceon.mods.clockworkphase2.world.schematic.SchematicHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ClockworkPhase2
{
    public static Random random = new Random(System.nanoTime());
    public static File sourceFile;
    public static Logger logger = LogManager.getLogManager().getLogger(Reference.MOD_NAME);

    @Mod.Instance(Reference.MOD_ID)
    public static ClockworkPhase2 instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static IProxy proxy;

    public final CreativeTabs CREATIVE_TAB = new CreativeTabClockworkPhase2("ClockworkPhase2");

    public WorldGeneratorOres oreGenerator = new WorldGeneratorOres();

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event)
    {
        LogHelper.logger = event.getModLog();

        Reference.MOD_DIRECTORY = event.getSourceFile();
        ConfigurationHandler.init(event.getModConfigurationDirectory());

        SchematicHandler.INSTANCE.setModResourceLocation(event.getSourceFile(), Reference.MOD_ID);
        SchematicHandler.INSTANCE.setMinecraftDirectory(proxy.getMinecraftDataDirectory());

        ModCapabilities.init();
        CustomProperties.init();

        ModItems.init();
        ModBlocks.init();
        ModBlocks.initTE();
        ModFluids.registerFluidContainers();
        proxy.registerCustomModels();

        ModBiomes.init();

        GameRegistry.registerWorldGenerator(oreGenerator, 0);

        proxy.registerFluidModels();
        proxy.registerKeybindings();
        sourceFile = event.getSourceFile();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        ModItems.initModels();
        ModBlocks.initModels();

        proxy.registerTESR();

        Recipes.init();

        ModEntities.init();

        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
        MinecraftForge.EVENT_BUS.register(new WorldHandler());
        MinecraftForge.EVENT_BUS.register(new TickHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilityAttachHandler());
        WorldGenHandler wgh = new WorldGenHandler();
        MinecraftForge.TERRAIN_GEN_BUS.register(wgh);
        MinecraftForge.EVENT_BUS.register(wgh);
        MinecraftForge.ORE_GEN_BUS.register(wgh);
        proxy.initSideHandlers();
        if(event.getSide() == Side.SERVER)
            ForgeChunkManager.setForcedChunkLoadingCallback(ClockworkPhase2.instance, new ChunkLoadingHandler.CP2ChunkLoadingCallback());

        PacketHandler.init();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInitialize(FMLPostInitializationEvent event)
    {
        MainspringMetalRegistry.INTERNAL.initDefaults();
        Recipes.initAlloyRecipes();
        Recipes.initCrusherRecipes();
    }
}