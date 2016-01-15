package lumaceon.mods.clockworkphase2.structure;

import lumaceon.mods.clockworkphase2.util.SchematicUtility;

public class StructureTemplate
{
    protected String uniqueName;
    protected String resourceName;
    protected boolean isResource;

    public StructureTemplate(String uniqueName, String resourceName, boolean isResource) {
        this.uniqueName = uniqueName;
        this.resourceName = resourceName;
        this.isResource = isResource;
    }

    public SchematicUtility.Schematic getSchematic() {
        return SchematicUtility.INSTANCE.loadModSchematic(resourceName, isResource);
    }
}