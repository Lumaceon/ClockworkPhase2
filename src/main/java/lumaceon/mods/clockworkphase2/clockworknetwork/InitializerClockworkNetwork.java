package lumaceon.mods.clockworkphase2.clockworknetwork;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.BlockCrank;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.BlockClockworkBrewery;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.BlockClockworkFurnace;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.BlockClockworkMelter;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.BlockClockworkMixer;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.itemblock.ItemBlockClockworkBrewery;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.itemblock.ItemBlockClockworkFurnace;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.itemblock.ItemBlockClockworkMelter;
import lumaceon.mods.clockworkphase2.clockworknetwork.block.child.itemblock.ItemBlockClockworkMixer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkController;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkBrewery;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkFurnace;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkMelter;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkMixer;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class InitializerClockworkNetwork
{
    public static Block crank;
    public static Block clockworkController;
    public static Block clockworkFurnace;
    public static Block clockworkMelter;
    public static Block clockworkSewingMachine;
    public static Block clockworkBrewery;
    public static Block clockworkMixer;
    public static void initBlocks()
    {
        crank = new BlockCrank(Material.iron, Names.BLOCK.CRANK);
        clockworkController = new BlockClockworkController(Material.iron, Names.BLOCK.CLOCKWORK_CONTROLLER);
        clockworkFurnace = new BlockClockworkFurnace(Material.iron, Names.BLOCK.CLOCKWORK_FURNACE);
        clockworkMelter = new BlockClockworkMelter(Material.iron, Names.BLOCK.CLOCKWORK_MELTER);
        clockworkBrewery = new BlockClockworkBrewery(Material.iron, Names.BLOCK.CLOCKWORK_BREWERY);
        clockworkMixer = new BlockClockworkMixer(Material.iron, Names.BLOCK.CLOCKWORK_MIXER);
        //clockworkSewingMachine = new BlockClockworkSewingMachine(Material.iron, Names.BLOCK.CLOCKWORK_SEWING_MACHINE);

        GameRegistry.registerBlock(crank, Names.BLOCK.CRANK);
        GameRegistry.registerBlock(clockworkFurnace, ItemBlockClockworkFurnace.class, Names.BLOCK.CLOCKWORK_FURNACE);
        GameRegistry.registerBlock(clockworkController, Names.BLOCK.CLOCKWORK_CONTROLLER);
        GameRegistry.registerBlock(clockworkMelter, ItemBlockClockworkMelter.class, Names.BLOCK.CLOCKWORK_MELTER);
        GameRegistry.registerBlock(clockworkBrewery, ItemBlockClockworkBrewery.class, Names.BLOCK.CLOCKWORK_BREWERY);
        GameRegistry.registerBlock(clockworkMixer, ItemBlockClockworkMixer.class, Names.BLOCK.CLOCKWORK_MIXER);
        //GameRegistry.registerBlock(clockworkSewingMachine, ItemBlockClockworkSewingMachine.class, Names.BLOCK.CLOCKWORK_SEWING_MACHINE);
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileClockworkController.class, Names.BLOCK.CLOCKWORK_CONTROLLER);
        GameRegistry.registerTileEntity(TileClockworkFurnace.class, Names.BLOCK.CLOCKWORK_FURNACE);
        GameRegistry.registerTileEntity(TileClockworkBrewery.class, Names.BLOCK.CLOCKWORK_BREWERY);
        GameRegistry.registerTileEntity(TileClockworkMixer.class, Names.BLOCK.CLOCKWORK_MIXER);
        GameRegistry.registerTileEntity(TileClockworkMelter.class, Names.BLOCK.CLOCKWORK_MELTER);
    }
}
