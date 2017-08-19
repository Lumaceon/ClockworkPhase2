package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.entity.EntityAdvancedGolem;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
    public static void init()
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "advanced_golem"), EntityAdvancedGolem.class, "advanced_golem", 0, ClockworkPhase2.instance, 32, 0, true);
        //RenderingRegistry.registerEntityRenderingHandler(); - Call this in client proxy.
    }
}
