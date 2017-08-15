package lumaceon.mods.clockworkphase2.world.schematic;

import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.FMLServerHandler;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SchematicHandler
{
    public static final SchematicHandler INSTANCE = new SchematicHandler();

    public File modFileDirectory;
    public String modID;
    public File mcDataDirectory;

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

    /**
     * Loads a .modschematic file and returns a ModSchematic class. Does not load .schematic files.
     *
     * @param fileName        The name of the file to load (file extension should not be included).
     * @param defaultResource If true, checks the schematics folder under cmo resources.
     *                        If false, checks modschematics folder (which is a sub of the main MC Directory).
     * @return The loaded ModSchematic.
     */
    public SchematicChunk.ModSchematicChunk loadModSchematic(String fileName, boolean defaultResource, int xChunkOffset, int zChunkOffset)
    {
        try {
            InputStream is;

            if(defaultResource)
                is = getFromResource(new ResourceLocation(Reference.MOD_ID, "schematics/" + fileName + "/" + fileName + xChunkOffset + "-" + zChunkOffset + ".modchunk"));
            else
                is = new FileInputStream(new File(mcDataDirectory, "\\modschematics\\" + fileName + "\\" + fileName + xChunkOffset + "-" + zChunkOffset + ".modchunk"));

            return loadModSchematic(is, fileName);
        } catch (Exception e) {
            System.err.println("Error loading modschematic file: \"" + fileName + "\"");
            return null;
        }
    }

    /**
     * Loads a .modchunk file and returns a ModSchematic class. Does not load .schematic files.
     *
     * @param is            The InputStream of the file to load.
     * @param schematicName Used for logging information about the structure, nothing else.
     * @return The loaded ModSchematic.
     */
    public SchematicChunk.ModSchematicChunk loadModSchematic(InputStream is, String schematicName)
    {
        NBTTagCompound nbt;
        try {
            nbt = CompressedStreamTools.readCompressed(is);
            is.close();
        } catch (Exception ex) {
            System.err.println("Error loading modchunk file: \"" + schematicName + "\"");
            return null;
        }

        if(nbt == null)
            return null;

        NBTTagList tileEntities = nbt.getTagList("TileEntities", 10);
        short width = nbt.getShort("Width");
        short height = nbt.getShort("Height");
        short length = nbt.getShort("Length");
        int[] blocks = nbt.getIntArray("Blocks");
        byte[] metadata = nbt.getByteArray("Data");

        //----------\\____ID Mapping____//----------\\
        NBTTagList idMapping = nbt.getTagList("IDMap", 10);
        ConcurrentHashMap<Integer, Integer> idMatrix = new ConcurrentHashMap<Integer, Integer>(idMapping.tagCount());

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

            targetBlock = Block.REGISTRY.getObject(new ResourceLocation(modId, name));
            //System.out.println(modId + ":" + name);
            if(!targetBlock.equals(Block.getBlockById(id))) //Id mapping for this block has changed.
                idMatrix.put(id, Block.getIdFromBlock(targetBlock));
            //else: Block still exists and is the same block it was when this schematic was created; do nothing.
        }

        this.convertBlocksWithIdMatrix(blocks, idMatrix);
        //----------\\____ID Mapping____//----------\\

        //System.out.println("/-ModSchematic \"" + schematicName + "\" loaded-\\");
        //System.out.println("Width: " + width + ", Height: " + height + ", Length: " + length + ", Tile Entity Count: " + tileEntities.tagCount());

        //Set<Integer> set = idMatrix.keySet();
        //for (int i : set)
        //    System.out.println("Old: " + i + ", New: " + idMatrix.get(i));
        return new SchematicChunk.ModSchematicChunk(tileEntities, width, height, length, blocks, metadata);
    }

    /**
     * Loads a .schematic file and returns a ModSchematic class. Does not load .modchunk files.
     *
     * @param fileName        The name of the file to load (file extension should not be included).
     * @param defaultResource If true, checks the schematics folder under cmo resources.
     *                        If false, checks modschematics folder (which is a sub of the main MC Directory).
     * @return The loaded Schematic.
     */
    public SchematicChunk.StandardSchematicChunk loadSchematic(String fileName, boolean defaultResource) {
        try {
            InputStream is;
            if (defaultResource)
                is = getFromResource(new ResourceLocation(Reference.MOD_ID, "schematics/" + fileName + ".schematic"));
            else
                is = new FileInputStream(new File(mcDataDirectory, "\\modschematics\\" + fileName + ".schematic"));
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(is);
            is.close();
            NBTTagList tileEntities = nbt.getTagList("TileEntities", 10);
            short width = nbt.getShort("Width");
            short height = nbt.getShort("Height");
            short length = nbt.getShort("Length");
            byte[] blocks = nbt.getByteArray("Blocks");
            byte[] metadata = nbt.getByteArray("Data");

            //System.out.println("/-Schematic \"" + fileName + "\" loaded-\\");
            //System.out.println("Width: " + width + ", Height: " + height + ", Length: " + length + "Tile Entity Count: " + tileEntities.tagCount());
            return new SchematicChunk.StandardSchematicChunk(tileEntities, width, height, length, blocks, metadata);
        } catch (Exception ex) {
            System.err.println("Error loading schematic file: \"" + fileName + "\"");
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a .modchunk folder in the Mod Schematics folder (a sub-directory of the Minecraft directory).
     *
     * @param area     The area in theWorld which will be saved as .modchunk files in the directory.
     * @param fileName The name of the folder to create.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createModSchematic(World world, Area area, String fileName) {
        if (world == null || area == null || fileName == null)
            return;

        TileEntity te;
        Block blc;
        try {
            File schematicFolderLocation;
            if(FMLServerHandler.instance().getServer() != null)
            {
                schematicFolderLocation = FMLServerHandler.instance().getServer().getFile("\\modschematics");
            }
            else
            {
                schematicFolderLocation = new File(".", "\\modschematics");
            }

            schematicFolderLocation.mkdir();
            schematicFolderLocation = new File(schematicFolderLocation, "\\" + fileName);
            schematicFolderLocation.mkdir();

            for(int chunkZ = 0; chunkZ * 16 < Math.max(area.z1, area.z2) - Math.min(area.z1, area.z2); chunkZ++)
            {
                for(int chunkX = 0; chunkX * 16 < Math.max(area.x1, area.x2) - Math.min(area.x1, area.x2); chunkX++)
                {
                    NBTTagCompound nbt = new NBTTagCompound();
                    nbt.setShort("Width", (short) 16);
                    nbt.setShort("Height", area.getHeight());
                    nbt.setShort("Length", (short) 16);
                    int[] blocks = new int[256 * area.getHeight()];
                    byte[] data = new byte[256 * area.getHeight()];
                    NBTTagList tileList = new NBTTagList();

                    NBTTagList idMapping = new NBTTagList();
                    ConcurrentHashMap<Integer, ResourceLocation> blockIDMap = new ConcurrentHashMap<Integer, ResourceLocation>(200);

                    int i = 0;
                    int schematicX = 0;
                    int schematicY = 0;
                    int schematicZ = 0;

                    int zIterations = 0;
                    for(int z = chunkZ * 16 + Math.min(area.z1, area.z2); z <= Math.max(area.z1, area.z2) && zIterations < 16; z++)
                    {
                        ++zIterations;
                        schematicX = 0;
                        int xIterations = 0;

                        for(int x = chunkX * 16 + Math.min(area.x1, area.x2); x <= Math.max(area.x1, area.x2) && xIterations < 16; x++)
                        {
                            ++xIterations;
                            schematicY = 0;

                            for(int y = Math.min(area.y1, area.y2); y <= Math.max(area.y1, area.y2); y++)
                            {
                                BlockPos p = new BlockPos(x, y, z);
                                blc = world.getBlockState(p).getBlock();
                                blocks[i] = Block.getIdFromBlock(blc);

                                //If we haven't encountered this block ID yet, add its unique identifier to the ID map.
                                if(!blockIDMap.containsKey(blocks[i])) {
                                    blockIDMap.put(blocks[i], blc.getRegistryName());
                                }

                                data[i] = (byte) blc.getMetaFromState(world.getBlockState(p));

                                te = world.getTileEntity(p);
                                if (te != null) {
                                    NBTTagCompound tileNBT = new NBTTagCompound();
                                    te.writeToNBT(tileNBT);
                                    tileNBT.setInteger("x", schematicX);
                                    tileNBT.setInteger("y", schematicY);
                                    tileNBT.setInteger("z", schematicZ);
                                    tileList.appendTag(tileNBT);
                                }

                                ++i;
                                ++schematicX;
                            }
                            ++schematicZ;
                        }
                        ++schematicY;
                    }


                    //For each block ID we encountered, add it to the ID mapping.
                    Set<Integer> idSet = blockIDMap.keySet();
                    NBTTagCompound temp;
                    for (int id : idSet) {
                        temp = new NBTTagCompound();
                        temp.setInteger("ID", id);
                        temp.setString("UI_Mod", blockIDMap.get(id).getResourceDomain());
                        temp.setString("UI_Name", blockIDMap.get(id).getResourcePath());
                        idMapping.appendTag(temp);
                    }

                    nbt.setIntArray("Blocks", blocks);
                    nbt.setByteArray("Data", data);
                    nbt.setTag("TileEntities", tileList);
                    nbt.setTag("IDMap", idMapping);
                    File newFile = new File(schematicFolderLocation, fileName + chunkX + "-" + chunkZ + ".modchunk");
                    FileOutputStream output = new FileOutputStream(newFile);
                    CompressedStreamTools.writeCompressed(nbt, output);
                    output.close();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Converts an array of saved block IDs to the new ID representing that block.
     *
     * @param blocks Array of int IDs representing blocks as they were when saved.
     * @param matrix A Map class where the key is the saved ID and the value is the new ID for that block.
     */
    public void convertBlocksWithIdMatrix(int[] blocks, ConcurrentHashMap<Integer, Integer> matrix) {
        for (int n = 0; n < blocks.length; n++) {
            if (matrix.containsKey(blocks[n])) {
                blocks[n] = matrix.get(blocks[n]);
            }
        }
    }

    @Nonnull
    private static InputStream getFromResource(@Nonnull ResourceLocation resourceLocation) throws IOException
    {
        String location = "/assets/" + resourceLocation.getResourceDomain() + "/" + resourceLocation.getResourcePath();
        InputStream ret = SchematicHandler.class.getResourceAsStream(location);
        if(ret != null)
            return ret;
        throw new FileNotFoundException(location);
    }
}
