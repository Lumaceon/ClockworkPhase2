package lumaceon.mods.clockworkphase2.timetravel.third;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.timetravel.third.block.BlockGhostlyLantern;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class InitializerThirdAge
{
    public static Block ghostlyLantern;
    public static void initBlocks()
    {
        ghostlyLantern = new BlockGhostlyLantern(Material.glass, Names.BLOCK.GHOSTLY_LANTERN);

        GameRegistry.registerBlock(ghostlyLantern, Names.BLOCK.GHOSTLY_LANTERN);
    }

    public static void registerTileEntities()
    {

    }
}
