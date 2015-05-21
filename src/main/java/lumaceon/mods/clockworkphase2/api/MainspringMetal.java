package lumaceon.mods.clockworkphase2.api;

import net.minecraft.item.ItemStack;

public class MainspringMetal
{
    public ItemStack metal = null;
    public String metalName = null;
    public int metalValue;

    public MainspringMetal(String metalName, int metalValue)
    {
        this.metalName = metalName;
        this.metalValue = metalValue;
    }

    public MainspringMetal(ItemStack metal, int metalValue)
    {
        this.metal = metal;
        this.metalValue = metalValue;
    }
}
