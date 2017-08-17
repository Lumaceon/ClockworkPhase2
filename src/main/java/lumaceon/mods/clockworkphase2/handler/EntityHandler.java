package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.ai.AIFactory;
import lumaceon.mods.clockworkphase2.capabilities.custombehavior.ICustomBehavior;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessagePlayerDataOnWorldJoin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler
{
    @CapabilityInject(ICustomBehavior.class)
    public static final Capability<ICustomBehavior> CUSTOM_BEHAVIOR = null;

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();

        //Update the client of the player with relevant data the player should know.
        if(entity instanceof EntityPlayer)
        {
            if(!event.getWorld().isRemote)
            {
                ITemporalToolbeltHandler toolbelt = entity.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
                if(toolbelt != null)
                {
                    PacketHandler.INSTANCE.sendTo(new MessagePlayerDataOnWorldJoin(toolbelt.getAllRows()), (EntityPlayerMP) entity);
                }
            }
        }

        //Apply custom AI overrides if available.
        if(entity instanceof EntityLiving)
        {
            EntityLiving entityLiving = (EntityLiving) entity;
            ICustomBehavior customBehavior = entityLiving.getCapability(CUSTOM_BEHAVIOR, EnumFacing.DOWN);

            if(customBehavior != null)
            {
                AIFactory[] tasks = customBehavior.getMainTasks(entityLiving);
                AIFactory[] targets = customBehavior.getTargetTasks(entityLiving);

                if(tasks != null)
                {
                    //Remove current AI tasks.
                    for(Object o : entityLiving.tasks.taskEntries.toArray())
                    {
                        if(o != null && o instanceof EntityAITasks.EntityAITaskEntry)
                        {
                            entityLiving.tasks.removeTask(((EntityAITasks.EntityAITaskEntry) o).action);
                        }
                    }

                    //Add in custom AI tasks.
                    for(AIFactory task : tasks)
                    {
                        if(task != null)
                        {
                            entityLiving.tasks.addTask(task.getPriority(entityLiving), task.createAITask(entityLiving));
                        }
                    }
                }

                if(targets != null)
                {
                    //Remove current target tasks.
                    for(Object o : entityLiving.targetTasks.taskEntries.toArray())
                    {
                        if(o != null && o instanceof EntityAITasks.EntityAITaskEntry)
                        {
                            entityLiving.targetTasks.removeTask(((EntityAITasks.EntityAITaskEntry) o).action);
                        }
                    }

                    //Add in custom target tasks.
                    for(AIFactory targetTask : targets)
                    {
                        if(targetTask != null)
                        {
                            entityLiving.tasks.addTask(targetTask.getPriority(entityLiving), targetTask.createAITask(entityLiving));
                        }
                    }
                }
            }
        }
    }
}
