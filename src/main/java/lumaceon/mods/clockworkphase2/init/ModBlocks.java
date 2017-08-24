package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.RelicExcavationRegistry;
import lumaceon.mods.clockworkphase2.block.*;
import lumaceon.mods.clockworkphase2.block.itemblock.ItemBlockMachine;
import lumaceon.mods.clockworkphase2.block.machine.*;
import lumaceon.mods.clockworkphase2.block.machine.lifeform.BlockLifeformConstructor;
import lumaceon.mods.clockworkphase2.block.machine.lifeform.BlockLifeformDeconstructor;
import lumaceon.mods.clockworkphase2.block.machine.lifeform.BlockLifeformReconstructor;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateArmillaryRing;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.block.temporal.*;
import lumaceon.mods.clockworkphase2.block.temporal.armillary.*;
import lumaceon.mods.clockworkphase2.tile.*;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkAlloyFurnace;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkCrusher;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkCrystallizer;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformConstructor;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformDeconstructor;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformReconstructor;
import lumaceon.mods.clockworkphase2.tile.temporal.*;
import lumaceon.mods.clockworkphase2.util.ISimpleNamed;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class ModBlocks
{
    public static ArrayList<Block> blocksForModel = new ArrayList<Block>(200);

    //ORES
    public static Block oreCopper;
    public static Block oreZinc;
    public static Block oreAluminum;
    public static Block relicMoonFlower;
    public static Block relicUnknown;
    //METAL BLOCKS
    public static Block blockCopper;
    public static Block blockZinc;
    public static Block blockBrass;
    public static Block blockAluminum;
    public static Block blockTemporal;
    //PLANTS
    public static Block moonFlower;
    //FLUIDS
        //public static BlockReference liquidTemporium = new BlockReference("liquid_temporium");
    //MACHINES
    public static Block clockworkFurnace;
    public static Block clockworkAlloyFurnace;
    public static Block clockworkCrusher;
    public static Block clockworkCrystallizer;
    public static Block experienceExtractor;
    public static Block lifeformConstructor;
    public static Block lifeformReconstructor;
    public static Block lifeformDeconstructor;
    //TEMPORAL BLOCKS
    public static Block temporalRelay;
    public static Block celestialCompass;
    public static Block celestialCompassSB;
    public static Block temporalZoningMachine;
    public static Block armillaryRing;
    public static Block armillaryRingFrame;
    public static Block armillaryRingFrameBottom;
    public static Block armillaryRingFrameBottomCorner;
    //MISC
    public static Block basicWindingBox;
    public static Block assemblyTable;
    public static Block multiblockAssembler;
    public static Block constructionBlock;
    public static Block bugSwatter;
    public static Block ruinedLand;
    public static void init()
    {
        //ORES
        oreCopper = new BlockClockworkPhaseOre(Material.ROCK, 1, "copper_ore");
        register(oreCopper);
        OreDictionary.registerOre("oreCopper", oreCopper);

        oreZinc = new BlockClockworkPhaseOre(Material.ROCK, 1, "zinc_ore");
        register(oreZinc);
        OreDictionary.registerOre("oreZinc", oreZinc);

        oreAluminum = new BlockClockworkPhaseOre(Material.ROCK, 1, "aluminum_ore");
        register(oreAluminum);
        OreDictionary.registerOre("oreAluminum", oreAluminum);

        relicMoonFlower = new BlockClockworkPhaseRelic(Material.GROUND, 0, "moon_flower_relic", RelicExcavationRegistry.getMoonFlowerRelicDropList());
        register(relicMoonFlower);

        relicUnknown = new BlockClockworkPhaseRelic(Material.GROUND, 0, "unknown_relic", RelicExcavationRegistry.getUnknownRelicDropList());
        register(relicUnknown);

        //METAL BLOCKS
        blockCopper = new BlockClockworkPhase(Material.IRON, "copper_block");
        register(blockCopper);
        OreDictionary.registerOre("blockCopper", blockCopper);

        blockZinc = new BlockClockworkPhase(Material.IRON, "zinc_block");
        register(blockZinc);
        OreDictionary.registerOre("blockZinc", blockZinc);

        blockBrass = new BlockClockworkPhase(Material.IRON, "brass_block");
        register(blockBrass);
        OreDictionary.registerOre("blockBrass", blockBrass);

        blockAluminum = new BlockClockworkPhase(Material.IRON, "aluminum_block");
        register(blockAluminum);
        OreDictionary.registerOre("blockAluminum", blockAluminum);

        blockTemporal = new BlockClockworkPhase(Material.IRON, "temporal_block");
        register(blockTemporal);
        OreDictionary.registerOre("blockTemporal", blockTemporal);

        //PLANTS
        moonFlower = new BlockMoonFlower(Material.PLANTS, "moon_flower");
        register(moonFlower);

        //MACHINES

        //standard clockwork machines
        clockworkFurnace = new BlockClockworkFurnace(Material.IRON, "clockwork_furnace");
        registerWithoutItemBlock(clockworkFurnace);
        ForgeRegistries.ITEMS.register(new ItemBlockMachine(clockworkFurnace).setRegistryName("clockwork_furnace"));

        clockworkAlloyFurnace = new BlockClockworkAlloyFurnace(Material.IRON, "clockwork_alloy_furnace");
        registerWithoutItemBlock(clockworkAlloyFurnace);
        ForgeRegistries.ITEMS.register(new ItemBlockMachine(clockworkAlloyFurnace).setRegistryName("clockwork_alloy_furnace"));

        clockworkCrusher = new BlockClockworkCrusher(Material.IRON, "clockwork_crusher");
        registerWithoutItemBlock(clockworkCrusher);
        ForgeRegistries.ITEMS.register(new ItemBlockMachine(clockworkCrusher).setRegistryName("clockwork_crusher"));

        clockworkCrystallizer = new BlockClockworkCrystallizer(Material.IRON, "clockwork_crystallizer");
        registerWithoutItemBlock(clockworkCrystallizer);
        ForgeRegistries.ITEMS.register(new ItemBlockMachine(clockworkCrystallizer).setRegistryName("clockwork_crystallizer"));

        //experience machines
        /*experienceExtractor = new BlockExperienceExtractor(Material.IRON, "experience_extractor");
        registerWithoutItemBlock(experienceExtractor);
        ForgeRegistries.ITEMS.register(new ItemBlockMachine(experienceExtractor).setRegistryName("experience_extractor"));*/

        //lifeform machines
        lifeformConstructor = new BlockLifeformConstructor(Material.IRON, "lifeform_constructor");
        registerWithoutItemBlock(lifeformConstructor);
        ForgeRegistries.ITEMS.register(new ItemBlockMachine(lifeformConstructor).setRegistryName("lifeform_constructor"));

        lifeformReconstructor = new BlockLifeformReconstructor(Material.IRON, "lifeform_reconstructor");
        registerWithoutItemBlock(lifeformReconstructor);
        ForgeRegistries.ITEMS.register(new ItemBlockMachine(lifeformReconstructor).setRegistryName("lifeform_reconstructor"));

        lifeformDeconstructor = new BlockLifeformDeconstructor(Material.IRON, "lifeform_deconstructor");
        registerWithoutItemBlock(lifeformDeconstructor);
        ForgeRegistries.ITEMS.register(new ItemBlockMachine(lifeformDeconstructor).setRegistryName("lifeform_deconstructor"));

        //TEMPORAL BLOCKS
        temporalRelay = new BlockTemporalRelay(Material.IRON, "temporal_relay");
        register(temporalRelay);

        //I guess you could call this a....TIMESHARE investment. BAHAHAHAHA!
        temporalZoningMachine = new BlockTemporalZoningMachine(Material.IRON, "temporal_zoning_machine");
        register(temporalZoningMachine);

        armillaryRing = new BlockArmillaryRing(Material.IRON, "armillary_ring");
        register(armillaryRing);

        armillaryRingFrame = new BlockArmillaryRingFrame(Material.IRON, "armillary_ring_frame");
        register(armillaryRingFrame);

        armillaryRingFrameBottom = new BlockArmillaryRingFrameBottom(Material.IRON, "armillary_ring_frame_bottom");
        register(armillaryRingFrameBottom);

        armillaryRingFrameBottomCorner = new BlockArmillaryRingFrameBottomCorner(Material.IRON, "armillary_ring_frame_bottom_corner");
        register(armillaryRingFrameBottomCorner);

        celestialCompass = new BlockCelestialCompass(Material.GLASS, "celestial_compass");
        register(celestialCompass);

        celestialCompassSB = new BlockCelestialCompass(Material.GLASS, "celestial_compass_sb");
        register(celestialCompassSB);
        //MISC


        basicWindingBox = new BlockBasicWindingBox(Material.IRON, "basic_winding_box");
        register(basicWindingBox);

        assemblyTable = new BlockAssemblyTable(Material.WOOD, "assembly_table");
        register(assemblyTable);

        multiblockAssembler = new BlockMultiblockAssembler(Material.IRON, "multiblock_assembler");
        register(multiblockAssembler);

        constructionBlock = new BlockConstruction(Material.IRON, "construction_block");
        register(constructionBlock);

        //bugSwatter = new BlockBugSwatter(Material.GLASS, "bug_swatter_block");
        //register(bugSwatter);

        ruinedLand = new BlockClockworkPhase(Material.ROCK, "ruined_land");
        register(ruinedLand);

        postInit();
    }

    /**
     * To initialize things that require blocks to already be registered.
     */
    public static void postInit()
    {
        MultiblockTemplateCelestialCompass.INSTANCE.init();
        MultiblockTemplateArmillaryRing.INSTANCE.init();
    }

    public static void initTE()
    {
        GameRegistry.registerTileEntity(TileMultiblockAssembler.class, multiblockAssembler.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileAssemblyTable.class, assemblyTable.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkFurnace.class, clockworkFurnace.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkAlloyFurnace.class, clockworkAlloyFurnace.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkCrusher.class, clockworkCrusher.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkCrystallizer.class, clockworkCrystallizer.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileLifeformConstructor.class, lifeformConstructor.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileLifeformReconstructor.class, lifeformReconstructor.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileLifeformDeconstructor.class, lifeformDeconstructor.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTemporalRelay.class, temporalRelay.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTemporalZoningMachine.class, temporalZoningMachine.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileArmillaryRing.class, armillaryRing.getUnlocalizedName());
        //GameRegistry.registerTileEntity(TileCelestialCompass.class, celestialCompass.getUnlocalizedName());

        //CLOCKWORK NETWORK
        /*GameRegistry.registerTileEntity(TileClockworkNetworkConnector.class, clockworkNetworkConnector.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileCreativeMainspring.class, creative_mainspring.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkController.class, clockworkController.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkFurnace.class, clockworkFurnace.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkBrewery.class, clockworkBrewery.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkMixer.class, clockworkMixer.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkMelter.class, clockworkMelter.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkSuperAlloyFurnace.class, clockworkExperimentalAlloyFurnace.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkAlloyFurnace.class, clockworkAlloyFurnace.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkCraftingTable.class, clockworkCraftingTable.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkItemStorage.class, clockworkItemStorage.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileClockworkScreen.class, clockworkScreen.getUnlocalizedName());*/
    }

    //************ START HELPER METHODS ************\\

    private static void register(Block block)
    {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        blocksForModel.add(block);
    }

    /**
     * By default, register also includes an ItemBlock (blocks are held in the inventory as ItemBlocks, which extend
     * Item). This will skip the ItemBlock and only register the Block, making it unable to be held in the inventory.
     *
     * Generally you would want to use this when you want to register your own custom ItemBlock for the block.
     */
    private static void registerWithoutItemBlock(Block block)
    {
        ForgeRegistries.BLOCKS.register(block);
        blocksForModel.add(block);
    }

    public static void initModels() {
        if(blocksForModel != null)
        {
            for(Block block : blocksForModel)
                if(block != null && block instanceof ISimpleNamed)
                    ClockworkPhase2.proxy.registerBlockModel(block, ((ISimpleNamed) block).getSimpleName());
            blocksForModel.clear();
        }
        blocksForModel = null;
    }
}
