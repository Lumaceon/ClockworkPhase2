package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.item.*;
import lumaceon.mods.clockworkphase2.item.components.*;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.*;
import lumaceon.mods.clockworkphase2.item.construct.tool.*;
import lumaceon.mods.clockworkphase2.item.construct.weapon.ItemClockworkSword;
import lumaceon.mods.clockworkphase2.item.construct.weapon.ItemLightningSword;
import lumaceon.mods.clockworkphase2.item.timezonemodulation.ItemTimezoneModulationSactification;
import lumaceon.mods.clockworkphase2.item.timezonemodulation.ItemTimezoneModulationTank;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ModItems
{
    public static ArrayList<ItemReference> itemsForModel = new ArrayList<ItemReference>(200);

    public static Item.ToolMaterial clockworkMaterial = EnumHelper.addToolMaterial("CLOCKWORK", 3, 100, 0, 0, 0);
    public static Item.ToolMaterial temporalMaterial = EnumHelper.addToolMaterial("TEMPORAL", 3, 100, 8.0F, 0, 0);

    //METALS
    public static ItemReference ingotCopper = new ItemReference("copper_ingot");
    public static ItemReference ingotZinc = new ItemReference("zinc_ingot");
    public static ItemReference ingotBrass = new ItemReference("brass_ingot");
    public static ItemReference ingotSteel = new ItemReference("steel_ingot");
    public static ItemReference ingotTemporal = new ItemReference("temporal_ingot");
    public static ItemReference ingotEternium = new ItemReference("eternium_ingot"); //Eternal metal; everlasting, durable, unchanging.
    public static ItemReference ingotMomentium = new ItemReference("momentium_ingot"); //Momentary metal; fast, momentary, build-up.
    public static ItemReference ingotParadoxium = new ItemReference("paradoxium_ingot"); //Paradoxical metal; random, unreliable, changing.
    public static ItemReference lumpBrass = new ItemReference("brass_lump");
    //CLOCKWORK COMPONENTS (GEARS/CLOCKWORK/MAINSPRING)
    public static ItemReference mainspring = new ItemReference("mainspring");
    public static ItemReference clockworkCore = new ItemReference("clockwork_core");
    public static ItemReference gearWood = new ItemReference("wood_gear");
    public static ItemReference gearStone = new ItemReference("stone_gear");
    public static ItemReference gearIron = new ItemReference("iron_gear");
    public static ItemReference gearGold = new ItemReference("gold_gear");
    public static ItemReference gearDiamond = new ItemReference("diamond_gear");
    public static ItemReference gearEmerald = new ItemReference("emerald_gear");
    public static ItemReference gearQuartz = new ItemReference("quartz_gear");
    public static ItemReference gearCopper = new ItemReference("copper_gear");
    public static ItemReference gearZinc = new ItemReference("zinc_gear");
    public static ItemReference gearBrass = new ItemReference("brass_gear");
    public static ItemReference gearEternium = new ItemReference("eternium_gear");
    public static ItemReference gearMomentium = new ItemReference("momentium_gear");
    public static ItemReference gearParadoxium = new ItemReference("paradoxium_gear");
    public static ItemReference gearSecondAge = new ItemReference("second_age_gear");
    //TOOLS
    public static ItemReference clockworkPickaxe = new ItemReference("clockwork_pickaxe");
    public static ItemReference clockworkAxe = new ItemReference("clockwork_axe");
    public static ItemReference clockworkShovel = new ItemReference("clockwork_shovel");
    public static ItemReference temporalExcavator = new ItemReference("temporal_excavator");
    public static ItemReference temporalHourglass = new ItemReference("temporal_hourglass");
    public static ItemReference trowelWood = new ItemReference("wood_trowel");
    public static ItemReference trowelStone = new ItemReference("stone_trowel");
    public static ItemReference trowelIron = new ItemReference("iron_trowel");
    public static ItemReference trowelDiamond = new ItemReference("diamond_trowel");
    public static ItemReference wireDuster = new ItemReference("wire_duster");
    //TOOL UPGRADES
    public static ItemReference toolUpgradeTemporalInfuser = new ItemReference("tool_upgrade_temporal_infusion");
    public static ItemReference toolUpgradeSilk = new ItemReference("tool_upgrade_silk");
    public static ItemReference toolUpgradeFurnace = new ItemReference("tool_upgrade_furnace");
    public static ItemReference toolUpgradeRelocate = new ItemReference("tool_upgrade_relocate");
    public static ItemReference toolUpgradeArea = new ItemReference("tool_upgrade_area");
    public static ItemReference toolUpgradeFortune = new ItemReference("tool_upgrade_fortune");
    public static ItemReference toolUpgradeXp = new ItemReference("tool_upgrade_xp");
    public static ItemReference toolUpgradeRightClicker = new ItemReference("tool_upgrade_right_clicker");
    public static ItemReference toolUpgradeMassStorage = new ItemReference("tool_upgrade_mass_storage");
    //TIMEZONE MODULATIONS
    public static ItemReference timezoneModulationSanctification = new ItemReference("timezone_modulation_sanctification");
    public static ItemReference timezoneModulationTank = new ItemReference("timezone_modulation_tank");
    //WEAPONS
    public static ItemReference clockworkSword = new ItemReference("clockwork_sword");
    public static ItemReference lightningSword = new ItemReference("lightning_sword");
    //MISC
    public static ItemReference temporalItemStorageMatrix = new ItemReference("temporal_item_storage_matrix");
    public static ItemReference bugSwatter = new ItemReference("bug_swatter");
    public static ItemReference ageDev = new ItemReference("age_developer");
    public static ItemReference schematicTool = new ItemReference("schematic_tool");
    public static ItemReference temporalDrive = new ItemReference("temporal_drive");
    public static ItemReference moonFlowerSeeds = new ItemReference("moon_flower_seeds");
    public static ItemReference temporalPearl = new ItemReference("temporal_pearl");
    public static ItemReference experimentalIngot = new ItemReference("experimental_ingot");
    public static ItemReference guidebook = new ItemReference("guidebook");
    public static ItemReference assemblyTable = new ItemReference("assembly_table_item");
    public static void init()
    {
        Class[] stackDamageName = new Class[] {int.class, int.class, String.class};
        Class[] nameQualitySpeedLevel = new Class[] {String.class, int.class, int.class, int.class};
        Class[] matName = new Class[] {Item.ToolMaterial.class, String.class};

        //METALS
        registerItem(ingotCopper, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotCopper.getUnlocalizedName()}, "ingotCopper");
        registerItem(ingotZinc, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotZinc.getUnlocalizedName()}, "ingotZinc");
        registerItem(ingotBrass, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotBrass.getUnlocalizedName()}, "ingotBrass");
        registerItem(ingotSteel, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotSteel.getUnlocalizedName()}, "ingotSteel");
        registerItem(ingotTemporal, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotTemporal.getUnlocalizedName()}, "ingotTemporal");
        registerItem(ingotEternium, ItemEterniumIngot.class, stackDamageName, new Object[] {64, 100, ingotEternium.getUnlocalizedName()}, "ingotEternium");
        registerItem(ingotMomentium, ItemMomentiumIngot.class, stackDamageName, new Object[] {64, 100, ingotMomentium.getUnlocalizedName()}, "ingotMomentium");
        registerItem(ingotParadoxium, ItemParadoxiumIngot.class, stackDamageName, new Object[] {64, 100, ingotParadoxium.getUnlocalizedName()}, "ingotParadoxium");
        registerItem(lumpBrass, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, lumpBrass.getUnlocalizedName()});
        //CLOCKWORK COMPONENTS (GEARS/CLOCKWORK/MAINSPRING)
        registerItem(mainspring, ItemMainspring.class, stackDamageName, new Object[] {1, 100, mainspring.getUnlocalizedName()});
        registerItem(clockworkCore, ItemClockworkCore.class, stackDamageName, new Object[] {1, 100, clockworkCore.getUnlocalizedName()});
        registerItem(gearWood, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearWood.getUnlocalizedName(), 10, 15, 0}, "gearWood");
        registerItem(gearStone, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearStone.getUnlocalizedName(), 15, 10, 1}, "gearStone");
        registerItem(gearIron, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearIron.getUnlocalizedName(), 35, 25, 2}, "gearIron");
        registerItem(gearGold, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearGold.getUnlocalizedName(), 10, 70, 0}, "gearGold");
        registerItem(gearDiamond, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearDiamond.getUnlocalizedName(), 50, 40, 3}, "gearDiamond");
        registerItem(gearEmerald, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearEmerald.getUnlocalizedName(), 40, 50, 3}, "gearEmerald");
        registerItem(gearQuartz, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearQuartz.getUnlocalizedName(), 70, 10, 2}, "gearQuartz");
        registerItem(gearCopper, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearCopper.getUnlocalizedName(), 20, 30, 2}, "gearCopper");
        registerItem(gearZinc, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearZinc.getUnlocalizedName(), 30, 20, 1}, "gearZinc");
        registerItem(gearBrass, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearBrass.getUnlocalizedName(), 60, 30, 2}, "gearBrass");
        registerItem(gearEternium, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearEternium.getUnlocalizedName(), 100, 20, 2}, "gearEternium");
        registerItem(gearMomentium, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearMomentium.getUnlocalizedName(), 20, 100, 2}, "gearMomentium");
        registerItem(gearParadoxium, ItemGearParadoxium.class, nameQualitySpeedLevel, new Object[] {gearParadoxium.getUnlocalizedName(), 0, 0, 0}, "gearParadoxium");
        registerItem(gearSecondAge, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearSecondAge.getUnlocalizedName(), 80, 80, 5});
        //TOOLS
        registerItem(clockworkPickaxe, ItemClockworkPickaxe.class, matName, new Object[] {clockworkMaterial, clockworkPickaxe.getUnlocalizedName()});
        registerItem(clockworkAxe, ItemClockworkAxe.class, matName, new Object[] {clockworkMaterial, clockworkAxe.getUnlocalizedName()});
        registerItem(clockworkShovel, ItemClockworkShovel.class, matName, new Object[] {clockworkMaterial, clockworkShovel.getUnlocalizedName()});
        registerItem(temporalExcavator, ItemTemporalExcavator.class, matName, new Object[] {temporalMaterial, temporalExcavator.getUnlocalizedName()});
        registerItem(temporalHourglass, ItemTemporalHourglass.class, new Class[] {int.class, int.class, int.class, String.class}, new Object[] {1, 100, TimeConverter.MONTH, temporalHourglass.getUnlocalizedName()});
        registerItem(trowelWood, ItemTrowel.class, matName, new Object[] {Item.ToolMaterial.WOOD, trowelWood.getUnlocalizedName()});
        registerItem(trowelStone, ItemTrowel.class, matName, new Object[] {Item.ToolMaterial.STONE, trowelStone.getUnlocalizedName()});
        registerItem(trowelIron, ItemTrowel.class, matName, new Object[] {Item.ToolMaterial.IRON, trowelIron.getUnlocalizedName()});
        registerItem(trowelDiamond, ItemTrowel.class, matName, new Object[] {Item.ToolMaterial.EMERALD, trowelDiamond.getUnlocalizedName()}); //Says emerald; is actually diamond. Such identity crisis.
        registerItem(wireDuster, ItemWireDuster.class, stackDamageName, new Object[] {1, 100, wireDuster.getUnlocalizedName()});
        //TOOL UPGRADES
        registerItem(toolUpgradeTemporalInfuser, ItemToolUpgradeTemporalInfuser.class, stackDamageName, new Object[] {1, 100, toolUpgradeTemporalInfuser.getUnlocalizedName()});
        registerItem(toolUpgradeSilk, ItemToolUpgradeSilk.class, stackDamageName, new Object[] {1, 100, toolUpgradeSilk.getUnlocalizedName()});
        registerItem(toolUpgradeFurnace, ItemToolUpgradeFurnace.class, stackDamageName, new Object[] {1, 100, toolUpgradeFurnace.getUnlocalizedName()});
        registerItem(toolUpgradeRelocate, ItemToolUpgradeRelocate.class, stackDamageName, new Object[] {1, 100, toolUpgradeRelocate.getUnlocalizedName()});
        registerItem(toolUpgradeArea, ItemToolUpgradeArea.class, stackDamageName, new Object[] {1, 100, toolUpgradeArea.getUnlocalizedName()});
        registerItem(toolUpgradeFortune, ItemToolUpgradeFortune.class, stackDamageName, new Object[] {1, 100, toolUpgradeFortune.getUnlocalizedName()});
        registerItem(toolUpgradeXp, ItemToolUpgradeXP.class, stackDamageName, new Object[] {1, 100, toolUpgradeXp.getUnlocalizedName()});
        registerItem(toolUpgradeRightClicker, ItemToolUpgradeRightClicker.class, stackDamageName, new Object[] {1, 100, toolUpgradeRightClicker.getUnlocalizedName()});
        registerItem(toolUpgradeMassStorage, ItemToolUpgradeStorage.class, stackDamageName, new Object[] {1, 100, toolUpgradeMassStorage.getUnlocalizedName()});
        //TIMEZONE MODULATIONS
        registerItem(timezoneModulationSanctification, ItemTimezoneModulationSactification.class, stackDamageName, new Object[] {1, 100, timezoneModulationSanctification.getUnlocalizedName()});
        registerItem(timezoneModulationTank, ItemTimezoneModulationTank.class, stackDamageName, new Object[] {1, 100, timezoneModulationTank.getUnlocalizedName()});
        //WEAPONS
        registerItem(clockworkSword, ItemClockworkSword.class, stackDamageName, new Object[] {1, 100, clockworkSword.getUnlocalizedName()});
        registerItem(lightningSword, ItemLightningSword.class, matName, new Object[] {clockworkMaterial, lightningSword.getUnlocalizedName()}); //TODO get rid of this?
        //MISC
        registerItem(temporalItemStorageMatrix, ItemTemporalItemStorageMatrix.class, stackDamageName, new Object[] {1, 100, temporalItemStorageMatrix.getUnlocalizedName()});
        registerItem(bugSwatter, ItemBugSwatter.class, stackDamageName, new Object[] {1, 100, bugSwatter.getUnlocalizedName()});
        registerItem(ageDev, ItemAgeDev.class, stackDamageName, new Object[] {1, 100, ageDev.getUnlocalizedName()});
        registerItem(schematicTool, ItemCreativeModSchematicTool.class, stackDamageName, new Object[] {1, 100, schematicTool.getUnlocalizedName()});
        registerItem(temporalDrive, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, temporalDrive.getUnlocalizedName()});
        registerItem(moonFlowerSeeds, ItemMoonFlowerSeeds.class, stackDamageName, new Object[] {64, 100, moonFlowerSeeds.getUnlocalizedName()});
        registerItem(temporalPearl, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, temporalPearl.getUnlocalizedName()});
        registerItem(experimentalIngot, ItemExperimentalIngot.class, stackDamageName, new Object[] {1, 100, experimentalIngot.getUnlocalizedName()});
        registerItem(guidebook, ItemGuidebook.class, stackDamageName, new Object[] {1, 100, guidebook.getUnlocalizedName()}); //Most OP item in the game. Like an auto-crafter, but with knowledge.
        registerItem(assemblyTable, ItemAssemblyTable.class, stackDamageName, new Object[] {64, 100, assemblyTable.getUnlocalizedName()});
    }

    public static void initModels() {
        if(itemsForModel != null)
        {
            for(ItemReference item : itemsForModel)
                if(item != null && item.getItem() != null)
                    ClockworkPhase2.proxy.registerItemModel(item.getItem(), item.getUnlocalizedName());
            itemsForModel.clear();
        }
        itemsForModel = null;
    }

    //************ START HELPER METHODS ************\\

    public static void registerItem(ItemReference itemReference, Class<? extends Item> itemClass, Class[] targetConstructor, Object[] parameters)
    {
        try {
            Constructor constructor = itemClass.getDeclaredConstructor(targetConstructor);
            Item item = (Item) constructor.newInstance(parameters);
            itemReference.setItem(item);
            GameRegistry.registerItem(item, itemReference.getUnlocalizedName());
            itemsForModel.add(itemReference);
        } catch (NoSuchMethodException e) {
            Logger.error("Tried to instantiate class for block '" + itemReference.getUnlocalizedName() + "' with invalid parameters.");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void registerItem(ItemReference itemReference, Class<? extends Item> itemClass, Class[] targetConstructor, Object[] parameters, String oreDictName)
    {
        try {
            Constructor constructor = itemClass.getDeclaredConstructor(targetConstructor);
            Item item = (Item) constructor.newInstance(parameters);
            itemReference.setItem(item);
            GameRegistry.registerItem(item, itemReference.getUnlocalizedName());
            OreDictionary.registerOre(oreDictName, item);
            itemsForModel.add(itemReference);
        } catch (NoSuchMethodException e) {
            Logger.error("Tried to instantiate class for block '" + itemReference.getUnlocalizedName() + "' with invalid parameters.");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gateway class to allow registerItem to modify the item references, simplifying the registration process somewhat.
     */
    public static class ItemReference
    {
        private Item item;
        private String unlocalizedName = null;

        public ItemReference(String unlocalizedName) {
            item = null;
            this.unlocalizedName = unlocalizedName;
        }

        public Item getItem() {
            return this.item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        public void setUnlocalizedName(String unlocalizedName) {
            this.unlocalizedName = unlocalizedName;
        }
    }
}
