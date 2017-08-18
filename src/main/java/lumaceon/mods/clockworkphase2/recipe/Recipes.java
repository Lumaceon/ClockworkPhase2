package lumaceon.mods.clockworkphase2.recipe;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class Recipes
{
    public static void init()
    {
        /*initTemporalClockworkModules();
        initMetalBlocks();
        initBlocks();
        initMisc();*/
        initFurnaceRecipes();
        initCrystallizerRecipes();
        initArmillaryFishingRecipes();
        initEntityConstructionRecipes();
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

        /*result = new ItemStack(ModItems.toolUpgradeTemporalInfuser);
        toolUpgradeTemporal = new ShapedOreRecipe(result, "bdb", "btb", "bbb", 'b', "ingotBrass", 't', "ingotTemporal", 'd', Items.DIAMOND_PICKAXE);
        GameRegistry.addRecipe(toolUpgradeTemporal);

        result = new ItemStack(ModItems.toolUpgradeSilk);
        toolUpgradeSilk = new ShapedOreRecipe(result, "bDb", "btb", "bbb", 'b', "ingotBrass", 't', "ingotTemporal", 'D', "blockDiamond");
        GameRegistry.addRecipe(toolUpgradeSilk);

        result = new ItemStack(ModItems.toolUpgradeRelocate);
        toolUpgradeRelocate = new ShapedOreRecipe(result, "beb", "btb", "bbb", 'b', "ingotBrass", 't', "ingotTemporal", 'e', Items.ENDER_PEARL);
        GameRegistry.addRecipe(toolUpgradeRelocate);

        result = new ItemStack(ModItems.toolUpgradeArea);
        toolUpgradeArea = new ShapedOreRecipe(result, "bSb", "beb", "bbb", 'b', "ingotBrass", 'e', "ingotEthereal", 'S', Items.NETHER_STAR);
        GameRegistry.addRecipe(toolUpgradeArea);

        result = new ItemStack(ModItems.toolUpgradeFortune);
        toolUpgradeFortune = new ShapedOreRecipe(result, "beb", "btb", "bbb", 'b', "ingotBrass", 't', "ingotTemporal", 'e', "gemEmerald");
        GameRegistry.addRecipe(toolUpgradeFortune);*/
    }

    public static IRecipe basicWindingBox;
    public static IRecipe multblockAssembler;
    public static IRecipe constructionBlock;
    public static void initBlocks()
    {
        /*ItemStack result;

        result = new ItemStack(ModBlocks.basicWindingBox);
        basicWindingBox = new ShapedOreRecipe(result, "ibi", "bBb", "ibi", 'i', "ingotIron", 'b', "ingotBrass", 'B', "gearIron");
        GameRegistry.addRecipe(basicWindingBox);

        result = new ItemStack(ModBlocks.multiblockAssembler);
        multblockAssembler = new ShapedOreRecipe(result, "cbc", "beb", "cbc", 'c', ModBlocks.constructionBlock, 'b', "ingotBrass", 'e', Items.ENDER_PEARL);
        GameRegistry.addRecipe(multblockAssembler);

        result = new ItemStack(ModBlocks.constructionBlock);
        constructionBlock = new ShapedOreRecipe(result, "sws", "wiw", "sws", 's', "stickWood", 'w', "plankWood", 'i', "ingotIron");
        GameRegistry.addRecipe(constructionBlock);*/
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
        /*ItemStack result;

        result = new ItemStack(ModItems.dustBrass);
        result.stackSize = 2;
        brassLump = new ShapelessOreRecipeDegradeItem(result, ModItems.alloyHammer, "oreCopper", "oreCopper", "oreCopper", "oreZinc", ModItems.alloyHammer);
        GameRegistry.addRecipe(brassLump);

        result = new ItemStack(ModItems.assemblyTable);
        assemblyTable = new ShapedOreRecipe(result, "wcw", "cgc", "wcw", 'w', "plankWood", 'c', Blocks.CRAFTING_TABLE, 'g', "gearWood");
        GameRegistry.addRecipe(assemblyTable);

        result = new ItemStack(ModItems.trowelIron);
        trowelIron = new ShapedOreRecipe(result, "   ", "ii ", "si ", 'i', "ingotIron", 's', "stickWood");
        GameRegistry.addRecipe(trowelIron);

        result = new ItemStack(ModItems.trowelDiamond);
        trowelDiamond = new ShapedOreRecipe(result, "   ", "dd ", "sd ", 'd', "gemDiamond", 's', "stickWood");
        GameRegistry.addRecipe(trowelDiamond);

        result = new ItemStack(ModItems.alloyHammer);
        alloyHammer = new ShapedOreRecipe(result, "ccc", "zsz", " s ", 'c', "ingotCopper", 'z', "ingotZinc", 's', "stickWood");
        GameRegistry.addRecipe(alloyHammer);

        result = new ItemStack(ModItems.multiblockCelestialCompass);
        multblockTemplateCelestialCompass = new ShapedOreRecipe(result, "cec", "eee", "cec", 'c', ModBlocks.constructionBlock, 'e', "ingotEthereal");
        GameRegistry.addRecipe(multblockTemplateCelestialCompass);

        result = new ItemStack(ModItems.guidebook);
        guidebook = new ShapelessOreRecipe(result, "gearWood", "gearWood", Items.BOOK);
        GameRegistry.addRecipe(guidebook);*/
    }

    public static void initFurnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(ModItems.ingotCopper), 0.7F);
        GameRegistry.addSmelting(ModBlocks.oreZinc, new ItemStack(ModItems.ingotZinc), 0.7F);
        GameRegistry.addSmelting(ModBlocks.oreAluminum, new ItemStack(ModItems.ingotAluminum), 0.7F);
        GameRegistry.addSmelting(ModItems.dustCopper, new ItemStack(ModItems.ingotCopper), 0.0F);
        GameRegistry.addSmelting(ModItems.dustZinc, new ItemStack(ModItems.ingotZinc), 0.0F);
        GameRegistry.addSmelting(ModItems.dustBrass, new ItemStack(ModItems.ingotBrass), 0.0F);
        GameRegistry.addSmelting(ModItems.dustAluminum, new ItemStack(ModItems.ingotAluminum), 0.0F);
        GameRegistry.addSmelting(ModItems.matterTemporal, new ItemStack(ModItems.ingotTemporal), 10F);
    }

    public static void initCrystallizerRecipes()
    {
        NonNullList<ItemStack> input;
        FluidStack fluid;
        ItemStack output;

        //Coal(16) to Diamond.
        input = NonNullList.withSize(1, ItemStack.EMPTY);
        input.set(0, new ItemStack(Items.COAL));
        input.get(0).setCount(8);
        output = new ItemStack(Items.DIAMOND);
        CrystallizerRecipes.instance.addCrystallizerRecipe(input, null, output);

        //Charcoal(16) to Diamond.
        input = NonNullList.withSize(1, ItemStack.EMPTY);
        input.set(0, new ItemStack(Items.COAL));
        input.get(0).setCount(8);
        input.get(0).setItemDamage(1); //CHARCOAL
        output = new ItemStack(Items.DIAMOND);
        CrystallizerRecipes.instance.addCrystallizerRecipe(input, null, output);

        //Cobblestone(64), Aluminum Ingot(1), Silicon(3), Water(10k) to Moonstone.
        input = NonNullList.withSize(3, ItemStack.EMPTY);
        input.set(0, new ItemStack(Blocks.COBBLESTONE));
        input.get(0).setCount(64);
        input.set(1, new ItemStack(ModItems.ingotAluminum));
        input.get(1).setCount(1);
        input.set(2, new ItemStack(ModItems.silicon));
        input.get(2).setCount(3);
        fluid = new FluidStack(FluidRegistry.WATER, 10000);
        output = new ItemStack(ModItems.gemMoonstone);
        CrystallizerRecipes.instance.addCrystallizerRecipe(input, fluid, output);

        //Emerald(8), Aluminum Ingot(2), Water(4k) -> Chrysoberyl (Also known by its variant, Alexandrite).
        input = NonNullList.withSize(2, ItemStack.EMPTY);
        input.set(0, new ItemStack(Items.EMERALD));
        input.get(0).setCount(8);
        input.set(1, new ItemStack(ModItems.ingotAluminum));
        input.get(1).setCount(2);
        fluid = new FluidStack(FluidRegistry.WATER, 4000);
        output = new ItemStack(ModItems.gemChrysoberyl);
        CrystallizerRecipes.instance.addCrystallizerRecipe(input, fluid, output);

        //Bone(3), Coal(3), Water(9k) -> Pearl
        input = NonNullList.withSize(2, ItemStack.EMPTY);
        input.set(0, new ItemStack(Items.BONE));
        input.get(0).setCount(3);
        input.set(1, new ItemStack(Items.COAL));
        input.get(1).setCount(3);
        fluid = new FluidStack(FluidRegistry.WATER, 9000);
        output = new ItemStack(ModItems.gemPearl);
        CrystallizerRecipes.instance.addCrystallizerRecipe(input, fluid, output);

        //Leaves(32), Aluminum Ingot(2), Water(3k) -> Spinel
        input = NonNullList.withSize(2, ItemStack.EMPTY);
        input.set(0, new ItemStack(Blocks.LEAVES));
        input.get(0).setCount(32);
        input.set(1, new ItemStack(ModItems.ingotAluminum));
        input.get(1).setCount(2);
        fluid = new FluidStack(FluidRegistry.WATER, 3000);
        output = new ItemStack(ModItems.gemSpinel);
        CrystallizerRecipes.instance.addCrystallizerRecipe(input, fluid, output);
    }

    public static void initArmillaryFishingRecipes()
    {
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.GRASS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.COBBLESTONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.ARROW)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearDiamond)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearEmerald)));
    }

    public static void initEntityConstructionRecipes()
    {
        NonNullList<ItemStack> inputStacks;
        ItemStack input = null;

        inputStacks = NonNullList.withSize(2, ItemStack.EMPTY);
        inputStacks.set(0, new ItemStack(Items.FEATHER));
        inputStacks.set(1, new ItemStack(Items.CHICKEN));
        EntityConstructionRecipes.INSTANCE.addRecipe("chicken", inputStacks, EntityChicken.class);

        inputStacks = NonNullList.withSize(2, ItemStack.EMPTY);
        inputStacks.set(0, new ItemStack(Items.LEATHER));
        inputStacks.set(1, new ItemStack(Items.BEEF));
        EntityConstructionRecipes.INSTANCE.addRecipe("cow", inputStacks, EntityCow.class);

        inputStacks = NonNullList.withSize(1, new ItemStack(Items.PORKCHOP));
        EntityConstructionRecipes.INSTANCE.addRecipe("pig", inputStacks, EntityPig.class);
    }

    /** START POST-INITIALIZATION MACHINE RECIPES **/
    /** _________________________________________ **/

    /**
     * Initializes certain pre-specified recipes, as long as the respective ingots exist to the OreDictionary.
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
            first = new AlloyRecipes.RecipeComponent(new ItemStack(ModItems.ingotCopper), (byte) 3);
            second = new AlloyRecipes.RecipeComponent(new ItemStack(ModItems.ingotZinc), (byte) 1);
            output = new ItemStack(ModItems.ingotBrass);
            output.setCount(4);
            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }

        if(copper && tin && bronze)
        {
            first = new AlloyRecipes.RecipeComponent(new ItemStack(ModItems.ingotCopper), (byte) 3);
            second = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotTin").get(0), (byte) 1);
            output = OreDictionary.getOres("ingotBronze").get(0).copy();
            output.setCount(4);
            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }

        if(nickel && invar)
        {
            first = new AlloyRecipes.RecipeComponent(new ItemStack(Items.IRON_INGOT), (byte) 2);
            second = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotNickel").get(0), (byte) 1);
            output = OreDictionary.getOres("ingotInvar").get(0).copy();
            output.setCount(3);
            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }

        if(copper && aluminum && aluminumBrass)
        {
            first = new AlloyRecipes.RecipeComponent(new ItemStack(ModItems.ingotCopper), (byte) 1);
            second = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotAluminum").get(0), (byte) 3);
            output = OreDictionary.getOres("ingotAluminumBrass").get(0).copy();
            output.setCount(4);
            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }

        if(cobalt && ardite && manyullyn)
        {
            first = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotCobalt").get(0), (byte) 1);
            second = new AlloyRecipes.RecipeComponent(OreDictionary.getOres("ingotArdite").get(0), (byte) 1);
            output = OreDictionary.getOres("ingotManyullyn").get(0).copy();
            output.setCount(2);
            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }
    }

    public static void initCrusherRecipes()
    {
        ItemStack input;
        ItemStack output;

        String[] names = OreDictionary.getOreNames();
        ArrayList<String> dusts = new ArrayList<>();
        for(String name : names)
        {
            //Check to see if the name starts with dust and has a capital directly after it.
            if(name.length() > 4 && name.startsWith("dust") && Character.isUpperCase(name.charAt(4)))
            {
                dusts.add(name);
            }
        }

        //Of the dusts we found.
        for(String dust : dusts)
        {
            List<ItemStack> validDusts = OreDictionary.getOres(dust);
            if(validDusts.size() <= 0)
                continue;

            //Most of the time, we really only care about the first dust. Most modders agree on what dust does.
            ItemStack firstDust = validDusts.get(0);

            ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(firstDust);
            if(smeltingResult.isEmpty())
                continue; //Since we're reversing smelting recipes, if it doesn't have a smelting recipe, we ignore it.

            if(smeltingResult.getCount() > 1)
                continue; //We're also only concerned with 1-to-1 recipes here.

            //Register a recipe for crushing the thing you can smelt the dust into (ex: gold ingot -> dust).
            input = smeltingResult;
            output = firstDust;
            CrusherRecipes.instance.addCrusherRecipe(input, output);

            String ore = "ore" + dust.substring(4); //Does the dust have a corresponding ore registered?
            List<ItemStack> validOres = OreDictionary.getOres(ore);
            if(validOres.size() <= 0)
                continue;

            ItemStack firstOre = validOres.get(0);
            ItemStack oreSmelting = FurnaceRecipes.instance().getSmeltingResult(firstOre);
            if(!oreSmelting.isEmpty() && OreDictionary.itemMatches(smeltingResult, oreSmelting, false))
            {
                //There is an ore, which smelts into the same item the dust does, so add the crusher recipe.
                input = firstOre;
                output = firstDust.copy();
                output.setCount(2);
                CrusherRecipes.instance.addCrusherRecipe(input, output);
            }
        }

        //Stone bricks to cracked stone bricks.
        input = new ItemStack(Blocks.STONEBRICK);
        input.setItemDamage(BlockStoneBrick.DEFAULT_META);
        output = new ItemStack(Blocks.STONEBRICK);
        output.setItemDamage(BlockStoneBrick.CRACKED_META);
        CrusherRecipes.instance.addCrusherRecipe(input, output);

        //Cracked stone bricks to cobble.
        input = new ItemStack(Blocks.STONEBRICK);
        input.setItemDamage(BlockStoneBrick.CRACKED_META);
        output = new ItemStack(Blocks.COBBLESTONE);
        CrusherRecipes.instance.addCrusherRecipe(input, output);

        //Stone to cobble.
        input = new ItemStack(Blocks.STONE);
        output = new ItemStack(Blocks.COBBLESTONE);
        CrusherRecipes.instance.addCrusherRecipe(input, output);

        //Cobble to gravel.
        input = new ItemStack(Blocks.COBBLESTONE);
        output = new ItemStack(Blocks.GRAVEL);
        CrusherRecipes.instance.addCrusherRecipe(input, output);

        //Gravel to snad.
        input = new ItemStack(Blocks.GRAVEL);
        output = new ItemStack(Blocks.SAND);
        CrusherRecipes.instance.addCrusherRecipe(input, output);
    }
}
