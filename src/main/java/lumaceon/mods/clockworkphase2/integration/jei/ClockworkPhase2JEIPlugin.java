package lumaceon.mods.clockworkphase2.integration.jei;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.integration.jei.alloyfurnace.AlloyFurnaceRecipeCategory;
import lumaceon.mods.clockworkphase2.integration.jei.alloyfurnace.AlloyFurnaceRecipeHandler;
import lumaceon.mods.clockworkphase2.integration.jei.alloyfurnace.AlloyFurnaceRecipeWrapperFactory;
import lumaceon.mods.clockworkphase2.integration.jei.armillary.ArmillaryRecipeCategory;
import lumaceon.mods.clockworkphase2.integration.jei.armillary.ArmillaryRecipeHandler;
import lumaceon.mods.clockworkphase2.integration.jei.armillary.ArmillaryRecipeWrapperFactory;
import lumaceon.mods.clockworkphase2.integration.jei.crusher.CrusherRecipeCategory;
import lumaceon.mods.clockworkphase2.integration.jei.crusher.CrusherRecipeHandler;
import lumaceon.mods.clockworkphase2.integration.jei.crusher.CrusherRecipeWrapperFactory;
import lumaceon.mods.clockworkphase2.integration.jei.crystallizer.CrystallizerRecipeCategory;
import lumaceon.mods.clockworkphase2.integration.jei.crystallizer.CrystallizerRecipeHandler;
import lumaceon.mods.clockworkphase2.integration.jei.crystallizer.CrystallizerRecipeWrapperFactory;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.recipe.AlloyRecipes;
import lumaceon.mods.clockworkphase2.recipe.ArmillaryRecipes;
import lumaceon.mods.clockworkphase2.recipe.CrusherRecipes;
import lumaceon.mods.clockworkphase2.recipe.CrystallizerRecipes;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

@JEIPlugin
public class ClockworkPhase2JEIPlugin implements IModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        registry.handleRecipes(AlloyRecipes.AlloyRecipe.class, new AlloyFurnaceRecipeWrapperFactory(), AlloyFurnaceRecipeCategory.UID);
        registry.handleRecipes(CrusherRecipes.CrusherRecipe.class, new CrusherRecipeWrapperFactory(), CrusherRecipeCategory.UID);
        registry.handleRecipes(CrystallizerRecipes.CrystallizerRecipe.class, new CrystallizerRecipeWrapperFactory(), CrystallizerRecipeCategory.UID);
        registry.handleRecipes(ArmillaryRecipes.ArmillaryRecipe.class, new ArmillaryRecipeWrapperFactory(), ArmillaryRecipeCategory.UID);

        registry.addRecipes(AlloyRecipes.instance.getRecipes(), AlloyFurnaceRecipeCategory.UID);
        registry.addRecipes(CrusherRecipes.instance.getRecipeClasses(), CrusherRecipeCategory.UID);
        registry.addRecipes(CrystallizerRecipes.instance.getRecipes(), CrystallizerRecipeCategory.UID);
        registry.addRecipes(ArmillaryRecipes.instance.getRecipes(), ArmillaryRecipeCategory.UID);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.clockworkAlloyFurnace), AlloyFurnaceRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.clockworkCrusher), CrusherRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.clockworkCrystallizer), CrystallizerRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModItems.temporalHourglass), ArmillaryRecipeCategory.UID);

        registry.addIngredientInfo(new ItemStack(ModItems.matterTemporal), ItemStack.class, Reference.MOD_ID + ":i_temporal_matter");
        registry.addIngredientInfo(new ItemStack(ModItems.clockworkCore), ItemStack.class, Reference.MOD_ID + ":i_clockwork");
        registry.addIngredientInfo(new ItemStack(ModItems.mainspring), ItemStack.class, Reference.MOD_ID + ":i_mainspring");
        registry.addIngredientInfo(new ItemStack(ModItems.temporalExcavator), ItemStack.class, Reference.MOD_ID + ":i_temporal_excavator");
        registry.addIngredientInfo(new ItemStack(ModItems.temporalHourglass), ItemStack.class, Reference.MOD_ID + ":i_temporal_hourglass");
        registry.addIngredientInfo(new ItemStack(ModItems.temporalToolbelt), ItemStack.class, Reference.MOD_ID + ":i_temporal_toolbelt");
        registry.addIngredientInfo(new ItemStack(ModItems.aquaticScepter), ItemStack.class, Reference.MOD_ID + ":i_aquatic_scepter");
        //Trowels
        //Alloy hammer
        registry.addIngredientInfo(new ItemStack(ModItems.toolUpgradeTemporalInfuser), ItemStack.class, Reference.MOD_ID + ":i_tool_upgrade_temporal");
        registry.addIngredientInfo(new ItemStack(ModItems.toolUpgradeSilk), ItemStack.class, Reference.MOD_ID + ":i_tool_upgrade_silk");
        registry.addIngredientInfo(new ItemStack(ModItems.toolUpgradeFurnace), ItemStack.class, Reference.MOD_ID + ":i_tool_upgrade_furnace");
        registry.addIngredientInfo(new ItemStack(ModItems.toolUpgradeRelocate), ItemStack.class, Reference.MOD_ID + ":i_tool_upgrade_teleport");
        registry.addIngredientInfo(new ItemStack(ModItems.toolUpgradeArea), ItemStack.class, Reference.MOD_ID + ":i_tool_upgrade_area");
        registry.addIngredientInfo(new ItemStack(ModItems.toolUpgradeFortune), ItemStack.class, Reference.MOD_ID + ":i_tool_upgrade_fortune");
        registry.addIngredientInfo(new ItemStack(ModItems.multiblockCelestialCompass), ItemStack.class, Reference.MOD_ID + ":i_celestial_compass");
        registry.addIngredientInfo(new ItemStack(ModItems.multiblockArmillaryRing), ItemStack.class, Reference.MOD_ID + ":i_armillary_ring");
        registry.addIngredientInfo(new ItemStack(ModItems.moonFlowerSeeds), ItemStack.class, Reference.MOD_ID + ":i_moon_flower_seeds");
        registry.addIngredientInfo(new ItemStack(ModItems.assemblyTable), ItemStack.class, Reference.MOD_ID + ":i_assembly_table");

        registry.addIngredientInfo(new ItemStack(ModBlocks.relicMoonFlower), ItemStack.class, Reference.MOD_ID + ":i_moon_flower_relic");
        registry.addIngredientInfo(new ItemStack(ModBlocks.relicUnknown), ItemStack.class, Reference.MOD_ID + ":i_unknown_relic");
        registry.addIngredientInfo(new ItemStack(ModBlocks.clockworkFurnace), ItemStack.class, Reference.MOD_ID + ":i_clockwork_furnace");
        registry.addIngredientInfo(new ItemStack(ModBlocks.clockworkAlloyFurnace), ItemStack.class, Reference.MOD_ID + ":i_clockwork_alloy_furnace");
        registry.addIngredientInfo(new ItemStack(ModBlocks.clockworkCrusher), ItemStack.class, Reference.MOD_ID + ":i_clockwork_crusher");
        registry.addIngredientInfo(new ItemStack(ModBlocks.clockworkCrystallizer), ItemStack.class, Reference.MOD_ID + ":i_clockwork_crystallizer");
        registry.addIngredientInfo(new ItemStack(ModBlocks.experienceExtractor), ItemStack.class, Reference.MOD_ID + ":i_experience_extractor");
        registry.addIngredientInfo(new ItemStack(ModBlocks.temporalRelay), ItemStack.class, Reference.MOD_ID + ":i_temporal_relay");
        registry.addIngredientInfo(new ItemStack(ModBlocks.temporalZoningMachine), ItemStack.class, Reference.MOD_ID + ":i_timezones");
        registry.addIngredientInfo(new ItemStack(ModBlocks.armillaryRingController), ItemStack.class, Reference.MOD_ID + ":i_armillary_ring");
        registry.addIngredientInfo(new ItemStack(ModBlocks.basicWindingBox), ItemStack.class, Reference.MOD_ID + ":i_basic_winding_box");
        registry.addIngredientInfo(new ItemStack(ModBlocks.multiblockAssembler), ItemStack.class, Reference.MOD_ID + ":i_multiblock_assembler");
        registry.addIngredientInfo(new ItemStack(ModBlocks.constructionBlock), ItemStack.class, Reference.MOD_ID + ":i_multiblock_assembler");

        ArrayList<ItemStack> itemList = new ArrayList<>(4);
        itemList.add(new ItemStack(ModItems.clockworkPickaxe));
        itemList.add(new ItemStack(ModItems.clockworkAxe));
        itemList.add(new ItemStack(ModItems.clockworkShovel));
        itemList.add(new ItemStack(ModItems.clockworkSword));
        registry.addIngredientInfo(itemList, ItemStack.class, Reference.MOD_ID + ":i_clockwork_tools");

        itemList = new ArrayList<>(2);
        itemList.add(new ItemStack(ModBlocks.relicMoonFlower));
        itemList.add(new ItemStack(ModBlocks.relicUnknown));
        registry.addIngredientInfo(itemList, ItemStack.class, Reference.MOD_ID + ":i_relics");

        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        if(blacklist != null)
        {
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.bugSwatter));
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.armillaryRing));
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.armillaryRingFrame));
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.armillaryRingFrameBottom));
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.armillaryRingFrameBottomCorner));
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.celestialCompass));
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.celestialCompassSB));
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.assemblyTable));
            blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.moonFlower));
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(
                new AlloyFurnaceRecipeCategory(guiHelper),
                new CrusherRecipeCategory(guiHelper),
                new CrystallizerRecipeCategory(guiHelper),
                new ArmillaryRecipeCategory(guiHelper)
        );
    }
}