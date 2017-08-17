package lumaceon.mods.clockworkphase2.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * Although it's called CustomAI, it's more of a factory for instances of EntityAiBase. You can return custom or
 * vanilla tasks interchangeably, so long as they're valid.
 */
public abstract class AIFactory
{
    public String uniqueID;
    private int priority = 0;

    public AIFactory(String uniqueID, int priority) {
        this.uniqueID = uniqueID;
        this.priority = priority;
        CustomAIs.CUSTOM_AI_FACTORIES.put(uniqueID, this);
    }

    public abstract EntityAIBase createAITask(EntityLiving entityLiving);

    public int getPriority(EntityLiving entityLiving) {
        return priority;
    }
}
