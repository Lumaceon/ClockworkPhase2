package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.RelicExcavationRegistry;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateArmillaryRing;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.item.mob.ItemBrain;
import lumaceon.mods.clockworkphase2.item.mob.ItemMobCapsule;
import lumaceon.mods.clockworkphase2.item.multiblocktemplate.ItemMultiblockTemplate;
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
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class ModItems
{
    public static ArrayList<Item> itemsForModel = new ArrayList<>(200);

    public static Item.ToolMaterial clockworkMaterial = EnumHelper.addToolMaterial("CLOCKWORK", 3, 100, 1, 1, 0);
    public static Item.ToolMaterial temporalMaterial = EnumHelper.addToolMaterial("TEMPORAL", 3, 100, 8.0F, 0, 0);

    //MATERIALS
    public static Item ingotCopper;
    public static Item ingotZinc;
    public static Item ingotBrass;
    public static Item ingotAluminum;
    public static Item ingotTemporal;
    public static Item dustCopper;
    public static Item dustZinc;
    public static Item dustBrass;
    public static Item dustAluminum;
    public static Item gemMoonstone;
    public static Item gemChrysoberyl;
    public static Item gemPearl;
    public static Item gemSpinel;
    //public static Item gemMetasite;
    //public static Item gemLuasite;
    //public static Item gemAeonite;
    public static Item matterTemporal;
    public static Item silicon;
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
    public static Item gearChrysoberyl;
    public static Item gearPearl;
    public static Item gearSpinel;
    public static Item gearMoonstone;
    //public static Item gearParadoxium = new Item("paradoxium_gear");
    public static Item gearCreative;
    //TOOLS
    public static Item clockworkPickaxe;
    public static Item clockworkAxe;
    public static Item clockworkShovel;
    public static Item temporalExcavator;
    public static Item temporalHourglass;
    public static Item temporalToolbelt;
    public static Item temporalFishingRod;
    public static Item stasisShield;
    public static Item aquaticScepter;
    public static Item trowelIron;
    public static Item trowelDiamond;
    //LIFE MODIFICATION
    public static Item mobCapsule;
    public static Item mobBrain;
    //TOOL UPGRADES
    public static Item toolUpgradeTemporalInfuser;
    public static Item toolUpgradeSilk;
    public static Item toolUpgradeFurnace;
    public static Item toolUpgradeRelocate;
    public static Item toolUpgradeArea;
    public static Item toolUpgradeFortune;
    //MACHINE UPGRADES
    public static Item temporalMachineConduit;
    //WEAPONS
    public static Item clockworkSword;
    //MULTIBLOCK ASSEMBLERS
    public static Item multiblockCelestialCompass;
    public static Item multiblockArmillaryRing;
    //MISC
    public static Item bugSwatter;
    public static Item moonFlowerSeeds;
    public static Item assemblyTable;
    //CRAFTING ITEMS
    public static Item gizmoLife;
    public static Item gizmoLifeRelic;
    public static Item gizmoLight;
    public static Item gizmoLightRelic;
    public static Item gizmoWater;
    public static Item gizmoWaterRelic;
    public static Item gizmoEarth;
    public static Item gizmoEarthRelic;
    public static Item gizmoAir;
    public static Item gizmoAirRelic;
    public static Item gizmoFire;
    public static Item gizmoFireRelic;
    public static Item gizmoAura;
    public static Item gizmoAuraRelic;
    public static Item splitTimelineMatrix;
    public static Item splitTimelineMatrixRelic;
    public static Item temporalDrive;
    public static void init()
    {
        //MATERIALS

        //ingots
        ingotCopper = new ItemClockworkPhase(64, 100, "ingot_copper");
        register(ingotCopper);
        OreDictionary.registerOre("ingotCopper", ingotCopper);

        ingotZinc = new ItemClockworkPhase(64, 100, "ingot_zinc");
        register(ingotZinc);
        OreDictionary.registerOre("ingotZinc", ingotZinc);

        ingotBrass = new ItemClockworkPhase(64, 100, "ingot_brass");
        register(ingotBrass);
        OreDictionary.registerOre("ingotBrass", ingotBrass);

        ingotAluminum = new ItemClockworkPhase(64, 100, "ingot_aluminum");
        register(ingotAluminum);
        OreDictionary.registerOre("ingotAluminum", ingotAluminum);

        ingotTemporal = new ItemClockworkPhase(64, 100, "ingot_temporal");
        register(ingotTemporal);
        OreDictionary.registerOre("ingotTemporal", ingotTemporal);

        //dusts
        dustCopper = new ItemClockworkPhase(64, 100, "dust_copper");
        register(dustCopper);
        OreDictionary.registerOre("dustCopper", dustCopper);

        dustZinc = new ItemClockworkPhase(64, 100, "dust_zinc");
        register(dustZinc);
        OreDictionary.registerOre("dustZinc", dustZinc);

        dustBrass = new ItemClockworkPhase(64, 100, "dust_brass");
        register(dustBrass);
        OreDictionary.registerOre("dustBrass", dustBrass);

        dustAluminum = new ItemClockworkPhase(64, 100, "dust_aluminum");
        register(dustAluminum);
        OreDictionary.registerOre("dustAluminum", dustAluminum);

        //gems
        gemMoonstone = new ItemClockworkPhase(64, 100, "moonstone");
        register(gemMoonstone);
        OreDictionary.registerOre("gemMoonstone", gemMoonstone);

        gemChrysoberyl = new ItemClockworkPhase(64, 100, "chrysoberyl");
        register(gemChrysoberyl);
        OreDictionary.registerOre("gemChrysoberyl", gemChrysoberyl);

        gemPearl = new ItemClockworkPhase(64, 100, "pearl");
        register(gemPearl);

        gemSpinel = new ItemClockworkPhase(64, 100, "spinel");
        register(gemSpinel);
        OreDictionary.registerOre("gemSpinel", gemSpinel);

        /*gemMetasite = new ItemClockworkPhase(64, 100, "metasite");
        register(gemMetasite);

        gemLuasite = new ItemClockworkPhase(64, 100, "luasite"); //These three are for later....
        register(gemLuasite);

        gemAeonite = new ItemClockworkPhase(64, 100, "aeonite");
        register(gemAeonite);*/

        //other
        matterTemporal = new ItemClockworkPhase(64, 100, "matter_temporal");
        register(matterTemporal);

        silicon = new ItemClockworkPhase(64, 100, "silicon");
        register(silicon);
        OreDictionary.registerOre("itemSilicon", silicon);

        //CLOCKWORK COMPONENTS (GEARS/CLOCKWORK/MAINSPRING)

        //main components
        mainspring = new ItemMainspring(1, 100, "mainspring");
        register(mainspring);

        clockworkCore = new ItemClockworkCore(1, 100, "clockwork_core");
        register(clockworkCore);

        //gears
        gearWood = new ItemGear("gear_wood", 10, 15, 0);
        register(gearWood);
        OreDictionary.registerOre("gearWood", gearWood);

        gearStone = new ItemGear("gear_stone", 15, 10, 1);
        register(gearStone);
        OreDictionary.registerOre("gearStone", gearStone);

        gearIron = new ItemGear("gear_iron", 35, 25, 2);
        register(gearIron);
        OreDictionary.registerOre("gearIron", gearIron);

        gearGold = new ItemGear("gear_gold", 5, 70, 1);
        register(gearGold);
        OreDictionary.registerOre("gearGold", gearGold);

        gearDiamond = new ItemGear("gear_diamond", 50, 40, 3);
        register(gearDiamond);
        OreDictionary.registerOre("gearDiamond", gearDiamond);

        gearEmerald = new ItemGear("gear_emerald", 40, 50, 3);
        register(gearEmerald);
        OreDictionary.registerOre("gearEmerald", gearEmerald);

        gearQuartz = new ItemGear("gear_quartz", 70, 10, 2);
        register(gearQuartz);
        OreDictionary.registerOre("gearQuartz", gearQuartz);

        gearCopper = new ItemGear("gear_copper", 20, 30, 1);
        register(gearCopper);
        OreDictionary.registerOre("gearCopper", gearCopper);

        gearZinc = new ItemGear("gear_zinc", 30, 20, 1);
        register(gearZinc);
        OreDictionary.registerOre("gearZinc", gearZinc);

        gearBrass = new ItemGear("gear_brass", 60, 30, 2);
        register(gearBrass);
        OreDictionary.registerOre("gearBrass", gearBrass);

        gearChrysoberyl = new ItemGear("gear_chrysoberyl", 40, 70, 4);
        register(gearChrysoberyl);
        OreDictionary.registerOre("gearChrysoberyl", gearChrysoberyl);

        gearPearl = new ItemGear("gear_pearl", 80, 30, 4);
        register(gearPearl);
        OreDictionary.registerOre("gearPearl", gearPearl);

        gearSpinel = new ItemGear("gear_spinel", 70, 40, 4);
        register(gearSpinel);
        OreDictionary.registerOre("gearSpinel", gearSpinel);

        gearMoonstone = new ItemGear("gear_moonstone", 10, 110, 4);
        register(gearMoonstone);
        OreDictionary.registerOre("gearMoonstone", gearMoonstone);

        gearCreative = new ItemGear("gear_creative", 120, 120, 100);
        register(gearCreative);
        OreDictionary.registerOre("gearCreative", gearCreative);

        //registerItem(gearParadoxium, ItemGearParadoxium.class, nameQualitySpeedLevel, new Object[] {gearParadoxium.getUnlocalizedName(), 0, 0, 0}, "gearParadoxium");

        //TOOLS

        //clockwork
        clockworkPickaxe = new ItemClockworkPickaxe(clockworkMaterial, "clockwork_pickaxe");
        register(clockworkPickaxe);

        clockworkAxe = new ItemClockworkAxe(clockworkMaterial, "clockwork_axe");
        register(clockworkAxe);

        clockworkShovel = new ItemClockworkShovel(clockworkMaterial, "clockwork_shovel");
        register(clockworkShovel);

        //multitool shenanigans
        temporalExcavator = new ItemTemporalExcavator(temporalMaterial, "temporal_excavator");
        register(temporalExcavator);

        temporalHourglass = new ItemHourglass(1, 100, "temporal_hourglass", ConfigValues.TEMPORAL_HOURGLASS_MAX);
        register(temporalHourglass);

        temporalToolbelt = new ItemTemporalToolbelt(1, 100, "temporal_toolbelt");
        register(temporalToolbelt);

        temporalFishingRod = new ItemTemporalFishingRod("temporal_fishing_rod");
        register(temporalFishingRod);

        //stasisShield = new ItemPersonalStasisShield(1, 100, "stasis_shield");
        //register(stasisShield);

        //aquaticScepter = new ItemAquaticScepter(1, 100, "aquatic_scepter");
        //register(aquaticScepter);

        //misc
        trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "iron_trowel");
        register(trowelIron);

        trowelDiamond = new ItemTrowel(Item.ToolMaterial.DIAMOND, "diamond_trowel");
        register(trowelDiamond);

        //LIFE MODIFICATION

        mobCapsule = new ItemMobCapsule(1, 64, "mob_capsule");
        register(mobCapsule);

        mobBrain = new ItemBrain(1, 64, "mob_brain");
        register(mobBrain);

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

        //MACHINE UPGRADES
        temporalMachineConduit = new ItemMachineUpgrade(1, 100, "machine_upgrade_temporal");
        register(temporalMachineConduit);

        //WEAPONS
        clockworkSword = new ItemClockworkSword(1, 100, "clockwork_sword");
        register(clockworkSword);

        //MULTIBLOCK ASSEMBLERS
        multiblockCelestialCompass = new ItemMultiblockTemplate(1, 100, "multiblock_template_celestial_compass", MultiblockTemplateCelestialCompass.INSTANCE);
        register(multiblockCelestialCompass);

        multiblockArmillaryRing = new ItemMultiblockTemplate(1, 100, "multiblock_template_armillary_ring", MultiblockTemplateArmillaryRing.INSTANCE);
        register(multiblockArmillaryRing);

        //MISC
        moonFlowerSeeds = new ItemMoonFlowerSeeds(64, 100, "moon_flower_seeds");
        register(moonFlowerSeeds);

        assemblyTable = new ItemAssemblyTable(64, 100, "assembly_table_item");
        register(assemblyTable);

        bugSwatter = new ItemBugSwatter(1, 100, "bug_swatter");
        register(bugSwatter);

        //CRAFTING ITEMS
        gizmoLife = new ItemClockworkPhase(64, 100, "gizmo_life");
        register(gizmoLife);

        gizmoLifeRelic = new ItemRelic(64, 100, "gizmo_life_relic") {
            ItemStack result = new ItemStack(gizmoLife);
            @Override
            public ItemStack getResultItem(ItemStack inputStack) {
                return result;
            }
        };
        register(gizmoLifeRelic);

        gizmoLight = new ItemClockworkPhase(64, 100, "gizmo_light");
        register(gizmoLight);

        gizmoLightRelic = new ItemRelic(64, 100, "gizmo_light_relic") {
            ItemStack result = new ItemStack(gizmoLight);
            @Override
            public ItemStack getResultItem(ItemStack inputStack) {
                return result;
            }
        };
        register(gizmoLightRelic);

        gizmoWater = new ItemClockworkPhase(64, 100, "gizmo_water");
        register(gizmoWater);

        gizmoWaterRelic = new ItemRelic(64, 100, "gizmo_water_relic") {
            ItemStack result = new ItemStack(gizmoWater);
            @Override
            public ItemStack getResultItem(ItemStack inputStack) {
                return result;
            }
        };
        register(gizmoWaterRelic);

        gizmoEarth = new ItemClockworkPhase(64, 100, "gizmo_earth");
        register(gizmoEarth);

        gizmoEarthRelic = new ItemRelic(64, 100, "gizmo_earth_relic") {
            ItemStack result = new ItemStack(gizmoEarth);
            @Override
            public ItemStack getResultItem(ItemStack inputStack) {
                return result;
            }
        };
        register(gizmoEarthRelic);

        gizmoAir = new ItemClockworkPhase(64, 100, "gizmo_air");
        register(gizmoAir);

        gizmoAirRelic = new ItemRelic(64, 100, "gizmo_air_relic") {
            ItemStack result = new ItemStack(gizmoAir);
            @Override
            public ItemStack getResultItem(ItemStack inputStack) {
                return result;
            }
        };
        register(gizmoAirRelic);

        gizmoFire = new ItemClockworkPhase(64, 100, "gizmo_fire");
        register(gizmoFire);

        gizmoFireRelic = new ItemRelic(64, 100, "gizmo_fire_relic") {
            ItemStack result = new ItemStack(gizmoFire);
            @Override
            public ItemStack getResultItem(ItemStack inputStack) {
                return result;
            }
        };
        register(gizmoFireRelic);

        gizmoAura = new ItemClockworkPhase(64, 100, "gizmo_aura");
        register(gizmoAura);

        gizmoAuraRelic = new ItemRelic(64, 100, "gizmo_aura_relic") {
            ItemStack result = new ItemStack(gizmoAura);
            @Override
            public ItemStack getResultItem(ItemStack inputStack) {
                return result;
            }
        };
        register(gizmoAuraRelic);

        splitTimelineMatrix = new ItemClockworkPhase(64, 100, "split_timeline_matrix");
        register(splitTimelineMatrix);

        splitTimelineMatrixRelic = new ItemRelic(64, 100, "split_timeline_matrix_relic")  {
            ItemStack result = new ItemStack(splitTimelineMatrix);
            @Override
            public ItemStack getResultItem(ItemStack inputStack) {
                return result;
            }
        };
        register(splitTimelineMatrixRelic);

        temporalDrive = new ItemClockworkPhase(64, 100, "temporal_drive");
        register(temporalDrive);


        RelicExcavationRegistry.registerMoonFlowerRelicDrop(new WeightedChance<>(new ItemStack(moonFlowerSeeds), 100));
        RelicExcavationRegistry.registerOtherRelicDrop(new WeightedChance<>(new ItemStack(gizmoLifeRelic), 100));
        RelicExcavationRegistry.registerOtherRelicDrop(new WeightedChance<>(new ItemStack(gizmoLightRelic), 100));
        RelicExcavationRegistry.registerOtherRelicDrop(new WeightedChance<>(new ItemStack(gizmoWaterRelic), 100));
        RelicExcavationRegistry.registerOtherRelicDrop(new WeightedChance<>(new ItemStack(gizmoEarthRelic), 100));
        RelicExcavationRegistry.registerOtherRelicDrop(new WeightedChance<>(new ItemStack(gizmoAirRelic), 100));
        RelicExcavationRegistry.registerOtherRelicDrop(new WeightedChance<>(new ItemStack(gizmoFireRelic), 100));
        RelicExcavationRegistry.registerOtherRelicDrop(new WeightedChance<>(new ItemStack(gizmoAuraRelic), 100));
        RelicExcavationRegistry.registerOtherRelicDrop(new WeightedChance<>(new ItemStack(splitTimelineMatrixRelic), 100));
    }

    //************ START HELPER METHODS ************\\

    private static void register(Item item)
    {
        ForgeRegistries.ITEMS.register(item);
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
