package lumaceon.mods.clockworkphase2.api.phase;

import lumaceon.mods.clockworkphase2.api.phase.supplier.PhaseSupplier;
import lumaceon.mods.clockworkphase2.api.phase.supplier.PhaseSupplierReoccurring;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Phases
{
    private static ArrayList<PhaseSupplier> suppliers = new ArrayList<PhaseSupplier>();
    public static PhaseSupplierReoccurring supplierReoccurring = new PhaseSupplierReoccurring();

    public static PhaseReoccurring elysianComet;
    public static PhaseReoccurring spiritFestival;
    public static PhaseReoccurring harvestMoon;
    public static PhaseReoccurring meteorShower;

    public static boolean isPhaseActive(World world, int x, int y, int z, Phase phase)
    {
        Phase[] phases = getPhases(world, x, y, z);
        for(Phase p : phases)
            if(p != null && p.equals(phase))
                return true;
        return false;
    }

    public static Phase[] getPhases(World world, int x, int y, int z)
    {
        List<Phase> phases = new ArrayList<Phase>(10);
        for(PhaseSupplier supplier : suppliers)
            supplier.addPhases(world, x, y, z, phases);
        return phases.toArray(new Phase[phases.size()]);
    }

    /**
     * Gets the phases current active for the entire world. This is called every world tick for each active world.
     */
    public static Phase[] getPhases(World world)
    {
        List<Phase> phases = new ArrayList<Phase>(10);
        for(PhaseSupplier supplier : suppliers)
            supplier.addPhases(world, phases);
        return phases.toArray(new Phase[phases.size()]);
    }

    public static Phase[] getPhases(Entity entity)
    {
        List<Phase> phases = new ArrayList<Phase>(10);
        for(PhaseSupplier supplier : suppliers)
            supplier.addPhases(entity, phases);
        return phases.toArray(new Phase[phases.size()]);
    }

    /**
     * Registers a phase which will automatically reoccur in each world every so-many ticks.
     * Depending on the world, this occurrence may be modified.
     *
     * @param phase The phase to register.
     */
    public static void registerReoccurringPhase(PhaseReoccurring phase) {
        supplierReoccurring.registerReoccurringPhase(phase);
    }

    public static void registerPhaseSupplier(PhaseSupplier supplier)
    {
        if(suppliers.isEmpty())
            suppliers.add(supplier);
        else
        {
            int index = 0;
            PhaseSupplier s = suppliers.get(index);
            while(s != null && supplier.priority < s.priority)
            {
                index++;
                s = suppliers.get(index);
            }
            suppliers.add(index, supplier);
        }
    }
}
