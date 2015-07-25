package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.phase.PhaseReoccurring;
import lumaceon.mods.clockworkphase2.api.phase.Phases;
import lumaceon.mods.clockworkphase2.phase.PhaseElysianComet;
import lumaceon.mods.clockworkphase2.phase.PhaseMeteorShower;

public class ModPhases
{
    public static void init()
    {
        Phases.elysianComet = new PhaseElysianComet(24000, 72000);
        Phases.spiritFestival = new PhaseReoccurring(24000, 168000);
        Phases.harvestMoon = new PhaseReoccurring(24000, 720000);
        Phases.meteorShower = new PhaseMeteorShower(12000, 360000);

        Phases.registerReoccurringPhase(Phases.elysianComet);
        Phases.registerReoccurringPhase(Phases.spiritFestival);
        Phases.registerReoccurringPhase(Phases.harvestMoon);
        Phases.registerReoccurringPhase(Phases.meteorShower);

        Phases.registerPhaseSupplier(Phases.supplierReoccurring);
    }
}
