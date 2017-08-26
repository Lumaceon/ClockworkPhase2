package lumaceon.mods.clockworkphase2.world.deadzone;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

/**
 * Overrides MapGenBase to create a "deadzone" in the center of the map, where it will refuse to generate anything.
 */
public class MapGenDeadzone extends MapGenBase
{
    MapGenBase childGen;

    public MapGenDeadzone(MapGenBase base) {
        this.childGen = base;
    }

    @Override
    public void generate(World worldIn, int x, int z, ChunkPrimer primer)
    {
        if((x > 30 || x < -30) || (z > 30 || z < -30))
        {
            childGen.generate(worldIn, x, z, primer);
        }
    }

    /**
     * Recursively called by generate()
     */
    @Override
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn) {
        //NOOP
    }
}
