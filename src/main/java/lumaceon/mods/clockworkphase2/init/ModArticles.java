package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.guidebook.Categories;
import lumaceon.mods.clockworkphase2.api.guidebook.GuidebookFileHelper;
import lumaceon.mods.clockworkphase2.api.guidebook.GuidebookImageRegistry;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.util.ResourceLocation;

import java.io.File;

public class ModArticles
{
    public static void init(File modDirectory)
    {
        initImages();

        Categories.JOURNAL_ENTRIES.addArticle(GuidebookFileHelper.getArticleFromFile(modDirectory, Reference.MOD_ID, "jintro", Textures.GUI.CLOCK, null, "Entry #1: introduction."));
        Categories.JOURNAL_ENTRIES.addArticle(GuidebookFileHelper.getArticleFromFile(modDirectory, Reference.MOD_ID, "jcwtools", null, ModItems.clockworkPickaxe.getItem(), "Entry #2: clockwork mining?"));
        Categories.JOURNAL_ENTRIES.addArticle(GuidebookFileHelper.getArticleFromFile(modDirectory, Reference.MOD_ID, "jcwmachines", null, ModItems.clockworkCore.getItem(), "Entry #3: machinery."));
    }

    //private static final ResourceLocation EX = new ResourceLocation(Reference.MOD_ID, "textures/misc/example.png");
    private static void initImages()
    {
        //GuidebookImageRegistry.registerImage("example", EX, 125, 84);
    }
}
