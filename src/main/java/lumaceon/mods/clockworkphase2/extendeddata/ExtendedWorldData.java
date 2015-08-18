package lumaceon.mods.clockworkphase2.extendeddata;

import lumaceon.mods.clockworkphase2.api.RuinRegistry;
import lumaceon.mods.clockworkphase2.api.RuinTemplate;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.ruins.Ruins;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.ArrayList;
import java.util.Random;

public class ExtendedWorldData extends WorldSavedData
{
    private static final String ID = Reference.MOD_ID + "_savedata";
    private boolean ruinMapGenerated;
    public ArrayList<Ruins> ruins = new ArrayList<Ruins>();

    public ExtendedWorldData()
    {
        super(ID);
    }

    public ExtendedWorldData(String p_i2141_1_)
    {
        super(p_i2141_1_);
    }

    public static ExtendedWorldData get(World world)
    {
        ExtendedWorldData dataHandler = (ExtendedWorldData) world.perWorldStorage.loadData(ExtendedWorldData.class, ID);
        if(dataHandler == null)
        {
            dataHandler = new ExtendedWorldData();
            world.perWorldStorage.setData(ID, dataHandler);
            Logger.info("[Custom World Data Created]"); //TODO - remove this later.
            Logger.info("[[World: " + world.provider.getDimensionName() + "]]");
        }
        return dataHandler;
    }

    public boolean isRuinMapGenerated(World world) {
        return this.ruinMapGenerated;
    }

    public void generateRuinMap(World world)
    {
        for(RuinTemplate ruinTemplate : RuinRegistry.ruinTemplates)
        {
            if(ruinTemplate != null && ruinTemplate.areaRadius > 0)
            {

            }
        }
        this.ruinMapGenerated = true;
        markDirty();
    }

    /**
     * Does the actual world generation or, more specifically, passes it to the Ruins for generation.
     */
    public void generateRuins(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        for(Ruins ruin : ruins)
            ruin.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setBoolean("ruin_map_generated", this.ruinMapGenerated);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.ruinMapGenerated = nbt.getBoolean("ruin_map_generated");
    }
}
