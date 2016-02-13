package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemTrowel extends ItemTool
{
    public ItemTrowel(ToolMaterial material, String unlocalizedName)
    {
        super(0, material, null);
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
        this.setHarvestLevel("trowel", material.getHarvestLevel());
    }

    @Override
    public float getStrVsBlock(ItemStack stack, Block block) {
        return block.getHarvestTool(block.getDefaultState()).equals("trowel") ? this.efficiencyOnProperMaterial : 1.0F;
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
