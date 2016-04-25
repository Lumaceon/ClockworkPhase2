package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.time.timezone.TileTimezoneModulator;
import lumaceon.mods.clockworkphase2.block.*;
import lumaceon.mods.clockworkphase2.block.fluids.BlockLiquidTemporium;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockClockworkNetworkConnector;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockCrank;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockCreativeMainspring;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.*;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.itemblock.*;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkConnector;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileCreativeMainspring;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.*;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.tile.*;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidExporter;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneFluidImporter;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeCollector;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeWell;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ModBlocks
{
    public static ArrayList<BlockReference> blocksForModel = new ArrayList<BlockReference>(200);

    //ORES
    public static BlockReference oreCopper = new BlockReference("copper_ore");
    public static BlockReference oreZinc = new BlockReference("zinc_ore");
    public static BlockReference relicThirdAge = new BlockReference("third_age_relic");
    public static BlockReference relicSecondAge = new BlockReference("second_age_relic");
    public static BlockReference relicFirstAge = new BlockReference("first_age_relic");
    //METAL BLOCKS
    public static BlockReference blockCopper = new BlockReference("copper_block");
    public static BlockReference blockZinc = new BlockReference("zinc_block");
    public static BlockReference blockBrass = new BlockReference("brass_block");
    public static BlockReference blockTemporal = new BlockReference("temporal_block");
    //PLANTS
    public static BlockReference moonFlower = new BlockReference("moon_flower");
    //FLUIDS
    public static BlockReference liquidTemporium = new BlockReference("liquid_temporium");
    //CLOCKWORK NETWORK
    public static BlockReference crank = new BlockReference("crank");
    public static BlockReference clockworkNetworkConnector = new BlockReference("clockwork_network_connector");
    public static BlockReference creative_mainspring = new BlockReference("mainspring_creative");
    public static BlockReference clockworkController = new BlockReference("clockwork_controller");
    public static BlockReference clockworkFurnace = new BlockReference("clockwork_furnace");
    public static BlockReference clockworkMelter = new BlockReference("clockwork_melter");
    public static BlockReference clockworkBrewery = new BlockReference("clockwork_brewery");
    public static BlockReference clockworkMixer = new BlockReference("clockwork_mixer");
    public static BlockReference clockworkAlloyFurnace = new BlockReference("clockwork_alloy_furnace");
    public static BlockReference clockworkExperimentalAlloyFurnace = new BlockReference("clockwork_experimental_alloy_furnace");
    public static BlockReference clockworkCraftingTable = new BlockReference("clockwork_crafting_table");
    public static BlockReference clockworkItemStorage = new BlockReference("clockwork_item_storage");
    public static BlockReference clockworkScreen = new BlockReference("clockwork_screen");
    //TEMPORAL BLOCKS
    public static BlockReference timezoneController = new BlockReference("timezone_controller");
    public static BlockReference timezoneControllerSB = new BlockReference("timezone_controller_sb");
    public static BlockReference timezoneModulator = new BlockReference("timezone_modulator");
    public static BlockReference timezoneFluidExporter = new BlockReference("timezone_fluid_exporter");
    public static BlockReference timezoneFluidImporter = new BlockReference("timezone_fluid_importer");
    //MISC
    public static BlockReference basicWindingBox = new BlockReference("basic_winding_box");
    public static BlockReference assemblyTable = new BlockReference("assembly_table");
    public static BlockReference temporalDistortionAltar = new BlockReference(Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR);
    public static BlockReference temporalDistortionAltarSB = new BlockReference(Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR_SB);
    public static void init()
    {
        Class[] matName = new Class[] {Material.class, String.class};
        Class[] matLevelName = new Class[] {Material.class, int.class, String.class};
        Class[] matLevelNameItemRefs = new Class[] {Material.class, int.class, String.class, ArrayList.class};
        Class[] matNameFluid = new Class[] {Material.class, String.class, Fluid.class};

        ArrayList<ModItems.ItemReference> thirdAgeRelics = new ArrayList<ModItems.ItemReference>();
        thirdAgeRelics.add(ModItems.moonFlowerSeeds);
        ArrayList<ModItems.ItemReference> secondAgeRelics = new ArrayList<ModItems.ItemReference>();
        secondAgeRelics.add(ModItems.mainspring);
        ArrayList<ModItems.ItemReference> firstAgeRelics = new ArrayList<ModItems.ItemReference>();
        firstAgeRelics.add(ModItems.clockworkPickaxe);

        //ORES
        registerBlock(oreCopper, BlockClockworkPhaseOre.class, matLevelName, new Object[] {Material.rock, 1, oreCopper.getUnlocalizedName()}, "oreCopper");
        registerBlock(oreZinc, BlockClockworkPhaseOre.class, matLevelName, new Object[] {Material.rock, 1, oreZinc.getUnlocalizedName()}, "oreZinc");
        registerBlock(relicThirdAge, BlockClockworkPhaseRelic.class, matLevelNameItemRefs, new Object[] {Material.ground, 1, relicThirdAge.getUnlocalizedName(), thirdAgeRelics});
        registerBlock(relicSecondAge, BlockClockworkPhaseRelic.class, matLevelNameItemRefs, new Object[] {Material.ground, 2, relicSecondAge.getUnlocalizedName(), secondAgeRelics});
        registerBlock(relicFirstAge, BlockClockworkPhaseRelic.class, matLevelNameItemRefs, new Object[] {Material.ground, 3, relicFirstAge.getUnlocalizedName(), firstAgeRelics});
        //METAL BLOCKS
        registerBlock(blockCopper, BlockClockworkPhase.class, matName, new Object[] {Material.iron, blockCopper.getUnlocalizedName()}, "blockCopper");
        registerBlock(blockZinc, BlockClockworkPhase.class, matName, new Object[] {Material.iron, blockZinc.getUnlocalizedName()}, "blockZinc");
        registerBlock(blockBrass, BlockClockworkPhase.class, matName, new Object[] {Material.iron, blockBrass.getUnlocalizedName()}, "blockBrass");
        registerBlock(blockTemporal, BlockClockworkPhase.class, matName, new Object[] {Material.iron, blockTemporal.getUnlocalizedName()}, "blockTemporal");
        //PLANTS
        registerBlock(moonFlower, BlockMoonFlower.class, matName, new Object[] {Material.plants, moonFlower.getUnlocalizedName()});
        //FLUIDS
        registerBlock(liquidTemporium, BlockLiquidTemporium.class, matNameFluid, new Object[] {Material.water, liquidTemporium.getUnlocalizedName(), ModFluids.liquidTemporium});
        //CLOCKWORK NETWORK
        registerBlock(crank, BlockCrank.class, matName, new Object[] {Material.iron, crank.getUnlocalizedName()});
        registerBlock(clockworkNetworkConnector, BlockClockworkNetworkConnector.class, matName, new Object[] {Material.iron, clockworkNetworkConnector.getUnlocalizedName()});
        registerBlock(creative_mainspring, BlockCreativeMainspring.class, matName, new Object[] {Material.iron, creative_mainspring.getUnlocalizedName()});
        registerBlock(clockworkController, BlockClockworkController.class, matName, new Object[] {Material.iron, clockworkController.getUnlocalizedName()});
        registerBlock(clockworkFurnace, BlockClockworkFurnace.class, matName, new Object[] {Material.iron, clockworkFurnace.getUnlocalizedName()}, ItemBlockClockworkFurnace.class);
        registerBlock(clockworkMelter, BlockClockworkMelter.class, matName, new Object[] {Material.iron, clockworkMelter.getUnlocalizedName()}, ItemBlockClockworkMelter.class);
        registerBlock(clockworkBrewery, BlockClockworkBrewery.class, matName, new Object[] {Material.iron, clockworkBrewery.getUnlocalizedName()}, ItemBlockClockworkBrewery.class);
        registerBlock(clockworkMixer, BlockClockworkMixer.class, matName, new Object[] {Material.iron, clockworkMixer.getUnlocalizedName()}, ItemBlockClockworkMixer.class);
        registerBlock(clockworkAlloyFurnace, BlockClockworkAlloyFurnace.class, matName, new Object[] {Material.iron, clockworkAlloyFurnace.getUnlocalizedName()}, ItemBlockClockworkAlloyFurnace.class);
        registerBlock(clockworkExperimentalAlloyFurnace, BlockClockworkSuperAlloyFurnace.class, matName, new Object[] {Material.iron, clockworkExperimentalAlloyFurnace.getUnlocalizedName()}, ItemBlockClockworkSuperAlloyFurnace.class);
        registerBlock(clockworkCraftingTable, BlockClockworkCraftingTable.class, matName, new Object[] {Material.iron, clockworkCraftingTable.getUnlocalizedName()});
        registerBlock(clockworkItemStorage, BlockClockworkItemStorage.class, matName, new Object[] {Material.iron, clockworkItemStorage.getUnlocalizedName()});
        registerBlock(clockworkScreen, BlockClockworkScreen.class, matName, new Object[] {Material.iron, clockworkScreen.getUnlocalizedName()});
        //TEMPORAL BLOCKS
        registerBlock(timezoneController, BlockTimezoneController.class, matName, new Object[] {Material.iron, timezoneController.getUnlocalizedName()});
        registerBlock(timezoneControllerSB, BlockTimezoneControllerSB.class, matName, new Object[] {Material.iron, timezoneControllerSB.getUnlocalizedName()});
        registerBlock(timezoneModulator, BlockTimezoneModulator.class, matName, new Object[] {Material.iron, timezoneModulator.getUnlocalizedName()});
        registerBlock(timezoneFluidExporter, BlockTimezoneFluidExporter.class, matName, new Object[] {Material.iron, timezoneFluidExporter.getUnlocalizedName()});
        registerBlock(timezoneFluidImporter, BlockTimezoneFluidImporter.class, matName, new Object[] {Material.iron, timezoneFluidImporter.getUnlocalizedName()});
        //MISC
        registerBlock(basicWindingBox, BlockBasicWindingBox.class, matName, new Object[] {Material.iron, basicWindingBox.getUnlocalizedName()});
        registerBlock(assemblyTable, BlockAssemblyTable.class, matName, new Object[] {Material.wood, assemblyTable.getUnlocalizedName()});
        registerBlock(temporalDistortionAltar, BlockTemporalDisplacementAltar.class, matName, new Object[] {Material.iron, temporalDistortionAltar.getUnlocalizedName()});
        registerBlock(temporalDistortionAltarSB, BlockTemporalDisplacementAltarSB.class, matName, new Object[] {Material.iron, temporalDistortionAltarSB.getUnlocalizedName()});
    }

    /*public static Block timezoneController;
    public static Block timezoneControllerSB;
    public static Block temporalDisplacementAltar;
    public static Block temporalDisplacementAltarSB;
    public static Block tda;
    public static Block tdaSB;
    public static Block timezoneFluidExporter;
    public static Block timezoneFluidImporter;
    public static Block timeCollector;
    public static Block temporalFurnace;
    public static Block timeWell;
    public static void initTimeMachines()
    {
        timezoneController = new BlockCelestialCompass(Material.iron, Names.BLOCK.CELESTIAL_COMPASS);
        timezoneControllerSB = new BlockCelestialCompassSB(Material.iron, Names.BLOCK.CELESTIAL_COMPASS_SB);
        temporalDisplacementAltar = new BlockTemporalDisplacementAltar(Material.iron, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR);
        temporalDisplacementAltarSB = new BlockTemporalDisplacementAltarSB(Material.iron, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR_SB);
        tda = new BlockTDA(Material.iron, Names.BLOCK.TDA);
        tdaSB = new BlockTDASB(Material.iron, Names.BLOCK.TDA_SB);
        timezoneFluidExporter = new BlockTimezoneFluidExporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        timezoneFluidImporter = new BlockTimezoneFluidImporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
        timeCollector = new BlockTimeCollector(Material.iron, Names.BLOCK.TIME_COLLECTOR);
        temporalFurnace = new BlockTemporalFurnace(Material.iron, Names.BLOCK.TEMPORAL_FURNACE);
        timeWell = new BlockTimeWell(Material.iron, Names.BLOCK.TIME_WELL);

        GameRegistry.registerBlock(timezoneController, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerBlock(timezoneControllerSB, Names.BLOCK.CELESTIAL_COMPASS_SB);
        GameRegistry.registerBlock(temporalDisplacementAltar, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR);
        GameRegistry.registerBlock(temporalDisplacementAltarSB, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR_SB);
        GameRegistry.registerBlock(tda, Names.BLOCK.TDA);
        GameRegistry.registerBlock(tdaSB, Names.BLOCK.TDA_SB);
        GameRegistry.registerBlock(timezoneFluidExporter, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        GameRegistry.registerBlock(timezoneFluidImporter, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
        GameRegistry.registerBlock(timeCollector, Names.BLOCK.TIME_COLLECTOR);
        GameRegistry.registerBlock(temporalFurnace, Names.BLOCK.TEMPORAL_FURNACE);
        GameRegistry.registerBlock(timeWell, Names.BLOCK.TIME_WELL);
    }*/

    public static void initTE()
    {
        GameRegistry.registerTileEntity(TileTimezoneController.class, timezoneController.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTimezoneModulator.class, timezoneModulator.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTemporalDisplacementAltar.class, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR);
        GameRegistry.registerTileEntity(TileTDA.class, Names.BLOCK.TDA);
        GameRegistry.registerTileEntity(TileTimezoneFluidExporter.class, timezoneFluidExporter.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTimezoneFluidImporter.class, timezoneFluidImporter.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTimeCollector.class, Names.BLOCK.TIME_COLLECTOR);
        GameRegistry.registerTileEntity(TileAssemblyTable.class, assemblyTable.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTemporalFurnace.class, Names.BLOCK.TEMPORAL_FURNACE);
        GameRegistry.registerTileEntity(TileTimeWell.class, Names.BLOCK.TIME_WELL);

        //CLOCKWORK NETWORK
        GameRegistry.registerTileEntity(TileClockworkNetworkConnector.class, clockworkNetworkConnector.getUnlocalizedName());
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
        GameRegistry.registerTileEntity(TileClockworkScreen.class, clockworkScreen.getUnlocalizedName());
    }

    public static void initModels() {
        if(blocksForModel != null)
        {
            for(BlockReference block : blocksForModel)
                if(block != null && block.getBlock() != null)
                    ClockworkPhase2.proxy.registerBlockModel(block.getBlock(), block.getUnlocalizedName());
            blocksForModel.clear();
        }
        blocksForModel = null;
    }

    //************ START HELPER METHODS ************\\

    /**
     * Used to construct a new class of the specified type, set the block in the BlockReference, register the block with
     * Forge and register the model.
     * @param block A container class, referencing the block we're registering.
     * @param blockClass The class to instantiate the block to.
     * @param targetConstructor An array of classes the target constructor for blockClass expects.
     * @param parameters The parameters mentioned in targetConstructor.
     */
    public static void registerBlock(BlockReference block, Class<? extends Block> blockClass, Class[] targetConstructor, Object[] parameters)
    {
        try {
            Constructor constructor = blockClass.getDeclaredConstructor(targetConstructor);
            Block blc = (Block) constructor.newInstance(parameters);
            block.setBlock(blc);
            GameRegistry.registerBlock(blc, block.getUnlocalizedName());
            blocksForModel.add(block);
        } catch (NoSuchMethodException e) {
            Logger.error("Tried to instantiate class for block '" + block.getUnlocalizedName() + "' with invalid parameters.");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void registerBlock(BlockReference block, Class<? extends Block> blockClass, Class[] targetConstructor, Object[] parameters, String oreDictName)
    {
        try {
            Constructor constructor = blockClass.getDeclaredConstructor(targetConstructor);
            Block blc = (Block) constructor.newInstance(parameters);
            block.setBlock(blc);
            GameRegistry.registerBlock(blc, block.getUnlocalizedName());
            OreDictionary.registerOre(oreDictName, blc);
            blocksForModel.add(block);
        } catch (NoSuchMethodException e) {
            Logger.error("Tried to instantiate class for block '" + block.getUnlocalizedName() + "' with invalid parameters.");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void registerBlock(BlockReference block, Class<? extends Block> blockClass, Class[] targetConstructor, Object[] parameters, Class<? extends ItemBlock> itemBlockClass)
    {
        try {
            Constructor constructor = blockClass.getDeclaredConstructor(targetConstructor);
            Block blc = (Block) constructor.newInstance(parameters);
            block.setBlock(blc);
            GameRegistry.registerBlock(blc, itemBlockClass, block.getUnlocalizedName());
            blocksForModel.add(block);
        } catch (NoSuchMethodException e) {
            Logger.error("Tried to instantiate class for block '" + block.getUnlocalizedName() + "' with invalid parameters.");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void registerBlock(BlockReference block, Class<? extends Block> blockClass, Class[] targetConstructor, Object[] parameters, Class<? extends ItemBlock> itemBlockClass, String oreDictName)
    {
        try {
            Constructor constructor = blockClass.getDeclaredConstructor(targetConstructor);
            Block blc = (Block) constructor.newInstance(parameters);
            block.setBlock(blc);
            GameRegistry.registerBlock(blc, itemBlockClass, block.getUnlocalizedName());
            OreDictionary.registerOre(oreDictName, blc);
            blocksForModel.add(block);
        } catch (NoSuchMethodException e) {
            Logger.error("Tried to instantiate class for block '" + block.getUnlocalizedName() + "' with invalid parameters.");
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
     * Gateway class to allow registerBlock to modify the block references, simplifying the registration process.
     */
    public static class BlockReference
    {
        private Block block;
        private String unlocalizedName = null;

        public BlockReference(String unlocalizedName) {
            block = null;
            this.unlocalizedName = unlocalizedName;
        }

        public Block getBlock() {
            return this.block;
        }

        public void setBlock(Block block) {
            this.block = block;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        public void setUnlocalizedName(String unlocalizedName) {
            this.unlocalizedName = unlocalizedName;
        }
    }
}
