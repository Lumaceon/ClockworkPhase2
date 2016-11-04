package lumaceon.mods.clockworkphase2.api.omnimachine;

import java.util.HashMap;

public class OmnimachineModuleRegistry
{
    private final static HashMap<String, OmnimachineModule> MODULES = new HashMap<String, OmnimachineModule>();

    public static void registerModule(OmnimachineModule module, String key)
    {
        if(module == null || key == null)
            return;
        if(MODULES.containsKey(key))
            System.out.println("[ClockworkPhase2] Attempted to register duplicate omnimachine module; module '" + key + "' already exists.");
        MODULES.put(key, module);
    }

    public static HashMap<String, OmnimachineModule> getModules() { return MODULES; }
    public static OmnimachineModule getModule(String ID) { return MODULES.get(ID); }
}
