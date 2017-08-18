package lumaceon.mods.clockworkphase2.client.gui.machine.lifeform;

import com.sun.javafx.collections.ImmutableObservableList;
import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkMachine;
import lumaceon.mods.clockworkphase2.inventory.lifeform.ContainerLifeformConstructor;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformConstructor;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class GuiLifeformConstructor extends GuiClockworkMachine
{
    static ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/lifeform_constructor.png");
    static List<String> INPUT_TT = new ImmutableObservableList<>(
            "Input Slot",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are consumed to construct entities"
    );
    static List<String> INPUT_CAPSULE = new ImmutableObservableList<>(
            "Input Slot",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are consumed to construct entities"
    );
    static List<String> OUTPUT_TT = new ImmutableObservableList<> (
            "Crusher Output Slot",
            Colors.GREY + "Does not accept input"
    );

    private float oldMouseX;

    public GuiLifeformConstructor(EntityPlayer player, TileClockworkMachine te) {
        super
        (
                player,
                new ContainerLifeformConstructor(player, te),
                216, 165,
                8, 7,
                92, 26,
                189, 5,
                189, 27,
                BG,
                te,
                new HoverableLocation[] { new HoverableLocationEnergy(7, 22, 6, 76, te) },
                new IOConfiguration[]
                        {
                                new IOConfigurationSlot(te.slots[0], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[1], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[2], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[3], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[4], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[5], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[6], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[7], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[8], te, INPUT_TT),
                                new IOConfigurationSlot(te.slots[9], te, INPUT_CAPSULE),
                                new IOConfigurationSlot(te.slots[10], te, OUTPUT_TT)
                        }
        );
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.oldMouseX = (float)mouseX;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        if(tileEntity != null && tileEntity instanceof TileLifeformConstructor && ((TileLifeformConstructor) tileEntity).activeRecipe != null)
        {
            Entity entityToRender = ((TileLifeformConstructor) tileEntity).activeRecipe.posterChild;
            if(entityToRender instanceof EntityLivingBase)
            {
                if(entityToRender.world == null)
                {
                    entityToRender.world = tileEntity.getWorld();
                }
                GuiInventory.drawEntityOnScreen(this.guiLeft + 157, this.guiTop + 65, 30, (float) (guiLeft + 150) - oldMouseX, -20, (EntityLivingBase) entityToRender);
            }
        }
    }
}
