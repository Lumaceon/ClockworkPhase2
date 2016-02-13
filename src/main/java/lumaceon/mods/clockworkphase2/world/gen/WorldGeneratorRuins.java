package lumaceon.mods.clockworkphase2.world.gen;

import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGeneratorRuins implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int dimID = world.provider.getDimensionId();
        if(dimID == 0 || dimID == Defaults.DIM_ID.ZEROTH_AGE || dimID == Defaults.DIM_ID.FIRST_AGE || dimID == Defaults.DIM_ID.SECOND_AGE || dimID == Defaults.DIM_ID.THIRD_AGE)
        {
            //ExtendedMapData worldData = ExtendedMapData.get(world);
            //if(worldData != null && worldData.isRuinMapGenerated() && !worldData.isDimensionAlreadyGenerated(dimID))
            //    worldData.generateRuins(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }
}
