package lumaceon.mods.clockworkphase2.world.schematic;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;

public abstract class SchematicChunk
{
    private final NBTTagList tileDataList;
    public short width, height, length;
    public byte[] data;
    private Map<ChunkPos, NBTTagCompound> tiles;
    public int areaBlockCount; //Does not actually count blocks, just the size of the area in blocks.

    public SchematicChunk(NBTTagList tileEntities, short width, short height, short length, byte[] data)
    {
        this.tileDataList = tileEntities;
        this.width = width;
        this.height = height;
        this.length = length;
        this.data = data;
        areaBlockCount = width * height * length;
        tiles = Maps.newHashMap();
        reloadTileMap();
    }

    /**
     * Reloads the tile data in the schematic.
     */
    private void reloadTileMap()
    {
        tiles.clear();
        for (int i = 0; i < tileDataList.tagCount(); i++) {
            NBTTagCompound tag = tileDataList.getCompoundTagAt(i).copy();
            ChunkPos pos = new ChunkPos(tag.getInteger("x"), tag.getInteger("y"));
            tiles.put(pos, tag);
        }
    }

    /**
     * Gets the block at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
     * 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param x Coordinate x within the local schematic coordinates.
     * @param y Coordinate y within the local schematic coordinates.
     * @param z Coordinate z within the local schematic coordinates.
     * @return The blocks located at the specified local schematic coordinates, or null if out of bounds.
     */
    public abstract Block getBlock(int x, int y, int z);

    /**
     * Gets the tile entity at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
     * 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param x Coordinate x within the local schematic coordinates.
     * @param y Coordinate y within the local schematic coordinates.
     * @param z Coordinate z within the local schematic coordinates.
     * @return The tile entity located at the specified local schematic coordinates, or null if not found.

    @Deprecated  Replaced by the faster getTileData below
    public TileEntity getTileEntity(int x, int y, int z) {
        for (int i = 0; i < tileDataList.tagCount(); i++) {
            NBTTagCompound tagCompound = tileDataList.getCompoundTagAt(i);
            if (tagCompound.getInteger("x") == x && tagCompound.getInteger("y") == y && tagCompound.getInteger("z") == z) {
                return TileEntity.createAndLoadEntity(tileDataList.getCompoundTagAt(i));
            }
        }
        return null;
    }*/

    /**
     * Gets the data for the tile entity at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
     * 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param x Coordinate x within the local schematic coordinates.
     * @param y Coordinate y within the local schematic coordinates.
     * @param z Coordinate z within the local schematic coordinates.
     * @return The data from the tile entity located at the specified local schematic coordinates, or null if not found.
     */
    public NBTTagCompound getTileData(int x, int y, int z, int worldX, int worldY, int worldZ)
    {
        NBTTagCompound tag = getTileData(x, y, z);
        if(tag != null)
        {
            tag.setInteger("x", worldX);
            tag.setInteger("y", worldY);
            tag.setInteger("z", worldZ);
            return tag;
        }
        return null;
    }

    /**
     * Gets the data for the tile entity at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
     * 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param x Coordinate x within the local schematic coordinates.
     * @param y Coordinate y within the local schematic coordinates.
     * @param z Coordinate z within the local schematic coordinates.
     * @return The data from the tile entity located at the specified local schematic coordinates, or null if not found.
     */
    public NBTTagCompound getTileData(int x, int y, int z)
    {
        NBTTagCompound tag = tiles.get(new ChunkPos(x, y));
        if (tag != null)
            return tag.copy();
        return null;
    }

    /**
     * Gets the metadata for the block at the specified coordinates, ranging from 0 to the width (x), height (y) and
     * length (z). 0 is inclusive, the width, height and length values are exclusive.
     *
     * @param x Coordinate x within the local schematic coordinates.
     * @param y Coordinate y within the local schematic coordinates.
     * @param z Coordinate z within the local schematic coordinates.
     * @return The metadata for the block at the specified local schematic coordinates.
     */
    public byte getMetadata(int x, int y, int z)
    {
        if(x < width && y < height && z < length)
            return data[getIndexFromCoordinates(x, y, z)];
        return 0;
    }

    /**
     * Gets this schematic's area based on a centered coordinate on the horizon.
     *
     * @param x The x coordinate in world-space this is to be centered on.
     * @param y The horizon coordinate in world-space this schematic's horizon is to be matched with.
     * @param z The z coordinate in world-space this is to be centered on.
     * @return The area in world space that this schematic will fill.
     */
    public Area getAreaFromWorldCoordinates(int x, int y, int z) {
        int minX, maxX, minY, maxY, minZ, maxZ;
        if (width % 2 == 0) {
            minX = x + 1 - width / 2;
            maxX = x + width / 2;
        } else {
            minX = x - (width - 1) / 2;
            maxX = x + (width - 1) / 2;
        }

        minY = y;
        maxY = y + height;

        if (length % 2 == 0) {
            minZ = z + 1 - length / 2;
            maxZ = z + length / 2;
        } else {
            minZ = z - (length - 1) / 2;
            maxZ = z + (length - 1) / 2;
        }

        return new Area(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Used mostly for worldgen. Gets an offset between world and schematic coordinates. That is, what number must
     * be added to a world coordinate to get a schematic coordinate. The world coordinates should represent the
     * CENTER of the schematic for X/Z and the HORIZON for the Y.
     *
     * @param worldX The center of this schematic on the X axis.
     * @param worldY The horizon of this schematic on the Y axis (where the ground should be).
     * @param worldZ The center of this schematic on the Z axis.
     * @return An integer array containing the translation to be added to world coordinates {x, y, z}.

    public int[] getSchematicToWorldOffset(int worldX, int worldY, int worldZ) {
        int[] result = new int[3];

        if (width % 2 == 0) {
            result[0] = (-worldX + width / 2) + 1;
        } else {
            result[0] = -worldX + width / 2;
        }

        result[1] = -worldY;

        if (length % 2 == 0) {
            result[2] = (-worldZ + length / 2) + 1;
        } else {
            result[2] = -worldZ + length / 2;
        }

        return result;
    } */

    /**
     * Get the index for the block based on the local schematic coordinates.
     */
    protected int getIndexFromCoordinates(int x, int y, int z) {
        return z * width * height + x * height + y;
    }

    public static class StandardSchematicChunk extends SchematicChunk {
        public byte[] blocks;

        public StandardSchematicChunk(NBTTagList tileEntities, short width, short height, short length, byte[] blocks, byte[] data) {
            super(tileEntities, width, height, length, data);
            this.blocks = blocks;
        }

        /**
         * Gets the block at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
         * 0 is inclusive, while the width, height and length are exclusive.
         *
         * @param x Coordinate x within the local schematic coordinates.
         * @param y Coordinate y within the local schematic coordinates.
         * @param z Coordinate z within the local schematic coordinates.
         * @return The blocks located at the specified local schematic coordinates, or null if out of bounds.
         */
        @Override
        public Block getBlock(int x, int y, int z) {
            if (x < width && y < height && z < length) {
                return Block.getBlockById(blocks[getIndexFromCoordinates(x, y, z)]);
            }
            return null;
        }
    }

    public static class ModSchematicChunk extends SchematicChunk
    {
        public int[] blocks;

        public ModSchematicChunk(NBTTagList tileEntities, short width, short height, short length, int[] blocks, byte[] data) {
            super(tileEntities, width, height, length, data);
            this.blocks = blocks;
        }

        /**
         * Gets the block at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
         * 0 is inclusive, while the width, height and length are exclusive.
         *
         * @param x Coordinate x within the local schematic coordinates.
         * @param y Coordinate y within the local schematic coordinates.
         * @param z Coordinate z within the local schematic coordinates.
         * @return The blocks located at the specified local schematic coordinates, or null if out of bounds.
         */
        @Override
        public Block getBlock(int x, int y, int z) {
            if (x < width && y < height && z < length) {
                return Block.getBlockById(blocks[getIndexFromCoordinates(x, y, z)]);
            }
            return null;
        }
    }

}