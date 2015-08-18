package lumaceon.mods.clockworkphase2.ruins;

import lumaceon.mods.clockworkphase2.api.RuinTemplate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class Ruins
{
    public final RuinTemplate template;
    public int x, z;
    public int y; //Not determined on initial world load like x and z. This should only be set once though.

    public Ruins(RuinTemplate template, int x, int z) {
        this.template = template;
        this.x = x;
        this.z = z;
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {

    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        x = nbt.getInteger("x");
        y = nbt.getInteger("y");
        z = nbt.getInteger("z");
    }
}
