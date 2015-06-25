package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.block.*;
import lumaceon.mods.clockworkphase2.block.steammachine.BlockBoiler;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTable;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTableSB;
import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import lumaceon.mods.clockworkphase2.tile.TileTimestreamExtractionChamber;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTemporalizer;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTimezoneFluidExporter;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTimezoneFluidImporter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks
{
    public static void init()
    {
        initSteamMachines();
        initTimeMachines();
        initFluids();
        initOres();
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
    public static Block timestreamExtractionChamberSB;
    public static Block temporalizer;
    public static Block timezoneFluidExporter;
    public static Block timezoneFluidImporter;
    public static void initTimeMachines()
    {
        celestialCompass = new BlockCelestialCompass(Material.iron, Names.BLOCK.CELESTIAL_COMPASS);
        celestialCompassSB = new BlockCelestialCompassSB(Material.iron, Names.BLOCK.CELESTIAL_COMPASS_SB);
        timestreamExtractionChamber = new BlockTimestreamExtractionChamber(Material.iron, Names.BLOCK.TIMESTREAM_EXTRACTION_CHAMBER);
        timestreamExtractionChamberSB = new BlockTimestreamExtractionChamberSB(Material.iron, Names.BLOCK.TIMESTREAM_EXTRACTION_CHAMBER_SB);
        temporalizer = new BlockTemporalizer(Material.iron, Names.BLOCK.TEMPORALIZER);
        timezoneFluidExporter = new BlockTimezoneFluidExporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        timezoneFluidImporter = new BlockTimezoneFluidImporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);

        GameRegistry.registerBlock(celestialCompass, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerBlock(celestialCompassSB, Names.BLOCK.CELESTIAL_COMPASS_SB);
        GameRegistry.registerBlock(timestreamExtractionChamber, Names.BLOCK.TIMESTREAM_EXTRACTION_CHAMBER);
        GameRegistry.registerBlock(timestreamExtractionChamberSB, Names.BLOCK.TIMESTREAM_EXTRACTION_CHAMBER_SB);
        GameRegistry.registerBlock(temporalizer, Names.BLOCK.TEMPORALIZER);
        GameRegistry.registerBlock(timezoneFluidExporter, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        GameRegistry.registerBlock(timezoneFluidImporter, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
    }

    public static Block timeSand;
    public static void initFluids()
    {
        timeSand = new BlockTimeSand(ModFluids.timeSand, Material.water, Names.BLOCK.TIME_SAND);
        GameRegistry.registerBlock(timeSand, Names.BLOCK.TIME_SAND);
    }

    public static Block basicWindingBox;
    public static Block assemblyTable;
    public static Block assemblyTableSB;
    public static Block lightningRod;
    public static void initMisc()
    {
        basicWindingBox = new BlockBasicWindingBox(Material.iron, Names.BLOCK.BASIC_WINDING_BOX);
        assemblyTable = new BlockAssemblyTable(Material.wood, Names.BLOCK.ASSEMBLY_TABLE);
        assemblyTableSB = new BlockAssemblyTableSB(Material.wood, Names.BLOCK.ASSEMBLY_TABLE_SB);
        lightningRod = new BlockLightningRod(Material.wood, Names.BLOCK.LIGHTNING_ROD);

        GameRegistry.registerBlock(basicWindingBox, Names.BLOCK.BASIC_WINDING_BOX);
        GameRegistry.registerBlock(assemblyTable, Names.BLOCK.ASSEMBLY_TABLE);
        GameRegistry.registerBlock(assemblyTableSB, Names.BLOCK.ASSEMBLY_TABLE_SB);
        GameRegistry.registerBlock(lightningRod, Names.BLOCK.LIGHTNING_ROD);
    }

    public static Block oreCopper;
    public static Block oreZinc;
    public static void initOres()
    {
        oreCopper = new BlockClockworkPhaseOre(Material.rock, 1, Names.BLOCK.ORE_COPPER);
        oreZinc = new BlockClockworkPhaseOre(Material.rock, 1, Names.BLOCK.ORE_ZINC);

        GameRegistry.registerBlock(oreCopper, Names.BLOCK.ORE_COPPER);
        GameRegistry.registerBlock(oreZinc, Names.BLOCK.ORE_ZINC);

        OreDictionary.registerOre("oreCopper", oreCopper);
        OreDictionary.registerOre("oreZinc", oreZinc);
    }

    public static void initTE()
    {
        GameRegistry.registerTileEntity(TileCelestialCompass.class, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerTileEntity(TileTemporalizer.class, Names.BLOCK.TEMPORALIZER);
        GameRegistry.registerTileEntity(TileTimezoneFluidExporter.class, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        GameRegistry.registerTileEntity(TileTimezoneFluidImporter.class, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
        GameRegistry.registerTileEntity(TileAssemblyTable.class, Names.BLOCK.ASSEMBLY_TABLE);
        GameRegistry.registerTileEntity(TileAssemblyTableSB.class, Names.BLOCK.ASSEMBLY_TABLE_SB);
        GameRegistry.registerTileEntity(TileTimestreamExtractionChamber.class, Names.BLOCK.TIMESTREAM_EXTRACTION_CHAMBER);
    }
}
