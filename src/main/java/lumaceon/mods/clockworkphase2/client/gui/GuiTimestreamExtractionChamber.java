package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.api.crafting.timestream.ITimestreamCraftingRecipe;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRegistry;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.client.gui.pane.*;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTimestreamRecipe;
import lumaceon.mods.clockworkphase2.tile.TileTimestreamExtractionChamber;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiTimestreamExtractionChamber extends GuiScreen
{
    private TileEntity te;

    public int backgroundWidth, backgroundHeight;
    public Pane basePane = new Pane(mc);
    public PaneBorder borderPane = new PaneBorder(mc);
    public PaneComponent top = new PaneComponent(mc);
    public PaneComponent bottom = new PaneComponent(mc);
    public PaneFadeChange fader = new PaneFadeChange(mc);
    public PaneButtonWheel buttonWheel = new PaneButtonWheel(mc);

    public PaneComponent[] recipeButtons = new PaneComponent[TimestreamCraftingRegistry.TIMESTREAM_RECIPES.size()];

    public GuiTimestreamExtractionChamber(TileEntity te)
    {
        this.te = te;
        backgroundWidth = 1920;
        backgroundHeight = 1018;
        borderPane.spacingTop = 0.2F;
        borderPane.spacingBottom = 0.05F;
        top.ANCHOR = EnumSide.TOP;
        bottom.ANCHOR = EnumSide.BOTTOM;
        buttonWheel.ANCHOR = EnumSide.TOP;
        if(TimestreamCraftingRegistry.TIMESTREAM_RECIPES.size() > 0)
        {
            PaneComponent startBackground = new PaneComponent(mc);
            startBackground.setTexture(TimestreamCraftingRegistry.TIMESTREAM_RECIPES.get(0).getBackground());
            startBackground.setTextureProportions(backgroundWidth, backgroundHeight);
            startBackground.setRenderType(EnumRenderType.FILL);
            fader.addComponent(startBackground, width, height);
        }
        for(int n = 0; n < recipeButtons.length; n++)
        {
            PaneComponentTitled component = new PaneComponentTitled(mc);
            ITimestreamCraftingRecipe recipe = TimestreamCraftingRegistry.TIMESTREAM_RECIPES.get(n);
            component.setTexture(recipe.getIcon());
            component.setLocalSize(0.7F, 0.7F);
            component.setTitle(TimeConverter.parseNumber(recipe.getTimeSandRequirement(), 1));
            if(te != null && te instanceof TileTimestreamExtractionChamber && ((TileTimestreamExtractionChamber) te).getTimezone() != null)
                component.setTitleColor(((TileTimestreamExtractionChamber) te).getTimezone().getTimeSand() < recipe.getTimeSandRequirement() ? Color.RED.getRGB() : Color.GREEN.getRGB());
            else
                component.setTitleColor(Color.RED.getRGB());
            recipeButtons[n] = component;
            buttonWheel.addComponent(recipeButtons[n], width, height);
        }
        top.setRenderType(EnumRenderType.STRETCH);
        top.setTexture(Textures.GUI.TS_CRAFT_TOP);
        bottom.setTexture(Textures.GUI.TS_CRAFT_BOTTOM);
        borderPane.addComponent(top, width, height);
        borderPane.addComponent(bottom, width, height);
        borderPane.addComponent(buttonWheel, width, height);
        basePane.addComponent(fader, width, height);
        basePane.addComponent(borderPane, width, height);
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        basePane.draw(width, height, zLevel);
        GL11.glPopMatrix();
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        basePane.update(width, height);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.clear();
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if(p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
            this.mc.thePlayer.closeScreen();
        else if(p_73869_2_ == this.mc.gameSettings.keyBindLeft.getKeyCode())
        {
            buttonWheel.selectPrevious();
            PaneComponent newComponent = new PaneComponent(mc);
            newComponent.setTexture(TimestreamCraftingRegistry.TIMESTREAM_RECIPES.get(buttonWheel.targetIndex).getBackground());
            newComponent.setTextureProportions(backgroundWidth, backgroundHeight);
            newComponent.setRenderType(EnumRenderType.FILL);
            fader.addComponent(newComponent, width, height);
        }
        else if(p_73869_2_ == this.mc.gameSettings.keyBindRight.getKeyCode())
        {
            buttonWheel.selectNext();
            PaneComponent newComponent = new PaneComponent(mc);
            newComponent.setTexture(TimestreamCraftingRegistry.TIMESTREAM_RECIPES.get(buttonWheel.targetIndex).getBackground());
            newComponent.setTextureProportions(backgroundWidth, backgroundHeight);
            newComponent.setRenderType(EnumRenderType.FILL);
            fader.addComponent(newComponent, width, height);
        }
        else if(p_73869_2_ == Keyboard.KEY_RETURN || p_73869_2_ == this.mc.gameSettings.keyBindJump.getKeyCode())
        {
            if(te != null && te instanceof TileTimestreamExtractionChamber)
            {
                PacketHandler.INSTANCE.sendToServer(new MessageTimestreamRecipe(te.xCoord, te.yCoord, te.zCoord, buttonWheel.targetIndex));
                this.mc.thePlayer.closeScreen();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}