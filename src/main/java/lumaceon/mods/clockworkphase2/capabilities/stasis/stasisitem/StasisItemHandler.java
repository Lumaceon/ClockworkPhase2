package lumaceon.mods.clockworkphase2.capabilities.stasis.stasisitem;

import lumaceon.mods.clockworkphase2.api.util.TimeConverter;

public class StasisItemHandler implements IStasisItemHandler
{
    long timeToOffset = TimeConverter.MINUTE;

    @Override
    public void setTimeToOffset(long timeToOffset) {
        this.timeToOffset = timeToOffset;
    }

    @Override
    public long getTimeToOffset() {
        return this.timeToOffset;
    }
}
