package lumaceon.mods.clockworkphase2.client.model;

import lumaceon.mods.clockworkphase2.proxy.ClientProxy;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class CustomModelLoaderClockworkPhase implements ICustomModelLoader
{
    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        if(modelLocation.equals(ClientProxy.LEXICON_MODEL) || modelLocation.equals(ClientProxy.MULTITOOL_MODEL))
            return true;
        return false;
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        if(modelLocation.equals(ClientProxy.LEXICON_MODEL))
            return new ModelTemporalLexicon();
        else
            return new ModelTemporalMultitool();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
