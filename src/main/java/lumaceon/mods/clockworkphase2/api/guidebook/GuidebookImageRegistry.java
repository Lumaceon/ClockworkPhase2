package lumaceon.mods.clockworkphase2.api.guidebook;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class GuidebookImageRegistry
{
    public static final HashMap<String, GuidebookImage> IMAGES = new HashMap<String, GuidebookImage>();

    public static void registerImage(String id, ResourceLocation texture, int x, int y) {
        IMAGES.put(id, new GuidebookImage(id, texture, x, y));
    }

    public static GuidebookImage getImageFromID(String id) {
        return IMAGES.get(id);
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
}
