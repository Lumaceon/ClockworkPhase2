package lumaceon.mods.clockworkphase2.lib;

import net.minecraft.util.ResourceLocation;

public class Textures
{
    public static final String RESOURCE_PREFIX = Reference.MOD_ID + ":";
    public static final ResourceLocation VANILLA_PARTICLES = new ResourceLocation("textures/particle/particles.png");

    public static class GUI
    {
        public static ResourceLocation DEFAULT_ASSEMBLY_TABLE = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table.png");
        public static ResourceLocation TS_CRAFT_TOP = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_crafting_top.png");
        public static ResourceLocation TS_CRAFT_BOTTOM = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_crafting_bottom.png");
        public static ResourceLocation BLACK_COLOR_ALPHA80 = new ResourceLocation(Reference.MOD_ID, "textures/gui/black_color_80.png");
        public static ResourceLocation TS_BG_RELOCATION = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_background_relocation.png");
        public static ResourceLocation TS_BG_SMELT = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_background_smelt.png");
        public static ResourceLocation TS_BG_SILKY = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_background_silky.png");
        public static ResourceLocation TS_BG_TANK = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_background_tank.png");
        public static ResourceLocation TS_BG_LIGHTNING = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_background_lightning.png");
        public static ResourceLocation TS_BG_CONTRACT = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_background_contract.png");

        public static ResourceLocation TS_ICON_SMELT = new ResourceLocation(Reference.MOD_ID, "textures/gui/timestream_crafting_icon_smelt.png");
    }

    public static class GLYPH
    {
        public static ResourceLocation BASE_GLYPH = new ResourceLocation(Reference.MOD_ID, "textures/glyph/base.png");
        public static ResourceLocation[] BASE_GLYPH_GEMS = {
                new ResourceLocation(Reference.MOD_ID, "textures/glyph/gem0.png"),
                new ResourceLocation(Reference.MOD_ID, "textures/glyph/gem1.png"),
                new ResourceLocation(Reference.MOD_ID, "textures/glyph/gem2.png"),
                new ResourceLocation(Reference.MOD_ID, "textures/glyph/gem3.png"),
                new ResourceLocation(Reference.MOD_ID, "textures/glyph/gem4.png"),
                new ResourceLocation(Reference.MOD_ID, "textures/glyph/gem5.png"),
                new ResourceLocation(Reference.MOD_ID, "textures/glyph/gem6.png"),
                new ResourceLocation(Reference.MOD_ID, "textures/glyph/gem7.png")
        };
    }

    public static class PARTICLE
    {
        public static ResourceLocation TIME_SAND = new ResourceLocation(Reference.MOD_ID, "textures/particles/time_sand.png");
    }

    public static class ITEM
    {
        public static ResourceLocation MAINSPRING = new ResourceLocation(Reference.MOD_ID, "textures/items/mainspring.png");
        public static ResourceLocation CLOCKWORK_CORE = new ResourceLocation(Reference.MOD_ID, "textures/items/clockwork_core.png");
    }
}
