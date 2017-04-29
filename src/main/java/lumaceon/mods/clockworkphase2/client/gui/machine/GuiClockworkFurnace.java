package lumaceon.mods.clockworkphase2.client.gui.machine;

import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkFurnace;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiClockworkFurnace extends GuiClockworkMachine
{
    static ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_furnace.png");

    public GuiClockworkFurnace(EntityPlayer player, TileClockworkFurnace te) {
        super(player, new ContainerClockworkFurnace(player, te), 12, 50, BG, te, new HoverableLocation[] { new HoverableLocationEnergy(7, 47, 20, 62, te) });
    }
}
