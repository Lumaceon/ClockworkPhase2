package lumaceon.mods.clockworkphase2.capabilities.custombehavior;

import lumaceon.mods.clockworkphase2.ai.AIFactory;
import lumaceon.mods.clockworkphase2.ai.CustomAIs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class CustomBehaviorHandler implements ICustomBehavior
{
    AIFactory[] tasks = null;
    AIFactory[] targets = null;

    @Nullable
    @Override
    public AIFactory[] getMainTasks(EntityLiving entityToOverride) {
        return tasks;
    }

    @Nullable
    @Override
    public AIFactory[] getTargetTasks(EntityLiving entityToOverride) {
        return targets;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        if(tasks != null)
        {
            NBTTagList list = new NBTTagList();
            for(AIFactory task : tasks)
            {
                if(task != null)
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setString("id", task.uniqueID);
                    list.appendTag(tag);
                }
            }
            nbt.setTag("tasks", list);
        }

        if(targets != null)
        {
            NBTTagList list = new NBTTagList();
            for(AIFactory target : targets)
            {
                if(target != null)
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setString("id", target.uniqueID);
                    list.appendTag(tag);
                }
            }
            nbt.setTag("targets", list);
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound base)
    {
        if(base.hasKey("tasks"))
        {
            NBTTagList list = base.getTagList("tasks", Constants.NBT.TAG_COMPOUND);
            this.tasks = new AIFactory[list.tagCount()];
            for(int i = 0; i < list.tagCount(); i++)
            {
                NBTTagCompound tag = list.getCompoundTagAt(i);
                String id = tag.getString("id");
                AIFactory aiFactory = CustomAIs.getCustomAIFromID(id);
                if(aiFactory != null)
                {
                    this.tasks[i] = aiFactory;
                }
            }
        }

        if(base.hasKey("targets"))
        {
            NBTTagList list = base.getTagList("targets", Constants.NBT.TAG_COMPOUND);
            this.targets = new AIFactory[list.tagCount()];
            for(int i = 0; i < list.tagCount(); i++)
            {
                NBTTagCompound tag = list.getCompoundTagAt(i);
                String id = tag.getString("id");
                AIFactory aiFactory = CustomAIs.getCustomAIFromID(id);
                if(aiFactory != null)
                {
                    this.targets[i] = aiFactory;
                }
            }
        }
    }
}
