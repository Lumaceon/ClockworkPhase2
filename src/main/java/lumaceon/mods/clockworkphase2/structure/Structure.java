package lumaceon.mods.clockworkphase2.structure;

import lumaceon.mods.clockworkphase2.util.Area;
import lumaceon.mods.clockworkphase2.util.SchematicUtility;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

/**
 * A specific structure waiting to be generated in the given position in the world.
 * Big thanks to Tombenpotter and Elec332 for making this run considerably faster.
 */
public class Structure
{
    public final StructureTemplate template;
    public int x, y, z; //Represents the center of the structure, except for 'y' which represents the horizon.

    public Structure(StructureTemplate template, int x, int y, int z) {
        this.template = template;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        /*Area chunkArea = new Area(chunkX * 16, 0, chunkZ * 16, chunkX * 16 + 16, 255, chunkZ * 16 + 16);
        Area schematicArea = template.ruinSchematic.getAreaFromWorldCoordinates(this.x, this.y, this.z);
        if(schematicArea.doAreasIntersect(chunkArea))
        {
            System.out.println(schematicArea.getWidth() + ", " + template.ruinSchematic.width);
            System.out.println(schematicArea.getHeight() + ", " + template.ruinSchematic.height);
            System.out.println(schematicArea.getLength() + ", " + template.ruinSchematic.length);
            int[] offset = template.ruinSchematic.getSchematicToWorldOffset(this.x, this.y, this.z);
            int xTemp, yTemp, zTemp;
            Block block;
            for(int x = chunkX * 16; x <= chunkX * 16 + 16; x++)
                for(int y = 0; y <= 255; y++)
                    for(int z = chunkZ * 16; z <= chunkZ * 16 + 16; z++) //Remember the difference: "this.x" and x.
                    {
                        xTemp = x + offset[0];
                        yTemp = y + offset[1];
                        zTemp = z + offset[2];

                        if(xTemp > -1 && yTemp > -1 && zTemp > -1
                                && xTemp < template.ruinSchematic.width
                                && yTemp < template.ruinSchematic.height - template.ruinSchematic.horizon
                                && zTemp < template.ruinSchematic.length)
                        {
                            block = template.ruinSchematic.getBlock(xTemp, yTemp, zTemp);
                            if(block != null)
                            {
                                world.setBlockToAir(x, y, z);
                                if(!block.getMaterial().equals(Material.air))
                                    world.setBlock(x, y, z, block, template.ruinSchematic.getMetadata(xTemp, yTemp, zTemp), 2);
                            }
                        }
                    }
        }*/
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);
        nbt.setString("template", template.uniqueName);
    }
}
