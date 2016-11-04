package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.client.model.ModelTemporalLexicon;
import lumaceon.mods.clockworkphase2.client.model.ModelTemporalMultitool;
import lumaceon.mods.clockworkphase2.proxy.ClientProxy;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModelBakeHandler
{
    @SubscribeEvent
    public void onBake(ModelBakeEvent event)
    {
        Object object = event.getModelRegistry().getObject(ClientProxy.LEXICON_MODEL);
        if(object != null)
        {
            IBakedModel existingModel = (IBakedModel)object;
            ModelTemporalLexicon.Baked customModel = new ModelTemporalLexicon.Baked(existingModel);
            event.getModelRegistry().putObject(ClientProxy.LEXICON_MODEL, customModel);
        }

        object = event.getModelRegistry().getObject(ClientProxy.MULTITOOL_MODEL);
        if(object != null)
        {
            IBakedModel existingModel = (IBakedModel)object;
            ModelTemporalMultitool.Baked customModel = new ModelTemporalMultitool.Baked(existingModel);
            event.getModelRegistry().putObject(ClientProxy.MULTITOOL_MODEL, customModel);
        }
    }
}
