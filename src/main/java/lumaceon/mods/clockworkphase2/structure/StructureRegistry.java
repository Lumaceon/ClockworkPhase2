package lumaceon.mods.clockworkphase2.structure;

import java.util.concurrent.ConcurrentHashMap;

public class StructureRegistry
{
    public static ConcurrentHashMap<String, StructureTemplate> structureTemplates = new ConcurrentHashMap<String, StructureTemplate>();

    public static void registerStructure(StructureTemplate structureTemplate, String uniqueName) {
        structureTemplates.put(uniqueName, structureTemplate);
    }

    public static StructureTemplate getStructureTemplate(String uniqueName) {
        return structureTemplates.get(uniqueName);
    }
}
