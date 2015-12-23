package lumaceon.mods.clockworkphase2.item.construct.tool;

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
    public String getHarvestType() {
        return "axe";
    }

    @Override
    public Material[] getEffectiveMaterials() {
        return new Material[] { Material.wood, Material.plants, Material.vine, Material.gourd };
    }

    @Override
    public void setTier(ItemStack item, int tier) {
        super.setTier(item, tier);
        NBTHelper.INT.set(item, NBTTags.HARVEST_LEVEL_AXE, tier);
    }
}
