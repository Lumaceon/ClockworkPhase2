package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.temporal.Echo;
import lumaceon.mods.clockworkphase2.lib.Reference;

public class Echoes
{
    public static Echo smelt;

    public static void init()
    {
        smelt = new Echo(Reference.MOD_ID + ":furnace");
    }
}
