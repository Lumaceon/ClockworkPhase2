package lumaceon.mods.clockworkphase2.client.render.dissonantspecter;

import lumaceon.mods.clockworkphase2.entity.EntityDissonantSpecter;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class FactoryDissonantSpecter implements IRenderFactory<EntityDissonantSpecter>
{
    public static final FactoryDissonantSpecter INSTANCE = new FactoryDissonantSpecter();

    @Override
    public Render createRenderFor(RenderManager manager) {
        return new RenderDissonantSpecter<EntityDissonantSpecter>(manager, new ModelDissonantSpecter(0.0F, false), 0.5F);
    }
}
