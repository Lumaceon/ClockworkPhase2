package lumaceon.mods.clockworkphase2.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRecipe;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRegistry;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.recipe.timestream.TimestreamRecipeLightning;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes
{
    public static void init()
    {
        initClockworkComponentRecipes();
        initClockworkConstructs();
        initTemporalClockworkModules();
        initTimezoneModules();
        initMisc();
        initFurnaceRecipes();
        initTimestreamRecipes();
    }

    public static void initClockworkComponentRecipes()
    {
        addGearRecipe(new ItemStack(ModItems.gearWood), "plankWood");
        addGearRecipe(new ItemStack(ModItems.gearStone), "cobblestone");
        addGearRecipe(new ItemStack(ModItems.gearStone), "stone");
        addGearRecipe(new ItemStack(ModItems.gearIron), "ingotIron");
        addGearRecipe(new ItemStack(ModItems.gearGold), "ingotGold");
        addGearRecipe(new ItemStack(ModItems.gearQuartz), "gemQuartz");
        addGearRecipe(new ItemStack(ModItems.gearEmerald), "gemEmerald");
        addGearRecipe(new ItemStack(ModItems.gearDiamond), "gemDiamond");
    }

    public static void initClockworkConstructs()
    {
        ItemStack result;
        result = new ItemStack(ModItems.clockworkPickaxe);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bbb", " i ", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
        result = new ItemStack(ModItems.clockworkAxe);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bb ", "bi ", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(result, " bb", " ib", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
        result = new ItemStack(ModItems.clockworkShovel);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, " b ", " i ", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
        result = new ItemStack(ModItems.clockworkMultiTool);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bbb", "bib", " i ", 'b', "ingotBrass", 'i', "ingotIron"));
    }

    public static void initTemporalClockworkModules()
    {
        ItemStack result;
        result = new ItemStack(ModItems.temporalToolModuleSmelt);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bdb", "bSb", "bbb", 'b', "ingotBrass", 'd', ModItems.temporalDriveSimulate, 'S', ModItems.timestreamSmelt));
        result = new ItemStack(ModItems.temporalToolModuleSilkTouch);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bdb", "bSb", "bbb", 'b', "ingotBrass", 'd', ModItems.temporalDriveSimulate, 'S', ModItems.timestreamSilkyHarvest));
        result = new ItemStack(ModItems.temporalToolModuleTeleport);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bdb", "bSb", "bbb", 'b', "ingotBrass", 'd', ModItems.temporalDriveSimulate, 'S', ModItems.timestreamRelocation));
    }

    public static void initTimezoneModules()
    {
        ItemStack result;
        result = new ItemStack(ModItems.timezoneModuleTank);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "bgb", "gTg", "bgb", 'b', "ingotBrass", 'g', "blockGlass", 'T', ModItems.timestreamExtradimensionalTank));
    }

    public static void initMisc()
    {
        ItemStack result;
        result = new ItemStack(ModItems.lumpBrass);
        GameRegistry.addRecipe(new ShapelessOreRecipe(result, "oreCopper", "oreZinc"));
        result = new ItemStack(ModBlocks.assemblyTable);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "wcw", "cgc", "wcw", 'w', "plankWood", 'c', Blocks.crafting_table, 'g', "gearWood"));
        result = new ItemStack(ModItems.temporalDriveSimulate);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, " bb", "btb", "bb ", 'b', "ingotBrass", 't', "ingotTemporal"));
        result = new ItemStack(ModItems.temporalDriveModify);
        GameRegistry.addRecipe(new ShapedOreRecipe(result, "tbt", "bTb", "tbt", 't', "ingotTemporal", 'b', "ingotBrass", 'T', "blockTemporal"));
        //result = new ItemStack(ModItems.temporalDriveDuplicate);
        //GameRegistry.addRecipe(new ShapedOreRecipe(result, "", "", ""));
    }

    public static void initFurnaceRecipes()
    {
        GameRegistry.addSmelting(ModBlocks.oreCopper, new ItemStack(ModItems.ingotCopper), 0.7F);
        GameRegistry.addSmelting(ModBlocks.oreZinc, new ItemStack(ModItems.ingotZinc), 0.7F);
        GameRegistry.addSmelting(ModItems.lumpBrass, new ItemStack(ModItems.ingotBrass), 1.5F);
    }

    public static void initTimestreamRecipes()
    {
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("relocation", TimeConverter.HOUR, Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_RELOCATION, new ItemStack(ModItems.timestreamRelocation)));
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("smelt", TimeConverter.WEEK, Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_SMELT, new ItemStack(ModItems.timestreamSmelt)));
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("silky", TimeConverter.DAY, Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_SILKY, new ItemStack(ModItems.timestreamSilkyHarvest)));
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("tank", TimeConverter.MONTH, Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_TANK, new ItemStack(ModItems.timestreamExtradimensionalTank)));
        TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamRecipeLightning("lightning", TimeConverter.MINUTE, Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_LIGHTNING, new ItemStack(ModItems.timestreamLightning)));
        //TimestreamCraftingRegistry.registerTimestreamRecipe(new TimestreamCraftingRecipe("contract", Textures.ITEM.MAINSPRING, Textures.GUI.TS_BG_CONTRACT));
    }

    public static void addGearRecipe(ItemStack result, String materialName) {
        GameRegistry.addRecipe(new ShapedOreRecipe(result, " m ", "msm", " m ", 'm', materialName, 's', "stickWood"));
    }
}
