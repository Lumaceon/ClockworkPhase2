package lumaceon.mods.clockworkphase2.capabilities.custombehavior;

import lumaceon.mods.clockworkphase2.ai.AIFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public interface ICustomBehavior
{
    /**
     * Called during entity creation to override the entity's AI. The entity's AI will be removed in place of the
     * custom AI returned by each factory.
     *
     * It should be noted that this is usually called for all living entities. Returning null will skip all the AI
     * overriding, while returning an empty array will effectively delete the default AI, replacing it with nothing.
     *
     * @param entityToOverride The entity, as it's joining the world, which has this capability.
     * @return An array containing factories for new AI tasks, or null to skip AI overriding.
     */
    @Nullable
    public AIFactory[] getMainTasks(EntityLiving entityToOverride);

    /**
     * Works identically to the main tasks, except for target tasks (which override a separate list).
     *
     * @param entityToOverride The entity, as it's joining the world, which has this capability.
     * @return An array containing factories for new targeting tasks, or null to skip target overriding.
     */
    @Nullable
    public AIFactory[] getTargetTasks(EntityLiving entityToOverride);

    public NBTTagCompound serializeNBT();
    public void deserializeNBT(NBTTagCompound base);
}
