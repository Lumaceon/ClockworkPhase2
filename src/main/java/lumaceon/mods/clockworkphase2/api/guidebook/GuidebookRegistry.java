package lumaceon.mods.clockworkphase2.api.guidebook;

import lumaceon.mods.clockworkphase2.api.guidebook.renderers.GuidebookCustomRender;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.concurrent.ConcurrentHashMap;

public class GuidebookRegistry
{
    public static final ConcurrentHashMap<String, GuidebookImage> IMAGE_MAPPINGS = new ConcurrentHashMap<String, GuidebookImage>();
    public static final ConcurrentHashMap<String, GuidebookRecipe> RECIPE_MAPPINGS = new ConcurrentHashMap<String, GuidebookRecipe>();
    public static final ConcurrentHashMap<String, GuidebookCustomRender> CUSTOM_MAPPINGS = new ConcurrentHashMap<String, GuidebookCustomRender>();

    public static void registerImage(String id, ResourceLocation texture, int x, int y) {
        IMAGE_MAPPINGS.put(id, new GuidebookImage(id, texture, x, y));
    }

    public static GuidebookImage getImageForString(String id) {
        return IMAGE_MAPPINGS.get(id);
    }

    public static void registerNewRecipeMapping(String key, GuidebookRecipe recipe) {
        RECIPE_MAPPINGS.put(key, recipe);
    }

    public static GuidebookRecipe getRecipeForString(String key) {
        return RECIPE_MAPPINGS.get(key);
    }

    public static void registerNewCustomRender(String key, GuidebookCustomRender customRender) {
        CUSTOM_MAPPINGS.put(key, customRender);
    }

    public static GuidebookCustomRender getCustomRenderForString(String key) {
        return CUSTOM_MAPPINGS.get(key);
    }

    public static class GuidebookImage
    {
        public String id;
        public ResourceLocation texture;
        public int xSize, ySize;

        public GuidebookImage(String id, ResourceLocation texture, int xSize, int ySize)
        {
            this.id = id;
            this.texture = texture;
            this.xSize = xSize;
            this.ySize = ySize;
        }
    }

    public static class GuidebookRecipe
    {
        public final String stringID;
        public IRecipe recipe;

        public GuidebookRecipe(String stringID, IRecipe recipe) {
            this.stringID = stringID;
            this.recipe = recipe;
        }
    }
}
