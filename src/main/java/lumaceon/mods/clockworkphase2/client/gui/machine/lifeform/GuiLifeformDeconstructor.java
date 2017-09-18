package lumaceon.mods.clockworkphase2.client.gui.machine.lifeform;

import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkMachine;
import lumaceon.mods.clockworkphase2.inventory.lifeform.ContainerLifeformDeconstructor;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiLifeformDeconstructor extends GuiClockworkMachine
{
    static ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/lifeform_deconstructor.png");

    static String[] INPUT_TT = new String[] {
            "Input Slot",
            Colors.GREY + "Accepts entity containers",
            Colors.GREY + "Accepts entity brains",
            Colors.GREY + "-Items/Entities are deconstructed"
    };

    static String[] OUTPUT_CAPSULE_TT = new String[] {
            "Container Output Slot",
            Colors.GREY + "Does not accept input",
            Colors.GREY + "+Emptied containers output here"
    };

    static String[] OUTPUT_TT = new String[] {
            "Output Slot",
            Colors.GREY + "Does not accept input"
    };

    public GuiLifeformDeconstructor(EntityPlayer player, TileClockworkMachine te)
    {
        super
        (
             player,
             new ContainerLifeformDeconstructor(player, te),
             216, 185,
             8, 19,
             92, 28,
             189, 4,
             189, 25,
             BG,
             te,
             new HoverableLocation[] { new HoverableLocationEnergy(7, 22, 18, 88, te) },
             new IOConfiguration[]
             {
                  new IOConfigurationSlot(te.slots[0], te, INPUT_TT),
                  new IOConfigurationSlot(te.slots[1], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[2], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[3], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[4], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[5], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[6], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[7], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[8], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[9], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[10], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[11], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[12], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[13], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[14], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[15], te, OUTPUT_TT),
                  new IOConfigurationSlot(te.slots[16], te, OUTPUT_CAPSULE_TT)
             }
        );
    }
}
