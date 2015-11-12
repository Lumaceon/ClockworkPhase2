package lumaceon.mods.clockworkphase2.api;

import lumaceon.mods.clockworkphase2.util.SchematicUtility;
import net.minecraft.util.ResourceLocation;

public abstract class RuinTemplate
{
    public SchematicUtility.Schematic ruinSchematic;
    public ResourceLocation schematicLocation;
    public String uniqueName;

    public RuinTemplate(ResourceLocation schematicLocation, String uniqueName) {
        this.schematicLocation = schematicLocation;
        this.uniqueName = uniqueName; //TODO - cleanup resourceLocation schematic loading.
        ruinSchematic = SchematicUtility.INSTANCE.loadModSchematic("NewSchematic", true);
    }
}
