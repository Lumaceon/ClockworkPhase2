package lumaceon.mods.clockworkphase2.item.multiblocktemplate;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.api.item.IMultiblockTemplateItem;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;

public class ItemMultiblockTemplate extends ItemClockworkPhase implements IMultiblockTemplateItem
{
    protected IMultiblockTemplate multiblockTemplate;

    public ItemMultiblockTemplate(int maxStack, int maxDamage, String name, IMultiblockTemplate multiblockTemplate) {
        super(maxStack, maxDamage, name);
        this.multiblockTemplate = multiblockTemplate;
    }

    @Override
    public IMultiblockTemplate getTemplate() {
        return multiblockTemplate;
    }
}
