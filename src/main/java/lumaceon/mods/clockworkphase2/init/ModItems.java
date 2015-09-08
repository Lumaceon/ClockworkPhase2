package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.api.MemoryItemRegistry;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.item.*;
import lumaceon.mods.clockworkphase2.item.components.tool.temporal.ItemTemporalToolModuleHarvestTeleport;
import lumaceon.mods.clockworkphase2.item.components.tool.temporal.ItemTemporalToolModuleSilkHarvest;
import lumaceon.mods.clockworkphase2.item.components.tool.temporal.ItemTemporalToolModuleSmelt;
import lumaceon.mods.clockworkphase2.item.timestream.*;
import lumaceon.mods.clockworkphase2.item.components.tool.ItemClockworkCore;
import lumaceon.mods.clockworkphase2.item.components.tool.ItemMainspring;
import lumaceon.mods.clockworkphase2.item.components.ItemTemporalCore;
import lumaceon.mods.clockworkphase2.item.components.tool.clockwork.ItemGear;
import lumaceon.mods.clockworkphase2.item.components.tool.clockwork.ItemMemoryComponent;
import lumaceon.mods.clockworkphase2.item.construct.misc.ItemPAC;
import lumaceon.mods.clockworkphase2.item.construct.tool.ItemClockworkAxe;
import lumaceon.mods.clockworkphase2.item.construct.tool.ItemClockworkMultitool;
import lumaceon.mods.clockworkphase2.item.construct.tool.ItemClockworkPickaxe;
import lumaceon.mods.clockworkphase2.item.construct.tool.ItemClockworkShovel;
import lumaceon.mods.clockworkphase2.item.construct.weapon.ItemLightningSword;
import lumaceon.mods.clockworkphase2.item.timezonemodule.ItemTimezoneModuleMobRepellent;
import lumaceon.mods.clockworkphase2.item.timezonemodule.ItemTimezoneModuleTank;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems
{
    public static void init()
    {
        initTemporalToolModules();
        initTimezoneModules();
        initClockworkComponents();
        initTemporalComponents();
        initTimestreams();
        initTools();
        initBuckets();
        initMetals();
        initMisc();
    }

    public static Item temporalToolModuleSilkTouch;
    public static Item temporalToolModuleSmelt;
    public static Item temporalToolModuleTeleport;
    public static void initTemporalToolModules()
    {
        temporalToolModuleSilkTouch = new ItemTemporalToolModuleSilkHarvest(1, 100, Names.ITEM.TEMPORAL_TOOL_MODULE_SILK);
        temporalToolModuleSmelt = new ItemTemporalToolModuleSmelt(1, 100, Names.ITEM.TEMPORAL_TOOL_MODULE_SMELT);
        temporalToolModuleTeleport = new ItemTemporalToolModuleHarvestTeleport(1, 100, Names.ITEM.TEMPORAL_TOOL_MODULE_TELEPORT);

        GameRegistry.registerItem(temporalToolModuleSilkTouch, Names.ITEM.TEMPORAL_TOOL_MODULE_SILK);
        GameRegistry.registerItem(temporalToolModuleSmelt, Names.ITEM.TEMPORAL_TOOL_MODULE_SMELT);
        GameRegistry.registerItem(temporalToolModuleTeleport, Names.ITEM.TEMPORAL_TOOL_MODULE_TELEPORT);
    }

    public static Item timezoneModuleTank;
    public static Item timezoneModuleMobRepell;
    public static void initTimezoneModules()
    {
        timezoneModuleTank = new ItemTimezoneModuleTank(1, 100, Names.ITEM.TIMEZONE_MODULE_TANK);
        timezoneModuleMobRepell = new ItemTimezoneModuleMobRepellent(1, 100, Names.ITEM.TIMEZONE_MODULE_MOB_REPELL);

        GameRegistry.registerItem(timezoneModuleTank, Names.ITEM.TIMEZONE_MODULE_TANK);
        GameRegistry.registerItem(timezoneModuleMobRepell, Names.ITEM.TIMEZONE_MODULE_MOB_REPELL);
    }

    public static ItemMainspring mainspring;
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
    public static Item preciousCharm;
    public static Item dreamCatcher;
    public static Item ancientCoin;
    public static Item noteBottle;
    public static Item gearRusty;
    public static Item gearElysianBroken;
    public static void initClockworkComponents()
    {
        mainspring = new ItemMainspring(1, 20, Names.ITEM.MAINSPRING);
        clockworkCore = new ItemClockworkCore(1, 20, Names.ITEM.CLOCKWORK_CORE);
        gearWood = new ItemGear(Names.ITEM.GEAR_WOOD, 10, 15, 0, 0);
        gearStone = new ItemGear(Names.ITEM.GEAR_STONE, 15, 10, 0, 1);
        gearIron = new ItemGear(Names.ITEM.GEAR_IRON, 25, 25, 0, 2);
        gearGold = new ItemGear(Names.ITEM.GEAR_GOLD, 10, 70, 0, 0);
        gearDiamond = new ItemGear(Names.ITEM.GEAR_DIAMOND, 50, 40, 0, 3);
        gearEmerald = new ItemGear(Names.ITEM.GEAR_EMERALD, 40, 50, 0, 3);
        gearQuartz = new ItemGear(Names.ITEM.GEAR_QUARTZ, 70, 10, 0, 2);
        gearCopper = new ItemGear(Names.ITEM.GEAR_COPPER, 20, 30, 0, 2);
        gearZinc = new ItemGear(Names.ITEM.GEAR_ZINC, 30, 20, 0, 2);
        gearBrass = new ItemGear(Names.ITEM.GEAR_BRASS, 40, 35, 0, 2);
        preciousCharm = new ItemMemoryComponent(Names.ITEM.PRECIOUS_CHARM, 0, 0, 50, -1);
        dreamCatcher = new ItemMemoryComponent(Names.ITEM.DREAM_CATCHER, 0, 0, 40, -1);
        ancientCoin = new ItemMemoryComponent(Names.ITEM.ANCIENT_COIN, 0, 0, 30, -1);
        noteBottle = new ItemMemoryComponent(Names.ITEM.NOTE_BOTTLE, 0, 0, 20, -1);
        gearRusty = new ItemMemoryComponent(Names.ITEM.GEAR_RUSTY, 10, 30, 10, 0);
        gearElysianBroken = new ItemMemoryComponent(Names.ITEM.GEAR_BROKEN_ELYSIAN, 100, 100, 1000, 5);

        GameRegistry.registerItem(mainspring, Names.ITEM.MAINSPRING);
        GameRegistry.registerItem(clockworkCore, Names.ITEM.CLOCKWORK_CORE);
        GameRegistry.registerItem(gearWood, Names.ITEM.GEAR_WOOD);
        GameRegistry.registerItem(gearStone, Names.ITEM.GEAR_STONE);
        GameRegistry.registerItem(gearIron, Names.ITEM.GEAR_IRON);
        GameRegistry.registerItem(gearGold, Names.ITEM.GEAR_GOLD);
        GameRegistry.registerItem(gearDiamond, Names.ITEM.GEAR_DIAMOND);
        GameRegistry.registerItem(gearEmerald, Names.ITEM.GEAR_EMERALD);
        GameRegistry.registerItem(gearQuartz, Names.ITEM.GEAR_QUARTZ);
        GameRegistry.registerItem(gearCopper, Names.ITEM.GEAR_COPPER);
        GameRegistry.registerItem(gearZinc, Names.ITEM.GEAR_ZINC);
        GameRegistry.registerItem(gearBrass, Names.ITEM.GEAR_BRASS);
        GameRegistry.registerItem(preciousCharm, Names.ITEM.PRECIOUS_CHARM);
        GameRegistry.registerItem(dreamCatcher, Names.ITEM.DREAM_CATCHER);
        GameRegistry.registerItem(ancientCoin, Names.ITEM.ANCIENT_COIN);
        GameRegistry.registerItem(noteBottle, Names.ITEM.NOTE_BOTTLE);
        GameRegistry.registerItem(gearRusty, Names.ITEM.GEAR_RUSTY);
        GameRegistry.registerItem(gearElysianBroken, Names.ITEM.GEAR_BROKEN_ELYSIAN);

        MemoryItemRegistry.registerMemoryItem(preciousCharm);
        MemoryItemRegistry.registerMemoryItem(dreamCatcher);
        MemoryItemRegistry.registerMemoryItem(ancientCoin);
        MemoryItemRegistry.registerMemoryItem(noteBottle);
        MemoryItemRegistry.registerMemoryItem(gearRusty);
        //Elysian gear not registered as a memory item because it's considerably more rare and dropped separately.

        OreDictionary.registerOre("gearWood", gearWood);
        OreDictionary.registerOre("gearStone", gearStone);
        OreDictionary.registerOre("gearIron", gearIron);
        OreDictionary.registerOre("gearGold", gearGold);
        OreDictionary.registerOre("gearQuartz", gearQuartz);
        OreDictionary.registerOre("gearDiamond", gearDiamond);
        OreDictionary.registerOre("gearEmerald", gearEmerald);
        OreDictionary.registerOre("gearCopper", gearCopper);
        OreDictionary.registerOre("gearZinc", gearZinc);
        OreDictionary.registerOre("gearBrass", gearBrass);
    }

    public static Item temporalCore;
    public static void initTemporalComponents()
    {
        temporalCore = new ItemTemporalCore(1, 50, Names.ITEM.TEMPORAL_CORE);

        GameRegistry.registerItem(temporalCore, Names.ITEM.TEMPORAL_CORE);
    }

    public static ItemTimestreamRelocation timestreamRelocation;
    public static ItemTimestreamSilkyHarvest timestreamSilkyHarvest;
    public static ItemTimestreamSmelt timestreamSmelt;
    public static ItemTimestreamExtradimensionalTank timestreamExtradimensionalTank;
    public static ItemTimestreamMobRepulser timestreamMobRepulser;
    public static ItemTimestreamLightning timestreamLightning;
    public static void initTimestreams()
    {
        timestreamRelocation = new ItemTimestreamRelocation(1, 100, Names.ITEM.TIMESTREAM_RELOCATION);
        timestreamSilkyHarvest = new ItemTimestreamSilkyHarvest(1, 100, Names.ITEM.TIMESTREAM_SILKY_HARVEST);
        timestreamSmelt = new ItemTimestreamSmelt(1, 100, Names.ITEM.TIMESTREAM_SMELT);
        timestreamExtradimensionalTank = new ItemTimestreamExtradimensionalTank(1, 100, Names.ITEM.TIMESTREAM_EXTRA_TANK);
        timestreamMobRepulser = new ItemTimestreamMobRepulser(1, 100, Names.ITEM.TIMESTREAM_MOB_REPULSER);
        timestreamLightning = new ItemTimestreamLightning(1, 100, Names.ITEM.TIMESTREAM_LIGHTNING);

        GameRegistry.registerItem(timestreamRelocation, Names.ITEM.TIMESTREAM_RELOCATION);
        GameRegistry.registerItem(timestreamSilkyHarvest, Names.ITEM.TIMESTREAM_SILKY_HARVEST);
        GameRegistry.registerItem(timestreamSmelt, Names.ITEM.TIMESTREAM_SMELT);
        GameRegistry.registerItem(timestreamExtradimensionalTank, Names.ITEM.TIMESTREAM_EXTRA_TANK);
        GameRegistry.registerItem(timestreamMobRepulser, Names.ITEM.TIMESTREAM_MOB_REPULSER);
        GameRegistry.registerItem(timestreamLightning, Names.ITEM.TIMESTREAM_LIGHTNING);
    }

    public static Item.ToolMaterial clockworkMaterial = EnumHelper.addToolMaterial("CLOCKWORK", 3, 100, 0, 0, 0);
    public static Item clockworkPickaxe;
    public static Item clockworkAxe;
    public static Item clockworkShovel;
    public static Item clockworkMultiTool;
    public static Item pseudoTemporalHourglass;
    public static Item temporalHourglass;
    public static Item trowelStone;
    public static Item trowelIron;
    public static Item trowelDiamond;
    public static Item lightningSword;
    public static Item PAC;
    public static void initTools()
    {
        clockworkPickaxe = new ItemClockworkPickaxe(clockworkMaterial, Names.ITEM.CLOCKWORK_PICKAXE);
        clockworkAxe = new ItemClockworkAxe(clockworkMaterial, Names.ITEM.CLOCKWORK_AXE);
        clockworkShovel = new ItemClockworkShovel(clockworkMaterial, Names.ITEM.CLOCKWORK_SHOVEL);
        clockworkMultiTool = new ItemClockworkMultitool(0, clockworkMaterial, Names.ITEM.CLOCKWORK_MULTI_TOOL);
        pseudoTemporalHourglass = new ItemTemporalHourglass(1, 100, TimeConverter.DAY, Names.ITEM.PSEUDO_TEMPORAL_HOURGLASS);
        temporalHourglass = new ItemTemporalHourglass(1, 100, TimeConverter.YEAR, Names.ITEM.TEMPORAL_HOURGLASS);
        trowelStone = new ItemTrowel(Item.ToolMaterial.STONE, 1, Names.ITEM.STONE_TROWEL);
        trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, 1, Names.ITEM.IRON_TROWEL);
        trowelDiamond = new ItemTrowel(Item.ToolMaterial.EMERALD, 1, Names.ITEM.DIAMOND_TROWEL); //Says emerald; is actually diamond.
        lightningSword = new ItemLightningSword(clockworkMaterial, Names.ITEM.LIGHTNING_SWORD);
        PAC = new ItemPAC(1, 100, Names.ITEM.PAC);

        GameRegistry.registerItem(clockworkPickaxe, Names.ITEM.CLOCKWORK_PICKAXE);
        GameRegistry.registerItem(clockworkAxe, Names.ITEM.CLOCKWORK_AXE);
        GameRegistry.registerItem(clockworkShovel, Names.ITEM.CLOCKWORK_SHOVEL);
        GameRegistry.registerItem(clockworkMultiTool, Names.ITEM.CLOCKWORK_MULTI_TOOL);
        GameRegistry.registerItem(pseudoTemporalHourglass, Names.ITEM.PSEUDO_TEMPORAL_HOURGLASS);
        GameRegistry.registerItem(temporalHourglass, Names.ITEM.TEMPORAL_HOURGLASS);
        GameRegistry.registerItem(trowelStone, Names.ITEM.STONE_TROWEL);
        GameRegistry.registerItem(trowelIron, Names.ITEM.IRON_TROWEL);
        GameRegistry.registerItem(trowelDiamond, Names.ITEM.DIAMOND_TROWEL);
        GameRegistry.registerItem(lightningSword, Names.ITEM.LIGHTNING_SWORD);
        GameRegistry.registerItem(PAC, Names.ITEM.PAC);
    }

    public static Item bucketTimeSand;
    public static void initBuckets()
    {

    }

    public static Item ingotTemporal;
    public static Item ingotCopper;
    public static Item ingotZinc;
    public static Item lumpBrass;
    public static Item ingotBrass;
    public static void initMetals()
    {
        ingotTemporal = new ItemTemporalIngot(64, 100, Names.ITEM.TEMPORAL_INGOT);
        ingotCopper = new ItemClockworkPhase(64, 100, Names.ITEM.COPPER_INGOT);
        ingotZinc = new ItemClockworkPhase(64, 100, Names.ITEM.ZINC_INGOT);
        lumpBrass = new ItemClockworkPhase(64, 100, Names.ITEM.BRASS_LUMP);
        ingotBrass = new ItemClockworkPhase(64, 100, Names.ITEM.BRASS_INGOT);

        GameRegistry.registerItem(ingotTemporal, Names.ITEM.TEMPORAL_INGOT);
        GameRegistry.registerItem(ingotCopper, Names.ITEM.COPPER_INGOT);
        GameRegistry.registerItem(ingotZinc, Names.ITEM.ZINC_INGOT);
        GameRegistry.registerItem(lumpBrass, Names.ITEM.BRASS_LUMP);
        GameRegistry.registerItem(ingotBrass, Names.ITEM.BRASS_INGOT);

        OreDictionary.registerOre("ingotTemporal", ingotTemporal);
        OreDictionary.registerOre("ingotCopper", ingotCopper);
        OreDictionary.registerOre("ingotZinc", ingotZinc);
        OreDictionary.registerOre("ingotBrass", ingotBrass);
    }

    public static Item bugSwatter;
    public static Item ageDev;
    public static Item temporalDriveSimulate;
    public static Item temporalDriveModify;
    public static Item temporalDriveDuplicate;
    public static Item moonFlowerSeeds;
    public static Item moonPearl;
    public static Item elysianGem;
    public static void initMisc()
    {
        bugSwatter = new ItemBugSwatter(1, 100, "bug_swatter");
        ageDev = new ItemAgeDev(1, 100, "age_developer");
        temporalDriveSimulate = new ItemClockworkPhase(64, 100, Names.ITEM.TEMPORAL_DRIVE_SIMULATE);
        temporalDriveModify = new ItemClockworkPhase(64, 100, Names.ITEM.TEMPORAL_DRIVE_MODIFY);
        temporalDriveDuplicate = new ItemClockworkPhase(64, 100, Names.ITEM.TEMPORAL_DRIVE_DUPLICATE);
        moonFlowerSeeds = new ItemMoonFlowerSeeds(64, 0, Names.ITEM.MOON_FLOWER_SEEDS);
        moonPearl = new ItemClockworkPhase(64, 0, Names.ITEM.MOON_PEARL);
        elysianGem = new ItemClockworkPhase(64, 0, Names.ITEM.ELYSIAN_GEM);

        GameRegistry.registerItem(bugSwatter, "bug_swatter");
        GameRegistry.registerItem(ageDev, "age_developer");
        GameRegistry.registerItem(temporalDriveSimulate, Names.ITEM.TEMPORAL_DRIVE_SIMULATE);
        GameRegistry.registerItem(temporalDriveModify, Names.ITEM.TEMPORAL_DRIVE_MODIFY);
        GameRegistry.registerItem(temporalDriveDuplicate, Names.ITEM.TEMPORAL_DRIVE_DUPLICATE);
        GameRegistry.registerItem(moonFlowerSeeds, Names.ITEM.MOON_FLOWER_SEEDS);
        GameRegistry.registerItem(moonPearl, Names.ITEM.MOON_PEARL);
        GameRegistry.registerItem(elysianGem, Names.ITEM.ELYSIAN_GEM);
    }
}
