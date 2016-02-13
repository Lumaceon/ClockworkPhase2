package lumaceon.mods.clockworkphase2.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SchematicUtility
{
    public static final SchematicUtility INSTANCE = new SchematicUtility();

    public File modFileDirectory;
    public String modID;
    public File mcDataDirectory;

    public World clientWorld;

    /**
     * Used to set the location of your mod's file directory (most commonly the .jar file, though it varies).
     * Most mods will wish to pass in the value of FMLPreInitializationEvent.getSourceFile().
     * @param file A file representing your mod's file directory.
     * @param modID The ID of your mod.
     */
    public void setModResourceLocation(File file, String modID) {
        modFileDirectory = file;
        this.modID = modID;
    }

    /**
     * Used on the client to save and load from the modschematics folder in Minecraft's main directory.
     * @param mcDataDir Minecraft.mcDataDir or Minecraft's top data folder.
     */
    public void setMinecraftDirectory(File mcDataDir) {
        this.mcDataDirectory = mcDataDir;
    }

    private File getSchematicFile(String fileName, boolean defaultResource)
    {
        File file;
        if(defaultResource)
            file = new File(modFileDirectory, "assets\\" + this.modID + "\\schematics\\" + fileName + ".modschematic");
        else
        {
            if(FMLCommonHandler.instance().getEffectiveSide().isServer() && FMLServerHandler.instance().getServer() != null)
                file = FMLServerHandler.instance().getServer().getFile("\\modschematics\\" + fileName + ".modschematic");
            else if(this.mcDataDirectory != null)
                file = new File(this.mcDataDirectory.getName(), "\\modschematics\\" + fileName + ".modschematic");
            else
            {
                Logger.error("Attempted to load schematic file\'" + fileName + ".modschematic\' without specifying the Minecraft file directory.");
                return null;
            }
        }
        return file;
    }

    /**
     * Loads a .modschematic file and returns a ModSchematic class. Does not load .schematic files.
     * @param fileName The name of the file to load (file extension should not be included).
     * @param defaultResource If true, checks the schematics folder in your mods resources.
     *                        If false, checks modschematics folder (which is a sub of the main MC Directory).
     * @return The loaded ModSchematic.
     */
    public ModSchematic loadModSchematic(String fileName, boolean defaultResource)
    {
        if(modFileDirectory == null)
        {
            Logger.error("Attempted to load schematic file\'" + fileName + ".modschematic\' server-side without specifying your mod\'s file directory.");
            return null;
        }
        try
        {
            InputStream is = new FileInputStream(getSchematicFile(fileName, defaultResource));
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(is);
            is.close();
            NBTTagList tileEntities = nbt.getTagList("TileEntities", 10);
            short width = nbt.getShort("Width");
            short height = nbt.getShort("Height");
            short length = nbt.getShort("Length");
            short horizon = nbt.getShort("Horizon");
            int[] blocks = nbt.getIntArray("Blocks");
            byte[] metadata = nbt.getByteArray("Data");

            //----------\\____ID Mapping____//----------\\
            NBTTagList idMapping = nbt.getTagList("IDMap", 10);
            ConcurrentHashMap<Integer, Integer> idMatrix = new ConcurrentHashMap<Integer, Integer>(idMapping.tagCount());
            ArrayList<String> missingBlocks = new ArrayList<String>();

            NBTTagCompound tempTag;
            int id;
            String modId;
            String name;
            Block targetBlock;
            for(int n = 0; n < idMapping.tagCount(); n++)
            {
                tempTag = idMapping.getCompoundTagAt(n);
                id = tempTag.getInteger("ID");
                modId = tempTag.getString("UI_Mod");
                name = tempTag.getString("UI_Name");

                targetBlock = GameRegistry.findBlock(modId, name);
                //Logger.info(modId + ":" + name);
                if(targetBlock == null) //Block no longer exists.
                {
                    missingBlocks.add(modId + ":" + name);
                    idMatrix.put(id, Block.getIdFromBlock(Blocks.air)); //Change to air.
                }
                else if(!targetBlock.equals(Block.getBlockById(id))) //Id mapping for this block has changed.
                    idMatrix.put(id, Block.getIdFromBlock(targetBlock));
                //else: Block still exists and is the same block it was when this schematic was created; do nothing.
            }

            this.convertBlocksWithIdMatrix(blocks, idMatrix);
            //----------\\____ID Mapping____//----------\\

            Logger.info("/-ModSchematic \"" + fileName + "\" loaded-\\");
            Logger.info("Width: " + width + ", Height: " + height + ", Length: " + length + ", Tile Entity Count: " + tileEntities.tagCount());

            Set<Integer> set = idMatrix.keySet();
            //for(int i : set)
            //    Logger.info("Old: " + i + ", New: " + idMatrix.get(i));
            return new ModSchematic(tileEntities, width, height, length, horizon, blocks, metadata, missingBlocks.toArray(new String[missingBlocks.size()]));
        }
        catch(Exception ex)
        {
            Logger.fatal("Error loading modschematic file: \"" + fileName + "\"");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Loads a .schematic file and returns a ModSchematic class. Does not load .modschematic files.
     * @param fileName The name of the file to load (file extension should not be included).
     * @param defaultResource If true, checks the schematics folder under your mods resources.
     *                        If false, checks modschematics folder (which is a sub of the main MC Directory).
     * @return The loaded Schematic.
     */
    public StandardSchematic loadSchematic(String fileName, boolean defaultResource)
    {
        try
        {
            InputStream is = new FileInputStream(getSchematicFile(fileName, defaultResource));
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(is);
            is.close();
            NBTTagList tileEntities = nbt.getTagList("TileEntities", 10);
            short width = nbt.getShort("Width");
            short height = nbt.getShort("Height");
            short length = nbt.getShort("Length");
            byte[] blocks = nbt.getByteArray("Blocks");
            byte[] metadata = nbt.getByteArray("Data");

            Logger.info("/-Schematic \"" + fileName + "\" loaded-\\");
            Logger.info("Width: " + width + ", Height: " + height + ", Length: " + length + "Tile Entity Count: " + tileEntities.tagCount());
            return new StandardSchematic(tileEntities, width, height, length, (short) 0, blocks, metadata);
        }
        catch(Exception ex)
        {
            Logger.fatal("Error loading schematic file: \"" + fileName + "\"");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a .modschematic file in the Mod Schematics folder (a sub-directory of the Minecraft directory).
     * @param area The area in theWorld which will be saved as a .modschematic file.
     * @param fileName The name of the file to create, which is automatically given the .modschematic file extension.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createModSchematic(World world, Area area, short horizon, String fileName)
    {
        if(world == null)
        {
            Logger.error("Attempted to create a schematics with a null world.");
            return;
        }
        TileEntity te;
        Block blc;
        try
        {
            File schematicFolderLocation;
            if(FMLServerHandler.instance().getServer() != null)
                schematicFolderLocation = FMLServerHandler.instance().getServer().getFile("\\modschematics");
            else if(this.mcDataDirectory != null)
                schematicFolderLocation = new File(this.mcDataDirectory.getName(), "\\modschematics");
            else
            {
                Logger.error("Attempted to create a schematic file without specifying the Minecraft file directory.");
                return;
            }
            schematicFolderLocation.mkdir();
            schematicFolderLocation = new File(schematicFolderLocation, fileName + ".modschematic");
            FileOutputStream output = new FileOutputStream(schematicFolderLocation);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setShort("Width", area.getWidth());
            nbt.setShort("Height", area.getHeight());
            nbt.setShort("Length", area.getLength());
            nbt.setShort("Horizon", horizon);
            int[] blocks = new int[area.getBlockCount()];
            byte[] data = new byte[area.getBlockCount()];
            NBTTagList tileList = new NBTTagList();

            NBTTagList idMapping = new NBTTagList();
            ConcurrentHashMap<Integer, GameRegistry.UniqueIdentifier> blockIDMap = new ConcurrentHashMap<Integer, GameRegistry.UniqueIdentifier>(200);

            int i = 0;
            for(int y = Math.min(area.y1, area.y2); y <= Math.max(area.y1, area.y2); y++)
                for(int z = Math.min(area.z1, area.z2); z <= Math.max(area.z1, area.z2); z++)
                    for(int x = Math.min(area.x1, area.x2); x <= Math.max(area.x1, area.x2); x++)
                    {
                        blc = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                        blocks[i] = Block.getIdFromBlock(blc);

                        //If we haven't encountered this block ID yet, add it's unique identifier to the ID map.
                        if(!blockIDMap.containsKey(blocks[i]))
                            blockIDMap.put(blocks[i], GameRegistry.findUniqueIdentifierFor(blc));

                        //data[i] = (byte) world.getBlockMetadata(x, y, z);
                        te = world.getTileEntity(new BlockPos(x, y, z));
                        if(te != null)
                        {
                            NBTTagCompound tileNBT = new NBTTagCompound();
                            te.writeToNBT(tileNBT);
                            tileList.appendTag(tileNBT);
                        }
                        ++i;
                    }

            //For each block ID we encountered, add it to the ID mapping.
            Set<Integer> idSet = blockIDMap.keySet();
            NBTTagCompound temp;
            for(int id : idSet)
            {
                temp = new NBTTagCompound();
                temp.setInteger("ID", id);
                temp.setString("UI_Mod", blockIDMap.get(id).modId);
                temp.setString("UI_Name", blockIDMap.get(id).name);
                idMapping.appendTag(temp);
            }

            nbt.setIntArray("Blocks", blocks);
            nbt.setByteArray("Data", data);
            nbt.setTag("TileEntities", tileList);
            nbt.setTag("IDMap", idMapping);
            CompressedStreamTools.writeCompressed(nbt, output);
            output.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Converts an array of saved block IDs to the new ID representing that block.
     * @param blocks Array of int IDs representing blocks as they were when saved.
     * @param matrix A Map class where the key is the saved ID and the value is the new ID for that block.
     */
    public void convertBlocksWithIdMatrix(int[] blocks, ConcurrentHashMap<Integer, Integer> matrix) {
        for(int n = 0; n < blocks.length; n++)
            if(matrix.containsKey(blocks[n]))
                blocks[n] = matrix.get(blocks[n]);
    }

    public static abstract class Schematic
    {
        public NBTTagList tileEntities;
        public short width, height, length, horizon; //Horizon sets the "ground" or how far down to translate this.
        public byte[] data;
        public int areaBlockCount; //Does not actually count blocks, just the size of the area in blocks.

        public Schematic(NBTTagList tileEntities, short width, short height, short length, short horizon, byte[] data)
        {
            this.tileEntities = tileEntities;
            this.width = width;
            this.height = height;
            this.length = length;
            this.horizon = horizon;
            this.data = data;
            areaBlockCount = width * height * length;
        }

        /**
         * Gets the block at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
         * 0 is inclusive, the width, height and length values are exclusive.
         * @param x Coordinate x within the local schematic coordinates.
         * @param y Coordinate y within the local schematic coordinates.
         * @param z Coordinate z within the local schematic coordinates.
         * @return The blocks located at the specified local schematic coordinates, or null if out of bounds.
         */
        public abstract Block getBlock(int x, int y, int z);

        /**
         * Gets the metadata for the block at the specified coordinates, ranging from 0 to the width (x), height (y) and
         * length (z). 0 is inclusive, the width, height and length values are exclusive.
         * @param x Coordinate x within the local schematic coordinates.
         * @param y Coordinate y within the local schematic coordinates.
         * @param z Coordinate z within the local schematic coordinates.
         * @return The metadata for the block at the specified local schematic coordinates.
         */
        public byte getMetadata(int x, int y, int z) {
            return data[getIndexFromCoordinates(x, y, z)];
        }

        /**
         * Gets this schematic's area based on a centered coordinate on the horizon.
         * @param x The x coordinate in world-space this is to be centered on.
         * @param y The horizon coordinate in world-space this schematic's horizon is to be matched with.
         * @param z The z coordinate in world-space this is to be centered on.
         * @return The area in world space that this schematic will fill.
         */
        public Area getAreaFromWorldCoordinates(int x, int y, int z)
        {
            int minX;
            int maxX;
            int minY;
            int maxY;
            int minZ;
            int maxZ;

            if(width % 2 == 0)
            {
                minX = x + 1 - width / 2;
                maxX = x + width / 2;
            }
            else
            {
                minX = x - (width - 1) / 2;
                maxX = x + (width - 1) / 2;
            }

            minY = y - horizon;
            maxY = (y - horizon) + height;

            if(length % 2 == 0)
            {
                minZ = z + 1 - length / 2;
                maxZ = z + length / 2;
            }
            else
            {
                minZ = z - (length - 1) / 2;
                maxZ = z + (length - 1) / 2;
            }

            return new Area(minX, minY, minZ, maxX, maxY, maxZ);
        }

        /**
         * Used mostly for worldgen. Gets an offset between world and schematic coordinates. That is, what number must
         * be added to a world coordinate to get a schematic coordinate. The world coordinates should represent the
         * CENTER of the schematic for X/Z and the HORIZON for the Y.
         * @param worldX The center of this schematic on the X axis.
         * @param worldY The horizon of this schematic on the Y axis (where the ground should be).
         * @param worldZ The center of this schematic on the Z axis.
         * @return An integer array containing the translation to be added to world coordinates {x, y, z}.
         */
        public int[] getSchematicToWorldOffset(int worldX, int worldY, int worldZ)
        {
            int[] result = new int[3];

            if(width % 2 == 0)
                result[0] = (-worldX + width / 2) + 1;
            else
                result[0] = -worldX + width / 2;

            result[1] = -worldY + horizon;

            if(length % 2 == 0)
                result[2] = (-worldZ + length / 2) + 1;
            else
                result[2] = -worldZ + length / 2;

            return result;
        }

        /**
         * Get the index for the block based on the local schematic coordinates.
         */
        protected int getIndexFromCoordinates(int x, int y, int z) {
            return y * width * length + z * width + x;
        }
    }

    public static class ModSchematic extends Schematic
    {
        public int[] blocks;
        public String[] missingBlocks; //An array of unlocalized names which are missing in this MC instance.
        public ModSchematic(NBTTagList tileEntities, short width, short height, short length, short horizon, int[] blocks, byte[] data, String[] missingBlocks) {
            super(tileEntities, width, height, length, horizon, data);
            this.blocks = blocks;
            this.missingBlocks = missingBlocks;
        }

        /**
         * Gets the block at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
         * 0 is inclusive, while the width, height and length are exclusive.
         * @param x Coordinate x within the local schematic coordinates.
         * @param y Coordinate y within the local schematic coordinates.
         * @param z Coordinate z within the local schematic coordinates.
         * @return The blocks located at the specified local schematic coordinates, or null if out of bounds.
         */
        @Override
        public Block getBlock(int x, int y, int z) {
            if(x < width && y < height && z < length)
                return Block.getBlockById(blocks[getIndexFromCoordinates(x, y, z)]);
            return null;
        }
    }

    public static class StandardSchematic extends Schematic
    {
        public byte[] blocks;
        public StandardSchematic(NBTTagList tileEntities, short width, short height, short length, short horizon, byte[] blocks, byte[] data) {
            super(tileEntities, width, height, length, horizon, data);
            this.blocks = blocks;
        }

        /**
         * Gets the block at the specified coordinates, ranging from 0 to the width (x), height (y) and length (z).
         * 0 is inclusive, while the width, height and length are exclusive.
         * @param x Coordinate x within the local schematic coordinates.
         * @param y Coordinate y within the local schematic coordinates.
         * @param z Coordinate z within the local schematic coordinates.
         * @return The blocks located at the specified local schematic coordinates, or null if out of bounds.
         */
        @Override
        public Block getBlock(int x, int y, int z) {
            if(x < width && y < height && z < length)
                return Block.getBlockById(blocks[getIndexFromCoordinates(x, y, z)]);
            return null;
        }
    }
}