package lumaceon.mods.clockworkphase2.api.phase.supplier;

import lumaceon.mods.clockworkphase2.api.phase.Phase;
import lumaceon.mods.clockworkphase2.api.phase.PhaseReoccurring;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PhaseSupplierReoccurring extends PhaseSupplier
{
    public List<PhaseReoccurring> reoccurringPhases = new ArrayList<PhaseReoccurring>();

    @Override
    public void addPhases(World world, int x, int y, int z, List<Phase> phaseList)
    {
        //TODO - check for timezones.
        addPhases(world, phaseList);
    }

    @Override
    public void addPhases(World world, List<Phase> phaseList)
    {
        for(PhaseReoccurring phaseReoccurring : reoccurringPhases)
        {
            long time = world.getWorldTime() % (phaseReoccurring.ticksBetweenOccurrences + phaseReoccurring.duration);
            boolean exists = false;
            if(time > phaseReoccurring.ticksBetweenOccurrences)
            {
                for(Phase phase : phaseList)
                    if(phase.equals(phaseReoccurring))
                        exists = true;
                if(!exists)
                    phaseList.add(phaseReoccurring);
                else
                    Logger.info("Problem");
            }
        }
    }

    @Override
    public void addPhases(Entity entity, List<Phase> phaseList)
    {
        if(entity.worldObj == null)
            return;

        //TODO - check for hourglasses.
        addPhases(entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ, phaseList);
    }

    public void registerReoccurringPhase(PhaseReoccurring phase) {
        reoccurringPhases.add(phase);
    }
}
