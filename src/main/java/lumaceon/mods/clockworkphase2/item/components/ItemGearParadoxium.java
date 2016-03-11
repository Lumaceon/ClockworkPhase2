package lumaceon.mods.clockworkphase2.item.components;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class ItemGearParadoxium extends ItemGear
{
    public ItemGearParadoxium(String unlocalizedName, int quality, int speed, int harvestLevel) {
        super(unlocalizedName, quality, speed, harvestLevel);
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag)
    {
        if(!NBTHelper.hasTag(is, "quality") || !NBTHelper.hasTag(is, "speed") || !NBTHelper.hasTag(is, "tier"))
        {
            list.add(Colors.LIGHT_PURPLE + "Right-click while holding to roll stats.");
            list.add(Colors.WHITE + "Quality: " + Colors.LIGHT_PURPLE + "?");
            list.add(Colors.WHITE + "Speed: " + Colors.LIGHT_PURPLE + "?");
            list.add(Colors.WHITE + "Harvest Level: " + Colors.LIGHT_PURPLE + "?");
        }
        else
            super.addInformation(is, player, list, flag);
    }

    @Override
    public int getQuality(ItemStack is) {
        return NBTHelper.INT.get(is, "quality");
    }

    @Override
    public int getSpeed(ItemStack is) {
        return NBTHelper.INT.get(is, "speed");
    }

    @Override
    public int getTier(ItemStack is) {
        return NBTHelper.INT.get(is, "tier");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(stack != null && stack.getItem().equals(ModItems.gearParadoxium.getItem()))
            if(!NBTHelper.hasTag(stack, "quality") || !NBTHelper.hasTag(stack, "speed") || !NBTHelper.hasTag(stack, "tier"))
            {
                Random random = ClockworkPhase2.random;
                int quality = random.nextInt(50) + random.nextInt(51) + 1; //1 to 100
                int speed = random.nextInt(50) + random.nextInt(51) + 1; //1 to 100
                int tier = random.nextInt(6); //0 to 5

                NBTHelper.INT.set(stack, "quality", quality);
                NBTHelper.INT.set(stack, "speed", speed);
                NBTHelper.INT.set(stack, "tier", tier);
            }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(stack != null && stack.getItem().equals(ModItems.gearParadoxium.getItem()))
            if(!NBTHelper.hasTag(stack, "quality") || !NBTHelper.hasTag(stack, "speed") || !NBTHelper.hasTag(stack, "tier"))
            {
                Random random = ClockworkPhase2.random;
                int quality = random.nextInt(50) + random.nextInt(51) + 1; //1 to 100
                int speed = random.nextInt(50) + random.nextInt(51) + 1; //1 to 100
                int tier = random.nextInt(6); //0 to 5

                NBTHelper.INT.set(stack, "quality", quality);
                NBTHelper.INT.set(stack, "speed", speed);
                NBTHelper.INT.set(stack, "tier", tier);
            }
        return stack;
    }
}
