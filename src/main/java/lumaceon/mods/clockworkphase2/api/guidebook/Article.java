package lumaceon.mods.clockworkphase2.api.guidebook;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * Represents an article for a category in the guidebook.
 */
public class Article
{
    public static ContainerGuidebook guidebookContainer = new ContainerGuidebook();
    public ArrayList<ArticlePage> pages = new ArrayList<ArticlePage>();
    public String displayName;
    public String fileName;
    public ResourceLocation texture;
    public Item textureItem;

    public Article(NBTTagCompound nbt, String displayName, String fileName, ResourceLocation texture)
    {
        if(nbt != null)
        {
            NBTTagList pagesTag = nbt.getTagList("pages", 8);
            for(int n = 0; n < pagesTag.tagCount(); n++)
            {
                NBTBase base = pagesTag.get(n);
                if(base != null && base instanceof NBTTagString)
                    pages.add(new ArticlePage(((NBTTagString) base).getString(), guidebookContainer));
            }
        }
        this.displayName = displayName;
        this.fileName = fileName;
        this.texture = texture;
    }

    public Article(NBTTagCompound nbt, String displayName, String fileName, Item textureItem)
    {
        if(nbt != null)
        {
            NBTTagList pagesTag = nbt.getTagList("pages", 8);
            for(int n = 0; n < pagesTag.tagCount(); n++)
            {
                NBTBase base = pagesTag.get(n);
                if(base != null && base instanceof NBTTagString)
                    pages.add(new ArticlePage(((NBTTagString) base).getString(), guidebookContainer));
            }
        }
        this.displayName = displayName;
        this.fileName = fileName;
        this.textureItem = textureItem;
    }

    public NBTTagCompound compilePagesIntoTag()
    {
        NBTTagList pagesTag = new NBTTagList();
        if(pages != null && !pages.isEmpty())
            for(ArticlePage page : pages)
                if(page != null)
                    pagesTag.appendTag(new NBTTagString(page.saveToString()));
        NBTTagCompound nbtTag = new NBTTagCompound();
        nbtTag.setTag("pages", pagesTag);
        return nbtTag;
    }
}
