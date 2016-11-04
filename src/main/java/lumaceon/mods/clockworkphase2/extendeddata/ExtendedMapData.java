package lumaceon.mods.clockworkphase2.extendeddata;

import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.recipe.ExperimentalAlloyRecipes;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class ExtendedMapData extends WorldSavedData
{
    private static final String ID = Reference.MOD_ID + "_savedata";
    private boolean alloysRegistered;
    private int experimentalAlloyGeneration = -1; //Every time new recipes generate, it becomes a new "generation."

    public ExtendedMapData() {
        super(ID);
    }

    public ExtendedMapData(String p_i2141_1_) {
        super(p_i2141_1_);
    }

    public static ExtendedMapData get(World world)
    {
        ExtendedMapData dataHandler = (ExtendedMapData) world.getMapStorage().getOrLoadData(ExtendedMapData.class, ID);
        if(dataHandler == null) {
            dataHandler = new ExtendedMapData();
            world.getMapStorage().setData(ID, dataHandler);
        }
        return dataHandler;
    }

    public boolean areExperimentalAlloysRegistered() {
        return alloysRegistered;
    }

    public void registerExperimentalAlloys() {
        Logger.info("Registered new experimental alloy recipes.");
        ExperimentalAlloyRecipes.generateNewRecipes();
        alloysRegistered = true;
        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setBoolean("alloys_registered", alloysRegistered);
        nbt.setInteger("alloy_generation", experimentalAlloyGeneration);

        for(int n = 0; n < 6; n++)
            nbt.setString("eternium_" + n, ExperimentalAlloyRecipes.eterniumRecipe[n]);
        for(int n = 0; n < 6; n++)
            nbt.setString("momentium_" + n, ExperimentalAlloyRecipes.momentiumRecipe[n]);
        for(int n = 0; n < 6; n++)
            nbt.setString("paradoxium_" + n, ExperimentalAlloyRecipes.paradoxiumRecipe[n]);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
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
    }
}
