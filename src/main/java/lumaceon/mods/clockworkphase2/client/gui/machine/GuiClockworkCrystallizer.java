package lumaceon.mods.clockworkphase2.client.gui.machine;

import com.sun.javafx.collections.ImmutableObservableList;
import lumaceon.mods.clockworkphase2.client.gui.GuiHelper;
import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkCrystallizer;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkCrystallizer;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class GuiClockworkCrystallizer extends GuiClockworkMachine
{
    static ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_crystallizer.png");
    static List<String> INPUT_TT1 = new ImmutableObservableList<String>(
            "Input Slot 1",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are crystallized"
    );
    static List<String> INPUT_TT2 = new ImmutableObservableList<String>(
            "Input Slot 2",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are crystallized"
    );
    static List<String> INPUT_TT3 = new ImmutableObservableList<String>(
            "Input Slot 3",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are crystallized"
    );
    static List<String> INPUT_TT4 = new ImmutableObservableList<String>(
            "Input Slot 4",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are crystallized"
    );
    static List<String> INPUT_TT5 = new ImmutableObservableList<String>(
            "Input Slot 5",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are crystallized"
    );
    static List<String> INPUT_TT6 = new ImmutableObservableList<String>(
            "Input Slot 6",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are crystallized"
    );
    static List<String> BUCKET_TT = new ImmutableObservableList<String>(
            "Fluid Insertion Slot",
            Colors.GREY + "Accepts any item which contains fluid",
            Colors.GREY + "-Fluid is inserted to the machine from the item"
    );
    static List<String> TANK_TT = new ImmutableObservableList<String> (
            "Internal Fluid Tank",
            Colors.GREY + "Accepts up to 10 buckets of fluids",
            Colors.GREY + "-Fluid can be used in crystallization"
    );
    static List<String> OUTPUT_TT = new ImmutableObservableList<String> (
            "Crystal Output Slot",
            Colors.GREY + "Does not accept input"
    );

    public GuiClockworkCrystallizer(EntityPlayer player, TileClockworkCrystallizer te)
    {
        super(  player,
                new ContainerClockworkCrystallizer(player, te),
                176, 165,
                10, 6,
                72, 26,
                152, 2,
                152, 24,
                BG,
                te,
                new HoverableLocation[] { new HoverableLocationEnergy(9, 24, 5, 74, te),
                                          new HoverableLocationTank(132, 148, 6, 56, te, 0)
                },
                new IOConfiguration[]
                {
                        new IOConfigurationSlot(te.slots[0], te, INPUT_TT1),
                        new IOConfigurationSlot(te.slots[1], te, INPUT_TT2),
                        new IOConfigurationSlot(te.slots[2], te, INPUT_TT3),
                        new IOConfigurationSlot(te.slots[3], te, INPUT_TT4),
                        new IOConfigurationSlot(te.slots[4], te, INPUT_TT5),
                        new IOConfigurationSlot(te.slots[5], te, INPUT_TT6),
                        new IOConfigurationSlot(te.slots[6], te, BUCKET_TT),
                        new IOConfigurationTank(132, 6, 16, 50, 0, te, TANK_TT),
                        new IOConfigurationSlot(te.slots[7], te, OUTPUT_TT)
                }
        );
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        mc.renderEngine.bindTexture(ICONS);
        int i = guiLeft;
        int j = guiTop;

        if(tileEntity.isOperable(tileEntity.getEnergyCostPerTick()) && tileEntity.canWork())
        {
            this.drawTexturedModalRect(i + 80, j + 61, 32, 0, 16, 16);
        }

        if(tileEntity.fluidTanks.length > 0)
        {
            FluidStack fs = tileEntity.fluidTanks[0].getFluid();
            if(fs != null)
            {
                GuiHelper.drawFluidBar(i+132, j+6, zLevel, 16, 50, tileEntity.fluidTanks[0].getCapacity(), fs.amount, fs, mc);
            }

            mc.renderEngine.bindTexture(Textures.GUI.TANK_LINES_10K);
            GuiHelper.drawTexturedModalRectStretched(i+132, j+6, zLevel, 16, 50);
        }
    }
}
