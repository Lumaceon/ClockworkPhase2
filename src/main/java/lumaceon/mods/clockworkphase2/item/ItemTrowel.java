package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.block.ITrowelBlock;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

public class ItemTrowel extends ItemTool
{
    public ItemTrowel(ToolMaterial material, String registryName)
    {
        super(0, 1, material, new HashSet<Block>());
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setRegistryName(registryName);
        this.setUnlocalizedName(registryName);
        this.setHarvestLevel("shovel", material.getHarvestLevel());
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(stack != null && stack.getItem() instanceof ItemTrowel)
        {
            Block block = worldIn.getBlockState(pos).getBlock();
            if(block instanceof ITrowelBlock)
            {
                ((ITrowelBlock) block).onTrowelRightClick(stack, worldIn, pos, toolMaterial.getHarvestLevel());
                if(stack.isItemStackDamageable())
                    stack.damageItem(1, playerIn);
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
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack is) {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }
}
