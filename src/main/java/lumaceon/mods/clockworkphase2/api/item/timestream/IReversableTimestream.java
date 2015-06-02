package lumaceon.mods.clockworkphase2.api.item.timestream;

import net.minecraft.item.ItemStack;

/**
 * Used in the timestream reversalizer to reverse this timestream. Most generating timestreams are reverse timestreams.
 * The resultant timestream should also be reversible to allow 2-way reversals.
 */
public interface IReversableTimestream extends ITimestream
{
    /**
     * Called from the timestream reversalizer to reverse this timestream's function. Reversed timestreams are usually
     * considered cannibalistic timestreams; in most cases (though not all cases), they consume effects and generate
     * time sand. Normal timestreams on the other hand, consume time sand to cause effects.
     * @param item The IReversableTimestream stack to reverse.
     */
    public void reverseTimestream(ItemStack item);
}
