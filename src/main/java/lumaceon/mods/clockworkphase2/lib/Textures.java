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
        public static ResourceLocation BASE = new ResourceLocation(Reference.MOD_ID, "textures/gui/base_gui.png");

        public static ResourceLocation TIME_WELL = new ResourceLocation(Reference.MOD_ID, "textures/gui/time_well.png");

        public static ResourceLocation ASSEMBLY_TABLE = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table.png");
        public static ResourceLocation ASSEMBLY_TABLE_MAINSPRING = new ResourceLocation(Reference.MOD_ID, "textures/gui/assembly_table_mainspring.png");

        public static ResourceLocation PLAYER_INVENTORY = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/player_inventory.png");
        public static ResourceLocation POWER_METER = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/power.png");
        public static ResourceLocation POWER_METER_HAND = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/power_hand.png");
        public static ResourceLocation POWER_METER_CENTER = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/power_center.png");

        public static ResourceLocation FURNACE = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/furnace.png");
        public static ResourceLocation FURNACE_FORE = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/furnace_fore.png");
        public static ResourceLocation BREWERY = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/brewery.png");
        public static ResourceLocation ALLOY_FURNACE = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/alloy_furnace.png");
        public static ResourceLocation MELTER = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/melter.png");
        public static ResourceLocation MIXER = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/mixer.png");
        public static ResourceLocation CHEST = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockworkmachine/chest.png");
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
        public static ResourceLocation MAINSPRING = new ResourceLocation(Reference.MOD_ID, "textures/item/mainspring.png");
        public static ResourceLocation CLOCKWORK_CORE = new ResourceLocation(Reference.MOD_ID, "textures/item/clockwork_core.png");
        public static ResourceLocation WOOD_GEAR = new ResourceLocation(Reference.MOD_ID, "textures/item/wood_gear.png");
    }

    public static class MISC
    {
        public static final ResourceLocation TEMPORAL_MODULE = new ResourceLocation("textures/temporal_module.png");
        public static final ResourceLocation INVALID = new ResourceLocation(Reference.MOD_ID, "textures/misc/invalid.png");
        public static final ResourceLocation VALID = new ResourceLocation(Reference.MOD_ID, "textures/misc/valid.png");

        public static final ResourceLocation FORTH_AGE_GEAR = new ResourceLocation(Reference.MOD_ID, "textures/misc/gear_age4.png");
        public static final ResourceLocation THIRD_AGE_GEAR = new ResourceLocation(Reference.MOD_ID, "textures/misc/gear_age3.png");
        public static final ResourceLocation SECOND_AGE_GEAR = new ResourceLocation(Reference.MOD_ID, "textures/misc/gear_age2.png");
        public static final ResourceLocation FIRST_AGE_GEAR = new ResourceLocation(Reference.MOD_ID, "textures/misc/gear_age1.png");

        public static final ResourceLocation TDA_METAL_FRAME = new ResourceLocation(Reference.MOD_ID, "textures/blocks/temporal_displacement_altar_sb/metal_frame.png");
        public static final ResourceLocation TDA_GLASS_FRAME = new ResourceLocation(Reference.MOD_ID, "textures/blocks/temporal_displacement_altar_sb/glass_frame.png");
        public static final ResourceLocation TDA_BOTTOM = new ResourceLocation(Reference.MOD_ID, "textures/blocks/temporal_displacement_altar_sb/bottom.png");
        public static final ResourceLocation TDA_GEARS = new ResourceLocation(Reference.MOD_ID, "textures/blocks/temporal_displacement_altar_sb/gears.png");

        public static final ResourceLocation TDA = new ResourceLocation(Reference.MOD_ID, "textures/blocks/special_renders/TDA.png");
        public static final ResourceLocation TDA_SIDES = new ResourceLocation(Reference.MOD_ID, "textures/blocks/special_renders/TDA_sides.png");
    }
}
