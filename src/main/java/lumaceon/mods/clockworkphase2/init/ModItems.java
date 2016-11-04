package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.EnumExpTier;
import lumaceon.mods.clockworkphase2.api.HourglassDropRegistry;
import lumaceon.mods.clockworkphase2.api.RelicExcavationRegistry;
import lumaceon.mods.clockworkphase2.api.item.ITimeframeKeyItem;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.item.multiblocktemplate.ItemMultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.item.temporal.*;
import lumaceon.mods.clockworkphase2.api.util.WeightedChance;
import lumaceon.mods.clockworkphase2.item.*;
import lumaceon.mods.clockworkphase2.item.clockwork.*;
import lumaceon.mods.clockworkphase2.item.clockwork.component.ItemClockworkCore;
import lumaceon.mods.clockworkphase2.item.clockwork.component.ItemGear;
import lumaceon.mods.clockworkphase2.item.clockwork.component.ItemMainspring;
import lumaceon.mods.clockworkphase2.item.clockwork.tool.*;
import lumaceon.mods.clockworkphase2.item.lexicon.ItemTemporalLexicon;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.*;
import lumaceon.mods.clockworkphase2.item.timeframekey.*;
import lumaceon.mods.clockworkphase2.item.timeframekey.artifact.ItemArtifact;
import lumaceon.mods.clockworkphase2.item.timeframekey.artifact.ItemArtifactParadox;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ModItems
{
    public static ArrayList<ItemReference> itemsForModel = new ArrayList<ItemReference>(200);

    public static Item.ToolMaterial clockworkMaterial = EnumHelper.addToolMaterial("CLOCKWORK", 3, 100, 1, 1, 0);
    public static Item.ToolMaterial temporalMaterial = EnumHelper.addToolMaterial("TEMPORAL", 3, 100, 8.0F, 0, 0);

    //MATERIALS
    public static ItemReference ingotCopper = new ItemReference("copper_ingot");
    public static ItemReference ingotZinc = new ItemReference("zinc_ingot");
    public static ItemReference ingotBrass = new ItemReference("brass_ingot");
    public static ItemReference ingotSteel = new ItemReference("steel_ingot");
    public static ItemReference ingotTemporal = new ItemReference("temporal_ingot");
    public static ItemReference ingotEthereal = new ItemReference("ethereal_ingot");
    public static ItemReference ingotPhasic = new ItemReference("phasic_ingot");
    public static ItemReference ingotEternal = new ItemReference("eternal_ingot");
    //public static ItemReference ingotEternium = new ItemReference("eternium_ingot");
    //public static ItemReference ingotMomentium = new ItemReference("momentium_ingot");
    //public static ItemReference ingotParadoxium = new ItemReference("paradoxium_ingot");
    public static ItemReference lumpBrass = new ItemReference("brass_lump");
    public static ItemReference matterTemporal = new ItemReference("matter_temporal");
    public static ItemReference matterEthereal = new ItemReference("matter_ethereal");
    public static ItemReference matterPhasic = new ItemReference("matter_phasic");
    public static ItemReference matterEternal = new ItemReference("matter_eternal");
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
    //public static ItemReference gearEternium = new ItemReference("eternium_gear");
    //public static ItemReference gearMomentium = new ItemReference("momentium_gear");
    //public static ItemReference gearParadoxium = new ItemReference("paradoxium_gear");
    public static ItemReference gearCreative = new ItemReference("creative_gear");
    //TOOLS
    public static ItemReference clockworkPickaxe = new ItemReference("clockwork_pickaxe");
    public static ItemReference clockworkAxe = new ItemReference("clockwork_axe");
    public static ItemReference clockworkShovel = new ItemReference("clockwork_shovel");
    public static ItemReference temporalLexicon = new ItemReference("temporal_lexicon");
    public static ItemReference temporalExcavator = new ItemReference("temporal_excavator");
    public static ItemReference temporalHourglass = new ItemReference("temporal_hourglass");
    public static ItemReference etherealHourglass = new ItemReference("ethereal_hourglass");
    //public static ItemReference phasicHourglass = new ItemReference("phasic_hourglass");
    //public static ItemReference eternalHourglass = new ItemReference("eternal_hourglass");
    public static ItemReference trowelIron = new ItemReference("iron_trowel");
    public static ItemReference trowelDiamond = new ItemReference("diamond_trowel");
    public static ItemReference alloyHammer = new ItemReference("alloy_hammer");
    //TOOL UPGRADES
    public static ItemReference toolUpgradeTemporalInfuser = new ItemReference("tool_upgrade_temporal_infusion");
    public static ItemReference toolUpgradeSilk = new ItemReference("tool_upgrade_silk");
    //public static ItemReference toolUpgradeFurnace = new ItemReference("tool_upgrade_furnace");
    public static ItemReference toolUpgradeRelocate = new ItemReference("tool_upgrade_relocate");
    public static ItemReference toolUpgradeArea = new ItemReference("tool_upgrade_area");
    public static ItemReference toolUpgradeFortune = new ItemReference("tool_upgrade_fortune");
    //public static ItemReference toolUpgradeXp = new ItemReference("tool_upgrade_xp");
    //public static ItemReference toolUpgradeRightClicker = new ItemReference("tool_upgrade_right_clicker");
    //public static ItemReference toolUpgradeMassStorage = new ItemReference("tool_upgrade_mass_storage");
    //TEMPORAL DRIVES
    public static ItemReference temporalDrive = new ItemReference("temporal_drive");
    public static ItemReference etherealDrive = new ItemReference("ethereal_drive");
    //public static ItemReference phasicDrive = new ItemReference("phasic_drive");
    //public static ItemReference eternalDrive = new ItemReference("eternal_drive");
    //TIMEFRAME KEYS
    public static ItemReference timeframeKeyAnima = new ItemReference("timeframe_key_anima");
    public static ItemReference timeframeKeySolar = new ItemReference("timeframe_key_solar");
    public static ItemReference timeframeKeyParadox = new ItemReference("timeframe_key_paradox");
    //public static ItemReference timeframeKeyStorm = new ItemReference("timeframe_key_storm");
    //public static ItemReference timeframeKeyRune = new ItemReference("timeframe_key_rune");
    public static ItemReference timeframeKeyTivadari = new ItemReference("timeframe_key_tivadari");
    //ARTIFACTS
    public static ItemReference artifactSolaria = new ItemReference("artifact_solar");
    public static ItemReference artifactAnimar = new ItemReference("artifact_anima");
    public static ItemReference artifactTivadari = new ItemReference("artifact_tivadari");
    public static ItemReference artifactParadox = new ItemReference("artifact_paradox");
    //WEAPONS
    public static ItemReference clockworkSword = new ItemReference("clockwork_sword");
    //MULTIBLOCK ASSEMBLERS
    public static ItemReference multiblockCelestialCompass = new ItemReference("multiblock_template_celestial_compass");
    //MISC
    //public static ItemReference temporalItemStorageMatrix = new ItemReference("temporal_item_storage_matrix");
    public static ItemReference bugSwatter = new ItemReference("bug_swatter");
    public static ItemReference moonFlowerSeeds = new ItemReference("moon_flower_seeds");
    //public static ItemReference experimentalIngot = new ItemReference("experimental_ingot");
    public static ItemReference guidebook = new ItemReference("guidebook");
    public static ItemReference assemblyTable = new ItemReference("assembly_table_item");
    public static void init()
    {
        Class[] stackDamageName = new Class[] {int.class, int.class, String.class};
        Class[] stackDamageNameKeyItem = new Class[] {int.class, int.class, String.class, ITimeframeKeyItem.class};
        Class[] stackDamageNameTimeTickMinXP = new Class[] {int.class, int.class, String.class, int.class, int.class, EnumExpTier.class};
        Class[] nameQualitySpeedLevel = new Class[] {String.class, int.class, int.class, int.class};
        Class[] matName = new Class[] {Item.ToolMaterial.class, String.class};
        Class[] hourglassClass = new Class[] {int.class, int.class, String.class, long.class, EnumExpTier.class};

        //MATERIALS
        registerItem(ingotCopper, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotCopper.getUnlocalizedName()}, "ingotCopper");
        registerItem(ingotZinc, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotZinc.getUnlocalizedName()}, "ingotZinc");
        registerItem(ingotBrass, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotBrass.getUnlocalizedName()}, "ingotBrass");
        registerItem(ingotSteel, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotSteel.getUnlocalizedName()}, "ingotSteel");
        registerItem(ingotTemporal, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, ingotTemporal.getUnlocalizedName()}, "ingotTemporal");
        registerItem(ingotEthereal, ItemEtherealIngot.class, stackDamageName, new Object[] {64, 100, ingotEthereal.getUnlocalizedName()}, "ingotEthereal");
        registerItem(ingotPhasic, ItemPhasicIngot.class, stackDamageName, new Object[] {64, 100, ingotPhasic.getUnlocalizedName()}, "ingotPhasic");
        registerItem(ingotEternal, ItemEternalIngot.class, stackDamageName, new Object[] {64, 100, ingotEternal.getUnlocalizedName()}, "ingotEternal");
        //registerItem(ingotEternium, ItemEterniumIngot.class, stackDamageName, new Object[] {64, 100, ingotEternium.getUnlocalizedName()}, "ingotEternium");
        //registerItem(ingotMomentium, ItemMomentiumIngot.class, stackDamageName, new Object[] {64, 100, ingotMomentium.getUnlocalizedName()}, "ingotMomentium");
        //registerItem(ingotParadoxium, ItemParadoxiumIngot.class, stackDamageName, new Object[] {64, 100, ingotParadoxium.getUnlocalizedName()}, "ingotParadoxium");
        registerItem(lumpBrass, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, lumpBrass.getUnlocalizedName()});
        registerItem(matterTemporal, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, matterTemporal.getUnlocalizedName()});
        registerItem(matterEthereal, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, matterEthereal.getUnlocalizedName()});
        registerItem(matterPhasic, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, matterPhasic.getUnlocalizedName()});
        registerItem(matterEternal, ItemClockworkPhase.class, stackDamageName, new Object[] {64, 100, matterEternal.getUnlocalizedName()});
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
        //registerItem(gearEternium, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearEternium.getUnlocalizedName(), 100, 20, 2}, "gearEternium");
        //registerItem(gearMomentium, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearMomentium.getUnlocalizedName(), 20, 100, 2}, "gearMomentium");
        //registerItem(gearParadoxium, ItemGearParadoxium.class, nameQualitySpeedLevel, new Object[] {gearParadoxium.getUnlocalizedName(), 0, 0, 0}, "gearParadoxium");
        registerItem(gearCreative, ItemGear.class, nameQualitySpeedLevel, new Object[] {gearCreative.getUnlocalizedName(), 120, 120, 100});
        //TOOLS
        registerItem(clockworkPickaxe, ItemClockworkPickaxe.class, matName, new Object[] {clockworkMaterial, clockworkPickaxe.getUnlocalizedName()});
        registerItem(clockworkAxe, ItemClockworkAxe.class, matName, new Object[] {clockworkMaterial, clockworkAxe.getUnlocalizedName()});
        registerItem(clockworkShovel, ItemClockworkShovel.class, matName, new Object[] {clockworkMaterial, clockworkShovel.getUnlocalizedName()});

        registerItem(temporalLexicon, ItemTemporalLexicon.class, stackDamageName, new Object[] {1, 100, temporalLexicon.getUnlocalizedName()});
        itemsForModel.remove(temporalLexicon);

        registerItem(temporalExcavator, ItemTemporalExcavator.class, matName, new Object[] {temporalMaterial, temporalExcavator.getUnlocalizedName()});
        registerItem(temporalHourglass, ItemHourglass.class, hourglassClass, new Object[] {1, 100, temporalHourglass.getUnlocalizedName(), (long) ConfigValues.TEMPORAL_HOURGLASS_START, EnumExpTier.TEMPORAL});
        registerItem(etherealHourglass, ItemHourglass.class, hourglassClass, new Object[] {1, 100, etherealHourglass.getUnlocalizedName(), (long) ConfigValues.ETHEREAL_HOURGLASS_START, EnumExpTier.ETHEREAL});
        //registerItem(phasicHourglass, ItemHourglass.class, hourglassClass, new Object[] {1, 100, phasicHourglass.getUnlocalizedName(), (long) TimeConverter.DAY, EnumExpTier.PHASIC});
        //registerItem(eternalHourglass, ItemHourglass.class, hourglassClass, new Object[] {1, 100, eternalHourglass.getUnlocalizedName(), (long) TimeConverter.MONTH, EnumExpTier.ETERNAL});
        registerItem(trowelIron, ItemTrowel.class, matName, new Object[] {Item.ToolMaterial.IRON, trowelIron.getUnlocalizedName()});
        registerItem(trowelDiamond, ItemTrowel.class, matName, new Object[] {Item.ToolMaterial.DIAMOND, trowelDiamond.getUnlocalizedName()});
        registerItem(alloyHammer, ItemClockworkPhase.class, stackDamageName, new Object[] {1, 64, alloyHammer.getUnlocalizedName()});
        //TOOL UPGRADES
        registerItem(toolUpgradeTemporalInfuser, ItemToolUpgradeTemporalInfuser.class, stackDamageName, new Object[] {1, 100, toolUpgradeTemporalInfuser.getUnlocalizedName()});
        registerItem(toolUpgradeSilk, ItemToolUpgradeSilk.class, stackDamageName, new Object[] {1, 100, toolUpgradeSilk.getUnlocalizedName()});
        //registerItem(toolUpgradeFurnace, ItemToolUpgradeFurnace.class, stackDamageName, new Object[] {1, 100, toolUpgradeFurnace.getUnlocalizedName()});
        registerItem(toolUpgradeRelocate, ItemToolUpgradeRelocate.class, stackDamageName, new Object[] {1, 100, toolUpgradeRelocate.getUnlocalizedName()});
        registerItem(toolUpgradeArea, ItemToolUpgradeArea.class, stackDamageName, new Object[] {1, 100, toolUpgradeArea.getUnlocalizedName()});
        registerItem(toolUpgradeFortune, ItemToolUpgradeFortune.class, stackDamageName, new Object[] {1, 100, toolUpgradeFortune.getUnlocalizedName()});
        //registerItem(toolUpgradeXp, ItemToolUpgradeXP.class, stackDamageName, new Object[] {1, 100, toolUpgradeXp.getUnlocalizedName()});
        //registerItem(toolUpgradeRightClicker, ItemToolUpgradeRightClicker.class, stackDamageName, new Object[] {1, 100, toolUpgradeRightClicker.getUnlocalizedName()});
        //registerItem(toolUpgradeMassStorage, ItemToolUpgradeStorage.class, stackDamageName, new Object[] {1, 100, toolUpgradeMassStorage.getUnlocalizedName()});
        //TEMPORAL DRIVES
        registerItem(temporalDrive, ItemTimeCompressor.class, stackDamageNameTimeTickMinXP, new Object[] {1, 100, temporalDrive.getUnlocalizedName(), ConfigValues.TEMPORAL_DRIVE_TIME_PER_TICK, ConfigValues.TEMPORAL_DRIVE_TICKS, EnumExpTier.TEMPORAL}); //1 minute over 1 minute
        registerItem(etherealDrive, ItemTimeCompressor.class, stackDamageNameTimeTickMinXP, new Object[] {1, 100, etherealDrive.getUnlocalizedName(), ConfigValues.ETHEREAL_DRIVE_TIME_PER_TICK, ConfigValues.ETHEREAL_DRIVE_TICKS, EnumExpTier.ETHEREAL}); //30 minutes over 7.5 minutes.
        //registerItem(phasicDrive, ItemTimeGenerator.class, stackDamageNameTimeTickMinXP, new Object[] {1, 100, phasicDrive.getUnlocalizedName(), 64, 13500, EnumExpTier.PHASIC}); //12 hours over 11.25 minutes.
        //registerItem(eternalDrive, ItemTimeGenerator.class, stackDamageNameTimeTickMinXP, new Object[] {1, 100, eternalDrive.getUnlocalizedName(), 128, 54000, EnumExpTier.ETERNAL}); //4 days over 45 minutes.
        //TIMEFRAME KEYS
        registerItem(timeframeKeySolar, ItemTimeframeKeySolaria.class, stackDamageName, new Object[] {64, 100, timeframeKeySolar.getUnlocalizedName()});
        registerItem(timeframeKeyAnima, ItemTimeframeKeyAnimar.class, stackDamageName, new Object[] {64, 100, timeframeKeyAnima.getUnlocalizedName()});
        registerItem(timeframeKeyTivadari, ItemTimeframeKeyTivadari.class, stackDamageName, new Object[] {64, 100, timeframeKeyTivadari.getUnlocalizedName()});
        registerItem(timeframeKeyParadox, ItemTimeframeKeyParadox.class, stackDamageName, new Object[] {64, 100, timeframeKeyParadox.getUnlocalizedName()});
        //ARTIFACTS
        registerItem(artifactSolaria, ItemArtifact.class, stackDamageNameKeyItem, new Object[] {64, 100, artifactSolaria.getUnlocalizedName(), timeframeKeySolar.getItem()});
        registerItem(artifactAnimar, ItemArtifact.class, stackDamageNameKeyItem, new Object[] {64, 100, artifactAnimar.getUnlocalizedName(), timeframeKeyAnima.getItem()});
        registerItem(artifactTivadari, ItemArtifact.class, stackDamageNameKeyItem, new Object[] {64, 100, artifactTivadari.getUnlocalizedName(), timeframeKeyTivadari.getItem()});
        registerItem(artifactParadox, ItemArtifactParadox.class, stackDamageNameKeyItem, new Object[] {64, 100, artifactParadox.getUnlocalizedName(), timeframeKeyParadox.getItem()});
        //WEAPONS
        registerItem(clockworkSword, ItemClockworkSword.class, stackDamageName, new Object[] {1, 100, clockworkSword.getUnlocalizedName()});
        //MULTIBLOCK ASSEMBLERS
        registerItem(multiblockCelestialCompass, ItemMultiblockTemplateCelestialCompass.class, stackDamageName, new Object[] {64, 100, multiblockCelestialCompass.getUnlocalizedName()});
        //MISC
        //registerItem(temporalItemStorageMatrix, ItemTemporalItemStorageMatrix.class, stackDamageName, new Object[] {1, 100, temporalItemStorageMatrix.getUnlocalizedName()});
        registerItem(moonFlowerSeeds, ItemMoonFlowerSeeds.class, stackDamageName, new Object[] {64, 100, moonFlowerSeeds.getUnlocalizedName()});
        //registerItem(experimentalIngot, ItemExperimentalIngot.class, stackDamageName, new Object[] {1, 100, experimentalIngot.getUnlocalizedName()});
        registerItem(guidebook, ItemGuidebook.class, stackDamageName, new Object[] {1, 100, guidebook.getUnlocalizedName()});
        registerItem(assemblyTable, ItemAssemblyTable.class, stackDamageName, new Object[] {64, 100, assemblyTable.getUnlocalizedName()});
        registerItem(bugSwatter, ItemBugSwatter.class, stackDamageName, new Object[] {1, 100, bugSwatter.getUnlocalizedName()});

        RelicExcavationRegistry.registerMoonFlowerRelicDrop(new WeightedChance<ItemStack>(new ItemStack(moonFlowerSeeds.getItem()), 100));
        RelicExcavationRegistry.registerUnknownRelicDrop(new WeightedChance<ItemStack>(new ItemStack(artifactSolaria.getItem()), 100));
        RelicExcavationRegistry.registerUnknownRelicDrop(new WeightedChance<ItemStack>(new ItemStack(artifactAnimar.getItem()), 100));
        RelicExcavationRegistry.registerUnknownRelicDrop(new WeightedChance<ItemStack>(new ItemStack(artifactTivadari.getItem()), 100));
        RelicExcavationRegistry.registerUnknownRelicDrop(new WeightedChance<ItemStack>(new ItemStack(artifactParadox.getItem()), 100));

        HourglassDropRegistry.registerTimeframeKeyDrop(new WeightedChance<ItemStack>(new ItemStack(timeframeKeySolar.getItem()), 100));
        HourglassDropRegistry.registerTimeframeKeyDrop(new WeightedChance<ItemStack>(new ItemStack(timeframeKeyAnima.getItem()), 100));
        HourglassDropRegistry.registerTimeframeKeyDrop(new WeightedChance<ItemStack>(new ItemStack(timeframeKeyTivadari.getItem()), 100));
        HourglassDropRegistry.registerTimeframeKeyDrop(new WeightedChance<ItemStack>(new ItemStack(timeframeKeyParadox.getItem()), 100));
    }

    //************ START HELPER METHODS ************\\

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

    public static void registerItem(ItemReference itemReference, Class<? extends Item> itemClass, Class[] targetConstructor, Object[] parameters)
    {
        try {
            Constructor constructor = itemClass.getDeclaredConstructor(targetConstructor);
            Item item = (Item) constructor.newInstance(parameters);
            itemReference.setItem(item);
            GameRegistry.register(item);
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
            GameRegistry.register(item);
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
