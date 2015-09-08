package lumaceon.mods.clockworkphase2.client;

import lumaceon.mods.clockworkphase2.util.SchematicUtility;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class FakeSchematicWorld implements IBlockAccess
{
    public SchematicUtility.Schematic schematic;

    public FakeSchematicWorld(SchematicUtility.Schematic schematic) {
        this.schematic = schematic;
    }

    @Override
    public Block getBlock(int x, int y, int z)
    {
        int index = (y * schematic.width * schematic.length) + (z * schematic.width) + x;
        return Blocks.stone; //Block.getBlockById(schematic.blocks[index]);
    }

    @Override
    public TileEntity getTileEntity(int x, int y, int z) {
        return null;
    }

    @Override
    public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
        return 15;
    }

    @Override
    public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_) {
        return 0;
    }

    @Override
    public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
        return 0;
    }

    @Override
    public boolean isAirBlock(int x, int y, int z) {
        int index = y * schematic.width * schematic.length + z * schematic.width + x;
        return false; //Block.getBlockById(schematic.blocks[index]).getMaterial() == Material.air;
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int x, int z) {
        return BiomeGenBase.plains;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public boolean extendedLevelsInChunkCache() {
        return false;
    }

    @Override
    public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
        return true;
    }
}
