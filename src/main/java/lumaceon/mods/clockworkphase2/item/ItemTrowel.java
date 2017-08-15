package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.block.ITrowelBlock;
import lumaceon.mods.clockworkphase2.util.ISimpleNamed;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.HashSet;

public class ItemTrowel extends ItemTool implements ISimpleNamed
{
    String simpleName;

    public ItemTrowel(ToolMaterial material, String name)
    {
        super(0, 1, material, new HashSet<Block>());
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.simpleName = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHarvestLevel("shovel", material.getHarvestLevel());
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(stack != null && stack.getItem() instanceof ItemTrowel)
        {
            Block block = worldIn.getBlockState(pos).getBlock();
            if(block instanceof ITrowelBlock)
            {
                ((ITrowelBlock) block).onTrowelRightClick(stack, worldIn, pos, toolMaterial.getHarvestLevel());
                if(stack.isItemStackDamageable())
                    stack.damageItem(1, player);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        if(state != null && state.getBlock() != null && state.getBlock().getHarvestTool(state) != null && stack != null)
            return state.getBlock().getHarvestTool(state).equals("shovel") ? this.efficiencyOnProperMaterial : 1.0F;
        return 1.0F;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.translateToLocal(this.getUnlocalizedName(stack));
    }

    @Override
    public String getUnlocalizedName() {
        return this.getRegistryName().toString();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName();
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }
}
