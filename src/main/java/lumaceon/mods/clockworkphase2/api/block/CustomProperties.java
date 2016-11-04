package lumaceon.mods.clockworkphase2.api.block;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collections;

public class CustomProperties
{
    //These should NEVER be changed.
    public static PropertyDirection FACING_HORIZONTAL;

    public static void init()
    {
        ArrayList<EnumFacing> directions = new ArrayList<EnumFacing>();
        Collections.addAll(directions, EnumFacing.HORIZONTALS);
        FACING_HORIZONTAL = PropertyDirection.create("horizontal_facing", directions);
    }
}
