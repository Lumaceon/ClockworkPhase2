package lumaceon.mods.clockworkphase2;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import lumaceon.mods.clockworkphase2.api.TemporalAchievementList;
import lumaceon.mods.clockworkphase2.api.TemporalHarvestRegistry;
import lumaceon.mods.clockworkphase2.client.gui.GuiHandler;
import lumaceon.mods.clockworkphase2.config.ConfigurationHandler;
import lumaceon.mods.clockworkphase2.creativetab.CreativeTabClockworkPhase2;
import lumaceon.mods.clockworkphase2.handler.*;
import lumaceon.mods.clockworkphase2.init.*;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.proxy.IProxy;
import lumaceon.mods.clockworkphase2.recipe.Recipes;
import lumaceon.mods.clockworkphase2.util.Logger;
import lumaceon.mods.clockworkphase2.util.SchematicUtility;
import lumaceon.mods.clockworkphase2.world.gen.WorldGeneratorOres;
import lumaceon.mods.clockworkphase2.world.gen.WorldGeneratorRuins;
import lumaceon.mods.clockworkphase2.world.provider.firstage.WorldProviderFirstAge;
import lumaceon.mods.clockworkphase2.world.provider.forthage.WorldProviderForthAge;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ClockworkPhase2
{
    @Mod.Instance(Reference.MOD_ID)
    public static ClockworkPhase2 instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static IProxy proxy;

    public final CreativeTabs CREATIVE_TAB = new CreativeTabClockworkPhase2("ClockworkPhase2");

    public WorldGeneratorOres oreGenerator = new WorldGeneratorOres();
    public WorldGeneratorRuins ruinGenerator = new WorldGeneratorRuins();

    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        SchematicUtility.INSTANCE.setModResourceLocation(event.getSourceFile(), Reference.MOD_ID);
        SchematicUtility.INSTANCE.setMinecraftDirectory(proxy.getMinecraftDataDirectory());
        SchematicUtility.INSTANCE.loadModSchematic("NewSchematic", false);

        ModFluids.init();

        ModItems.init();
        ModBlocks.init();
        ModBlocks.initTE();

        GameRegistry.registerWorldGenerator(oreGenerator, 0);
        GameRegistry.registerWorldGenerator(ruinGenerator, 0);

        ModFluids.bindBlocks();

        ModPhases.init();

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

        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerHandler());
        MinecraftForge.EVENT_BUS.register(new WorldHandler());
        MinecraftForge.EVENT_BUS.register(new AchievementHandler());
        FMLCommonHandler.instance().bus().register(new TickHandler());
        proxy.initSideHandlers();

        new GuiHandler();

        PacketHandler.init();

        //---- Age Registration ----\\
        Defaults.DIM_ID.FORTH_AGE = DimensionManager.getNextFreeDimId();
        DimensionManager.registerProviderType(Defaults.DIM_ID.FORTH_AGE, WorldProviderForthAge.class, false);
        DimensionManager.registerDimension(Defaults.DIM_ID.FORTH_AGE, Defaults.DIM_ID.FORTH_AGE);

        Defaults.DIM_ID.FIRST_AGE = DimensionManager.getNextFreeDimId();
        DimensionManager.registerProviderType(Defaults.DIM_ID.FIRST_AGE, WorldProviderFirstAge.class, false);
        DimensionManager.registerDimension(Defaults.DIM_ID.FIRST_AGE, Defaults.DIM_ID.FIRST_AGE);
        //---- Age Registration ----\\

        ModRuins.init();
    }

    @Mod.EventHandler
    public void postInitialize(FMLPostInitializationEvent event)
    {
        MainspringMetalRegistry.INTERNAL.initDefaults();
        TemporalHarvestRegistry.init();

        long value;
        Logger.info("||Detecting Individual Achievements||");
        for(Object achievement : AchievementList.achievementList)
        {
            if(achievement != null && achievement instanceof Achievement)
            {
                value = TemporalAchievementList.INTERNAL.registerAchievement((Achievement) achievement);
                if(value <= 0)
                    Logger.info("{SKIPPED achievement '" + ((Achievement) achievement).statId + "'}");
                else if(((Achievement) achievement).getSpecial())
                    Logger.info("{Registered achievement \'" + ((Achievement) achievement).statId + "\' with a value of " + value + "} [Adds to special multiplier]");
                else
                    Logger.info("{Registered achievement \'" + ((Achievement) achievement).statId + "\' with a value of " + value + "}");
            }
        }
        Logger.info("Total Temporal Influence From Achievements: " + TemporalAchievementList.INTERNAL.totalWeight);
        Logger.info("");
        Logger.info("||Detecting Pages||");
        for(AchievementPage page : AchievementPage.getAchievementPages())
        {
            int pageValue = TemporalAchievementList.INTERNAL.registerPage(page);
            if(pageValue > 0)
                Logger.info("Page Found - " + page.getName() + " [Completionist Multiplier Bonus: x" + pageValue + "]");
        }
        Logger.info("Maximum Multiplier From Completionist Bonuses: " + TemporalAchievementList.INTERNAL.maxPageMultiplier);
        Logger.info("");
        Logger.info("||Calculating Special Achievement Exponential Increment Rate||");
        TemporalAchievementList.INTERNAL.setupSpecialMultiplier();
        Logger.info("Found " + TemporalAchievementList.INTERNAL.specialAchievementCount + " special achievements, exponential increment set to " + TemporalAchievementList.INTERNAL.specialAchievementMultiplierExponent);
        Logger.info("Maximum Special Achievement Multiplier: " + (long) Math.pow(TemporalAchievementList.INTERNAL.specialAchievementCount + 1, TemporalAchievementList.INTERNAL.specialAchievementMultiplierExponent));
        for(int n = 1; n <= TemporalAchievementList.INTERNAL.specialAchievementCount + 1; n++)
        {
            Logger.info((n - 1) + " Special Achievement(s): x" + (long) Math.pow(n, TemporalAchievementList.INTERNAL.specialAchievementMultiplierExponent));
        }
    }
}