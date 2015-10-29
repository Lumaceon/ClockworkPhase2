package lumaceon.mods.clockworkphase2.lib;

import net.minecraft.util.ResourceLocation;

public class Textures
{
    public static final String RESOURCE_PREFIX = Reference.MOD_ID + ":";

    public static class VANILLA
    {
        public static final ResourceLocation VANILLA_PARTICLES = new ResourceLocation("textures/particle/particles.png");
        public static final ResourceLocation MOON_PHASES = new ResourceLocation("textures/environment/moon_phases.png");
        public static ResourceLocation SUN = new ResourceLocation("textures/environment/sun.png");
        public static ResourceLocation END_SKY = new ResourceLocation("textures/environment/end_sky.png");
    }

    public static class GUI
    {
        public static ResourceLocation CLOCK = new ResourceLocation(Reference.MOD_ID, "textures/gui/clock.png");
        public static ResourceLocation CLOCK_SECOND = new ResourceLocation(Reference.MOD_ID, "textures/gui/clock_second.png");
        public static ResourceLocation CLOCK_MINUTE = new ResourceLocation(Reference.MOD_ID, "textures/gui/clock_minute.png");
        public static ResourceLocation CLOCK_HOUR = new ResourceLocation(Reference.MOD_ID, "textures/gui/clock_hour.png");
        public static ResourceLocation CLOCK_CENTER_PEG = new ResourceLocation(Reference.MOD_ID, "textures/gui/clock_center.png");
        public static ResourceLocation BASE = new ResourceLocation(Reference.MOD_ID, "textures/gui/base_gui.png");

        public static ResourceLocation TIME_WELL = new ResourceLocation(Reference.MOD_ID, "textures/gui/time_well.png");

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
        public static ResourceLocation TEST = new ResourceLocation(Reference.MOD_ID, "textures/particles/particle.png");
    }

    public static class ITEM
    {
        public static ResourceLocation MAINSPRING = new ResourceLocation(Reference.MOD_ID, "textures/items/mainspring.png");
        public static ResourceLocation CLOCKWORK_CORE = new ResourceLocation(Reference.MOD_ID, "textures/items/clockwork_core.png");
        public static ResourceLocation WOOD_GEAR = new ResourceLocation(Reference.MOD_ID, "textures/items/wood_gear.png");
    }

    public static class MISC
    {
        public static final ResourceLocation TEMPORAL_MODULE = new ResourceLocation("textures/temporal_module.png");
        public static final ResourceLocation INVALID = new ResourceLocation(Reference.MOD_ID, "textures/misc/invalid.png");
        public static final ResourceLocation VALID = new ResourceLocation(Reference.MOD_ID, "textures/misc/valid.png");
    }
}
