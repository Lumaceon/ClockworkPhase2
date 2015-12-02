package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.client.gui.components.GuiButtonItem;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageToolUpgradeActivate;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class GuiClockworkTool extends GuiScreen
{
    public ItemStack[] items;
    public RenderItem itemRenders;
    public int guiLeft, guiTop, xSize, ySize;

    public GuiClockworkTool(ItemStack[] itemStacks) {
        super();
        itemRenders = new RenderItem();
        if(itemStacks == null)
            itemStacks = new ItemStack[0];
        this.items = itemStacks;
        this.xSize = 300;
        this.ySize = 60;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        buttonList.clear();
        int index = 0;
        for(int x = 0; x < 10; x++)
        {
            for(int y = 0; y < 2; y++)
            {
                if(items.length > index && items[index] != null)
                    buttonList.add(new GuiButtonItem(items[index], index, guiLeft + (x % 10) * 30, guiTop + y * 30, "", itemRenders, fontRendererObj, NBTHelper.BOOLEAN.get(items[index], NBTTags.ACTIVE)));
                else
                    buttonList.add(new GuiButtonItem(null, index, guiLeft + (x % 10) * 30, guiTop + y * 30, "", itemRenders, fontRendererObj, false));
                index++;
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        PacketHandler.INSTANCE.sendToServer(new MessageToolUpgradeActivate(button.id));
        ((GuiButtonItem) buttonList.get(button.id)).active = !((GuiButtonItem) buttonList.get(button.id)).active;
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
        if(p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
            this.mc.thePlayer.closeScreen();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
