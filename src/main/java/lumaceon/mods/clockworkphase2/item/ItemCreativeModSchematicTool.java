package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.util.Area;
import lumaceon.mods.clockworkphase2.util.SchematicUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemCreativeModSchematicTool extends ItemClockworkPhase
{
    public ItemCreativeModSchematicTool(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
        this.setCreativeTab(null);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3)
    {
        if(player.isSneaking())
        {
            NBTHelper.INT.set(is, "x1", x);
            NBTHelper.INT.set(is, "y1", y);
            NBTHelper.INT.set(is, "z1", z);
            player.addChatComponentMessage(new ChatComponentText("Position 1: (X " + x + " | Y " + y + " | Z " + z + ")"));
            player.addChatComponentMessage(new ChatComponentText("Position 2: (X " + NBTHelper.INT.get(is, "x2") + " | Y " + NBTHelper.INT.get(is, "y2") + " | Z " + NBTHelper.INT.get(is, "z2") + ")"));
        }
        else
        {
            NBTHelper.INT.set(is, "x2", x);
            NBTHelper.INT.set(is, "y2", y);
            NBTHelper.INT.set(is, "z2", z);
            player.addChatComponentMessage(new ChatComponentText("Position 1: (X " + NBTHelper.INT.get(is, "x1") + " | Y " + NBTHelper.INT.get(is, "y1") + " | Z " + NBTHelper.INT.get(is, "z1") + ")"));
            player.addChatComponentMessage(new ChatComponentText("Position 2: (X " + x + " | Y " + y + " | Z " + z + ")"));
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            Area area = new Area(NBTHelper.INT.get(item, "x1"), NBTHelper.INT.get(item, "y1"), NBTHelper.INT.get(item, "z1"), NBTHelper.INT.get(item, "x2"), NBTHelper.INT.get(item, "y2"), NBTHelper.INT.get(item, "z2"));
            SchematicUtility.INSTANCE.createModSchematic(area, (short) 0, "NewSchematic");
            player.addChatComponentMessage(new ChatComponentText("Created new schematic with a block size of (" + area.getBlockCount() + ")"));
        }
        return item;
    }
}
