package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.RelicExcavationRegistry;
import lumaceon.mods.clockworkphase2.block.*;
import lumaceon.mods.clockworkphase2.block.clockwork.BlockBasicWindingBox;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.block.temporal.*;
import lumaceon.mods.clockworkphase2.tile.*;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
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
    public static BlockReference relicMoonFlower = new BlockReference("moon_flower_relic");
    public static BlockReference relicUnknown = new BlockReference("unknown_relic");
    //METAL BLOCKS
    public static BlockReference blockCopper = new BlockReference("copper_block");
    public static BlockReference blockZinc = new BlockReference("zinc_block");
    public static BlockReference blockBrass = new BlockReference("brass_block");
    public static BlockReference blockTemporal = new BlockReference("temporal_block");
    //PLANTS
    public static BlockReference moonFlower = new BlockReference("moon_flower");
    //FLUIDS
    //public static BlockReference liquidTemporium = new BlockReference("liquid_temporium");
    //TEMPORAL BLOCKS
    public static BlockReference celestialCompass = new BlockReference("celestial_compass");
    public static BlockReference celestialCompassSB = new BlockReference("celestial_compass_sb");
    //MISC
    public static BlockReference basicWindingBox = new BlockReference("basic_winding_box");
    public static BlockReference assemblyTable = new BlockReference("assembly_table");
    public static BlockReference multiblockAssembler = new BlockReference("multiblock_assembler");
    public static BlockReference constructionBlock = new BlockReference("construction_block");
    public static void init()
    {
        Class[] matName = new Class[] {Material.class, String.class};
        Class[] matLevelName = new Class[] {Material.class, int.class, String.class};
        Class[] matLevelNameItemRefs = new Class[] {Material.class, int.class, String.class, ArrayList.class};

        //ORES
        registerBlock(oreCopper, BlockClockworkPhaseOre.class, matLevelName, new Object[] {Material.ROCK, 1, oreCopper.getUnlocalizedName()}, "oreCopper");
        registerBlock(oreZinc, BlockClockworkPhaseOre.class, matLevelName, new Object[] {Material.ROCK, 1, oreZinc.getUnlocalizedName()}, "oreZinc");
        registerBlock(relicMoonFlower, BlockClockworkPhaseRelic.class, matLevelNameItemRefs, new Object[] {Material.GROUND, 0, relicMoonFlower.getUnlocalizedName(), RelicExcavationRegistry.getMoonFlowerRelicDropList()});
        registerBlock(relicUnknown, BlockClockworkPhaseRelic.class, matLevelNameItemRefs, new Object[] {Material.GROUND, 0, relicUnknown.getUnlocalizedName(), RelicExcavationRegistry.getUnknownRelicDropList()});
        //METAL BLOCKS
        registerBlock(blockCopper, BlockClockworkPhase.class, matName, new Object[] {Material.IRON, blockCopper.getUnlocalizedName()}, "blockCopper");
        registerBlock(blockZinc, BlockClockworkPhase.class, matName, new Object[] {Material.IRON, blockZinc.getUnlocalizedName()}, "blockZinc");
        registerBlock(blockBrass, BlockClockworkPhase.class, matName, new Object[] {Material.IRON, blockBrass.getUnlocalizedName()}, "blockBrass");
        registerBlock(blockTemporal, BlockClockworkPhase.class, matName, new Object[] {Material.IRON, blockTemporal.getUnlocalizedName()}, "blockTemporal");
        //PLANTS
        registerBlock(moonFlower, BlockMoonFlower.class, matName, new Object[] {Material.PLANTS, moonFlower.getUnlocalizedName()});
        //FLUIDS
        //TEMPORAL BLOCKS
        registerBlock(celestialCompass, BlockCelestialCompass.class, matName, new Object[] {Material.IRON, celestialCompass.getUnlocalizedName()});
        registerBlock(celestialCompassSB, BlockCelestialCompassSB.class, matName, new Object[] {Material.IRON, celestialCompassSB.getUnlocalizedName()});
        //MISC
        registerBlock(basicWindingBox, BlockBasicWindingBox.class, matName, new Object[] {Material.IRON, basicWindingBox.getUnlocalizedName()});
        registerBlock(assemblyTable, BlockAssemblyTable.class, matName, new Object[]{Material.WOOD, assemblyTable.getUnlocalizedName()}, true);
        registerBlock(multiblockAssembler, BlockMultiblockAssembler.class, matName, new Object[] {Material.IRON, multiblockAssembler.getUnlocalizedName()});
        registerBlock(constructionBlock, BlockConstruction.class, matName, new Object[] {Material.IRON, constructionBlock.getUnlocalizedName()});
        postInit();
    }

    /**
     * To initialize things that require blocks to already be registered.
     */
    public static void postInit()
    {
        MultiblockTemplateCelestialCompass.INSTANCE.init();
    }

    /*public static Block celestialCompass;
    public static Block celestialCompassSB;
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
        celestialCompass = new BlockCelestialCompass(Material.iron, Names.BLOCK.CELESTIAL_COMPASS);
        celestialCompassSB = new BlockCelestialCompassSB(Material.iron, Names.BLOCK.CELESTIAL_COMPASS_SB);
        temporalDisplacementAltar = new BlockTemporalDisplacementAltar(Material.iron, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR);
        temporalDisplacementAltarSB = new BlockTemporalDisplacementAltarSB(Material.iron, Names.BLOCK.TEMPORAL_DISPLACEMENT_ALTAR_SB);
        tda = new BlockTDA(Material.iron, Names.BLOCK.TDA);
        tdaSB = new BlockTDASB(Material.iron, Names.BLOCK.TDA_SB);
        timezoneFluidExporter = new BlockTimezoneFluidExporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_EXPORTER);
        timezoneFluidImporter = new BlockTimezoneFluidImporter(Material.iron, Names.BLOCK.TIMEZONE_FLUID_IMPORTER);
        timeCollector = new BlockTimeCollector(Material.iron, Names.BLOCK.TIME_COLLECTOR);
        temporalFurnace = new BlockTemporalFurnace(Material.iron, Names.BLOCK.TEMPORAL_FURNACE);
        timeWell = new BlockTimeWell(Material.iron, Names.BLOCK.TIME_WELL);

        GameRegistry.registerBlock(celestialCompass, Names.BLOCK.CELESTIAL_COMPASS);
        GameRegistry.registerBlock(celestialCompassSB, Names.BLOCK.CELESTIAL_COMPASS_SB);
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
        GameRegistry.registerTileEntity(TileMultiblockAssembler.class, multiblockAssembler.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileCelestialCompass.class, celestialCompass.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileAssemblyTable.class, assemblyTable.getUnlocalizedName());

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

    public static void initModels() {
        if(blocksForModel != null)
        {
            for(BlockReference block : blocksForModel)
                if(block != null && block.getBlock() != null)
                    ClockworkPhase2.proxy.registerBlockModel(block.getBlock(), block.getUnlocalizedName(), block.getIsCustomModel());
            blocksForModel.clear();
        }
        blocksForModel = null;
    }

    /**
     * Used to construct a new class of the specified type, set the block in the BlockReference, register the block with
     * Forge, create/register an itemblock for said block, and prepare to register the model.
     * @param blockReference A container class, referencing the block we're registering.
     * @param blockClass The class to instantiate the block to.
     * @param targetConstructor An array of classes the target constructor for blockClass expects.
     * @param parameters The parameters mentioned in targetConstructor.
     */
    public static void registerBlock(BlockReference blockReference, Class<? extends Block> blockClass, Class[] targetConstructor, Object[] parameters)
    {
        try {
            Constructor constructor = blockClass.getDeclaredConstructor(targetConstructor);
            Block blc = (Block) constructor.newInstance(parameters);
            blockReference.setBlock(blc);
            GameRegistry.register(blc);
            GameRegistry.register(new ItemBlock(blc).setRegistryName(blc.getRegistryName()));
            blocksForModel.add(blockReference);
        } catch (NoSuchMethodException e) {
            Logger.error("Tried to instantiate class for block '" + blockReference.getUnlocalizedName() + "' with invalid parameters.");
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
     * Used to construct a new class of the specified type, set the block in the BlockReference, register the block with
     * Forge, create/register an itemblock for said block, and prepare to register the model.
     * @param blockReference A container class, referencing the block we're registering.
     * @param blockClass The class to instantiate the block to.
     * @param targetConstructor An array of classes the target constructor for blockClass expects.
     * @param parameters The parameters mentioned in targetConstructor.
     */
    public static void registerBlock(BlockReference blockReference, Class<? extends Block> blockClass, Class[] targetConstructor, Object[] parameters, boolean cancelModelRegistry)
    {
        try {
            Constructor constructor = blockClass.getDeclaredConstructor(targetConstructor);
            Block blc = (Block) constructor.newInstance(parameters);
            blockReference.setBlock(blc);
            GameRegistry.register(blc);
            GameRegistry.register(new ItemBlock(blc).setRegistryName(blc.getRegistryName()));
            if(!cancelModelRegistry)
                blocksForModel.add(blockReference);
        } catch (NoSuchMethodException e) {
            Logger.error("Tried to instantiate class for block '" + blockReference.getUnlocalizedName() + "' with invalid parameters.");
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
            GameRegistry.register(blc);
            GameRegistry.register(new ItemBlock(blc).setRegistryName(blc.getRegistryName()));
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
            GameRegistry.register(blc);
            ItemBlock ib = itemBlockClass.getDeclaredConstructor(Block.class).newInstance(blc);
            ib.setRegistryName(blc.getRegistryName());
            GameRegistry.register(ib);
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
            GameRegistry.register(blc);
            ItemBlock ib = itemBlockClass.getDeclaredConstructor(Block.class).newInstance(blc);
            ib.setRegistryName(blc.getRegistryName());
            GameRegistry.register(ib);
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
        private boolean isCustomModel = false;

        public BlockReference(String unlocalizedName) {
            block = null;
            this.unlocalizedName = unlocalizedName;
        }

        public BlockReference(String unlocalizedName, boolean isCustomModel) {
            this(unlocalizedName);
            this.isCustomModel = isCustomModel;
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

        public boolean getIsCustomModel() {
            return isCustomModel;
        }
    }
}
