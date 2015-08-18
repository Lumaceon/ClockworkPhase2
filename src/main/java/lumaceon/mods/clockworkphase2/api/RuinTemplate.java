package lumaceon.mods.clockworkphase2.api;

public abstract class RuinTemplate
{
    /**
     * The radius of space to reserve for these ruins, not including vertical space.
     * The area is a square going from lowest to highest height, and is reserved with corners as opposed to a cylinder.
     * You don't necessarily have to use the entire area you reserve, it just marks the borders of the ruins.
     */
    public int areaRadius;


}
