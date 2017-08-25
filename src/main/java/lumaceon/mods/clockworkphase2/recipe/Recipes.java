package lumaceon.mods.clockworkphase2.recipe;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionUtils;
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
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.STONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.GRASS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.DIRT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.COBBLESTONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.PLANKS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SAND)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.GRAVEL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.IRON_ORE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.GOLD_ORE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.COAL_ORE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.LAPIS_ORE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.QUARTZ_ORE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.EMERALD_ORE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.DIAMOND_ORE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.REDSTONE_ORE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.LOG)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.LOG2)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SPONGE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.GLASS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SANDSTONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.WOOL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.BRICK_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.BOOKSHELF)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.OBSIDIAN)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.ICE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SNOW)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.CLAY)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.PUMPKIN)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.NETHERRACK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SOUL_SAND)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.GLOWSTONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.MELON_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.MYCELIUM)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.NETHER_BRICK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.QUARTZ_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.PRISMARINE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SEA_LANTERN)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.HAY_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.COAL_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.PACKED_ICE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.RED_SANDSTONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.END_BRICKS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.MAGMA)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.NETHER_WART_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.BONE_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.CONCRETE)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.LEAVES)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.LEAVES2)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.WEB)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SAPLING)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.RED_FLOWER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.YELLOW_FLOWER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.BROWN_MUSHROOM)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.RED_MUSHROOM)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.TORCH)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.CRAFTING_TABLE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.FURNACE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.LADDER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.CACTUS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.JUKEBOX)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.OAK_FENCE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.OAK_FENCE_GATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.IRON_BARS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.GLASS_PANE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.VINE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.WATERLILY)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.NETHER_BRICK_FENCE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.ENCHANTING_TABLE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.ENDER_CHEST)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.COBBLESTONE_WALL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.ANVIL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SLIME_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.CARPET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.CHORUS_PLANT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.CHORUS_FLOWER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.WHITE_SHULKER_BOX)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.BLACK_SHULKER_BOX)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.PAINTING)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BED)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.ITEM_FRAME)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FLOWER_POT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SKULL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.ARMOR_STAND)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BANNER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.END_CRYSTAL)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.DISPENSER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.NOTEBLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.PISTON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.STICKY_PISTON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.TNT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.LEVER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.STONE_PRESSURE_PLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.REDSTONE_TORCH)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.WOODEN_BUTTON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.STONE_BUTTON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.TRAPDOOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.REDSTONE_LAMP)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.TRIPWIRE_HOOK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.DAYLIGHT_DETECTOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.REDSTONE_BLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.HOPPER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.DROPPER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.IRON_TRAPDOOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.OBSERVER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.OAK_DOOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.BIRCH_DOOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.SPRUCE_DOOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.IRON_DOOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.REDSTONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.REPEATER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COMPARATOR)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.RAIL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.ACTIVATOR_RAIL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.DETECTOR_RAIL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Blocks.GOLDEN_RAIL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.MINECART)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SADDLE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BOAT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BIRCH_BOAT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.ACACIA_BOAT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DARK_OAK_BOAT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.JUNGLE_BOAT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SPRUCE_BOAT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CARROT_ON_A_STICK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.TNT_MINECART)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FURNACE_MINECART)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.HOPPER_MINECART)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COAL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_INGOT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLD_INGOT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.STICK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BOWL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.STRING)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FEATHER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GUNPOWDER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WHEAT_SEEDS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WHEAT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FLINT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BUCKET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WATER_BUCKET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.LAVA_BUCKET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SNOWBALL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.LEATHER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.MILK_BUCKET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BRICK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CLAY_BALL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.REEDS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.PAPER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BOOK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SLIME_BALL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.EGG)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GLOWSTONE_DUST)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DYE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BONE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SUGAR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.MELON_SEEDS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.PUMPKIN_SEEDS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BEETROOT_SEEDS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.ENDER_PEARL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BLAZE_ROD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLD_NUGGET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.NETHER_WART)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.ENDER_EYE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.EXPERIENCE_BOTTLE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FIRE_CHARGE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WRITABLE_BOOK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.EMERALD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.MAP)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.NETHERBRICK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.QUARTZ)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.PRISMARINE_SHARD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.PRISMARINE_CRYSTALS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RABBIT_HIDE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_HORSE_ARMOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_HORSE_ARMOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_HORSE_ARMOR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CHORUS_FRUIT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CHORUS_FRUIT_POPPED)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_NUGGET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_11)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_13)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_BLOCKS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_CAT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_CHIRP)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_FAR)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_MALL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_MELLOHI)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_STAL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_STRAD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_WAIT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RECORD_WARD)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.APPLE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.MUSHROOM_STEW)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BREAD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.PORKCHOP)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COOKED_PORKCHOP)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_APPLE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FISH)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COOKED_FISH)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CAKE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COOKIE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.MELON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BEEF)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COOKED_BEEF)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CHICKEN)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COOKED_CHICKEN)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.ROTTEN_FLESH)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SPIDER_EYE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CARROT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.POTATO)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BAKED_POTATO)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.POISONOUS_POTATO)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.PUMPKIN_PIE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RABBIT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COOKED_RABBIT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RABBIT_STEW)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.MUTTON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COOKED_MUTTON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BEETROOT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BEETROOT_SOUP)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_SHOVEL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_PICKAXE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_AXE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FLINT_AND_STEEL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WOODEN_SHOVEL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WOODEN_PICKAXE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WOODEN_AXE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.STONE_SHOVEL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.STONE_AXE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.STONE_PICKAXE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_SHOVEL)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_AXE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_PICKAXE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_HOE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WOODEN_HOE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.STONE_HOE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_HOE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_HOE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.COMPASS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FISHING_ROD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CLOCK)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SHEARS)));
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.EFFICIENCY, 7));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.SILK_TOUCH, 1));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.UNBREAKING, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.FORTUNE, 4));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.LUCK_OF_THE_SEA, 4));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.LURE, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.MENDING, 1));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.VANISHING_CURSE, 1));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.LEAD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.NAME_TAG)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BOW)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.ARROW)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_SWORD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.WOODEN_SWORD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.STONE_SWORD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_SWORD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_SWORD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.LEATHER_HELMET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.LEATHER_CHESTPLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.LEATHER_LEGGINGS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.LEATHER_BOOTS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_HELMET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_CHESTPLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_LEGGINGS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.IRON_BOOTS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_HELMET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_CHESTPLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_LEGGINGS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_BOOTS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CHAINMAIL_HELMET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CHAINMAIL_CHESTPLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CHAINMAIL_LEGGINGS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CHAINMAIL_BOOTS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_HELMET)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_CHESTPLATE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_LEGGINGS)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.DIAMOND_BOOTS)));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.PROTECTION, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.FIRE_PROTECTION, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.FEATHER_FALLING, 10));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.BLAST_PROTECTION, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.RESPIRATION, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.AQUA_AFFINITY, 1));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.THORNS, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.DEPTH_STRIDER, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.FROST_WALKER, 3));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.BINDING_CURSE, 1));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.SHARPNESS, 7));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.SMITE, 7));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, 7));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.KNOCKBACK, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.FIRE_ASPECT, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.LOOTING, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.SWEEPING, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.UNBREAKING, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.POWER, 7));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.PUNCH, 5));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.INFINITY, 1));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantedBook.addEnchantment(enchantedBook, new EnchantmentData(Enchantments.FLAME, 1));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(enchantedBook));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SPECTRAL_ARROW)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SHIELD)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.TOTEM_OF_UNDYING)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GHAST_TEAR)));
        ItemStack potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.MUNDANE);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.THICK);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.AWKWARD);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.MUNDANE);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.LONG_NIGHT_VISION);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.LONG_INVISIBILITY);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.LONG_LEAPING);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.STRONG_LEAPING);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.LONG_FIRE_RESISTANCE);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.LONG_FIRE_RESISTANCE);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.LONG_SWIFTNESS);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.STRONG_SWIFTNESS);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.STRONG_HEALING);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.LONG_REGENERATION);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.STRONG_REGENERATION);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.LONG_STRENGTH);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        potionStack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(potionStack, PotionTypes.STRONG_STRENGTH);
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(potionStack));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GLASS_BOTTLE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BLAZE_POWDER)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.MAGMA_CREAM)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.BREWING_STAND)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.CAULDRON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.SPECKLED_MELON)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.GOLDEN_CARROT)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(Items.RABBIT_FOOT)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearWood)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearStone)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearIron)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearGold)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearCopper)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearZinc)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearDiamond)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearEmerald)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearBrass)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gearQuartz)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.ingotAluminum)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.ingotCopper)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.ingotZinc)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.ingotBrass)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.dustAluminum)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.dustCopper)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.dustZinc)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.dustBrass)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.trowelIron)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.trowelDiamond)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.silicon)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.assemblyTable)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.moonFlowerSeeds)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModBlocks.oreAluminum)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModBlocks.oreCopper)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModBlocks.oreZinc)));

        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gizmoLife)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gizmoLight)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gizmoWater)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gizmoEarth)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gizmoAir)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gizmoFire)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.gizmoAura)));
        ArmillaryFishingRecipes.INSTANCE.addRecipe(new ArmillaryFishingRecipes.ArmillaryFishingRecipe(new ItemStack(ModItems.splitTimelineMatrix)));
    }

    public static void initEntityConstructionRecipes()
    {
        NonNullList<ItemStack> inputStacks;
        ItemStack input;

        //TODO - More sensable recipes...

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

        inputStacks = NonNullList.withSize(1, new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getMetadata()));
        EntityConstructionRecipes.INSTANCE.addRecipe("squid", inputStacks, EntitySquid.class);

        inputStacks = NonNullList.withSize(1, new ItemStack(Blocks.WOOL));
        EntityConstructionRecipes.INSTANCE.addRecipe("sheep", inputStacks, EntitySheep.class);

        input = new ItemStack(Items.EMERALD);
        input.setCount(3);
        inputStacks = NonNullList.withSize(1, input);
        EntityConstructionRecipes.INSTANCE.addRecipe("villager", inputStacks, EntityVillager.class);

        inputStacks = NonNullList.withSize(1, new ItemStack(Items.BONE));
        EntityConstructionRecipes.INSTANCE.addRecipe("wolf", inputStacks, EntityWolf.class);

        inputStacks = NonNullList.withSize(1, new ItemStack(Items.RABBIT_FOOT));
        EntityConstructionRecipes.INSTANCE.addRecipe("rabbit", inputStacks, EntityRabbit.class);

        inputStacks = NonNullList.withSize(1, new ItemStack(Items.GUNPOWDER));
        EntityConstructionRecipes.INSTANCE.addRecipe("ocelot", inputStacks, EntityOcelot.class);
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
        ItemStack firstItem;
        ItemStack secondItem;
        ItemStack output;
        if(copper && zinc && brass)
        {
            firstItem = new ItemStack(ModItems.ingotCopper);
            firstItem.setCount(3);
            first = new AlloyRecipes.RecipeComponent(firstItem, (byte) 3);

            secondItem = new ItemStack(ModItems.ingotZinc);
            second = new AlloyRecipes.RecipeComponent(secondItem, (byte) 1);

            output = new ItemStack(ModItems.ingotBrass);
            output.setCount(4);

            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }

        if(copper && tin && bronze)
        {
            firstItem = new ItemStack(ModItems.ingotCopper);
            firstItem.setCount(3);
            first = new AlloyRecipes.RecipeComponent(firstItem, (byte) 3);

            secondItem = OreDictionary.getOres("ingotTin").get(0).copy();
            secondItem.setCount(1);
            second = new AlloyRecipes.RecipeComponent(secondItem, (byte) 1);

            output = OreDictionary.getOres("ingotBronze").get(0).copy();
            output.setCount(4);
            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }

        if(nickel && invar)
        {
            firstItem = new ItemStack(Items.IRON_INGOT);
            firstItem.setCount(2);
            first = new AlloyRecipes.RecipeComponent(firstItem, (byte) 2);

            secondItem = OreDictionary.getOres("ingotNickel").get(0).copy();
            secondItem.setCount(1);
            second = new AlloyRecipes.RecipeComponent(secondItem, (byte) 1);

            output = OreDictionary.getOres("ingotInvar").get(0).copy();
            output.setCount(3);
            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }

        if(copper && aluminum && aluminumBrass)
        {
            firstItem = new ItemStack(ModItems.ingotCopper);
            firstItem.setCount(1);
            first = new AlloyRecipes.RecipeComponent(firstItem, (byte) 1);

            secondItem = OreDictionary.getOres("ingotAluminum").get(0).copy();
            secondItem.setCount(3);
            second = new AlloyRecipes.RecipeComponent(secondItem, (byte) 3);

            output = OreDictionary.getOres("ingotAluminumBrass").get(0).copy();
            output.setCount(4);
            AlloyRecipes.instance.addAlloyRecipe(first, second, output);
        }

        if(cobalt && ardite && manyullyn)
        {
            firstItem = OreDictionary.getOres("ingotCobalt").get(0).copy();
            firstItem.setCount(1);
            first = new AlloyRecipes.RecipeComponent(firstItem, (byte) 1);

            secondItem = OreDictionary.getOres("ingotArdite").get(0).copy();
            secondItem.setCount(1);
            second = new AlloyRecipes.RecipeComponent(secondItem, (byte) 1);

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

        //Snad to silicon
        input = new ItemStack(Blocks.SAND);
        output = new ItemStack(ModItems.silicon);
        CrusherRecipes.instance.addCrusherRecipe(input, output);
    }
}
