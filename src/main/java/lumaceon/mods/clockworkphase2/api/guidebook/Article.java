package lumaceon.mods.clockworkphase2.api.guidebook;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

/**
 * Represents an article for a category in the guidebook.
 */
public class Article
{
    public NBTTagList pages;
    public String displayName;
    public String fileName;
    public ResourceLocation texture;
    public Item textureItem;

    public Article(NBTTagCompound nbt, String displayName, String fileName, ResourceLocation texture) {
        if(nbt != null)
            pages = nbt.getTagList("pages", 8);
        else
            pages = new NBTTagList();
        this.displayName = displayName;
        this.fileName = fileName;
        this.texture = texture;
    }

    public Article(NBTTagCompound nbt, String displayName, String fileName, Item textureItem) {
        if(nbt != null)
            pages = nbt.getTagList("pages", 8);
        else
            pages = new NBTTagList();
        this.displayName = displayName;
        this.fileName = fileName;
        this.textureItem = textureItem;
    }

    public NBTTagCompound compilePagesIntoTag() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        nbtTag.setTag("pages", pages);
        return nbtTag;
    }
}
