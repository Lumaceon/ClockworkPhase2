package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.timezonefunction.type.*;

import java.util.HashMap;

public class ModTZFunctions
{
    public static HashMap<String, TimezoneFunctionType> TYPE_REGISTRY = new HashMap<>();

    public static TimezoneFunctionType reservoir;
    public static TimezoneFunctionType colony;
    public static TimezoneFunctionType quarry;
    public static TimezoneFunctionType capacitor;
    public static TimezoneFunctionType purgatory;
    public static void init()
    {
        reservoir = new TimezoneFunctionTypeReservoir(Reference.MOD_ID + ":reservoir", Textures.GUI.TZF_RESERVOIR);
        TYPE_REGISTRY.put(reservoir.getUniqueID(), reservoir);

        colony = new TimezoneFunctionTypeColony(Reference.MOD_ID + ":colony", Textures.GUI.TZF_COLONY);
        TYPE_REGISTRY.put(colony.getUniqueID(), colony);

        quarry = new TimezoneFunctionTypeQuarry(Reference.MOD_ID + ":quarry", Textures.MISC.CELESTIAL_COMPASS_MAIN);
        TYPE_REGISTRY.put(quarry.getUniqueID(), quarry);

        capacitor = new TimezoneFunctionTypeCapacitor(Reference.MOD_ID + ":capacitor", Textures.GUI.TZF_CAPACITOR);
        TYPE_REGISTRY.put(capacitor.getUniqueID(), capacitor);

        purgatory = new TimezoneFunctionTypePurgatory(Reference.MOD_ID + ":purgatory", Textures.MISC.GLYPH_MAIN);
        TYPE_REGISTRY.put(purgatory.getUniqueID(), purgatory);
    }

    public static TimezoneFunctionType getTypeFromID(String id) {
        return TYPE_REGISTRY.get(id);
    }
}
