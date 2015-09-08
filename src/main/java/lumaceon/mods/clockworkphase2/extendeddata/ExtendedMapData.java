package lumaceon.mods.clockworkphase2.extendeddata;

import lumaceon.mods.clockworkphase2.api.RuinRegistry;
import lumaceon.mods.clockworkphase2.api.RuinTemplate;
import lumaceon.mods.clockworkphase2.init.ModRuins;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.ruins.Ruins;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.ArrayList;
import java.util.Random;

public class ExtendedMapData extends WorldSavedData
{
    private static final String ID = Reference.MOD_ID + "_savedata";
    private boolean ruinMapGenerated;
    public ArrayList<Ruins> firstAgeRuins = new ArrayList<Ruins>();
    public ArrayList<Ruins> secondAgeRuins = new ArrayList<Ruins>();
    public ArrayList<Ruins> thirdAgeRuins = new ArrayList<Ruins>();
    public ArrayList<Ruins> forthAgeRuins = new ArrayList<Ruins>();
    public ArrayList<Ruins> fifthAgeRuins = new ArrayList<Ruins>();

    public ExtendedMapData() {
        super(ID);
    }

    public ExtendedMapData(String p_i2141_1_) {
        super(p_i2141_1_);
    }

    public static ExtendedMapData get(World world)
    {
        ExtendedMapData dataHandler = (ExtendedMapData) world.loadItemData(ExtendedMapData.class, ID);
        if(dataHandler == null) {
            dataHandler = new ExtendedMapData();
            world.setItemData(ID, dataHandler);
        }
        return dataHandler;
    }

    public boolean isRuinMapGenerated() {
        return this.ruinMapGenerated;
    }

    public void generateRuinMap()
    {
        /*for(RuinTemplate ruinTemplate : RuinRegistry.ruinTemplates)
        {
            if(ruinTemplate != null)
            {

            }
        }*/
        forthAgeRuins.add(new Ruins(ModRuins.testRuins, 0, 64, 0));
        this.ruinMapGenerated = true;
        markDirty();
    }

    /**
     * Does the actual world generation or, more specifically, passes it to the Ruins for generation.
     */
    public void generateRuins(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int dimID = world.provider.dimensionId;
        if(dimID == 0) //OVERWORLD.
            for(Ruins ruin : fifthAgeRuins)
                ruin.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        else if(dimID == Defaults.DIM_ID.FORTH_AGE)
            for(Ruins ruin : forthAgeRuins)
                ruin.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        else if(dimID == Defaults.DIM_ID.THIRD_AGE)
            for(Ruins ruin : thirdAgeRuins)
                ruin.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        else if(dimID == Defaults.DIM_ID.SECOND_AGE)
            for(Ruins ruin : secondAgeRuins)
                ruin.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        else if(dimID == Defaults.DIM_ID.FIRST_AGE)
            for(Ruins ruin : firstAgeRuins)
                ruin.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setBoolean("ruin_map_generated", this.ruinMapGenerated);
        if(!forthAgeRuins.isEmpty())
        {
            NBTTagList tagList = new NBTTagList();
            for(Ruins ruin : forthAgeRuins)
            {
                NBTTagCompound nbtTag = new NBTTagCompound();
                ruin.writeToNBT(nbtTag);
                tagList.appendTag(nbtTag);
            }
            nbt.setTag("forth_age_ruins", tagList);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.ruinMapGenerated = nbt.getBoolean("ruin_map_generated");
        if(nbt.hasKey("forth_age_ruins"))
        {
            NBTTagList tagList = nbt.getTagList("forth_age_ruins", 10);
            for(int n = 0; n < tagList.tagCount(); n++)
            {
                NBTTagCompound nbtTag = tagList.getCompoundTagAt(n);
                Ruins newRuins = new Ruins(RuinRegistry.getRuinTemplate(nbtTag.getString("template")), nbtTag.getInteger("x"), nbtTag.getInteger("y"), nbtTag.getInteger("z"));
                forthAgeRuins.add(newRuins);
            }
        }
    }
}
