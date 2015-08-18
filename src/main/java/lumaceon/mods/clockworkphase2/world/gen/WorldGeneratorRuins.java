package lumaceon.mods.clockworkphase2.world.gen;

import cpw.mods.fml.common.IWorldGenerator;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedWorldData;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldGeneratorRuins implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.dimensionId == 0)
        {
            ExtendedWorldData worldData = ExtendedWorldData.get(world);
            if(worldData != null && worldData.isRuinMapGenerated(world))
                worldData.generateRuins(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }
}
