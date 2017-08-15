package lumaceon.mods.clockworkphase2.capabilities.stasis;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import java.util.List;

public interface IStasis
{
    public void setStasisTime(long stasisTime);
    public long getStasisTime();

    /**
     * Should tick the internal stasis timer, as well as check for stasis attacks to make and remove.
     */
    public void onUpdate(Entity entity);

    /**
     * Adds a stasis attack that will hit the player when the internal stasis timer is greater or equal to timeToHit.
     * @param source The original source of the damage.
     * @param amount The amount of damage.
     * @param timeToHit The time, relative to the internal stasis timer, that this will hit the player.
     * @param experienceToReturn The experience it cost the player to stall this attack.
     */
    public void addStasisAttack(DamageSource source, float amount, long timeToHit, int experienceToReturn);

    /**
     * Get a list of stasis attacks that are waiting to hit the player.
     * @return By default, this returns the actual list and not a copy of it.
     */
    public List<StasisAttack> getStasisAttacks();

    public NBTTagCompound serializeNBT();
    public void deserializeNBT(NBTTagCompound nbt);
}
