package lumaceon.mods.clockworkphase2.item.timeframekey.artifact;

import lumaceon.mods.clockworkphase2.api.item.IArtifactItem;
import lumaceon.mods.clockworkphase2.api.item.ITimeframeKeyItem;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;

public class ItemArtifact extends ItemClockworkPhase implements IArtifactItem
{
    public ITimeframeKeyItem key;

    public ItemArtifact(int maxStack, int maxDamage, String registryName, ITimeframeKeyItem key) {
        super(maxStack, maxDamage, registryName);
        this.key = key;
    }

    @Override
    public ITimeframeKeyItem getTimeframeKeyItem() {
        return key;
    }
}
