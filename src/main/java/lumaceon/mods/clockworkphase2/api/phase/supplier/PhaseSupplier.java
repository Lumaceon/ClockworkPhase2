package lumaceon.mods.clockworkphase2.api.phase.supplier;

import lumaceon.mods.clockworkphase2.api.phase.Phase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.List;

/**
 * Each phase supplier registered in the Phases class is checked during Phases.getPhases. A "check" calls the
 * corresponding getPhases method within the supplier.
 *
 * Phase suppliers should be registered on load and changed as necessary. As an example, if special blocks supplied
 * phases for an area, you should make one phase supplier with a list of coordinates to check around. You should NOT
 * register a PhaseSupplier for every one of those blocks, as PhaseSuppliers would persist even if the block did not.
 */
public abstract class PhaseSupplier
{
    /**
     * Higher priority suppliers are called zeroth. Priority defaults to 0 and can be negative. Suppliers that effect
     * other phases should occur after the others have added their phases (negative priority).
     */
    public int priority;

    public PhaseSupplier() {
        this(0);
    }

    public PhaseSupplier(int priority) {
        this.priority = priority;
    }

    /**
     * Adds (or removes) phases from the list of phases returned during the getPhases method from the Phases class.
     * @param world The world to check for phases.
     * @param phaseList A list of phases to be added on to or removed from; this may or may not already contain phases.
     */
    public abstract void addPhases(World world, int x, int y, int z, List<Phase> phaseList);

    public abstract void addPhases(World world, List<Phase> phaseList);

    /**
     * Adds (or removes) phases from the list of phases returned during the getPhases method from the Phases class.
     * @param entity The entity requesting available phases.
     * @param phaseList A list of phases to be added on to or removed from; this may or may not already contain phases.
     */
    public abstract void addPhases(Entity entity, List<Phase> phaseList);
}