package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.HourglassDropRegistry;
import lumaceon.mods.clockworkphase2.api.IPhaseEntity;
import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.CapabilityAchievementScore;
import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.IAchievementScoreHandler;
import lumaceon.mods.clockworkphase2.api.item.IHourglass;
import lumaceon.mods.clockworkphase2.api.util.HourglassHelper;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageAchievementScore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class EntityHandler
{
    public static Random random = ClockworkPhase2.random;
    @SubscribeEvent
    public void onEntityItemDrop(LivingDropsEvent event) //Drop special items.
    {
        if(event.getSource() != null)
        {
            Entity entitySource = event.getSource().getSourceOfDamage();
            Entity entity = event.getEntity();
            if(entity != null && entitySource != null && entitySource instanceof EntityPlayer && entity instanceof IPhaseEntity)
            {
                EntityPlayer player = (EntityPlayer) entitySource;
                IPhaseEntity phaseEntity = (IPhaseEntity) entity;
                if(random == null)
                    random = new Random();
                int chance;
                switch(phaseEntity.getTier())
                {
                    case TEMPORAL: //Doesn't drop at temporal tier.
                        break;
                    case ETHEREAL: //1 in 1000
                        chance = ConfigValues.BASE_ETHEREAL_TIMEFRAME_KEY_DROP_RATE;
                        if(chance <= 1 || chance <= event.getLootingLevel() + 1 || random.nextInt(chance / (event.getLootingLevel() + 1)) == 0)
                            event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, HourglassDropRegistry.getRandomTimeframeKeyDrop()));
                        break;
                    case PHASIC: //1 in 50
                        chance = ConfigValues.BASE_PHASIC_TIMEFRAME_KEY_DROP_RATE;
                        if(chance <= 1 || chance <= event.getLootingLevel() + 1 || random.nextInt(chance / (event.getLootingLevel() + 1)) == 0)
                            event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, HourglassDropRegistry.getRandomTimeframeKeyDrop()));
                        break;
                    case ETERNAL: //1 in 10
                        chance = ConfigValues.BASE_ETERNAL_TIMEFRAME_KEY_DROP_RATE;
                        if(chance <= 1 || chance <= event.getLootingLevel() + 1 || random.nextInt(chance / (event.getLootingLevel() + 1)) == 0)
                            event.getDrops().add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, HourglassDropRegistry.getRandomTimeframeKeyDrop()));
                        break;
                    case ASCENDANT: //Doesn't drop at ascendant tier either.
                        break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if(player.hasCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN) && !player.worldObj.isRemote)
            {
                IAchievementScoreHandler achievementScoreHandler = player.getCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN);
                achievementScoreHandler.calculateTier(player);
                PacketHandler.INSTANCE.sendTo(new MessageAchievementScore(achievementScoreHandler.getAchievementPoints()), (EntityPlayerMP) player);
            }
        }
    }

    /*@SubscribeEvent
    public void onEntityClicked(EntityInteractEvent event) //Player right-clicked an entity.
    {
        if(event.target != null && event.target instanceof EntityPAC) //The entity was a PAC.
            ((EntityPAC) event.target).onRightClicked(event.entityPlayer);
    }*/

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.CheckSpawn event)
    {
        if(!event.getResult().equals(Event.Result.DENY))
        {
            World world = event.getWorld();
            if(world != null && event.getEntityLiving() instanceof IPhaseEntity)
            {
                IPhaseEntity phaseEntity = (IPhaseEntity) event.getEntityLiving();
                EntityPlayer player = world.getClosestPlayer(event.getX(), event.getY(), event.getZ(), 64, true);
                if(player == null)
                {
                    event.setResult(Event.Result.DENY);
                    return;
                }
                ItemStack[] hourglasses = HourglassHelper.getActiveHourglasses(player);
                for(ItemStack stack : hourglasses)
                    if(stack != null && stack.getItem() instanceof IHourglass && ((IHourglass) stack.getItem()).isSpawningPhaseEntities(stack, player.experienceLevel, phaseEntity.getTier()))
                        return; //The entity can spawn, so return and keep default values.
                event.setResult(Event.Result.DENY);
                return; //The entity cannot spawn; the requirements for the IPhaseEntity aren't met, so cancel.
            }
        }
    }
}
