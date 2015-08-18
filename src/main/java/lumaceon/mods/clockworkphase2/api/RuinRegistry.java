package lumaceon.mods.clockworkphase2.api;

import java.util.ArrayList;

public class RuinRegistry
{
    public static ArrayList<RuinTemplate> ruinTemplates = new ArrayList<RuinTemplate>();

    public static void registerRuins(RuinTemplate ruinTemplate) {
        ruinTemplates.add(ruinTemplate);
    }
}
