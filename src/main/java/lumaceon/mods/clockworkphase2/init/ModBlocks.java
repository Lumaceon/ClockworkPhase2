package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.block.BlockAssemblyTable;
import lumaceon.mods.clockworkphase2.block.BlockTimeSand;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks
{
    public static void init()
    {
        initFluids();
        initMisc();
    }

    public static Block timeSand;
    public static void initFluids()
    {
        timeSand = new BlockTimeSand(ModFluids.timeSand, Material.water, Names.BLOCK.TIME_SAND);
        GameRegistry.registerBlock(timeSand, Names.BLOCK.TIME_SAND);
    }

    public static Block assemblyTable;
    public static void initMisc()
    {
        assemblyTable = new BlockAssemblyTable(Material.iron, Names.BLOCK.ASSEMBLY_TABLE);
        GameRegistry.registerBlock(assemblyTable, Names.BLOCK.ASSEMBLY_TABLE);
    }

    public static void initTE()
    {

    }
}
