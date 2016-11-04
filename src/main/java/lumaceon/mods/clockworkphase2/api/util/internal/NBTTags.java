package lumaceon.mods.clockworkphase2.api.util.internal;

public class NBTTags
{
    public static final String COMPONENT_INVENTORY = "component_inventory"; //Used for most assembly items.
    public static final String ACTIVE = "cp_is_active"; //Used in several places, frequently temporal excavator upgrades.
    public static final String IS_COMPONENT = "is_component";
    public static final String MAX_TENSION = "max_tension"; //Used for both mainspring and clockwork constructs.
    public static final String CURRENT_TENSION = "current_tension"; //Used in clockwork constructs.
    public static final String QUALITY = "cp_quality"; //Used by clockwork cores and constructs.
    public static final String SPEED = "cp_speed"; //Used by clockwork cores and constructs.
    public static final String TIER = "cp_tier"; //Used by clockwork cores and constructs.
    public static final String HARVEST_LEVEL_PICKAXE = "harvest_lvl_pick"; //Used by clockwork tools and the temporal excavator.
    public static final String HARVEST_LEVEL_AXE = "harvest_lvl_axe"; //Used by clockwork tools and the temporal excavator.
    public static final String HARVEST_LEVEL_SHOVEL = "harvest_lvl_shovel"; //Used by clockwork tools and the temporal excavator.
    public static final String TIME = "time_stored"; //Used by hourglasses and time storage items.
    public static final String TIME_GENERATION_PER_TICK = "time_gen_rate"; //Used by hourglasses.
    public static final String TOTAL_TIME_GENERATION = "total_time_gen"; //Used by hourglasses.
    public static final String MIN_XP = "min_xp_lvl"; //Used by hourglasses.
}
