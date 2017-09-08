package lumaceon.mods.clockworkphase2.item.clockwork.component;

import lumaceon.mods.clockworkphase2.util.Colors;
import lumaceon.mods.clockworkphase2.util.NBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGearParadoxium extends ItemGear
{
    public ItemGearParadoxium(String unlocalizedName, int quality, int speed, int harvestLevel) {
        super(unlocalizedName, quality, speed, harvestLevel);
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(!NBTHelper.hasTag(stack, "quality") || !NBTHelper.hasTag(stack, "speed") || !NBTHelper.hasTag(stack, "tier"))
        {
            tooltip.add(Colors.LIGHT_PURPLE + "Right-click while holding to roll stats.");
            tooltip.add(Colors.WHITE + "Quality: " + Colors.LIGHT_PURPLE + "?");
            tooltip.add(Colors.WHITE + "Speed: " + Colors.LIGHT_PURPLE + "?");
            tooltip.add(Colors.WHITE + "Harvest Level: " + Colors.LIGHT_PURPLE + "?");
        }
        else
            super.addInformation(stack, worldIn, tooltip, flagIn);
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
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        /*if(stack != null && stack.getItem().equals(ModItems.gearParadoxium.getItem()))
            if(!NBTHelper.hasTag(stack, "quality") || !NBTHelper.hasTag(stack, "speed") || !NBTHelper.hasTag(stack, "tier"))
            {
                Random random = ClockworkPhase2.random;
                int quality = random.nextInt(50) + random.nextInt(51) + 1; //1 to 100
                int speed = random.nextInt(50) + random.nextInt(51) + 1; //1 to 100
                int tier = random.nextInt(6); //0 to 5

                NBTHelper.INT.set(stack, "quality", quality);
                NBTHelper.INT.set(stack, "speed", speed);
                NBTHelper.INT.set(stack, "tier", tier);
            }*/
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
        ItemStack is = player.getHeldItem(handIn);
        /*if(is != null && is.getItem().equals(ModItems.gearParadoxium.getItem()))
            if(!NBTHelper.hasTag(is, "quality") || !NBTHelper.hasTag(is, "speed") || !NBTHelper.hasTag(is, "tier"))
            {
                Random random = ClockworkPhase2.random;
                int quality = random.nextInt(50) + random.nextInt(51) + 1; //1 to 100
                int speed = random.nextInt(50) + random.nextInt(51) + 1; //1 to 100
                int tier = random.nextInt(6); //0 to 5

                NBTHelper.INT.set(is, "quality", quality);
                NBTHelper.INT.set(is, "speed", speed);
                NBTHelper.INT.set(is, "tier", tier);
            }*/
        return ActionResult.newResult(EnumActionResult.SUCCESS, is);
    }
}
