package lumaceon.mods.clockworkphase2.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRecipe;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRegistry;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.recipe.timestream.TimestreamRecipeLightning;
import net.minecraft.item.ItemStack;

public class Recipes
{
    public static void init()
    {
        initFurnaceRecipes();
        initTimestreamRecipes();
    }

    public static void initFurnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(ModItems.ingotCopper), 0.7F);
        GameRegistry.addSmelting(ModBlocks.oreZinc, new ItemStack(ModItems.ingotZinc), 0.7F);
    }

    public static void initTimestreamRecipes()
    {
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("relocation", Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_RELOCATION, new ItemStack(ModItems.timestreamRelocation)));
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("smelt", Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_SMELT, new ItemStack(ModItems.timestreamSmelt)));
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("silky", Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_SILKY, new ItemStack(ModItems.timestreamSilkyHarvest)));
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("tank", Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_TANK, new ItemStack(ModItems.timestreamExtradimensionalTank)));
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamRecipeLightning("lightning", Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_LIGHTNING, new ItemStack(ModItems.timestreamLightning)));
        //TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("contract", Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_CONTRACT));
    }
}
