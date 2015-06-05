package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.block.*;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTemporalizer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks
{
    public static void init()
    {
        initTimeMachines();
        initFluids();
        initMisc();
    }

    public static Block temporalizer;
    public static void initTimeMachines()
    {
        temporalizer = new BlockTemporalizer(Material.iron, Names.BLOCK.TEMPORALIZER);

        GameRegistry.registerBlock(temporalizer, Names.BLOCK.TEMPORALIZER);
    }

    public static Block timeSand;
    public static void initFluids()
    {
        timeSand = new BlockTimeSand(ModFluids.timeSand, Material.water, Names.BLOCK.TIME_SAND);
        GameRegistry.registerBlock(timeSand, Names.BLOCK.TIME_SAND);
    }

    public static Block basicWindingBox;
    public static Block assemblyTable;
    public static Block celestialCompass;
    public static Block celestialCompassSB;
    public static void initMisc()
    {
        basicWindingBox = new BlockBasicWindingBox(Material.iron, Names.BLOCK.BASIC_WINDING_BOX);
        assemblyTable = new BlockAssemblyTable(Material.iron, Names.BLOCK.ASSEMBLY_TABLE);
        celestialCompass = new BlockCelestialCompass(Material.iron, Names.BLOCK.CELESTIAL_COMPASS);
        celestialCompassSB = new BlockCelestialCompassSB(Material.iron, Names.BLOCK.CELESTIAL_COMPASS_SB);

        GameRegistry.registerBlock(basicWindingBox, Names.BLOCK.BASIC_WINDING_BOX);
        GameRegistry.registerBlock(assemblyTable, Names.BLOCK.ASSEMBLY_TABLE);
        GameRegistry.registerBlock(celestialCompass, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerBlock(celestialCompassSB, Names.BLOCK.CELESTIAL_COMPASS_SB);
    }

    public static void initTE()
    {
        GameRegistry.registerTileEntity(TileCelestialCompass.class, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerTileEntity(TileTemporalizer.class, Names.BLOCK.TEMPORALIZER);
    }
}
