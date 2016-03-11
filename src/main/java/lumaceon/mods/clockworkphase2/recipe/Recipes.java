package lumaceon.mods.clockworkphase2.recipe;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes
{
    public static void init()
    {
        initClockworkComponentRecipes();
        initClockworkConstructs();
        initTemporalClockworkModules();
        initMisc();
        initFurnaceRecipes();
    }

    public static void initClockworkComponentRecipes()
    {
        addGearRecipe(new ItemStack(ModItems.gearWood.getItem()), "plankWood");
        addGearRecipe(new ItemStack(ModItems.gearStone.getItem()), "cobblestone");
        addGearRecipe(new ItemStack(ModItems.gearStone.getItem()), "stone");
        addGearRecipe(new ItemStack(ModItems.gearIron.getItem()), "ingotIron");
        addGearRecipe(new ItemStack(ModItems.gearGold.getItem()), "ingotGold");
        addGearRecipe(new ItemStack(ModItems.gearQuartz.getItem()), "gemQuartz");
        addGearRecipe(new ItemStack(ModItems.gearEmerald.getItem()), "gemEmerald");
        addGearRecipe(new ItemStack(ModItems.gearDiamond.getItem()), "gemDiamond");

        addGearRecipe(new ItemStack(ModItems.gearEternium.getItem()), "ingotEternium");
        addGearRecipe(new ItemStack(ModItems.gearMomentium.getItem()), "ingotMomentium");
        addGearRecipe(new ItemStack(ModItems.gearParadoxium.getItem()), "ingotParadoxium");
    }

    public static void initClockworkConstructs()
    {
        ItemStack result;
        result = new ItemStack(ModItems.clockworkPickaxe.getItem());
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bbb", " i ", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
        result = new ItemStack(ModItems.clockworkAxe.getItem());
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bb ", "bi ", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(result, " bb", " ib", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
        result = new ItemStack(ModItems.clockworkShovel.getItem());
        GameRegistry.addRecipe(new ShapedOreRecipe(result, " b ", " i ", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
    }

    public static void initTemporalClockworkModules()
    {
        ItemStack result;
        result = new ItemStack(ModItems.toolUpgradeFurnace.getItem());
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bdb", "bSb", "bbb", 'b', "ingotBrass", 'd', ModItems.temporalDrive.getItem(), 'S', Items.lava_bucket));
        result = new ItemStack(ModItems.toolUpgradeSilk.getItem());
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bdb", "bSb", "bbb", 'b', "ingotBrass", 'd', ModItems.temporalDrive.getItem(), 'S', Items.emerald));
        result = new ItemStack(ModItems.toolUpgradeRelocate.getItem());
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bdb", "bSb", "bbb", 'b', "ingotBrass", 'd', ModItems.temporalDrive.getItem(), 'S', Items.ender_pearl));
    }

    /*public static void initTimezoneModules()
    {
        ItemStack result;
        result = new ItemStack(ModItems.timezoneModuleTank);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bgb", "gTg", "bgb", 'b', "ingotBrass", 'g', "blockGlass", 'T', Items.bucket));
    }*/

    public static void initMisc()
    {
        ItemStack result;
        result = new ItemStack(ModItems.lumpBrass.getItem());
        GameRegistry.addRecipe(new ShapelessOreRecipe(result, "oreCopper", "oreZinc"));
        result = new ItemStack(ModBlocks.assemblyTable.getBlock());
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "wcw", "cgc", "wcw", 'w', "plankWood", 'c', Blocks.crafting_table, 'g', "gearWood"));
        result = new ItemStack(ModItems.temporalDrive.getItem());
        GameRegistry.addRecipe(new ShapedOreRecipe(result, " bb", "btb", "bb ", 'b', "ingotBrass", 't', "ingotTemporal"));
        //result = new ItemStack(ModItems.temporalDriveDuplicate);
        //GameRegistry.addRecipe(new ShapedOreRecipe(result, "", "", ""));
    }

    public static void initFurnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.oreCopper.getBlock(), new ItemStack(ModItems.ingotCopper.getItem()), 0.7F);
        GameRegistry.addSmelting(ModBlocks.oreZinc.getBlock(), new ItemStack(ModItems.ingotZinc.getItem()), 0.7F);
        GameRegistry.addSmelting(ModItems.lumpBrass.getItem(), new ItemStack(ModItems.ingotBrass.getItem()), 1.5F);
        GameRegistry.addSmelting(ModItems.temporalPearl.getItem(), new ItemStack(ModItems.ingotTemporal.getItem()), 10F);
    }

    public static void addGearRecipe(ItemStack result, String materialName) {
        GameRegistry.addRecipe(new ShapedOreRecipe(result, " m ", "msm", " m ", 'm', materialName, 's', "stickWood"));
    }

    /**
     * Called during post-init as opposed to the initialization phase for the rest of the recipes.
     */
    public static void initAlloyRecipes()
    {
        boolean copper = OreDictionary.doesOreNameExist("ingotCopper");
        boolean zinc = OreDictionary.doesOreNameExist("ingotZinc");
        boolean tin = OreDictionary.doesOreNameExist("ingotTin");
        boolean brass = OreDictionary.doesOreNameExist("ingotBrass");
        boolean bronze = OreDictionary.doesOreNameExist("ingotBronze");
        boolean nickel = OreDictionary.doesOreNameExist("ingotNickel");
        boolean invar = OreDictionary.doesOreNameExist("ingotInvar");
        boolean aluminum = OreDictionary.doesOreNameExist("ingotAluminum");
        boolean aluminumBrass = OreDictionary.doesOreNameExist("ingotAluminumBrass");
        boolean cobalt = OreDictionary.doesOreNameExist("ingotCobalt");
        boolean ardite = OreDictionary.doesOreNameExist("ingotArdite");
        boolean manyullyn = OreDictionary.doesOreNameExist("ingotManyullyn");

        AlloyRecipes.RecipeComponent first;
        AlloyRecipes.RecipeComponent second;
        ItemStack output;
        if(copper && zinc && brass)
        {
            first = new AlloyRecipes.RecipeComponent(new ItemStack(ModItems.ingotCopper.getItem()), (byte) 3);
            second = new AlloyRecipes.RecipeComponent(new ItemStack(ModItems.ingotZinc.getItem()), (byte) 1);
            output = new ItemStack(ModItems.ingotBrass.getItem());
            output.stackSize = 4;
            AlloyRecipes.addAlloyRecipe(first, second, output);
        }

        if(copper && tin && bronze)
        {
            first = new AlloyRecipes.RecipeComponent(new ItemStack(ModItems.ingotCopper.getItem()), (byte) 3);
            second = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotTin").get(0), (byte) 1);
            output = OreDictionary.getOres("ingotBronze").get(0).copy();
            output.stackSize = 4;
            AlloyRecipes.addAlloyRecipe(first, second, output);
        }

        if(nickel && invar)
        {
            first = new AlloyRecipes.RecipeComponent(new ItemStack(Items.iron_ingot), (byte) 2);
            second = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotNickel").get(0), (byte) 1);
            output = OreDictionary.getOres("ingotInvar").get(0).copy();
            output.stackSize = 3;
            AlloyRecipes.addAlloyRecipe(first, second, output);
        }

        if(copper && aluminum && aluminumBrass)
        {
            first = new AlloyRecipes.RecipeComponent(new ItemStack(ModItems.ingotCopper.getItem()), (byte) 1);
            second = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotAluminum").get(0), (byte) 3);
            output = OreDictionary.getOres("ingotAluminumBrass").get(0).copy();
            output.stackSize = 4;
            AlloyRecipes.addAlloyRecipe(first, second, output);
        }

        if(cobalt && ardite && manyullyn)
        {
            first = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotCobalt").get(0), (byte) 1);
            second = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotArdite").get(0), (byte) 1);
            output = OreDictionary.getOres("ingotManyullyn").get(0).copy();
            output.stackSize = 2;
            AlloyRecipes.addAlloyRecipe(first, second, output);
        }
    }
}
