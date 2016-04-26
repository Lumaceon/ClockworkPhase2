package lumaceon.mods.clockworkphase2.extendeddata;

import lumaceon.mods.clockworkphase2.lib.Configs;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.recipe.ExperimentalAlloyRecipes;
import lumaceon.mods.clockworkphase2.structure.Structure;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.*;

public class ExtendedMapData extends WorldSavedData
{
    private static final String ID = Reference.MOD_ID + "_savedata";
    private boolean ruinMapGenerated;
    private boolean alloysRegistered;
    private int experimentalAlloyGeneration = -1; //Every time new recipes generate, it becomes a new "generation."
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
        ExtendedMapData dataHandler = (ExtendedMapData) world.getMapStorage().loadData(ExtendedMapData.class, ID);
        if(dataHandler == null) {
            dataHandler = new ExtendedMapData();
            world.getMapStorage().setData(ID, dataHandler);
        }
        return dataHandler;
    }

    public boolean areExperimentalAlloysRegistered() {
        return alloysRegistered;
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
        //thirdAgeRuins.add(new Structure(ModRuins.testRuins, 0, 10, 0));
        this.ruinMapGenerated = true;
        markDirty();
    }

    public void registerExperimentalAlloys() {
        Logger.info("Registered new experimental alloy recipes.");
        ExperimentalAlloyRecipes.generateNewRecipes();
        alloysRegistered = true;
        markDirty();
    }

    /**
     * Does the actual world generation or, more specifically, passes it to the Structures for generation.
     */
    public void generateRuins(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int dimID = world.provider.getDimensionId();
        System.out.println(thirdAgeRuins.size());
        if(isDimensionAlreadyGenerated(dimID))
            return;
        dimensionsGenerated.add(dimID);
        if(dimID == 0) //OVERWORLD.
        {
            for(Structure structure : overworldRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if(dimID == Configs.DIM_ID.THIRD_AGE)
        {
            for(Structure structure : thirdAgeRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if(dimID == Configs.DIM_ID.SECOND_AGE)
        {
            for(Structure structure : secondAgeRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if(dimID == Configs.DIM_ID.FIRST_AGE)
        {
            for(Structure structure : firstAgeRuins)
                structure.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
        else if(dimID == Configs.DIM_ID.ZEROTH_AGE)
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

        nbt.setBoolean("alloys_registered", alloysRegistered);
        nbt.setInteger("alloy_generation", experimentalAlloyGeneration);

        for(int n = 0; n < 6; n++)
            nbt.setString("eternium_" + n, ExperimentalAlloyRecipes.eterniumRecipe[n]);
        for(int n = 0; n < 6; n++)
            nbt.setString("momentium_" + n, ExperimentalAlloyRecipes.momentiumRecipe[n]);
        for(int n = 0; n < 6; n++)
            nbt.setString("paradoxium_" + n, ExperimentalAlloyRecipes.paradoxiumRecipe[n]);


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

        if(nbt.hasKey("alloys_registered"))
            this.alloysRegistered = nbt.getBoolean("alloys_registered");

        if(nbt.hasKey("alloy_generation"))
            this.experimentalAlloyGeneration = nbt.getInteger("alloy_generation");

        if(alloysRegistered)
        {
            String[] eterniumRecipe = new String[6];
            String[] momentiumRecipe = new String[6];
            String[] paradoxiumRecipe = new String[6];

            for(int n = 0; n < 6; n++)
                if(nbt.hasKey("eternium_" + n))
                    eterniumRecipe[n] = nbt.getString("eternium_" + n);
            for(int n = 0; n < 6; n++)
                if(nbt.hasKey("momentium_" + n))
                    momentiumRecipe[n] = nbt.getString("momentium_" + n);
            for(int n = 0; n < 6; n++)
                if(nbt.hasKey("paradoxium_" + n))
                    paradoxiumRecipe[n] = nbt.getString("paradoxium_" + n);

            if(!ExperimentalAlloyRecipes.loadRecipes(eterniumRecipe, momentiumRecipe, paradoxiumRecipe)) //Failed to load?
            {
                //Increase generation index and generate new recipes.
                Logger.error("Invalid experimental alloy recipes found; generating new ones...");
                ++experimentalAlloyGeneration;
                ExperimentalAlloyRecipes.generateNewRecipes();
                markDirty();
            }
        }

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
