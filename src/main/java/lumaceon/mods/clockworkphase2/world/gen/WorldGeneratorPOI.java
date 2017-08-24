package lumaceon.mods.clockworkphase2.world.gen;

import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.world.schematic.SchematicChunk;
import lumaceon.mods.clockworkphase2.world.schematic.SchematicHandler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

@SuppressWarnings("deprecation")
public class WorldGeneratorPOI implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.getDimension() == 0)
        {
            if(ConfigValues.SPAWN_WORLD_CRATER)
            {
                if(chunkX >= 0 && chunkX < 21 && chunkZ >= 0 && chunkZ < 21)
                {
                    Chunk c = world.getChunkFromChunkCoords(chunkX, chunkZ);
                    SchematicChunk.ModSchematicChunk chunk = SchematicHandler.INSTANCE.loadModSchematic("Ragnarok_castle", false, chunkX, chunkZ);
                    int startY = 2;
                    for(int x = 0; x < 16; x++)
                    {
                        for(int z = 0; z < 16; z++)
                        {
                            int y2 = 0;
                            for(int y = startY; y < startY + chunk.height; y++)
                            {
                                //c.setBlockState(x, y, z, chunk.getBlock(x, y2, z).getStateFromMeta(chunk.getMetadata(x, y2, z)));
                                ++y2;
                            }
                        }
                    }
                }
            }
        }
    }
}
