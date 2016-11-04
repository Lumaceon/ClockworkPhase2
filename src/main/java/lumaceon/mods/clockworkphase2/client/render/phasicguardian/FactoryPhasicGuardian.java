package lumaceon.mods.clockworkphase2.client.render.phasicguardian;

import lumaceon.mods.clockworkphase2.entity.EntityPhasicGuardian;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FactoryPhasicGuardian implements IRenderFactory<EntityPhasicGuardian>
{
    public static final FactoryPhasicGuardian INSTANCE = new FactoryPhasicGuardian();

    @Override
    public Render createRenderFor(RenderManager manager) {
        return new RenderPhasicGuardian<EntityPhasicGuardian>(manager, new ModelPhasicGuardian(), 0.5F);
    }
}
