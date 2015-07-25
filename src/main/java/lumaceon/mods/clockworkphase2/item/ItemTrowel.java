package lumaceon.mods.clockworkphase2.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.ForgeHooks;

public class ItemTrowel extends ItemTool
{
    public ItemTrowel(ToolMaterial material, int maxStack, String unlocalizedName)
    {
        super(0, material, null);
        this.setMaxStackSize(maxStack);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
        this.setHarvestLevel("trowel", material.getHarvestLevel());
    }

    @Override
    public float func_150893_a(ItemStack p_150893_1_, Block block) {
        return block.getHarvestTool(0).equals("trowel") ? this.efficiencyOnProperMaterial : 1.0F;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta)
    {
        if(ForgeHooks.isToolEffective(stack, block, meta))
            return this.efficiencyOnProperMaterial;
        return func_150893_a(stack, block);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack is)
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry)
    {
        this.itemIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
}
