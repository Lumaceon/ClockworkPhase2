package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.block.*;
import lumaceon.mods.clockworkphase2.block.steammachine.BlockBoiler;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.tile.*;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTimezoneFluidExporter;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTimezoneFluidImporter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks
{
    public static void init()
    {
        //initSteamMachines();
        initTimeMachines();
        initFluids();
        initOres();
        initPlants();
        initMetalBlocks();
        initMisc();
    }

    public static Block boiler;
    public static void initSteamMachines()
    {
        boiler = new BlockBoiler(Material.iron, Names.BLOCK.BOILER);

        GameRegistry.registerBlock(boiler, Names.BLOCK.BOILER);
    }

    public static Block celestialCompass;
    public static Block celestialCompassSB;
    public static Block timestreamExtractionChamber;
    //public static Block temporalizer;
    public static Block timezoneFluidExporter;
    public static Block timezoneFluidImporter;
    public static void initTimeMachines()
    {
        celestialCompass = new BlockCelestialCompass(Material.iron, Names.BLOCK.CELESTIAL_COMPASS);
        celestialCompassSB = new BlockCelestialCompassSB(Material.iron, Names.BLOCK.CELESTIAL_COMPASS_SB);
        timestreamExtractionChamber = new BlockTimestreamExtractionChamber(Material.iron, Names.BLOCK.TIMESTREAM_EXTRACTION_CHAMBER);
        //temporalizer = new BlockTemporalizer(Material.iron, Names.BLOCK.TEMPORALIZER);
        timezoneFluidExporter = new BlockTimezoneFluidExporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        timezoneFluidImporter = new BlockTimezoneFluidImporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);

        GameRegistry.registerBlock(celestialCompass, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerBlock(celestialCompassSB, Names.BLOCK.CELESTIAL_COMPASS_SB);
        GameRegistry.registerBlock(timestreamExtractionChamber, Names.BLOCK.TIMESTREAM_EXTRACTION_CHAMBER);
        //GameRegistry.registerBlock(temporalizer, Names.BLOCK.TEMPORALIZER);
        GameRegistry.registerBlock(timezoneFluidExporter, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        GameRegistry.registerBlock(timezoneFluidImporter, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
    }

    public static Block timeSand;
    public static void initFluids()
    {
        timeSand = new BlockTimeSand(ModFluids.timeSand, Material.water, Names.BLOCK.TIME_SAND);
        GameRegistry.registerBlock(timeSand, Names.BLOCK.TIME_SAND);
    }

    public static Block oreCopper;
    public static Block oreZinc;
    public static Block fossilMoonFlower;
    public static void initOres()
    {
        oreCopper = new BlockClockworkPhaseOre(Material.rock, 1, Names.BLOCK.ORE_COPPER);
        oreZinc = new BlockClockworkPhaseOre(Material.rock, 1, Names.BLOCK.ORE_ZINC);
        fossilMoonFlower = new BlockClockworkPhaseFossil(Material.rock, 1, Names.BLOCK.FOSSIL_MOON_FLOWER, ModItems.moonFlowerSeeds);

        GameRegistry.registerBlock(oreCopper, Names.BLOCK.ORE_COPPER);
        GameRegistry.registerBlock(oreZinc, Names.BLOCK.ORE_ZINC);
        GameRegistry.registerBlock(fossilMoonFlower, Names.BLOCK.FOSSIL_MOON_FLOWER);

        OreDictionary.registerOre("oreCopper", oreCopper);
        OreDictionary.registerOre("oreZinc", oreZinc);
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
    public static Block telescope;
    public static void initMisc()
    {
        basicWindingBox = new BlockBasicWindingBox(Material.iron, Names.BLOCK.BASIC_WINDING_BOX);
        assemblyTable = new BlockAssemblyTable(Material.wood, Names.BLOCK.ASSEMBLY_TABLE);
        assemblyTableSB = new BlockAssemblyTableSB(Material.wood, Names.BLOCK.ASSEMBLY_TABLE_SB);
        telescope = new BlockTelescope(Material.wood, Names.BLOCK.TELESCOPE);

        GameRegistry.registerBlock(basicWindingBox, Names.BLOCK.BASIC_WINDING_BOX);
        GameRegistry.registerBlock(assemblyTable, Names.BLOCK.ASSEMBLY_TABLE);
        GameRegistry.registerBlock(assemblyTableSB, Names.BLOCK.ASSEMBLY_TABLE_SB);
        GameRegistry.registerBlock(telescope, Names.BLOCK.TELESCOPE);
    }

    public static void initTE()
    {
        GameRegistry.registerTileEntity(TileCelestialCompass.class, Names.BLOCK.CELESTIAL_COMPASS);
        //GameRegistry.registerTileEntity(TileTemporalizer.class, Names.BLOCK.TEMPORALIZER);
        GameRegistry.registerTileEntity(TileTimezoneFluidExporter.class, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        GameRegistry.registerTileEntity(TileTimezoneFluidImporter.class, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
        GameRegistry.registerTileEntity(TileAssemblyTable.class, Names.BLOCK.ASSEMBLY_TABLE);
        GameRegistry.registerTileEntity(TileAssemblyTableSB.class, Names.BLOCK.ASSEMBLY_TABLE_SB);
        GameRegistry.registerTileEntity(TileTimestreamExtractionChamber.class, Names.BLOCK.TIMESTREAM_EXTRACTION_CHAMBER);
        GameRegistry.registerTileEntity(TileTelescope.class, Names.BLOCK.TELESCOPE);
    }
}
