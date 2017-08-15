package lumaceon.mods.clockworkphase2.world.deadzone;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;

import java.util.Random;

/**
 * Overrides MapGenBase to create a "deadzone" in the center of the map, where it will refuse to generate anything.
 */
public class MapGenMineshaftDeadzone extends MapGenMineshaft
{
    MapGenMineshaft childGen;

    public MapGenMineshaftDeadzone(MapGenMineshaft base) {
        this.childGen = base;
    }

    @Override
    public void generate(World worldIn, int x, int z, ChunkPrimer primer)
    {
        if((x > 500 || x < -500) && (z > 500 || z < -500))
        {
            childGen.generate(worldIn, x, z, primer);
        }
    }

    @Override
    public synchronized boolean generateStructure(World worldIn, Random randomIn, ChunkPos chunkCoord)
    {
        if((chunkCoord.getXStart() > 500 || chunkCoord.getXEnd() < -500) && (chunkCoord.getZStart() > 500 || chunkCoord.getZEnd() < -500))
        {
            return childGen.generateStructure(worldIn, randomIn, chunkCoord);
        }
        return false;
    }

    @Override
    public boolean isInsideStructure(BlockPos pos) {
        return childGen.isInsideStructure(pos);
    }

    @Override
    public boolean isPositionInStructure(World worldIn, BlockPos pos) {
        return childGen.isPositionInStructure(worldIn, pos);
    }

    @Override
    public String getStructureName() {
        return childGen.getStructureName();
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
        return childGen.getNearestStructurePos(worldIn, pos, findUnexplored);
    }
}
