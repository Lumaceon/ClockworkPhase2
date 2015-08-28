package lumaceon.mods.clockworkphase2.world.gen;

import cpw.mods.fml.common.IWorldGenerator;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGeneratorOres implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.isSurfaceWorld())
        {
            spawnOres(ModBlocks.oreCopper, world, random, chunkX, chunkZ, 16, 16, 4 + random.nextInt(5), 10, 35, 128);
            spawnOres(ModBlocks.oreZinc, world, random, chunkX, chunkZ, 16, 16, 4 + random.nextInt(5), 10, 20, 128);
        }
    }

    private void spawnOres(Block block, World world, Random random, int chunkX, int chunkZ, int maxX, int maxZ, int maxVeinSize, int amountPerChunk, int minY, int maxY)
    {
        for(int n = 0; n < amountPerChunk; n++)
        {
            int posX = (chunkX * 16) + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = (chunkZ * 16) + random.nextInt(maxZ);
            new WorldGenMinable(block, maxVeinSize).generate(world, random, posX, posY, posZ);
        }
    }
}
