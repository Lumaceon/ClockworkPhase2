package lumaceon.mods.clockworkphase2.api;

import java.util.concurrent.ConcurrentHashMap;

public class RuinRegistry
{
    public static ConcurrentHashMap<String, RuinTemplate> ruinTemplates = new ConcurrentHashMap<String, RuinTemplate>();

    public static void registerRuins(RuinTemplate ruinTemplate, String uniqueName) {
        ruinTemplates.put(uniqueName, ruinTemplate);
    }

    public static RuinTemplate getRuinTemplate(String uniqueName) {
        return ruinTemplates.get(uniqueName);
    }
}
