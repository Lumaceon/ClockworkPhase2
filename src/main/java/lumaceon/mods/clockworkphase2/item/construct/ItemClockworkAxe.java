package lumaceon.mods.clockworkphase2.item.construct;

import com.google.common.collect.Sets;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import java.util.Set;

public class ItemClockworkAxe extends ItemClockworkTool
{
    private static final Set field_150917_c = Sets.newHashSet(new Block[]{Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});

    public ItemClockworkAxe(ToolMaterial material, String unlocalizedName) {
        super(3.0F, material, field_150917_c, unlocalizedName);
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    public float func_150893_a(ItemStack is, Block block)
    {
        float efficiency = block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine ? super.func_150893_a(is, block) : this.efficiencyOnProperMaterial;
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
        NBTHelper.INT.set(item, NBTTags.HARVEST_LEVEL_AXE, harvestLevel);
    }
}
