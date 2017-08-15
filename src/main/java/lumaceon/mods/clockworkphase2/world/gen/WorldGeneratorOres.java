package lumaceon.mods.clockworkphase2.world.gen;

import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGeneratorOres implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.isSurfaceWorld())
        {
            if(ConfigValues.SPAWN_COPPER)
                spawnOres(ModBlocks.oreCopper, world, random, chunkX, chunkZ, 16, 16, 4 + random.nextInt(5), 10, 35, 128);
            if(ConfigValues.SPAWN_ZINC)
                spawnOres(ModBlocks.oreZinc, world, random, chunkX, chunkZ, 16, 16, 4 + random.nextInt(5), 10, 20, 128);
            if(ConfigValues.SPAWN_ALUMINUM)
                spawnOres(ModBlocks.oreAluminum, world, random, chunkX, chunkZ, 16, 16, 4 + random.nextInt(5), 16, 50, 128);
            if(ConfigValues.SPAWN_MOON_FLOWER_RELIC)
                spawnOres(ModBlocks.relicMoonFlower, world, random, chunkX, chunkZ, 16, 16, 1 + random.nextInt(20), 1, 1, 20);
            if(ConfigValues.SPAWN_UNKNOWN_RELIC)
                spawnOres(ModBlocks.relicUnknown, world, random, chunkX, chunkZ, 16, 16, 1 + random.nextInt(4), 4, 1, 20);
        }
    }

    private void spawnOres(Block block, World world, Random random, int chunkX, int chunkZ, int maxX, int maxZ, int maxVeinSize, int amountPerChunk, int minY, int maxY)
    {
        for(int n = 0; n < amountPerChunk; n++)
        {
            int posX = (chunkX * 16) + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = (chunkZ * 16) + random.nextInt(maxZ);
            new WorldGenMinable(block.getDefaultState(), maxVeinSize).generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }
}
