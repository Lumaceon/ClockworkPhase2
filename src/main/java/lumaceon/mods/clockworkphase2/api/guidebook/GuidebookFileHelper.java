package lumaceon.mods.clockworkphase2.api.guidebook;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.io.*;

public class GuidebookFileHelper
{
    public static Article getArticleFromFile(File modDirectory, String modID, String fileName, ResourceLocation texture, Item textureItem, String displayName)
    {
        //File file;
        //file = new File(modDirectory, "assets/" + modID + "/articles/" + fileName + ".cparticle");
        InputStream is = null;
        NBTTagCompound nbt = null;
        try
        {
            is = ClockworkPhase2.instance.getClass().getClassLoader().getResourceAsStream("assets/" + modID + "/articles/" + fileName + ".cparticle");
            nbt = CompressedStreamTools.readCompressed(is);
        }
        catch(FileNotFoundException e) { e.printStackTrace(); }
        catch(IOException e) { e.printStackTrace(); }

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(nbt == null)
        {
            System.out.println("[WARNING] Article \'" + displayName + "\' loaded with a null NBT tag.");
            if(texture == null)
                return new Article(null, displayName, fileName, textureItem);
            return new Article(null, displayName, fileName, texture);
        }
        if(texture == null)
            return new Article(nbt, displayName, fileName, textureItem);
        else
            return new Article(nbt, displayName, fileName, texture);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void saveArticleToFile(Article article)
    {
        try
        {

            File file = null;
            if(Minecraft.getMinecraft().mcDataDir != null)
                file = new File(Minecraft.getMinecraft().mcDataDir.getName(), "/articles");

            if(file != null)
            {
                file.mkdir();
                file = new File(file, article.fileName + ".cparticle");
                FileOutputStream output = new FileOutputStream(file);
                CompressedStreamTools.writeCompressed(article.compilePagesIntoTag(), output);
                output.close();
            }
            System.out.println("Attempted to save article '" + article.fileName + "', to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
