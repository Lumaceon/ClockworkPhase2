package lumaceon.mods.clockworkphase2.extendeddata;

import lumaceon.mods.clockworkphase2.init.ModRuins;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.structure.Structure;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.*;

public class ExtendedMapData extends WorldSavedData
{
    private static final String ID = Reference.MOD_ID + "_savedata";
    private boolean ruinMapGenerated;
    public List<Integer> dimensionsGenerated = new ArrayList<Integer>(5);
    public List<Structure> zerothAgeRuins = new ArrayList<Structure>();
    public List<Structure> firstAgeRuins = new ArrayList<Structure>();
    public List<Structure> secondAgeRuins = new ArrayList<Structure>();
    public List<Structure> thirdAgeRuins = new ArrayList<Structure>();
    public List<Structure> overworldRuins = new ArrayList<Structure>();

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

    public boolean isDimensionAlreadyGenerated(int dimID) {
        for(Integer id : dimensionsGenerated)
            if(id.equals(dimID))
                return true;
        return false;
    }

    public void generateRuinMap()
    {
        /*for(RuinTemplate ruinTemplate : RuinRegistry.structureTemplates)
        {
            if(ruinTemplate != null)
            {

            }
        }*/
        //overworldRuins.add(new Ruins(ModRuins.testRuins, 50, 64, 200));
        //overworldRuins.add(new Ruins(ModRuins.smallerRuins, 300, 64, -20));
        thirdAgeRuins.add(new Structure(ModRuins.testRuins, 0, 10, 0));
        this.ruinMapGenerated = true;
        markDirty();
    }

    /**
     * Does the actual world generation or, more specifically, passes it to the Structures for generation.
     */
    public void generateRuins(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int dimID = world.provider.dimensionId;
        System.out.println(thirdAgeRuins.size());
        if(isDimensionAlreadyGenerated(dimID))
            return;
        dimensionsGenerated.add(dimID);
        if(dimID == 0) //OVERWORLD.
        {
            for(Structure structure : overworldRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if(dimID == Defaults.DIM_ID.THIRD_AGE)
        {
            for(Structure structure : thirdAgeRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if(dimID == Defaults.DIM_ID.SECOND_AGE)
        {
            for(Structure structure : secondAgeRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if(dimID == Defaults.DIM_ID.FIRST_AGE)
        {
            for(Structure structure : firstAgeRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if(dimID == Defaults.DIM_ID.ZEROTH_AGE)
        {
            for(Structure structure : zerothAgeRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setBoolean("ruin_map_generated", this.ruinMapGenerated);

        nbt.setInteger("dimensions_generated", dimensionsGenerated.size());
        if(dimensionsGenerated != null && !dimensionsGenerated.isEmpty())
            for(int i = 0; i < dimensionsGenerated.size(); i++)
                nbt.setInteger("dim" + i, dimensionsGenerated.get(i));

        /*if(!thirdAgeRuins.isEmpty())
        {
            NBTTagList tagList = new NBTTagList();
            for(Structure ruin : thirdAgeRuins)
            {
                NBTTagCompound nbtTag = new NBTTagCompound();
                ruin.writeToNBT(nbtTag);
                tagList.appendTag(nbtTag);
            }
            nbt.setTag("third_age_ruins", tagList);
        }*/
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.ruinMapGenerated = nbt.getBoolean("ruin_map_generated");

        dimensionsGenerated.clear();
        int dimCount = nbt.getInteger("dimensions_generated");
        for(int i = 0; i < dimCount; i++)
            dimensionsGenerated.add(i, nbt.getInteger("dim" + 1));

        /*if(nbt.hasKey("third_age_ruins"))
        {
            NBTTagList tagList = nbt.getTagList("third_age_ruins", 10);
            for(int n = 0; n < tagList.tagCount(); n++)
            {
                NBTTagCompound nbtTag = tagList.getCompoundTagAt(n);
                Structure newStructure = new Structure(RuinRegistry.getStructureTemplate(nbtTag.getString("template")), nbtTag.getInteger("x"), nbtTag.getInteger("y"), nbtTag.getInteger("z"));
                thirdAgeRuins.add(newStructure);
            }
        }*/
    }
}
