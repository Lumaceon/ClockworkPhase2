package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.client.gui.components.GuiButtonItem;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageToolUpgradeActivate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class GuiTemporalExcavatorUpgrades extends GuiScreen
{
    public ItemStack[] items;
    public RenderItem itemRenders;
    public int guiLeft, guiTop, xSize, ySize;

    public GuiTemporalExcavatorUpgrades(ItemStack[] itemStacks) {
        super();
        itemRenders = Minecraft.getMinecraft().getRenderItem();
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
            if(items.length > index && items[index] != null)
            {
                if(items[index].getItem() instanceof IToolUpgrade)
                {
                    buttonList.add(new GuiButtonItem(items[index], index, guiLeft + (x % 10) * 30, guiTop + 15, "", itemRenders, fontRenderer, ((IToolUpgrade) items[index].getItem()).getActive(items[index], this.mc.player.inventory.getCurrentItem())));
                }
            }
            else
            {
                buttonList.add(new GuiButtonItem(null, index, guiLeft + (x % 10) * 30, guiTop + 15, "", itemRenders, fontRenderer, false));
            }
            index++;
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        PacketHandler.INSTANCE.sendToServer(new MessageToolUpgradeActivate(button.id));
        ((GuiButtonItem) buttonList.get(button.id)).active = !((GuiButtonItem) buttonList.get(button.id)).active;
        /*ItemStack itemClicked = items[button.id];
        if(itemClicked != null && itemClicked.getItem() instanceof IToolUpgrade)
        {
            ((IToolUpgrade) itemClicked.getItem()).setActive(itemClicked, mc.thePlayer.inventory.getCurrentItem(), ((GuiButtonItem) buttonList.get(button.id)).active);
        }*/
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
        if(p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
            this.mc.player.closeScreen();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
