package lumaceon.mods.clockworkphase2.lib;

import net.minecraft.util.ResourceLocation;

public class Textures
{
    public static final String RESOURCE_PREFIX = Reference.MOD_ID + ":";
    public static final ResourceLocation VANILLA_PARTICLES = new ResourceLocation("textures/particle/particles.png");

    public static class GUI
    {
        public static ResourceLocation DEFAULT_ASSEMBLY_TABLE = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table.png");
    }

    public static class GLYPH
    {
        public static ResourceLocation BASE_GLYPH = new ResourceLocation(Reference.MOD_ID, "textures/glyph/base.png");
    }

    public static class PARTICLE
    {
        public static ResourceLocation TIME_SAND = new ResourceLocation(Reference.MOD_ID, "textures/particles/time_sand.png");
    }
}
