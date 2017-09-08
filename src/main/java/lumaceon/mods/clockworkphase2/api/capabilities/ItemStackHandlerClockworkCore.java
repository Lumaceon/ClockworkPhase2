package lumaceon.mods.clockworkphase2.api.capabilities;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import net.minecraft.item.ItemStack;

public class ItemStackHandlerClockworkCore extends ItemStackHandlerClockwork
{
    public ItemStackHandlerClockworkCore(int size) {
        super(size);
    }

    @Override
    protected void onContentsChanged(int slot)
    {
        quality = speed = tier = 0;
        for(ItemStack s : stacks)
        {
            if(s != null && s.getItem() instanceof IClockwork)
            {
                quality += ((IClockwork) s.getItem()).getQuality(s);
                speed += ((IClockwork) s.getItem()).getSpeed(s);
                tier = Math.max(tier, ((IClockwork) s.getItem()).getTier(s));
            }
        }
    }
}
