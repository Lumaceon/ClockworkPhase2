package lumaceon.mods.clockworkphase2;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import lumaceon.mods.clockworkphase2.api.TemporalHarvestRegistry;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRegistry;
import lumaceon.mods.clockworkphase2.client.gui.GuiHandler;
import lumaceon.mods.clockworkphase2.config.ConfigurationHandler;
import lumaceon.mods.clockworkphase2.creativetab.CreativeTabClockworkPhase2;
import lumaceon.mods.clockworkphase2.handler.*;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModEntities;
import lumaceon.mods.clockworkphase2.init.ModFluids;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.proxy.IProxy;
import lumaceon.mods.clockworkphase2.recipe.Recipes;
import lumaceon.mods.clockworkphase2.worldgen.WorldGeneratorOres;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ClockworkPhase2
{
    @Mod.Instance(Reference.MOD_ID)
    public static ClockworkPhase2 instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static IProxy proxy;

    public final CreativeTabs CREATIVE_TAB = new CreativeTabClockworkPhase2("Clockwork Phase 2");

    public WorldGeneratorOres oreGenerator = new WorldGeneratorOres();

    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        ModFluids.init();

        ModBlocks.init();
        ModBlocks.initTE();

        GameRegistry.registerWorldGenerator(oreGenerator, 0);

        ModFluids.bindBlocks();

        ModItems.init();

        ModEntities.init();

        proxy.registerKeybindings();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event)
    {
        proxy.registerTESR();

        proxy.registerModels();

        Recipes.init();


        BucketHandler.INSTANCE.buckets.put(ModBlocks.timeSand, ModItems.bucketTimeSand);

        MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenHandler());
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
        MinecraftForge.EVENT_BUS.register(new WorldHandler());
        FMLCommonHandler.instance().bus().register(new TickHandler());
        proxy.initSideHandlers();

        new GuiHandler();

        PacketHandler.init();
    }

    @Mod.EventHandler
    public void postInitialize(FMLPostInitializationEvent event)
    {
        MainspringMetalRegistry.INTERNAL.initDefaults();
        TemporalHarvestRegistry.init();
        TimestreamCraftingRegistry.sortRecipesByTimeRequirement();
    }
}
