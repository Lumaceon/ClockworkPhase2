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
        InputStream is = null;
        NBTTagCompound nbt = null;
        try
        {
            is = ClockworkPhase2.instance.getClass().getClassLoader().getResourceAsStream("assets/" + modID + "/articles/" + fileName + ".cparticle");
            nbt = CompressedStreamTools.readCompressed(is);
        }
        catch(FileNotFoundException e) { System.out.println("[Clockwork Phase 2] Article file not found: " + fileName); }
        catch(IOException e) { e.printStackTrace(); }
        catch(NullPointerException e) { System.out.println("[Clockwork Phase 2] Null pointer while loading article: " + fileName); }

        try {
            if(is != null)
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
