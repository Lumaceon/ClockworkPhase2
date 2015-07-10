package lumaceon.mods.clockworkphase2.world.gen;

import cpw.mods.fml.common.IWorldGenerator;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldGeneratorPast implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.dimensionId == Defaults.DIM_ID.PAST)
        {
            for(int x = 0; x < 16; x++)
            {
                for(int y = 1; y < 256; y++)
                {
                    for(int z = 0; z < 16; z++)
                    {
                        world.setBlock(chunkX * 16 + x, y, chunkZ * 16 + z, ModBlocks.blockBrass);
                    }
                }
            }
        }
    }
}
