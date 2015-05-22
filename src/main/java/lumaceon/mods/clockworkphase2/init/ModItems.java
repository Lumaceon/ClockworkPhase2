package lumaceon.mods.clockworkphase2.init;

import cpw.mods.fml.common.registry.GameRegistry;
import lumaceon.mods.clockworkphase2.item.components.ItemClockwork;
import lumaceon.mods.clockworkphase2.item.components.ItemMainspring;
import lumaceon.mods.clockworkphase2.item.construct.ItemClockworkTool;
import lumaceon.mods.clockworkphase2.lib.Names;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class ModItems
{
    public static void init()
    {
        initComponents();
        initConstructs();
        initBuckets();
    }

    public static Item mainspring;
    public static Item clockwork;
    public static void initComponents()
    {
        mainspring = new ItemMainspring(1, 20, Names.ITEM.MAINSPRING);
        clockwork = new ItemClockwork(1, 20, Names.ITEM.CLOCKWORK);

        GameRegistry.registerItem(mainspring, Names.ITEM.MAINSPRING);
        GameRegistry.registerItem(clockwork, Names.ITEM.CLOCKWORK);
    }

    public static Item clockworkTool;
    public static void initConstructs()
    {
        clockworkTool = new ItemClockworkTool(0, Item.ToolMaterial.IRON, ItemPickaxe.itemRegistry.getKeys(), Names.ITEM.CLOCKWORK_TOOL);

        GameRegistry.registerItem(clockworkTool, Names.ITEM.CLOCKWORK_TOOL);
    }

    public static Item bucketTimeSand;
    public static void initBuckets()
    {

    }
}
