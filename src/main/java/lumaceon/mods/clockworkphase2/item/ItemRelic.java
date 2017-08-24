package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.api.item.IFishingRelic;

public abstract class ItemRelic extends ItemClockworkPhase implements IFishingRelic
{
    public ItemRelic(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }
}
