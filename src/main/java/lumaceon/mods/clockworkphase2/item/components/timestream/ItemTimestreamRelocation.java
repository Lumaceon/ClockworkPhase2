package lumaceon.mods.clockworkphase2.item.components.timestream;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.item.timestream.ITimezoneTimestream;
import lumaceon.mods.clockworkphase2.api.item.timestream.IToolTimestreamPassive;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.TimestreamHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class ItemTimestreamRelocation extends ItemClockworkPhase implements IToolTimestreamPassive, ITimezoneTimestream
{
    public ItemTimestreamRelocation(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof IInventory)
        {
            NBTHelper.INT.set(is, "cp_x", x);
            NBTHelper.INT.set(is, "cp_y", y);
            NBTHelper.INT.set(is, "cp_z", z);
            NBTHelper.INT.set(is, "cp_side", meta);
            if(!world.isRemote)
                player.addChatComponentMessage(new ChatComponentText(Colors.AQUA + "Inventory location saved"));
            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addTimestreamInformation(is, player, list, getQualityMultiplier(is), getSpeedMultiplier(is), getMemoryMultiplier(is));
    }

    @Override
    public boolean isEnabled(ItemStack item) {
        return TimestreamHelper.isEnabled(item);
    }

    @Override
    public long getTimeSandCostPerApplication(ItemStack item) {
        return TimeConverter.MINUTE;
    }

    @Override
    public long getTimeSandCostPerBlock(ItemStack item) {
        return 0;
    }

    @Override
    public float getQualityMultiplier(ItemStack item) {
        return 1.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack item) {
        return 1.0F;
    }

    @Override
    public float getMemoryMultiplier(ItemStack item) {
        return 1.0F;
    }

    @Override
    public int getMagnitude(ItemStack item) {
        return TimestreamHelper.getMagnitude(item);
    }

    @Override
    public void setMagnitude(ItemStack item, int magnitude) {
        TimestreamHelper.setMagnitude(item, magnitude);
    }

    @Override
    public int getColorRed(ItemStack item) {
        return 210;
    }

    @Override
    public int getColorGreen(ItemStack item) {
        return 0;
    }

    @Override
    public int getColorBlue(ItemStack item) {
        return 230;
    }

    @Override
    public void addTimestreamInformation(ItemStack item, EntityPlayer player, List list)
    {

    }

    @Override
    public ResourceLocation getGlyphTexture(ItemStack item) {
        return Textures.PARTICLE.TIME_SAND;
    }
}