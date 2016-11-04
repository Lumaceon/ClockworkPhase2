package lumaceon.mods.clockworkphase2.item.temporal;

import lumaceon.mods.clockworkphase2.api.EnumExpTier;
import lumaceon.mods.clockworkphase2.api.item.IHourglass;
import lumaceon.mods.clockworkphase2.api.item.ITimeCompressor;
import lumaceon.mods.clockworkphase2.api.util.HourglassHelper;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class ItemTimeCompressor extends ItemClockworkPhase implements ITimeCompressor
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

    public ItemTimeCompressor(int maxStack, int maxDamage, String registryName, int generationPerTick, int totalTicks, EnumExpTier tier) {
        super(maxStack, maxDamage, registryName);
        this.generationPerTick = generationPerTick;
        this.totalTicks = totalTicks;
        this.tier = tier;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
    {
        list.add("Total seconds of compression: " + (int) (totalTicks * 0.05));
        String time = TimeConverter.parseNumber(generationPerTick*20, 1).toLowerCase();
        list.add("Time compression rate: " + time + " per second");
        String color = player.experienceLevel < tier.minimumXP ? Colors.RED : Colors.GREEN;
        list.add(color + "Required experience level: " + tier.minimumXP);
        list.add("");
        list.add("Right-click to add to an hourglass.");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if(!world.isRemote && player != null)
        {
            if(player.isSneaking())
                return ActionResult.newResult(EnumActionResult.PASS, stack);
            ItemStack[] hourglasses = HourglassHelper.getHourglasses(player);
            if(hourglasses != null)
            {
                for(ItemStack hourglass : hourglasses)
                {
                    if(hourglass.getItem() instanceof IHourglass)
                    {
                        EnumExpTier tier = ((IHourglass) hourglass.getItem()).getTier(hourglass);
                        if(getTier().tierIndex >= tier.tierIndex)
                        {
                            boolean added = ((IHourglass) hourglass.getItem()).addTimeCompressor(hourglass, stack);
                            if(added)
                            {
                                player.inventory.deleteStack(stack);
                                return ActionResult.newResult(EnumActionResult.PASS, stack);
                            }
                        }
                    }
                }
            }
            //if(!world.isRemote)
                player.addChatComponentMessage(new TextComponentString(Colors.RED + "Failed to find an hourglass for this compressor."));
        }
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem());
    }

    @Override
    public EnumExpTier getTier() {
        return tier;
    }

    @Override
    public int getCompressionRate() {
        return generationPerTick;
    }

    @Override
    public int getTotalTicks() {
        return totalTicks;
    }
}
