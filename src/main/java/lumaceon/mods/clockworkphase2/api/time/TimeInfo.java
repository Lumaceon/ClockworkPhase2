package lumaceon.mods.clockworkphase2.api.time;

/**
 * Helper class for putting the type and amount of time in one place.
 */
public class TimeInfo
{
    public Time time;
    public long amount;

    public TimeInfo(Time time) {
        this.time = time;
    }

    public TimeInfo(Time time, long amount) {
        this(time);
        this.amount = amount;
    }

    public Time getTime() {
        return time;
    }

    public long getAmount() {
        return amount;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
