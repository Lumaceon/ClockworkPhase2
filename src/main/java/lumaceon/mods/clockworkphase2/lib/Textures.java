package lumaceon.mods.clockworkphase2.lib;

import net.minecraft.util.ResourceLocation;

public class Textures
{
    public static final String RESOURCE_PREFIX = Reference.MOD_ID + ":";

    /*public static class VANILLA
    {
        public static final ResourceLocation VANILLA_PARTICLES = new ResourceLocation("textures/particle/particles.png");
        public static final ResourceLocation MOON_PHASES = new ResourceLocation("textures/environment/moon_phases.png");
        public static ResourceLocation SUN = new ResourceLocation("textures/environment/sun.png");
        public static ResourceLocation END_SKY = new ResourceLocation("textures/environment/end_sky.png");
    }*/

    public static class GUI
    {
        public static ResourceLocation BOOK_COVER = new ResourceLocation(Reference.MOD_ID, "textures/gui/guidebook.png");
        public static ResourceLocation BOOK_INSIDE = new ResourceLocation(Reference.MOD_ID, "textures/gui/guidebook_open.png");

        public static ResourceLocation ASSEMBLY_TABLE = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table.png");
        public static ResourceLocation ASSEMBLY_TABLE_MAINSPRING_ADD = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table_mainspring_add.png");
        public static ResourceLocation ASSEMBLY_TABLE_GEARS = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table_gears.png");
        public static ResourceLocation ASSEMBLY_TABLE_CONSTRUCT = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table_construct.png");
        public static ResourceLocation ASSEMBLY_TABLE_TEMPORAL_EXCAVATOR = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table_temporal_excavator.png");

        public static ResourceLocation GUIDEBOOK_CRAFTING = new ResourceLocation(Reference.MOD_ID, "textures/gui/guidebook/crafting_table_guide.png");

        public static ResourceLocation TANK_LINES_10K = new ResourceLocation(Reference.MOD_ID, "textures/gui/misc/tank_lines_10k.png");
    }

    public static class MISC
    {
        public static final ResourceLocation CELESTIAL_COMPASS_MAIN = new ResourceLocation(Reference.MOD_ID, "textures/blocks/celestial_compass/top.png");
        public static final ResourceLocation CELESTIAL_COMPASS_SIDE = new ResourceLocation(Reference.MOD_ID, "textures/blocks/celestial_compass/side.png");
    }
}
