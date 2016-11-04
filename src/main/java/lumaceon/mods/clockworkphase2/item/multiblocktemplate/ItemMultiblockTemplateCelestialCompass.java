package lumaceon.mods.clockworkphase2.item.multiblocktemplate;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.api.item.IMultiblockTemplateItem;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;

public class ItemMultiblockTemplateCelestialCompass extends ItemClockworkPhase implements IMultiblockTemplateItem
{
    public ItemMultiblockTemplateCelestialCompass(int maxStack, int maxDamage, String registryName) {
        super(maxStack, maxDamage, registryName);
    }

    @Override
    public IMultiblockTemplate getTemplate() {
        return MultiblockTemplateCelestialCompass.INSTANCE;
    }
}
