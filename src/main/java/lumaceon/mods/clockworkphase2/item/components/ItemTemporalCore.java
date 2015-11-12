package lumaceon.mods.clockworkphase2.item.components;

import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;

import java.util.List;

public class ItemTemporalCore extends ItemClockworkPhase
{
    public ItemTemporalCore(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    /*@Override
    public long getTimeSand(ItemStack item) {
        return TimeSandHelper.getTimeSand(item);
    }

    @Override
    public long getMaxTimeSand(ItemStack item) {
        return TimeConverter.CENTURY;
    }

    @Override
    public void setTimeSand(ItemStack item, EntityPlayer player, long timeSand) {
        TimeSandHelper.setTimeSand(item, timeSand);
    }

    @Override
    public void setTimeSand(ItemStack item, long timeSand) {
        TimeSandHelper.setTimeSand(item, timeSand);
    }

    @Override
    public long addTimeSand(ItemStack item, EntityPlayer player, long amount) {
        return TimeSandHelper.addTimeSand(item, player, amount);
    }

    @Override
    public long addTimeSand(ItemStack item, long amount) {
        return TimeSandHelper.addTimeSand(item, amount);
    }

    @Override
    public long consumeTimeSand(ItemStack item, EntityPlayer player, long amount) {
        return TimeSandHelper.consumeTimeSand(item, player, amount);
    }

    @Override
    public long consumeTimeSand(ItemStack item, long amount) {
        return TimeSandHelper.consumeTimeSand(item, amount);
    }

    @Override
    public ResourceLocation getGlyphTexture(ItemStack item) {
        return Textures.PARTICLE.TIME_SAND;
    }*/
}
