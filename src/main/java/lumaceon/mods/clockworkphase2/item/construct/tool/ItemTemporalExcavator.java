package lumaceon.mods.clockworkphase2.item.construct.tool;

import com.google.common.collect.Sets;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.util.TimeHelper;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Set;

public class ItemTemporalExcavator extends ItemTool
{
    private static final Set field_150915_c = Sets.newHashSet(new Block[]{Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail, Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium, Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});

    public ItemTemporalExcavator(float p_i45333_1_, ToolMaterial p_i45333_2_, String unlocalizedName)
    {
        super(p_i45333_1_, p_i45333_2_, field_150915_c);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
        this.setHarvestLevel("pickaxe", 3);
        this.setHarvestLevel("axe", 3);
        this.setHarvestLevel("shovel", 3);
    }

    @Override
    public float func_150893_a(ItemStack is, Block block)
    {
        float efficiency = block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil && block.getMaterial() != Material.rock && block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine && block.getMaterial() != Material.grass && block.getMaterial() != Material.ground && block.getMaterial() != Material.web && !block.getMaterial().isToolNotRequired() ? super.func_150893_a(is, block) : this.efficiencyOnProperMaterial;
        if(efficiency == 1.0F)
            return 0F;
        return 1000000000000F;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        return ForgeHooks.isToolEffective(stack, block, meta) ? 1000000000000F : func_150893_a(stack, block);
    }

    public boolean onBlockDestroyed(ItemStack is, World world, Block block, int x, int y, int z, EntityLivingBase entityHolding)
    {
        float blockHardness = block.getBlockHardness(world, x, y, z);
        if(blockHardness != 0.0F && entityHolding instanceof EntityPlayer)
            TimeHelper.consumeTimeMostPossible(((EntityPlayer) entityHolding).inventory, TimeHelper.timeToBreakBlock(blockHardness, (EntityPlayer) entityHolding, is));
        return true;
    }

    @Override
    public boolean func_150897_b(Block p_150897_1_) {
        return (p_150897_1_ == Blocks.snow_layer ? true : p_150897_1_ == Blocks.snow) || //SHOVEL
               (p_150897_1_ == Blocks.obsidian ? this.toolMaterial.getHarvestLevel() == 3 : (p_150897_1_ != Blocks.diamond_block && p_150897_1_ != Blocks.diamond_ore ? (p_150897_1_ != Blocks.emerald_ore && p_150897_1_ != Blocks.emerald_block ? (p_150897_1_ != Blocks.gold_block && p_150897_1_ != Blocks.gold_ore ? (p_150897_1_ != Blocks.iron_block && p_150897_1_ != Blocks.iron_ore ? (p_150897_1_ != Blocks.lapis_block && p_150897_1_ != Blocks.lapis_ore ? (p_150897_1_ != Blocks.redstone_ore && p_150897_1_ != Blocks.lit_redstone_ore ? (p_150897_1_.getMaterial() == Material.rock ? true : (p_150897_1_.getMaterial() == Material.iron ? true : p_150897_1_.getMaterial() == Material.anvil)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2)); //PICKAXE
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack is) {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        this.itemIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
}
