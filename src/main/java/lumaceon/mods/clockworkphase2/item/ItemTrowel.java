package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.block.ITrowelBlock;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemTrowel extends ItemTool
{
    public ItemTrowel(ToolMaterial material, String unlocalizedName)
    {
        super(0, material, null);
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
        this.setHarvestLevel("shovel", material.getHarvestLevel());
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
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
        return true;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block) {
        if(block != null && block.getHarvestTool(block.getDefaultState()) != null && stack != null)
            return block.getHarvestTool(block.getDefaultState()).equals("shovel") ? this.efficiencyOnProperMaterial : 1.0F;
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
