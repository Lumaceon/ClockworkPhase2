package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.block.*;
import lumaceon.mods.clockworkphase2.clockworknetwork.InitializerClockworkNetwork;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.tile.*;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidExporter;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidImporter;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeCollector;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeWell;
import lumaceon.mods.clockworkphase2.timetravel.third.InitializerThirdAge;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks
{
    public static void init()
    {
        InitializerClockworkNetwork.initBlocks();

        initTimeMachines();
        initFluids();
        initOres();
        initPlants();
        initMetalBlocks();
        initMisc();

        InitializerThirdAge.initBlocks();
    }

    public static Block celestialCompass;
    public static Block celestialCompassSB;
    public static Block temporalDisplacementAltar;
    public static Block temporalDisplacementAltarSB;
    public static Block tda;
    public static Block tdaSB;
    public static Block timezoneFluidExporter;
    public static Block timezoneFluidImporter;
    public static Block timeCollector;
    public static Block temporalFurnace;
    public static Block timeWell;
    public static void initTimeMachines()
    {
        celestialCompass = new BlockCelestialCompass(Material.iron, Names.BLOCK.CELESTIAL_COMPASS);
        celestialCompassSB = new BlockCelestialCompassSB(Material.iron, Names.BLOCK.CELESTIAL_COMPASS_SB);
        temporalDisplacementAltar = new BlockTemporalDisplacementAltar(Material.iron, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR);
        temporalDisplacementAltarSB = new BlockTemporalDisplacementAltarSB(Material.iron, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR_SB);
        tda = new BlockTDA(Material.iron, Names.BLOCK.TDA);
        tdaSB = new BlockTDASB(Material.iron, Names.BLOCK.TDA_SB);
        timezoneFluidExporter = new BlockTimezoneFluidExporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        timezoneFluidImporter = new BlockTimezoneFluidImporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
        timeCollector = new BlockTimeCollector(Material.iron, Names.BLOCK.TIME_COLLECTOR);
        temporalFurnace = new BlockTemporalFurnace(Material.iron, Names.BLOCK.TEMPORAL_FURNACE);
        timeWell = new BlockTimeWell(Material.iron, Names.BLOCK.TIME_WELL);

        GameRegistry.registerBlock(celestialCompass, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerBlock(celestialCompassSB, Names.BLOCK.CELESTIAL_COMPASS_SB);
        GameRegistry.registerBlock(temporalDisplacementAltar, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR);
        GameRegistry.registerBlock(temporalDisplacementAltarSB, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR_SB);
        GameRegistry.registerBlock(tda, Names.BLOCK.TDA);
        GameRegistry.registerBlock(tdaSB, Names.BLOCK.TDA_SB);
        GameRegistry.registerBlock(timezoneFluidExporter, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        GameRegistry.registerBlock(timezoneFluidImporter, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
        GameRegistry.registerBlock(timeCollector, Names.BLOCK.TIME_COLLECTOR);
        GameRegistry.registerBlock(temporalFurnace, Names.BLOCK.TEMPORAL_FURNACE);
        GameRegistry.registerBlock(timeWell, Names.BLOCK.TIME_WELL);
    }

    public static Block timeSand;
    public static void initFluids()
    {
        timeSand = new BlockTimeSand(ModFluids.timeSand, Material.water, Names.BLOCK.TIME_SAND);
        GameRegistry.registerBlock(timeSand, Names.BLOCK.TIME_SAND);
    }

    public static Block oreCopper;
    public static Block oreZinc;
    public static Block fossilForthAge;
    public static Block fossilThirdAge;
    public static Block fossilSecondAge;
    public static Block fossilFirstAge;
    public static void initOres()
    {
        oreCopper = new BlockClockworkPhaseOre(Material.rock, 1, Names.BLOCK.ORE_COPPER);
        oreZinc = new BlockClockworkPhaseOre(Material.rock, 1, Names.BLOCK.ORE_ZINC);
        fossilForthAge = new BlockClockworkPhaseFossil(Material.rock, 0, Names.BLOCK.FOSSIL_FORTH_AGE);
        fossilThirdAge = new BlockClockworkPhaseFossil(Material.rock, 1, Names.BLOCK.FOSSIL_THIRD_AGE);
        fossilSecondAge = new BlockClockworkPhaseFossil(Material.rock, 2, Names.BLOCK.FOSSIL_SECOND_AGE);
        fossilFirstAge = new BlockClockworkPhaseFossil(Material.rock, 3, Names.BLOCK.FOSSIL_FIRST_AGE);

        GameRegistry.registerBlock(oreCopper, Names.BLOCK.ORE_COPPER);
        GameRegistry.registerBlock(oreZinc, Names.BLOCK.ORE_ZINC);
        GameRegistry.registerBlock(fossilForthAge, Names.BLOCK.FOSSIL_FORTH_AGE);
        GameRegistry.registerBlock(fossilThirdAge, Names.BLOCK.FOSSIL_THIRD_AGE);
        GameRegistry.registerBlock(fossilSecondAge, Names.BLOCK.FOSSIL_SECOND_AGE);
        GameRegistry.registerBlock(fossilFirstAge, Names.BLOCK.FOSSIL_FIRST_AGE);

        OreDictionary.registerOre("oreCopper", oreCopper);
        OreDictionary.registerOre("oreZinc", oreZinc);
        OreDictionary.registerOre("fossil4", fossilForthAge);
        OreDictionary.registerOre("fossil3", fossilThirdAge);
        OreDictionary.registerOre("fossil2", fossilSecondAge);
        OreDictionary.registerOre("fossil1", fossilFirstAge);
    }

    public static Block moonFlower;
    public static void initPlants()
    {
        moonFlower = new BlockMoonFlower(Material.plants, Names.BLOCK.MOON_FLOWER);

        GameRegistry.registerBlock(moonFlower, Names.BLOCK.MOON_FLOWER);
    }

    public static Block blockCopper;
    public static Block blockZinc;
    public static Block blockBrass;
    public static Block blockTemporal;
    public static void initMetalBlocks()
    {
        blockCopper = new BlockClockworkPhase(Material.iron, Names.BLOCK.BLOCK_COPPER);
        blockZinc = new BlockClockworkPhase(Material.iron, Names.BLOCK.BLOCK_ZINC);
        blockBrass = new BlockClockworkPhase(Material.iron, Names.BLOCK.BLOCK_BRASS);
        blockTemporal = new BlockClockworkPhase(Material.iron, Names.BLOCK.BLOCK_TEMPORAL);

        GameRegistry.registerBlock(blockCopper, Names.BLOCK.BLOCK_COPPER);
        GameRegistry.registerBlock(blockZinc, Names.BLOCK.BLOCK_ZINC);
        GameRegistry.registerBlock(blockBrass, Names.BLOCK.BLOCK_BRASS);
        GameRegistry.registerBlock(blockTemporal, Names.BLOCK.BLOCK_TEMPORAL);

        OreDictionary.registerOre("blockCopper", blockCopper);
        OreDictionary.registerOre("blockZinc", blockZinc);
        OreDictionary.registerOre("blockBrass", blockBrass);
        OreDictionary.registerOre("blockTemporal", blockTemporal);
    }

    public static Block basicWindingBox;
    public static Block assemblyTable;
    public static Block assemblyTableSB;
    public static void initMisc()
    {
        basicWindingBox = new BlockBasicWindingBox(Material.iron, Names.BLOCK.BASIC_WINDING_BOX);
        assemblyTable = new BlockAssemblyTable(Material.wood, Names.BLOCK.ASSEMBLY_TABLE);
        assemblyTableSB = new BlockAssemblyTableSB(Material.wood, Names.BLOCK.ASSEMBLY_TABLE_SB);

        GameRegistry.registerBlock(basicWindingBox, Names.BLOCK.BASIC_WINDING_BOX);
        GameRegistry.registerBlock(assemblyTable, Names.BLOCK.ASSEMBLY_TABLE);
        GameRegistry.registerBlock(assemblyTableSB, Names.BLOCK.ASSEMBLY_TABLE_SB);
    }

    public static void initTE()
    {
        InitializerClockworkNetwork.registerTileEntities();

        GameRegistry.registerTileEntity(TileCelestialCompass.class, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerTileEntity(TileTemporalDisplacementAltar.class, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR);
        GameRegistry.registerTileEntity(TileTDA.class, Names.BLOCK.TDA);
        GameRegistry.registerTileEntity(TileTimezoneFluidExporter.class, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        GameRegistry.registerTileEntity(TileTimezoneFluidImporter.class, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
        GameRegistry.registerTileEntity(TileTimeCollector.class, Names.BLOCK.TIME_COLLECTOR);
        GameRegistry.registerTileEntity(TileAssemblyTable.class, Names.BLOCK.ASSEMBLY_TABLE);
        GameRegistry.registerTileEntity(TileTemporalFurnace.class, Names.BLOCK.TEMPORAL_FURNACE);
        GameRegistry.registerTileEntity(TileTimeWell.class, Names.BLOCK.TIME_WELL);

        InitializerThirdAge.registerTileEntities();
    }
}
