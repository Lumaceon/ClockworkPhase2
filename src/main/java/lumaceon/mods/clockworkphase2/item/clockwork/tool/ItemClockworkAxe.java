package lumaceon.mods.clockworkphase2.item.clockwork.tool;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.Set;

public class ItemClockworkAxe extends ItemClockworkTool
{
    private static final Set field_150917_c = Sets.newHashSet(new Block[]{Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN});

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
        return new Material[] { Material.WOOD, Material.PLANTS, Material.VINE, Material.GOURD };
    }
}
