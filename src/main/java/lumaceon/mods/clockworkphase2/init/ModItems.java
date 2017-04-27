package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.RelicExcavationRegistry;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.item.multiblocktemplate.ItemMultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.item.temporal.*;
import lumaceon.mods.clockworkphase2.api.util.WeightedChance;
import lumaceon.mods.clockworkphase2.item.*;
import lumaceon.mods.clockworkphase2.item.clockwork.*;
import lumaceon.mods.clockworkphase2.item.clockwork.component.ItemClockworkCore;
import lumaceon.mods.clockworkphase2.item.clockwork.component.ItemGear;
import lumaceon.mods.clockworkphase2.item.clockwork.component.ItemMainspring;
import lumaceon.mods.clockworkphase2.item.clockwork.tool.*;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.*;
import lumaceon.mods.clockworkphase2.util.ISimpleNamed;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class ModItems
{
    public static ArrayList<Item> itemsForModel = new ArrayList<Item>(200);

    public static Item.ToolMaterial clockworkMaterial = EnumHelper.addToolMaterial("CLOCKWORK", 3, 100, 1, 1, 0);
    public static Item.ToolMaterial temporalMaterial = EnumHelper.addToolMaterial("TEMPORAL", 3, 100, 8.0F, 0, 0);

    //MATERIALS
    public static Item ingotCopper;
    public static Item ingotZinc;
    public static Item ingotBrass;
    public static Item ingotTemporal;
    public static Item lumpBrass;
    public static Item matterTemporal;
    //CLOCKWORK COMPONENTS (GEARS/CLOCKWORK/MAINSPRING)
    public static Item mainspring;
    public static Item clockworkCore;
    public static Item gearWood;
    public static Item gearStone;
    public static Item gearIron;
    public static Item gearGold;
    public static Item gearDiamond;
    public static Item gearEmerald;
    public static Item gearQuartz;
    public static Item gearCopper;
    public static Item gearZinc;
    public static Item gearBrass;
    //public static Item gearParadoxium = new Item("paradoxium_gear");
    public static Item gearCreative;
    //TOOLS
    public static Item clockworkPickaxe;
    public static Item clockworkAxe;
    public static Item clockworkShovel;
    public static Item temporalExcavator;
    public static Item temporalHourglass;
    public static Item trowelIron;
    public static Item trowelDiamond;
    public static Item alloyHammer;
    //TOOL UPGRADES
    public static Item toolUpgradeTemporalInfuser;
    public static Item toolUpgradeSilk;
    public static Item toolUpgradeFurnace;
    public static Item toolUpgradeRelocate;
    public static Item toolUpgradeArea;
    public static Item toolUpgradeFortune;
    //WEAPONS
    public static Item clockworkSword;
    //MULTIBLOCK ASSEMBLERS
    public static Item multiblockCelestialCompass;
    //MISC
    public static Item bugSwatter;
    public static Item moonFlowerSeeds;
    public static Item guidebook;
    public static Item assemblyTable;
    public static void init()
    {
        //MATERIALS
        ingotCopper = new ItemClockworkPhase(64, 100, "copper_ingot");
        register(ingotCopper);
        OreDictionary.registerOre("ingotCopper", ingotCopper);

        ingotZinc = new ItemClockworkPhase(64, 100, "zinc_ingot");
        register(ingotZinc);
        OreDictionary.registerOre("ingotZinc", ingotZinc);

        ingotBrass = new ItemClockworkPhase(64, 100, "brass_ingot");
        register(ingotBrass);
        OreDictionary.registerOre("ingotBrass", ingotBrass);

        ingotTemporal = new ItemClockworkPhase(64, 100, "temporal_ingot");
        register(ingotTemporal);
        OreDictionary.registerOre("ingotTemporal", ingotTemporal);

        lumpBrass = new ItemClockworkPhase(64, 100, "brass_lump");
        register(lumpBrass);

        matterTemporal = new ItemClockworkPhase(64, 100, "matter_temporal");
        register(matterTemporal);

        //CLOCKWORK COMPONENTS (GEARS/CLOCKWORK/MAINSPRING)
        mainspring = new ItemMainspring(1, 100, "mainspring");
        register(mainspring);

        clockworkCore = new ItemClockworkCore(1, 100, "clockwork_core");
        register(clockworkCore);

        gearWood = new ItemGear("wood_gear", 10, 15, 0);
        register(gearWood);
        OreDictionary.registerOre("gearWood", gearWood);

        gearStone = new ItemGear("stone_gear", 15, 10, 1);
        register(gearStone);
        OreDictionary.registerOre("gearStone", gearStone);

        gearIron = new ItemGear("iron_gear", 35, 25, 2);
        register(gearIron);
        OreDictionary.registerOre("gearIron", gearIron);

        gearDiamond = new ItemGear("diamond_gear", 50, 40, 3);
        register(gearDiamond);
        OreDictionary.registerOre("gearDiamond", gearDiamond);

        gearEmerald = new ItemGear("emerald_gear", 40, 50, 3);
        register(gearEmerald);
        OreDictionary.registerOre("gearEmerald", gearEmerald);

        gearQuartz = new ItemGear("quartz_gear", 70, 10, 2);
        register(gearQuartz);
        OreDictionary.registerOre("gearQuartz", gearQuartz);

        gearCopper = new ItemGear("copper_gear", 20, 30, 1);
        register(gearCopper);
        OreDictionary.registerOre("gearCopper", gearCopper);

        gearZinc = new ItemGear("zinc_gear", 30, 20, 1);
        register(gearZinc);
        OreDictionary.registerOre("gearZinc", gearZinc);

        gearBrass = new ItemGear("brass_gear", 60, 30, 2);
        register(gearBrass);
        OreDictionary.registerOre("gearBrass", gearBrass);

        gearCreative = new ItemGear("creative_gear", 120, 120, 100);
        register(gearCreative);
        OreDictionary.registerOre("gearCreative", gearCreative);

        //registerItem(gearParadoxium, ItemGearParadoxium.class, nameQualitySpeedLevel, new Object[] {gearParadoxium.getUnlocalizedName(), 0, 0, 0}, "gearParadoxium");

        //TOOLS
        clockworkPickaxe = new ItemClockworkPickaxe(clockworkMaterial, "clockwork_pickaxe");
        register(clockworkPickaxe);

        clockworkAxe = new ItemClockworkAxe(clockworkMaterial, "clockwork_axe");
        register(clockworkAxe);

        clockworkShovel = new ItemClockworkShovel(clockworkMaterial, "clockwork_shovel");
        register(clockworkShovel);

        temporalExcavator = new ItemTemporalExcavator(temporalMaterial, "temporal_excavator");
        register(temporalExcavator);

        temporalHourglass = new ItemHourglass(1, 100, "temporal_hourglass", TimeConverter.HOUR);
        register(temporalHourglass);

        trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "iron_trowel");
        register(trowelIron);

        trowelDiamond = new ItemTrowel(Item.ToolMaterial.DIAMOND, "diamond_trowel");
        register(trowelDiamond);

        alloyHammer = new ItemClockworkPhase(1, 64, "alloy_hammer");
        register(alloyHammer);

        //TOOL UPGRADES
        toolUpgradeTemporalInfuser = new ItemToolUpgradeTemporalInfuser(1, 100, "tool_upgrade_temporal_infusion");
        register(toolUpgradeTemporalInfuser);

        toolUpgradeSilk = new ItemToolUpgradeSilk(1, 100, "tool_upgrade_silk");
        register(toolUpgradeSilk);

        toolUpgradeRelocate = new ItemToolUpgradeRelocate(1, 100, "tool_upgrade_relocate");
        register(toolUpgradeRelocate);

        toolUpgradeArea = new ItemToolUpgradeArea(1, 100, "tool_upgrade_area");
        register(toolUpgradeArea);

        toolUpgradeFortune = new ItemToolUpgradeFortune(1, 100, "tool_upgrade_fortune");
        register(toolUpgradeFortune);

        toolUpgradeFurnace = new ItemToolUpgradeFurnace(1, 100, "tool_upgrade_furnace");
        register(toolUpgradeFurnace);

        //WEAPONS
        clockworkSword = new ItemClockworkSword(1, 100, "clockwork_sword");
        register(clockworkSword);

        //MULTIBLOCK ASSEMBLERS
        multiblockCelestialCompass = new ItemMultiblockTemplateCelestialCompass(64, 100, "multiblock_template_celestial_compass");
        register(multiblockCelestialCompass);

        //MISC
        moonFlowerSeeds = new ItemMoonFlowerSeeds(64, 100, "moon_flower_seeds");
        register(moonFlowerSeeds);

        guidebook = new ItemGuidebook(1, 100, "guidebook");
        register(guidebook);

        assemblyTable = new ItemAssemblyTable(64, 100, "assembly_table_item");
        register(assemblyTable);

        bugSwatter = new ItemBugSwatter(1, 100, "bug_swatter");

        RelicExcavationRegistry.registerMoonFlowerRelicDrop(new WeightedChance<ItemStack>(new ItemStack(moonFlowerSeeds), 100));
    }

    //************ START HELPER METHODS ************\\

    private static void register(Item item)
    {
        GameRegistry.register(item);
        itemsForModel.add(item);
    }

    public static void initModels() {
        if(itemsForModel != null)
        {
            for(Item item : itemsForModel)
                if(item != null && item instanceof ISimpleNamed)
                    ClockworkPhase2.proxy.registerItemModel(item, ((ISimpleNamed) item).getSimpleName());
            itemsForModel.clear();
        }
        itemsForModel = null;
    }
}
