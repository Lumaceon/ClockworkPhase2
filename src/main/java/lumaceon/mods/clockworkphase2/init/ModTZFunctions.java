package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.timezonefunction.type.*;

public class ModTZFunctions
{
    public static TimezoneFunctionType reservoir;
    public static TimezoneFunctionType colony;
    public static TimezoneFunctionType quarry;
    public static TimezoneFunctionType capacitor;
    public static TimezoneFunctionType purgatory;
    public static void init()
    {
        reservoir = new TimezoneFunctionTypeReservoir(Reference.MOD_ID + ":reservoir", Textures.GUI.TZF_RESERVOIR);
        colony = new TimezoneFunctionTypeColony(Reference.MOD_ID + ":colony", Textures.GUI.TZF_COLONY);
        quarry = new TimezoneFunctionTypeQuarry(Reference.MOD_ID + ":quarry", Textures.MISC.CELESTIAL_COMPASS_MAIN);
        capacitor = new TimezoneFunctionTypeCapacitor(Reference.MOD_ID + ":capacitor", Textures.GUI.TZF_CAPACITOR);
        purgatory = new TimezoneFunctionTypePurgatory(Reference.MOD_ID + ":purgatory", Textures.MISC.GLYPH_MAIN);
    }
}
