package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.api.MemoryItemRegistry;
import lumaceon.mods.clockworkphase2.item.ItemTemporalIngot;
import lumaceon.mods.clockworkphase2.item.components.timestream.ItemTimestreamRelocation;
import lumaceon.mods.clockworkphase2.item.components.timestream.ItemTimestreamSilkyHarvest;
import lumaceon.mods.clockworkphase2.item.components.timestream.ItemTimestreamSmelt;
import lumaceon.mods.clockworkphase2.item.components.tool.ItemClockworkCore;
import lumaceon.mods.clockworkphase2.item.components.tool.ItemMainspring;
import lumaceon.mods.clockworkphase2.item.components.tool.ItemTemporalCore;
import lumaceon.mods.clockworkphase2.item.components.tool.clockwork.ItemGear;
import lumaceon.mods.clockworkphase2.item.components.tool.clockwork.ItemMemoryComponent;
import lumaceon.mods.clockworkphase2.item.construct.*;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems
{
    public static void init()
    {
        initClockworkComponents();
        initTemporalComponents();
        initTimestreams();
        initConstructs();
        initBuckets();
        initMisc();
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
    }

    public static Item temporalCore;
    public static void initTemporalComponents()
    {
        temporalCore = new ItemTemporalCore(1, 50, Names.ITEM.TEMPORAL_CORE);

        GameRegistry.registerItem(temporalCore, Names.ITEM.TEMPORAL_CORE);
    }

    public static ItemTimestreamRelocation timestreamRelocation;
    public static ItemTimestreamSilkyHarvest timestreamSilkyHarvest;
    public static ItemTimestreamSmelt temporalFunctionSmelt;
    public static void initTimestreams()
    {
        timestreamRelocation = new ItemTimestreamRelocation(1, 100, Names.ITEM.TIMESTREAM_RELOCATION);
        timestreamSilkyHarvest = new ItemTimestreamSilkyHarvest(1, 100, Names.ITEM.TIMESTREAM_SILKY_HARVEST);
        temporalFunctionSmelt = new ItemTimestreamSmelt(1, 100, Names.ITEM.TIMESTREAM_SMELT);

        GameRegistry.registerItem(timestreamRelocation, Names.ITEM.TIMESTREAM_RELOCATION);
        GameRegistry.registerItem(timestreamSilkyHarvest, Names.ITEM.TIMESTREAM_SILKY_HARVEST);
        GameRegistry.registerItem(temporalFunctionSmelt, Names.ITEM.TIMESTREAM_SMELT);
    }

    public static Item.ToolMaterial clockworkMaterial = EnumHelper.addToolMaterial("CLOCKWORK", 3, 100, 0, 0, 0);
    public static Item clockworkPickaxe;
    public static Item clockworkAxe;
    public static Item clockworkShovel;
    public static Item clockworkMultiTool;
    public static void initConstructs()
    {
        clockworkPickaxe = new ItemClockworkPickaxe(clockworkMaterial, Names.ITEM.CLOCKWORK_PICKAXE);
        clockworkAxe = new ItemClockworkAxe(clockworkMaterial, Names.ITEM.CLOCKWORK_AXE);
        clockworkShovel = new ItemClockworkShovel(clockworkMaterial, Names.ITEM.CLOCKWORK_SHOVEL);
        clockworkMultiTool = new ItemClockworkMultitool(0, Item.ToolMaterial.IRON, Names.ITEM.CLOCKWORK_MULTI_TOOL);

        GameRegistry.registerItem(clockworkPickaxe, Names.ITEM.CLOCKWORK_PICKAXE);
        GameRegistry.registerItem(clockworkAxe, Names.ITEM.CLOCKWORK_AXE);
        GameRegistry.registerItem(clockworkShovel, Names.ITEM.CLOCKWORK_SHOVEL);
        GameRegistry.registerItem(clockworkMultiTool, Names.ITEM.CLOCKWORK_MULTI_TOOL);
    }

    public static Item bucketTimeSand;
    public static void initBuckets()
    {

    }

    public static Item ingotTemporal;
    public static void initMisc()
    {
        ingotTemporal = new ItemTemporalIngot(64, 100, Names.ITEM.TEMPORAL_INGOT);

        GameRegistry.registerItem(ingotTemporal, Names.ITEM.TEMPORAL_INGOT);

        OreDictionary.registerOre("ingotTemporal", ingotTemporal);
    }
}
