package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.RelicExcavationRegistry;
import lumaceon.mods.clockworkphase2.block.*;
import lumaceon.mods.clockworkphase2.block.machine.BlockBasicWindingBox;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.block.temporal.*;
import lumaceon.mods.clockworkphase2.tile.*;
import lumaceon.mods.clockworkphase2.util.ISimpleNamed;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class ModBlocks
{
    public static ArrayList<Block> blocksForModel = new ArrayList<Block>(200);

    //ORES
    public static Block oreCopper;
    public static Block oreZinc;
    public static Block relicMoonFlower;
    public static Block relicUnknown;
    //METAL BLOCKS
    public static Block blockCopper;
    public static Block blockZinc;
    public static Block blockBrass;
    public static Block blockTemporal;
    //PLANTS
    public static Block moonFlower;
    //FLUIDS
        //public static BlockReference liquidTemporium = new BlockReference("liquid_temporium");
    //TEMPORAL BLOCKS
    public static Block celestialCompass;
    public static Block celestialCompassSB;
    //MISC
    public static Block basicWindingBox;
    public static Block assemblyTable;
    public static Block multiblockAssembler;
    public static Block constructionBlock;
    public static Block temporalZoningMachine;
    public static void init()
    {
        //ORES
        oreCopper = new BlockClockworkPhaseOre(Material.ROCK, 1, "copper_ore");
        register(oreCopper);
        OreDictionary.registerOre("oreCopper", oreCopper);

        oreZinc = new BlockClockworkPhaseOre(Material.ROCK, 1, "zinc_ore");
        register(oreZinc);
        OreDictionary.registerOre("oreZinc", oreZinc);

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

        blockTemporal = new BlockClockworkPhase(Material.IRON, "temporal_block");
        register(blockTemporal);
        OreDictionary.registerOre("blockTemporal", blockTemporal);

        //PLANTS
        moonFlower = new BlockMoonFlower(Material.IRON, "moon_flower");
        register(moonFlower);

        //TEMPORAL BLOCKS
            //registerBlock(celestialCompass, BlockCelestialCompass.class, matName, new Object[] {Material.IRON, celestialCompass.getUnlocalizedName()});
            //registerBlock(celestialCompassSB, BlockCelestialCompassSB.class, matName, new Object[] {Material.IRON, celestialCompassSB.getUnlocalizedName()});
        //MISC


        basicWindingBox = new BlockBasicWindingBox(Material.IRON, "basic_winding_box");
        register(basicWindingBox);

        assemblyTable = new BlockAssemblyTable(Material.WOOD, "assembly_table");
        register(assemblyTable);

        multiblockAssembler = new BlockMultiblockAssembler(Material.IRON, "multiblock_assembler");
        register(multiblockAssembler);

        constructionBlock = new BlockConstruction(Material.IRON, "construction_block");
        register(constructionBlock);

        //I guess you could call this a....TIMESHARE investment. BAHAHAHAHA!
        temporalZoningMachine = new BlockTemporalZoningMachine(Material.IRON, "temporal_zoning_machine");
        register(temporalZoningMachine);

        postInit();
    }

    /**
     * To initialize things that require blocks to already be registered.
     */
    public static void postInit()
    {
        MultiblockTemplateCelestialCompass.INSTANCE.init();
    }

    public static void initTE()
    {
        GameRegistry.registerTileEntity(TileMultiblockAssembler.class, multiblockAssembler.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileAssemblyTable.class, assemblyTable.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTemporalZoningMachine.class, temporalZoningMachine.getUnlocalizedName());
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
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
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
        GameRegistry.register(block);
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
