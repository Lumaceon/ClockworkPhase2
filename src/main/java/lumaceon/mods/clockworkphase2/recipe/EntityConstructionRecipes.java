package lumaceon.mods.clockworkphase2.recipe;

import com.sun.istack.internal.NotNull;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class EntityConstructionRecipes
{
    public static final EntityConstructionRecipes INSTANCE = new EntityConstructionRecipes();

    private HashMap<String, EntityConstructionRecipe> recipes = new HashMap<>(20);

    @Nullable
    public EntityConstructionRecipe getRecipe(String recipeID) {
        return recipes.get(recipeID);
    }

    public void addRecipe(@NotNull String recipeID, @NotNull NonNullList<ItemStack> inputItems, @NotNull Class<? extends Entity> output)
    {
        //Skip if the number of input items is greater than 9.
        if(inputItems.size() > 9)
        {
            LogHelper.info("Skipped entity construction recipe with too many inputs (maximum is 9 itemstacks)");
            return;
        }

        if(recipes.get(recipeID) != null)
        {
            LogHelper.info("Skipping duplicate entity construction recipe with ID \"" + recipeID + "\" - Recipe IDs must be unique.");
            return;
        }

        Constructor cons = null;
        try {
            cons = output.getConstructor(World.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if(cons == null)
        {
            LogHelper.info("Skipped entity construction recipe with an entity class lacking a constructor with a single World parameter.");
            return;
        }

        //Actually create and register the recipe.
        EntityConstructionRecipe recipe = new EntityConstructionRecipe(recipeID, inputItems, output, cons);
        recipes.put(recipeID, recipe);
    }

    public HashMap<String, EntityConstructionRecipe> getRecipes() {
        return this.recipes;
    }

    public static class EntityConstructionRecipe
    {
        /**
         * Mostly used just to render this entity in GUIs and such. As the world is null by default, be sure to null
         * check before rendering.
         */
        public Entity posterChild;

        public String recipeID;
        public NonNullList<ItemStack> inputItems;
        public Class<? extends Entity> output;
        public Constructor constructor;

        public EntityConstructionRecipe(String recipeID, NonNullList<ItemStack> inputItems, Class<? extends Entity> output, Constructor constructor)
        {
            try {
                posterChild = (Entity) constructor.newInstance((World) null);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                //noinspection ConstantConditions
                posterChild = new EntityEnderCrystal(null);
                e.printStackTrace();
            }

            this.recipeID = recipeID;
            this.inputItems = inputItems;
            this.output = output;
            this.constructor = constructor;
        }

        public boolean matchesRecipe(@NotNull NonNullList<ItemStack> inventory)
        {
            for(ItemStack is : inputItems)
            {
                int targetNumber  = is.getCount();
                int currentNumber = 0;
                for(ItemStack item : inventory)
                {
                    if(!item.isEmpty() && OreDictionary.itemMatches(is, item, false))
                    {
                        currentNumber += item.getCount();
                        if(currentNumber >= targetNumber)
                            break;
                    }
                }

                if(targetNumber > currentNumber)
                {
                    return false;
                }
            }
            return true;
        }
    }
}
