package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.api.EnumExpTier;
import lumaceon.mods.clockworkphase2.api.item.IHourglass;
import lumaceon.mods.clockworkphase2.api.util.HourglassHelper;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * A time generator is an item which generates time every tick. If an active hourglass is available, the time will fill
 * the hourglass.
 */
public class ItemTimeGenerator extends ItemClockworkPhase
{
    public static Random random = new Random();
    /**
     * The time energy to generate per tick. 1 will supply time on a 1-to-1 ratio: 1 second per real second in game.
     */
    public int generationPerTick;

    /**
     * The number of ticks this item will generate time before it breaks.
     * The total time generation of this item is equal to (totalTicks * generationPerTick).
     */
    public int totalTicks;

    /**
     * The minimum xp level required by the player to generate time. Used to ensure the difficulty of the active
     * hourglass is where it should be.
     */
    public EnumExpTier tier;

    public ItemTimeGenerator(int maxStack, int maxDamage, String registryName, int generationPerTick, int totalTicks, EnumExpTier tier) {
        super(maxStack, maxDamage, registryName);
        this.generationPerTick = generationPerTick;
        this.totalTicks = totalTicks;
        this.tier = tier;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        if(NBTHelper.hasTag(is, NBTTags.TIME))
            list.add("Remaining: " + NBTHelper.INT.get(is, NBTTags.TIME)/20);
        else
            list.add("Remaining: " + totalTicks/20);

        String time = TimeConverter.parseNumber(generationPerTick*20, 1).toLowerCase();
        list.add("Generation rate: " + time + " per second");
        String color = player.experienceLevel < tier.minimumXP ? Colors.RED : Colors.GREEN;
        list.add(color + "Required experience level: " + tier.minimumXP);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        int generation = generationPerTick;
        if(entityIn != null && entityIn instanceof EntityPlayer)
        {
            if(((EntityPlayer) entityIn).experienceLevel < tier.minimumXP)
                return; //Make sure the player has enough xp, otherwise we do nothing.

            ItemStack[] items = HourglassHelper.getActiveHourglasses((EntityPlayer) entityIn);
            for(ItemStack is : items)
                if(is != null && is.getItem() instanceof IHourglass && ((IHourglass) is.getItem()).isAcceptingTime(is, ((EntityPlayer) entityIn).experienceLevel, tier))
                {
                    generation -= ((IHourglass)is.getItem()).receiveTime(is, generation, true); //Simulate
                    if(generation < generationPerTick)
                    {
                        generation -= ((IHourglass)is.getItem()).receiveTime(is, generationPerTick, false);
                        break;
                    }
                    else
                        break;
                }

            if(generation < generationPerTick) //If ANY time generated into an hourglass, tick the timer NBT tag.
            {
                long timeLeft;
                if(NBTHelper.hasTag(stack, NBTTags.TIME))
                    timeLeft = NBTHelper.LONG.get(stack, NBTTags.TIME) - 1;
                else
                    timeLeft = totalTicks;

                if(timeLeft <= 0)
                    ((EntityPlayer) entityIn).inventory.setInventorySlotContents(itemSlot, null);
                else
                    NBTHelper.LONG.set(stack, NBTTags.TIME, timeLeft);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem());
    }
}
