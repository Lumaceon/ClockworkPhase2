package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.EntityStack;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.item.IHourglass;
import lumaceon.mods.clockworkphase2.api.util.HourglassHelper;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.capabilities.activatable.IActivatableHandler;
import lumaceon.mods.clockworkphase2.capabilities.entitycontainer.IEntityContainer;
import lumaceon.mods.clockworkphase2.capabilities.stasis.IStasis;
import lumaceon.mods.clockworkphase2.capabilities.stasis.stasisitem.IStasisItemHandler;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.entity.EntityTemporalFishHook;
import lumaceon.mods.clockworkphase2.init.ModBiomes;
import lumaceon.mods.clockworkphase2.init.ModFluids;
import lumaceon.mods.clockworkphase2.item.mob.ItemMobCapsule;
import lumaceon.mods.clockworkphase2.util.Colors;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemToolUpgradeTemporalInfuser;
import lumaceon.mods.clockworkphase2.item.temporal.excavator.ItemTemporalExcavator;
import lumaceon.mods.clockworkphase2.util.ExperienceHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerHandler
{
    @CapabilityInject(ITemporalToolbeltHandler.class)
    public static final Capability<ITemporalToolbeltHandler> TEMPORAL_TOOLBELT = null;

    @CapabilityInject(IStasis.class)
    public static final Capability<IStasis> STASIS_CAPABILITY = null;

    @CapabilityInject(IStasisItemHandler.class)
    public static final Capability<IStasisItemHandler> STASIS_ITEM_CAPABILITY = null;

    @CapabilityInject(IActivatableHandler.class)
    public static final Capability<IActivatableHandler> ACTIVATABLE_CAPABILITY = null;

    @CapabilityInject(ITimeStorage.class)
    public static final Capability<ITimeStorage> TIME_STORAGE_CAPABILITY = null;

    @CapabilityInject(IEntityContainer.class)
    public static final Capability<IEntityContainer> ENTITY_CONTAINER = null;

    /**
     * Copies data to the new player when respawning after death.
     */
    @SubscribeEvent
    public void onPlayerRespawning(PlayerEvent.Clone event)
    {
        if(event.isWasDeath())
        {
            EntityPlayer original = event.getOriginal();
            EntityPlayer newPlayer = event.getEntityPlayer();
            ITemporalToolbeltHandler temporalToolbeltHandler = original.getCapability(TEMPORAL_TOOLBELT, EnumFacing.DOWN);
            ITemporalToolbeltHandler newTemporalToolbeltHandler = newPlayer.getCapability(TEMPORAL_TOOLBELT, EnumFacing.DOWN);
            if(temporalToolbeltHandler != null && newTemporalToolbeltHandler != null)
            {
                newTemporalToolbeltHandler.setRowCount(temporalToolbeltHandler.getRowCount());
                for(int i = 0; i < temporalToolbeltHandler.getRowCount(); i++)
                {
                    newTemporalToolbeltHandler.setRow(i, temporalToolbeltHandler.getRow(i));
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerUpdateTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase.equals(TickEvent.Phase.START))
            return; //This event is called twice. Only work during the "end" phase.

        EntityPlayer player = event.player;

        //Find first hourglass in the inventory and update it if found (and xp is high enough).
        int playerXP = player.experienceLevel;
        if(playerXP >= ConfigValues.HOURGLASS_XP_LEVEL_TIER_1)
        {
            if(player.world != null)
            {
                for(int i = 0; i < player.inventory.getSizeInventory(); i++)
                {
                    ItemStack temp = player.inventory.getStackInSlot(i);
                    if(temp != null && temp.getItem() instanceof IHourglass && ((IHourglass) temp.getItem()).isActive(temp))
                    {
                        if(((IHourglass) temp.getItem()).generateTime(temp, player))
                        {
                            break;
                        }
                    }
                }
            }
        }

        //Check for a fishing hook entity, check if it's currently in a timestream block, change it to a custom hook.
        if(player.world != null && player.fishEntity != null && !(player.fishEntity instanceof EntityTemporalFishHook))
        {
            IBlockState whereHookIs = player.world.getBlockState(player.fishEntity.getPosition());
            IBlockState whereHookIsOver = player.world.getBlockState(player.fishEntity.getPosition().down());

            if(whereHookIs.getBlock().equals(ModFluids.TIMESTREAM.getBlock()) || whereHookIsOver.getBlock().equals(ModFluids.TIMESTREAM.getBlock()))
            {
                EntityFishHook oldFishHook = player.fishEntity;

                if(!player.world.isRemote)
                {
                    EntityTemporalFishHook newHook = new EntityTemporalFishHook(player.world, player);
                    newHook.setPosition(oldFishHook.posX, oldFishHook.posY, oldFishHook.posZ);
                    newHook.motionX = oldFishHook.motionX;
                    newHook.motionY = oldFishHook.motionY;
                    newHook.motionZ = oldFishHook.motionZ;
                    player.world.spawnEntity(newHook);
                    oldFishHook.setDead();
                    player.fishEntity = newHook;
                }
            }
        }

        //Check for stasis capability and update it if one is found (which should be pretty much always).
        IStasis stasisCap = player.getCapability(STASIS_CAPABILITY, EnumFacing.DOWN);
        if(stasisCap != null)
        {
            stasisCap.onUpdate(player);
        }

        //Check the player's position and apply negative effects if it's inside the temporal fallout biome.
        //The exception being when they have enough time to consume from hourglasses.
        BlockPos pos = player.getPosition();
        Biome biome = player.world.getBiome(pos);
        if(biome != null && biome == ModBiomes.temporalFallout)
        {
            if(player.world.getTotalWorldTime() % 20 == 0)
            {
                ItemStack[] hourglasses = HourglassHelper.getHourglasses(player);
                if(!HourglassHelper.consumeTimeAllOrNothing(hourglasses, TimeConverter.SECOND))
                {
                    if(!player.world.isRemote)
                    {
                        //Failed to consume time, wreck the player up.
                        if(player.isCreative())
                        {
                            player.attackEntityFrom(new DamageSource("temporal").setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode(), 2.0F);
                        }
                        else
                        {
                            player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 40, 2));
                            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 40, 4));
                            player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 40, 3));
                            player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 40, 3));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightclickEntity(PlayerInteractEvent.EntityInteract event)
    {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack heldItem = player.inventory.getCurrentItem();
        if(!event.getWorld().isRemote && heldItem != null && heldItem.getItem() instanceof ItemMobCapsule)
        {
            if(heldItem.hasCapability(ENTITY_CONTAINER, EnumFacing.DOWN))
            {
                Entity e = event.getTarget();
                if(e != null && !e.isDead)
                {
                    IEntityContainer cap = heldItem.getCapability(ENTITY_CONTAINER, EnumFacing.DOWN);
                    if(cap != null)
                    {
                        if(cap.addEntity(new EntityStack(event.getTarget().serializeNBT())))
                        {
                            event.getTarget().setDead();
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;

            DamageSource source = event.getSource();
            float amount = event.getAmount();

            //Check for a stasis item and attempt a stasis attack if one is found (ignoring absolute hits).
            if(!source.isDamageAbsolute() && !source.canHarmInCreative() && !source.damageType.equals("stasis"))
            {
                IStasis stasis = player.getCapability(STASIS_CAPABILITY, EnumFacing.DOWN);
                if(stasis != null)
                {
                    ItemStack[] hourglasses = HourglassHelper.getHourglasses(player);
                    long availableTime = 0;
                    for(ItemStack hg : hourglasses)
                    {
                        ITimeStorage timeStorage = hg.getCapability(TIME_STORAGE_CAPABILITY, EnumFacing.DOWN);
                        if(timeStorage != null)
                        {
                            availableTime += timeStorage.getTimeInTicks();
                        }
                    }

                    ItemStack is;
                    for(int i = 0; i < player.inventory.getSizeInventory(); i++)
                    {
                        is = player.inventory.getStackInSlot(i);
                        if(is.hasCapability(STASIS_ITEM_CAPABILITY, EnumFacing.DOWN))
                        {
                            IStasisItemHandler stasisItemCap = is.getCapability(STASIS_ITEM_CAPABILITY, EnumFacing.DOWN);
                            IActivatableHandler activatableHandler = is.getCapability(ACTIVATABLE_CAPABILITY, EnumFacing.DOWN);
                            if(activatableHandler == null || activatableHandler.getActive())
                            {
                                if(stasisItemCap != null)
                                {
                                    long timeOffset = stasisItemCap.getTimeToOffset();
                                    int xpCost = (int) (timeOffset * amount * 0.01);
                                    if(availableTime >= timeOffset && player.experienceTotal > xpCost)
                                    {
                                        stasis.addStasisAttack(source, amount, stasis.getStasisTime() + timeOffset, xpCost);
                                        HourglassHelper.consumeTimeMostPossible(hourglasses, timeOffset);
                                        ExperienceHelper.addPlayerXP(player, -xpCost);
                                        event.setCanceled(true);
                                        player.hurtResistantTime = 20;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void breakSpeedModification(PlayerEvent.BreakSpeed event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if(player != null)
        {
            ItemStack currentItem = player.inventory.getCurrentItem();
            if(!currentItem.isEmpty() && currentItem.getItem() instanceof ItemTemporalExcavator)
            {
                ItemTemporalExcavator.ItemStackHandlerTemporalExcavator inventory = ((ItemTemporalExcavator) currentItem.getItem()).getInventoryHandler(currentItem);
                if(inventory != null)
                {
                    for(int n = 3; n < inventory.getSlots(); n++)
                    {
                        ItemStack component = inventory.getStackInSlot(n);
                        if(!component.isEmpty() && component.getItem() instanceof ItemToolUpgradeTemporalInfuser)
                            if(((ItemToolUpgradeTemporalInfuser) component.getItem()).getActive(component, currentItem))
                            {
                                ItemStack[] hourglasses = HourglassHelper.getHourglasses(player);
                                if(HourglassHelper.getTimeFromHourglasses(hourglasses) < HourglassHelper.getTimeToBreakBlock(event.getEntity().world, event.getPos(), event.getState(), event.getEntityPlayer(), currentItem))
                                    event.setCanceled(true);
                            }

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTooltipGet(ItemTooltipEvent event) {
        if(!event.isCanceled() && event.getItemStack().getItem() instanceof IAssemblable)
            event.getToolTip().add(Colors.AQUA + "~Assembly Item~");
    }
}
