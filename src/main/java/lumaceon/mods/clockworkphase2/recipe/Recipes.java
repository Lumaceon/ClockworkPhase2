package lumaceon.mods.clockworkphase2.recipe;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.recipe.customrecipe.ShapelessOreRecipeDegradeItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes
{
    public static void init()
    {
        RecipeSorter.register(Reference.MOD_ID + ":degradeitem", ShapelessOreRecipeDegradeItem.class, RecipeSorter.Category.SHAPELESS, "after:forge:shapelessore");
        initClockworkComponentRecipes();
        initClockworkConstructs();
        initTemporalClockworkModules();
        initTimeManipulators();
        initMetalBlocks();
        initBlocks();
        initMisc();
        initFurnaceRecipes();
    }

    public static IRecipe gearWood;
    public static IRecipe gearCobbleStone;
    public static IRecipe gearStone;
    public static IRecipe gearIron;
    public static IRecipe gearGold;
    public static IRecipe gearQuartz;
    public static IRecipe gearEmerald;
    public static IRecipe gearDiamond;
    public static IRecipe gearCopper;
    public static IRecipe gearZinc;
    public static IRecipe gearBrass;
    public static void initClockworkComponentRecipes()
    {
        gearWood = addGearRecipe(new ItemStack(ModItems.gearWood.getItem()), "plankWood");
        gearCobbleStone = addGearRecipe(new ItemStack(ModItems.gearStone.getItem()), "cobblestone");
        gearStone = addGearRecipe(new ItemStack(ModItems.gearStone.getItem()), "stone");
        gearIron = addGearRecipe(new ItemStack(ModItems.gearIron.getItem()), "ingotIron");
        gearGold = addGearRecipe(new ItemStack(ModItems.gearGold.getItem()), "ingotGold");
        gearQuartz = addGearRecipe(new ItemStack(ModItems.gearQuartz.getItem()), "gemQuartz");
        gearEmerald = addGearRecipe(new ItemStack(ModItems.gearEmerald.getItem()), "gemEmerald");
        gearDiamond = addGearRecipe(new ItemStack(ModItems.gearDiamond.getItem()), "gemDiamond");
        gearCopper = addGearRecipe(new ItemStack(ModItems.gearCopper.getItem()), "ingotCopper");
        gearZinc = addGearRecipe(new ItemStack(ModItems.gearZinc.getItem()), "ingotZinc");
        gearBrass = addGearRecipe(new ItemStack(ModItems.gearBrass.getItem()), "ingotBrass");
    }

    public static IRecipe mainspring;
    public static IRecipe clockworkCore;
    public static IRecipe clockworkPickaxe;
    public static IRecipe clockworkAxeRight;
    public static IRecipe clockworkAxeLeft;
    public static IRecipe clockworkShovel;
    public static IRecipe clockworkSword;
    public static void initClockworkConstructs()
    {
        ItemStack result;

        result = new ItemStack(ModItems.mainspring.getItem());
        mainspring = new ShapedOreRecipe(result, "iii", "iIi", "iii", 'i', "ingotIron", 'I', "blockIron");
        GameRegistry.addRecipe(mainspring);

        result = new ItemStack(ModItems.clockworkCore.getItem());
        clockworkCore = new ShapedOreRecipe(result, "bib", "iii", "bib", 'i', "ingotIron", 'b', "ingotBrass");
        GameRegistry.addRecipe(clockworkCore);

        result = new ItemStack(ModItems.clockworkPickaxe.getItem());
        clockworkPickaxe = new ShapedOreRecipe(result, "bbb", "wiw", " i ", 'b', "ingotBrass", 'i', "ingotIron", 'w', "gearWood");
        GameRegistry.addRecipe(clockworkPickaxe);

        result = new ItemStack(ModItems.clockworkAxe.getItem());
        clockworkAxeRight = new ShapedOreRecipe(result, "bbw", "biw", " i ", 'b', "ingotBrass", 'i', "ingotIron", 'w', "gearWood");
        clockworkAxeLeft = new ShapedOreRecipe(result, "wbb", "wib", " i ", 'b', "ingotBrass", 'i', "ingotIron", 'w', "gearWood");
        GameRegistry.addRecipe(clockworkAxeRight);
        GameRegistry.addRecipe(clockworkAxeLeft);

        result = new ItemStack(ModItems.clockworkShovel.getItem());
        clockworkShovel = new ShapedOreRecipe(result, "wbw", " i ", " i ", 'b', "ingotBrass", 'i', "ingotIron", 'w', "gearWood");
        GameRegistry.addRecipe(clockworkShovel);

        result = new ItemStack(ModItems.clockworkSword.getItem());
        clockworkSword = new ShapedOreRecipe(result, " b ", "ibi", "wiw", 'b', "ingotBrass", 'i', "ingotIron", 'w', "gearWood");
        GameRegistry.addRecipe(clockworkSword);
    }

    public static IRecipe toolUpgradeTemporal;
    //public static IRecipe toolUpgradeFurnace;
    public static IRecipe toolUpgradeSilk;
    public static IRecipe toolUpgradeRelocate;
    public static IRecipe toolUpgradeArea;
    public static IRecipe toolUpgradeFortune;
    public static void initTemporalClockworkModules()
    {
        ItemStack result;

        //result = new ItemStack(ModItems.toolUpgradeFurnace.getItem());
        //toolUpgradeFurnace = new ShapedOreRecipe(result, "bdb", "bSb", "bbb", 'b', "ingotBrass", 'd', ModItems.temporalDrive.getItem(), 'S', Items.LAVA_BUCKET);
        //GameRegistry.addRecipe(toolUpgradeFurnace);

        result = new ItemStack(ModItems.toolUpgradeTemporalInfuser.getItem());
        toolUpgradeTemporal = new ShapedOreRecipe(result, "bdb", "btb", "bbb", 'b', "ingotBrass", 't', "ingotTemporal", 'd', Items.DIAMOND_PICKAXE);
        GameRegistry.addRecipe(toolUpgradeTemporal);

        result = new ItemStack(ModItems.toolUpgradeSilk.getItem());
        toolUpgradeSilk = new ShapedOreRecipe(result, "bDb", "btb", "bbb", 'b', "ingotBrass", 't', "ingotTemporal", 'D', "blockDiamond");
        GameRegistry.addRecipe(toolUpgradeSilk);

        result = new ItemStack(ModItems.toolUpgradeRelocate.getItem());
        toolUpgradeRelocate = new ShapedOreRecipe(result, "beb", "btb", "bbb", 'b', "ingotBrass", 't', "ingotTemporal", 'e', Items.ENDER_PEARL);
        GameRegistry.addRecipe(toolUpgradeRelocate);

        result = new ItemStack(ModItems.toolUpgradeArea.getItem());
        toolUpgradeArea = new ShapedOreRecipe(result, "bSb", "beb", "bbb", 'b', "ingotBrass", 'e', "ingotEthereal", 'S', Items.NETHER_STAR);
        GameRegistry.addRecipe(toolUpgradeArea);

        result = new ItemStack(ModItems.toolUpgradeFortune.getItem());
        toolUpgradeFortune = new ShapedOreRecipe(result, "beb", "btb", "bbb", 'b', "ingotBrass", 't', "ingotTemporal", 'e', "gemEmerald");
        GameRegistry.addRecipe(toolUpgradeFortune);
    }

    public static IRecipe temporalDrive;
    public static IRecipe etherealDrive;
    public static IRecipe temporalExcavator;
    public static IRecipe hourglassTemporal;
    public static IRecipe hourglassEthereal;
    public static void initTimeManipulators()
    {
        ItemStack result;

        result = new ItemStack(ModItems.temporalDrive.getItem());
        temporalDrive = new ShapedOreRecipe(result, "iti", "tet", "iti", 't', "ingotTemporal", 'e', Items.ENDER_PEARL, 'i', "ingotIron");
        GameRegistry.addRecipe(temporalDrive);

        result = new ItemStack(ModItems.etherealDrive.getItem());
        etherealDrive = new ShapedOreRecipe(result, "iti", "tet", "iti", 't', "ingotEthereal", 'e', Items.ENDER_PEARL, 'i', "ingotIron");
        GameRegistry.addRecipe(etherealDrive);

        result = new ItemStack(ModItems.temporalExcavator.getItem());
        temporalExcavator = new ShapedOreRecipe(result, "ttt", "tbt", " b ", 't', "ingotTemporal", 'b', "ingotBrass");
        GameRegistry.addRecipe(temporalExcavator);

        result = new ItemStack(ModItems.temporalHourglass.getItem());
        hourglassTemporal = new ShapedOreRecipe(result, "bbb", "tbt", "bbb", 't', "ingotTemporal", 'b', "ingotBrass");
        GameRegistry.addRecipe(hourglassTemporal);

        result = new ItemStack(ModItems.etherealHourglass.getItem());
        hourglassEthereal = new ShapedOreRecipe(result, "bbb", "tbt", "bbb", 't', "ingotTemporal", 'b', "ingotBrass");
        GameRegistry.addRecipe(hourglassEthereal);
    }

    public static IRecipe copperBlock;
    public static IRecipe zincBlock;
    public static IRecipe brassBlock;
    public static IRecipe temporalBlock;
    public static void initMetalBlocks()
    {
        ItemStack result;

        result = new ItemStack(ModBlocks.blockCopper.getBlock());
        copperBlock = new ShapedOreRecipe(result, "iii", "iii", "iii", 'i', ModItems.ingotCopper.getItem());
        GameRegistry.addRecipe(copperBlock);

        result = new ItemStack(ModBlocks.blockZinc.getBlock());
        zincBlock = new ShapedOreRecipe(result, "iii", "iii", "iii", 'i', ModItems.ingotZinc.getItem());
        GameRegistry.addRecipe(zincBlock);

        result = new ItemStack(ModBlocks.blockBrass.getBlock());
        brassBlock = new ShapedOreRecipe(result, "iii", "iii", "iii", 'i', ModItems.ingotBrass.getItem());
        GameRegistry.addRecipe(brassBlock);

        result = new ItemStack(ModBlocks.blockTemporal.getBlock());
        temporalBlock = new ShapedOreRecipe(result, "iii", "iii", "iii", 'i', ModItems.ingotTemporal.getItem());
        GameRegistry.addRecipe(temporalBlock);
    }

    public static IRecipe basicWindingBox;
    public static IRecipe multblockAssembler;
    public static IRecipe constructionBlock;
    public static void initBlocks()
    {
        ItemStack result;

        result = new ItemStack(ModBlocks.basicWindingBox.getBlock());
        basicWindingBox = new ShapedOreRecipe(result, "ibi", "bBb", "ibi", 'i', "ingotIron", 'b', "ingotBrass", 'B', "gearIron");
        GameRegistry.addRecipe(basicWindingBox);

        result = new ItemStack(ModBlocks.multiblockAssembler.getBlock());
        multblockAssembler = new ShapedOreRecipe(result, "cbc", "beb", "cbc", 'c', ModBlocks.constructionBlock.getBlock(), 'b', "ingotBrass", 'e', Items.ENDER_PEARL);
        GameRegistry.addRecipe(multblockAssembler);

        result = new ItemStack(ModBlocks.constructionBlock.getBlock());
        constructionBlock = new ShapedOreRecipe(result, "sws", "wiw", "sws", 's', "stickWood", 'w', "plankWood", 'i', "ingotIron");
        GameRegistry.addRecipe(constructionBlock);
    }

    public static IRecipe brassLump;
    public static IRecipe assemblyTable;
    public static IRecipe trowelIron;
    public static IRecipe trowelDiamond;
    public static IRecipe alloyHammer;
    public static IRecipe multblockTemplateCelestialCompass;
    public static IRecipe guidebook;
    public static void initMisc()
    {
        ItemStack result;

        result = new ItemStack(ModItems.lumpBrass.getItem());
        result.stackSize = 2;
        brassLump = new ShapelessOreRecipeDegradeItem(result, ModItems.alloyHammer.getItem(), "oreCopper", "oreCopper", "oreCopper", "oreZinc", ModItems.alloyHammer.getItem());
        GameRegistry.addRecipe(brassLump);

        result = new ItemStack(ModItems.assemblyTable.getItem());
        assemblyTable = new ShapedOreRecipe(result, "wcw", "cgc", "wcw", 'w', "plankWood", 'c', Blocks.CRAFTING_TABLE, 'g', "gearWood");
        GameRegistry.addRecipe(assemblyTable);

        result = new ItemStack(ModItems.trowelIron.getItem());
        trowelIron = new ShapedOreRecipe(result, "   ", "ii ", "si ", 'i', "ingotIron", 's', "stickWood");
        GameRegistry.addRecipe(trowelIron);

        result = new ItemStack(ModItems.trowelDiamond.getItem());
        trowelDiamond = new ShapedOreRecipe(result, "   ", "dd ", "sd ", 'd', "gemDiamond", 's', "stickWood");
        GameRegistry.addRecipe(trowelDiamond);

        result = new ItemStack(ModItems.alloyHammer.getItem());
        alloyHammer = new ShapedOreRecipe(result, "ccc", "zsz", " s ", 'c', "ingotCopper", 'z', "ingotZinc", 's', "stickWood");
        GameRegistry.addRecipe(alloyHammer);

        result = new ItemStack(ModItems.multiblockCelestialCompass.getItem());
        multblockTemplateCelestialCompass = new ShapedOreRecipe(result, "cec", "eee", "cec", 'c', ModBlocks.constructionBlock.getBlock(), 'e', "ingotEthereal");
        GameRegistry.addRecipe(multblockTemplateCelestialCompass);

        result = new ItemStack(ModItems.guidebook.getItem());
        guidebook = new ShapelessOreRecipe(result, "gearWood", "gearWood", Items.BOOK);
        GameRegistry.addRecipe(guidebook);
    }

    public static void initFurnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.oreCopper.getBlock(), new ItemStack(ModItems.ingotCopper.getItem()), 0.7F);
        GameRegistry.addSmelting(ModBlocks.oreZinc.getBlock(), new ItemStack(ModItems.ingotZinc.getItem()), 0.7F);
        GameRegistry.addSmelting(ModItems.lumpBrass.getItem(), new ItemStack(ModItems.ingotBrass.getItem()), 1.5F);
        GameRegistry.addSmelting(ModItems.matterTemporal.getItem(), new ItemStack(ModItems.ingotTemporal.getItem()), 10F);
        GameRegistry.addSmelting(ModItems.matterEthereal.getItem(), new ItemStack(ModItems.ingotEthereal.getItem()), 100F);
        GameRegistry.addSmelting(ModItems.matterPhasic.getItem(), new ItemStack(ModItems.ingotPhasic.getItem()), 1000F);
        GameRegistry.addSmelting(ModItems.matterEternal.getItem(), new ItemStack(ModItems.ingotEternal.getItem()), 10000F);
    }

    public static IRecipe addGearRecipe(ItemStack result, String materialName) {
        IRecipe ret = new ShapedOreRecipe(result, " m ", "msm", " m ", 'm', materialName, 's', "stickWood");
        GameRegistry.addRecipe(ret);
        return ret;
    }

    /**
     * Called during post-init as opposed to the initialization phase for the rest of the recipes.
     */
    public static void initAlloyRecipes()
    {
        boolean copper = OreDictionary.getOres("ingotCopper").size() > 0;
        boolean zinc = OreDictionary.getOres("ingotZinc").size() > 0;
        boolean tin = OreDictionary.getOres("ingotTin").size() > 0;
        boolean brass = OreDictionary.getOres("ingotBrass").size() > 0;
        boolean bronze = OreDictionary.getOres("ingotBronze").size() > 0;
        boolean nickel = OreDictionary.getOres("ingotNickel").size() > 0;
        boolean invar = OreDictionary.getOres("ingotInvar").size() > 0;
        boolean aluminum = OreDictionary.getOres("ingotAluminum").size() > 0;
        boolean aluminumBrass = OreDictionary.getOres("ingotAluminumBrass").size() > 0;
        boolean cobalt = OreDictionary.getOres("ingotCobalt").size() > 0;
        boolean ardite = OreDictionary.getOres("ingotArdite").size() > 0;
        boolean manyullyn = OreDictionary.getOres("ingotManyullyn").size() > 0;

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
            first = new AlloyRecipes.RecipeComponent(new ItemStack(Items.IRON_INGOT), (byte) 2);
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
