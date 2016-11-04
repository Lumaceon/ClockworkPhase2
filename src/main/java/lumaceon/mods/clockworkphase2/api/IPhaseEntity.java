package lumaceon.mods.clockworkphase2.api;

/**
 * Implement for entities which should only spawn when a nearby player has an active hourglass.
 */
public interface IPhaseEntity
{
    /**
     * @return The tier of this entity.
     */
    public EnumExpTier getTier();
}
