package lumaceon.mods.clockworkphase2.clockworknetwork;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockCrank;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockCreativeMainspring;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.*;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.itemblock.*;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileCreativeMainspring;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.*;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class InitializerClockworkNetwork
{
    public static Block crank;
    public static Block mainspring;
    public static Block clockworkController;
    public static Block clockworkFurnace;
    public static Block clockworkMelter;
    public static Block clockworkSewingMachine;
    public static Block clockworkBrewery;
    public static Block clockworkMixer;
    public static Block clockworkSuperAlloyFurnace;
    public static Block clockworkAlloyFurnace;
    public static Block clockworkCraftingTable;
    public static void initBlocks()
    {
        crank = new BlockCrank(Material.iron, Names.BLOCK.CRANK);
        mainspring = new BlockCreativeMainspring(Material.iron, Names.BLOCK.CREATIVE_MAINSPRING);
        clockworkController = new BlockClockworkController(Material.iron, Names.BLOCK.CLOCKWORK_CONTROLLER);
        clockworkFurnace = new BlockClockworkFurnace(Material.iron, Names.BLOCK.CLOCKWORK_FURNACE);
        clockworkMelter = new BlockClockworkMelter(Material.iron, Names.BLOCK.CLOCKWORK_MELTER);
        clockworkBrewery = new BlockClockworkBrewery(Material.iron, Names.BLOCK.CLOCKWORK_BREWERY);
        clockworkMixer = new BlockClockworkMixer(Material.iron, Names.BLOCK.CLOCKWORK_MIXER);
        clockworkSuperAlloyFurnace = new BlockClockworkSuperAlloyFurnace(Material.iron, Names.BLOCK.CLOCKWORK_SUPER_ALLOY_FURNACE);
        clockworkAlloyFurnace = new BlockClockworkAlloyFurnace(Material.iron, Names.BLOCK.CLOCKWORK_ALLOY_FURNACE);
        clockworkCraftingTable = new BlockClockworkCraftingTable(Material.iron, Names.BLOCK.CLOCKWORK_CRAFTING_TABLE);
        //clockworkSewingMachine = new BlockClockworkSewingMachine(Material.iron, Names.BLOCK.CLOCKWORK_SEWING_MACHINE);

        GameRegistry.registerBlock(crank, Names.BLOCK.CRANK);
        GameRegistry.registerBlock(mainspring, Names.BLOCK.CREATIVE_MAINSPRING);
        GameRegistry.registerBlock(clockworkFurnace, ItemBlockClockworkFurnace.class, Names.BLOCK.CLOCKWORK_FURNACE);
        GameRegistry.registerBlock(clockworkController, Names.BLOCK.CLOCKWORK_CONTROLLER);
        GameRegistry.registerBlock(clockworkMelter, ItemBlockClockworkMelter.class, Names.BLOCK.CLOCKWORK_MELTER);
        GameRegistry.registerBlock(clockworkBrewery, ItemBlockClockworkBrewery.class, Names.BLOCK.CLOCKWORK_BREWERY);
        GameRegistry.registerBlock(clockworkMixer, ItemBlockClockworkMixer.class, Names.BLOCK.CLOCKWORK_MIXER);
        GameRegistry.registerBlock(clockworkSuperAlloyFurnace, ItemBlockClockworkSuperAlloyFurnace.class, Names.BLOCK.CLOCKWORK_SUPER_ALLOY_FURNACE);
        GameRegistry.registerBlock(clockworkAlloyFurnace, ItemBlockClockworkAlloyFurnace.class, Names.BLOCK.CLOCKWORK_ALLOY_FURNACE);
        GameRegistry.registerBlock(clockworkCraftingTable, Names.BLOCK.CLOCKWORK_CRAFTING_TABLE);
        //GameRegistry.registerBlock(clockworkSewingMachine, ItemBlockClockworkSewingMachine.class, Names.BLOCK.CLOCKWORK_SEWING_MACHINE);
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileCreativeMainspring.class, Names.BLOCK.CREATIVE_MAINSPRING);
        GameRegistry.registerTileEntity(TileClockworkController.class, Names.BLOCK.CLOCKWORK_CONTROLLER);
        GameRegistry.registerTileEntity(TileClockworkFurnace.class, Names.BLOCK.CLOCKWORK_FURNACE);
        GameRegistry.registerTileEntity(TileClockworkBrewery.class, Names.BLOCK.CLOCKWORK_BREWERY);
        GameRegistry.registerTileEntity(TileClockworkMixer.class, Names.BLOCK.CLOCKWORK_MIXER);
        GameRegistry.registerTileEntity(TileClockworkMelter.class, Names.BLOCK.CLOCKWORK_MELTER);
        GameRegistry.registerTileEntity(TileClockworkSuperAlloyFurnace.class, Names.BLOCK.CLOCKWORK_SUPER_ALLOY_FURNACE);
        GameRegistry.registerTileEntity(TileClockworkAlloyFurnace.class, Names.BLOCK.CLOCKWORK_ALLOY_FURNACE);
        GameRegistry.registerTileEntity(TileClockworkCraftingTable.class, Names.BLOCK.CLOCKWORK_CRAFTING_TABLE);
    }
}
