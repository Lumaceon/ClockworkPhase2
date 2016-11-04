package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.guidebook.Categories;
import lumaceon.mods.clockworkphase2.api.guidebook.GuidebookFileHelper;
import lumaceon.mods.clockworkphase2.api.guidebook.GuidebookRegistry;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.recipe.Recipes;
import net.minecraft.item.Item;

import java.io.File;

public class ModArticles
{
    public static void init(File modDirectory)
    {
        initImages();

        Categories.GETTING_STARTED.addArticle(GuidebookFileHelper.getArticleFromFile(modDirectory, Reference.MOD_ID, "worldgen", null, Item.getItemFromBlock(ModBlocks.oreCopper.getBlock()), "What lies below..."));
        Categories.GETTING_STARTED.addArticle(GuidebookFileHelper.getArticleFromFile(modDirectory, Reference.MOD_ID, "clockwork_assembly", null, ModItems.gearWood.getItem(), "Clockwork assembly"));
        Categories.GETTING_STARTED.addArticle(GuidebookFileHelper.getArticleFromFile(modDirectory, Reference.MOD_ID, "moonflowers", null, ModItems.moonFlowerSeeds.getItem(), "Lunar garden"));
        Categories.GETTING_STARTED.addArticle(GuidebookFileHelper.getArticleFromFile(modDirectory, Reference.MOD_ID, "phase", null, ModItems.temporalHourglass.getItem(), "The phase"));
        //Categories.GETTING_STARTED.addArticle(GuidebookFileHelper.getArticleFromFile(modDirectory, Reference.MOD_ID, "time_thief", null, ModItems.alloyHammer.getItem(), "Time thievery"));
    }

    private static void initImages()
    {
        //GuidebookRegistry.registerImage("imgAssemblyTable", Textures.GUI.ASSEMBLY_TABLE_GEARS, 125, 95);
    }

    public static GuidebookRegistry.GuidebookRecipe RECIPE_BRASSLUMP;
    public static GuidebookRegistry.GuidebookRecipe RECIPE_CLOCKWORKPICK;
    //public static GuidebookRegistry.GuidebookRecipe RECIPE_ASSEMBLYTABLE;
    public static void initRecipes()
    {
        RECIPE_BRASSLUMP = new GuidebookRegistry.GuidebookRecipe("rpebl", Recipes.brassLump);
        RECIPE_CLOCKWORKPICK = new GuidebookRegistry.GuidebookRecipe("cp", Recipes.clockworkPickaxe);
        //RECIPE_ASSEMBLYTABLE = new GuidebookRegistry.GuidebookRecipe("at", Recipes.assemblyTable); Broken textures for some reason, fix later.
        GuidebookRegistry.registerNewRecipeMapping("rpebl", RECIPE_BRASSLUMP);
        GuidebookRegistry.registerNewRecipeMapping("cp", RECIPE_CLOCKWORKPICK);
        //GuidebookRegistry.registerNewRecipeMapping("at", RECIPE_ASSEMBLYTABLE);
    }
}
