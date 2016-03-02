package lumaceon.mods.clockworkphase2.recipe;

import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Random;

public class SuperAlloyRecipes
{
    private static Random random = new Random();
    public static ArrayList<String> defaults = new ArrayList<String>(); //The potentials that may be possible.
    public static ArrayList<String> superAlloyMetals = new ArrayList<String>(); //The potentials; confirmed to be possible.

    public static String[] eterniumRecipe = new String[6];
    public static String[] momentiumRecipe = new String[6];
    public static String[] paradoxiumRecipe = new String[6];

    public static int getNumberOfValidMetals(String[] items, ItemStack[] inventory)
    {
        int foundItems = 0;
        for(String s : items)
            for(int n = 0; n < 6; n++)
                if(OreDictionary.itemMatches(inventory[n], OreDictionary.getOres(s).get(0), false))
                {
                    ++foundItems;
                    break;
                }

        return foundItems;
    }

    /**
     * Lists the metals that we want to use if they are available in this MC instance.
     */
    public static void preInit()
    {
        defaults.add("ingotIron");
        defaults.add("ingotGold");
        defaults.add("ingotSilver");
        defaults.add("ingotAluminum");
        defaults.add("ingotAluminumBrass");
        defaults.add("ingotInvar");
        defaults.add("ingotDarkSteel");
        defaults.add("ingotElectrum");
        defaults.add("ingotLead");
        defaults.add("ingotPlatinum");
        defaults.add("ingotSteel");
        defaults.add("ingotThaumium");
        defaults.add("ingotCopper");
        defaults.add("ingotTin");
        defaults.add("ingotBronze");
        defaults.add("ingotZinc");
        defaults.add("ingotBrass");
        defaults.add("ingotTemporal");
    }

    /**
     * Checks to see what metals are actually initialized and adds them to an ArrayList. These will be the final
     * potential metals which can be researched and may be a part of a super alloy. This method also sets the recipes.
     */
    public static void postInit()
    {
        for(String s : defaults)
            if(s != null && OreDictionary.doesOreNameExist(s))
                superAlloyMetals.add(s);

        if(superAlloyMetals.size() <= 6)
            Logger.fatal("Number of super-alloy ready metals failed to exceed 6. If this error occurs, someone really screwed up.");

        boolean eterniumConflict = true;
        boolean momentiumConflict = true;

        //Setup eternium first
        ArrayList<String> metals = (ArrayList<String>) superAlloyMetals.clone();
        for(int n = 0; n < 6; n++)
        {
            int metalIndex = random.nextInt(metals.size());
            eterniumRecipe[n] = metals.get(metalIndex);
            metals.remove(metalIndex);
        }

        //Move on to momentium
        while(eterniumConflict)
        {
            metals = (ArrayList<String>) superAlloyMetals.clone();
            for(int n = 0; n < 6; n++)
            {
                int metalIndex = random.nextInt(metals.size());
                momentiumRecipe[n] = metals.get(metalIndex);
                metals.remove(metalIndex);
            }

            //Is momentium recipe the same as eternium recipe?
            for(String s : eterniumRecipe)
            {
                boolean found = false;
                for(String s2 : momentiumRecipe)
                    if(s.equals(s2))
                        found = true;
                if(!found) //We found a metal that isn't in eternium; the recipe doesn't conflict.
                    eterniumConflict = false;
            }
        }

        //Finally, move on to capricium
        while(eterniumConflict || momentiumConflict)
        {
            metals = (ArrayList<String>) superAlloyMetals.clone();
            eterniumConflict = true;
            momentiumConflict = true;

            for(int n = 0; n < 6; n++)
            {
                int metalIndex = random.nextInt(metals.size());
                paradoxiumRecipe[n] = metals.get(metalIndex);
                metals.remove(metalIndex);
            }

            //Is capricium recipe the same as eternium recipe?
            for(String s : eterniumRecipe)
            {
                boolean found = false;
                for(String s2 : paradoxiumRecipe)
                    if(s.equals(s2))
                        found = true;
                if(!found) //We found a metal that isn't in eternium; the recipe doesn't conflict.
                    eterniumConflict = false;
            }

            //Is capricium recipe the same as momentium recipe
            for(String s : momentiumRecipe)
            {
                boolean found = false;
                for(String s2 : paradoxiumRecipe)
                    if(s.equals(s2))
                        found = true;
                if(!found) //We found a metal that isn't in momentium; the recipe doesn't conflict.
                    momentiumConflict = false;
            }
        }
    }
}
