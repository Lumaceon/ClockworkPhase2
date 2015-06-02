package lumaceon.mods.clockworkphase2.item.construct;

import com.google.common.collect.Sets;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemClockworkPickaxe extends ItemClockworkTool
{
    private static final Set field_150915_c = Sets.newHashSet(new Block[]{Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});

    public ItemClockworkPickaxe(ToolMaterial material, String unlocalizedName) {
        super(2.0F, material, field_150915_c, unlocalizedName);
    }

    @Override
    public boolean func_150897_b(Block p_150897_1_) {
        return p_150897_1_ == Blocks.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (p_150897_1_ != Blocks.diamond_block && p_150897_1_ != Blocks.diamond_ore ? (p_150897_1_ != Blocks.emerald_ore && p_150897_1_ != Blocks.emerald_block ? (p_150897_1_ != Blocks.gold_block && p_150897_1_ != Blocks.gold_ore ? (p_150897_1_ != Blocks.iron_block && p_150897_1_ != Blocks.iron_ore ? (p_150897_1_ != Blocks.lapis_block && p_150897_1_ != Blocks.lapis_ore ? (p_150897_1_ != Blocks.redstone_ore && p_150897_1_ != Blocks.lit_redstone_ore ? (p_150897_1_.getMaterial() == Material.rock ? true : (p_150897_1_.getMaterial() == Material.iron ? true : p_150897_1_.getMaterial() == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
    }

    @Override
    public float func_150893_a(ItemStack is, Block block)
    {
        float efficiency = block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil && block.getMaterial() != Material.rock ? super.func_150893_a(is, block) : this.efficiencyOnProperMaterial;
        if(efficiency == 1.0F)
            return efficiency;

        int tension = NBTHelper.INT.get(is, NBTTags.CURRENT_TENSION);
        if(tension <= 0)
            return 1.0F;

        if(isTemporal(is) && getTimeSand(is) <= 0)
            return 1.0F;

        int speed = NBTHelper.INT.get(is, NBTTags.SPEED);
        if(speed <= 0)
            return 1.0F;

        return (float) speed / 25;
    }

    @Override
    public void setHarvestLevels(ItemStack item, int harvestLevel) {
        NBTHelper.INT.set(item, NBTTags.HARVEST_LEVEL_PICKAXE, harvestLevel);
    }
}
