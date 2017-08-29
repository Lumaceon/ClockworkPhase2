package lumaceon.mods.clockworkphase2.client.gui.machine.lifeform;

import lumaceon.mods.clockworkphase2.client.gui.GuiHelper;
import lumaceon.mods.clockworkphase2.client.gui.components.GuiButtonInvisible;
import lumaceon.mods.clockworkphase2.client.gui.machine.GuiClockworkMachine;
import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkMachine;
import lumaceon.mods.clockworkphase2.inventory.lifeform.ContainerLifeformConstructor;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageEntityConstructorSetRecipe;
import lumaceon.mods.clockworkphase2.recipe.EntityConstructionRecipes;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformConstructor;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiLifeformConstructor extends GuiClockworkMachine
{
    static ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/lifeform_constructor.png");
    static ResourceLocation BG_SELECTION = new ResourceLocation(Reference.MOD_ID, "textures/gui/lifeform_constructor_selection.png");
    static String[] INPUT_TT = new String[]{
            "Input Slot",
            Colors.GREY + "Accepts up to 64 of any item",
            Colors.GREY + "-Items are consumed to construct entities"
    };
    static String[] INPUT_CAPSULE = new String[]{
            "Capsule Input Slot",
            Colors.GREY + "Accepts mob capsules with at least one empty space",
            Colors.GREY + "-Capsule is filled and placed in Capsule Output Slot"
    };
    static String[] OUTPUT_TT = new String[]{
            "Capsule Output Slot",
            Colors.GREY + "Does not accept input",
            Colors.GREY + "+Filled capsules output here"
    };

    private HoverableLocation[] hoverableLocations;

    private float oldMouseX;
    private boolean isSelectingEntity = false;
    private boolean isSelectingEntityStateForNextTick = false; //For swapping values one tick late (otherwise we click things we shouldn't)
    private int page = 0;
    private HashMap<String, EntityConstructionRecipes.EntityConstructionRecipe> entityRecipes;

    public GuiLifeformConstructor(EntityPlayer player, TileClockworkMachine te)
    {
        super
        (
                player,
                new ContainerLifeformConstructor(player, te),
                216, 165,
                8, 7,
                92, 26,
                189, 4,
                189, 25,
                BG,
                te,
                new HoverableLocation[]
                        {
                            new HoverableLocationEnergy(7, 22, 6, 76, te),
                            new HoverableLocationEntityConstruction(132, 182, 6, 76, null, (TileLifeformConstructor) te)
                        },
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

        entityRecipes = EntityConstructionRecipes.INSTANCE.getRecipes();
        hoverableLocations = hoverables;
    }

    @Override
    public void initGui()
    {
        if(isSelectingEntity)
        {
            buttonList.clear();
            int buttonIndex = 0;

            this.buttonList.add(new GuiButton(buttonIndex, this.guiLeft, this.guiTop + (this.ySize / 2) - 10, 20, 20, "<"));
            ++buttonIndex;
            this.buttonList.add(new GuiButton(buttonIndex, this.guiLeft + xSize - 20, this.guiTop + (this.ySize / 2) - 10, 20, 20, ">"));
            ++buttonIndex;

            ArrayList<HoverableLocation> hoverableList = new ArrayList<>(6);
            EntityConstructionRecipes.EntityConstructionRecipe[] recipes = new EntityConstructionRecipes.EntityConstructionRecipe[entityRecipes.size()];
            recipes = entityRecipes.values().toArray(recipes);

            int entityButtonIndex = 0;
            for(int i = page*6; i < entityRecipes.size() && buttonIndex < 8; i++)
            {
                int x = 25 + 57 * (entityButtonIndex%3);
                int y = 5 + 85 * (int) Math.floor(entityButtonIndex / 3);
                this.buttonList.add(new GuiButtonInvisible(buttonIndex, this.guiLeft + x, this.guiTop + y, 52, 70));
                hoverableList.add(new HoverableLocationEntityConstruction(x, x + 52, y, y + 70, recipes[i], null));
                ++buttonIndex;
                ++entityButtonIndex;
            }

            hoverables = new HoverableLocation[hoverableList.size()];
            hoverables = hoverableList.toArray(hoverables);
        }
        else
        {
            super.initGui();
            this.buttonList.add(new GuiButtonInvisible(2, this.guiLeft + 132, this.guiTop + 6, 52, 70));
            hoverables = hoverableLocations;
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(!isSelectingEntity)
        {
            super.actionPerformed(button);
            switch(button.id)
            {
                case 2:
                    isSelectingEntityStateForNextTick = true;
                    break;
            }
        }
        else
        {
            switch(button.id)
            {
                case 0:
                    if(page > 0)
                    {
                        --page;
                    }
                    initGui();
                    break;
                case 1:
                    if(page + 1 < Math.ceil(entityRecipes.size() / 6.0F))
                    {
                        ++page;
                    }
                    initGui();
                    break;
                default:
                    isSelectingEntityStateForNextTick = false;
                    int index = 0;
                    for(EntityConstructionRecipes.EntityConstructionRecipe recipe : entityRecipes.values())
                    {
                        if(recipe != null)
                        {
                            if(index == button.id + (page*6) - 2)
                            {
                                if(tileEntity != null && tileEntity instanceof TileLifeformConstructor)
                                {
                                    ((TileLifeformConstructor) tileEntity).activeRecipe = recipe;
                                    PacketHandler.INSTANCE.sendToServer(new MessageEntityConstructorSetRecipe(tileEntity.getPos(), tileEntity.getWorld().provider.getDimension(), recipe.recipeID));
                                }
                                break;
                            }
                        }

                        ++index;
                    }
                    break;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.oldMouseX = (float)mouseX;
        if(isSelectingEntity != isSelectingEntityStateForNextTick)
        {
            isSelectingEntity = isSelectingEntityStateForNextTick;
            if(isSelectingEntity)
            {
                Container c = this.inventorySlots;
                if(c != null && c instanceof ContainerClockworkMachine)
                {
                    ((ContainerClockworkMachine) c).initializeSlots(player.inventory, true);
                }
            }
            else if(!isInConfigState())
            {
                Container c = this.inventorySlots;
                if(c != null && c instanceof ContainerClockworkMachine)
                {
                    ((ContainerClockworkMachine) c).initializeSlots(player.inventory, false);
                }
            }
            initGui();
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        int xEntityTranslation = 30;
        int yEntityTranslation = 65;
        if(!isSelectingEntity)
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
                    GuiInventory.drawEntityOnScreen(this.guiLeft + xEntityTranslation + 128, this.guiTop + yEntityTranslation + 6, 30, (float) (guiLeft + 158) - oldMouseX, -10, (EntityLivingBase) entityToRender);
                }
            }
        }
        else
        {
            Minecraft.getMinecraft().renderEngine.bindTexture(BG_SELECTION);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GuiHelper.drawTexturedModalRectStretched(this.guiLeft, this.guiTop, zLevel, xSize, ySize);

            int loopIndex = 0;
            int startingIndex = page*6;
            int translatedLoopIndex = 0; //The index since we hit the starting index.
            for(EntityConstructionRecipes.EntityConstructionRecipe recipe : entityRecipes.values())
            {
                if(loopIndex >= startingIndex && translatedLoopIndex < 6 &&  recipe != null)
                {
                    Entity entityToRender = recipe.posterChild;
                    if(entityToRender instanceof EntityLivingBase)
                    {
                        if(entityToRender.world == null)
                        {
                            entityToRender.world = tileEntity.getWorld();
                        }
                        GuiInventory.drawEntityOnScreen((this.guiLeft + 20 + xEntityTranslation) + 57 * (translatedLoopIndex%3), (this.guiTop + 6 + yEntityTranslation) + 85 * (int) Math.floor(translatedLoopIndex / 3), 30, (guiLeft + xSize*0.5F) - oldMouseX, -10, (EntityLivingBase) entityToRender);
                    }
                    ++translatedLoopIndex;
                }
                ++loopIndex;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(!isSelectingEntity)
        {
            super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        }
        else
        {
            for(HoverableLocation h : hoverables)
            {
                if(h != null && mouseX >= this.guiLeft + h.getMinX() && mouseX <= this.guiLeft + h.getMaxX() && mouseY >= this.guiTop + h.getMinY() && mouseY <= this.guiTop + h.getMaxY())
                {
                    this.renderToolTip(h.getTooltip(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)), mouseX - this.guiLeft, mouseY - this.guiTop);
                    break;
                }
            }
        }
    }

    public static class HoverableLocationEntityConstruction extends HoverableLocation
    {
        public EntityConstructionRecipes.EntityConstructionRecipe recipe;
        public TileLifeformConstructor te;

        public HoverableLocationEntityConstruction(int minX, int maxX, int minY, int maxY, EntityConstructionRecipes.EntityConstructionRecipe entityConstructionRecipe, TileLifeformConstructor te) {
            super(minX, maxX, minY, maxY);
            this.recipe = entityConstructionRecipe;
            this.te = te;
        }

        @Override
        public List<String> getTooltip(boolean isShiftDown)
        {
            if(recipe != null)
            {
                String name = recipe.posterChild.getDisplayName().getFormattedText();
                ArrayList<String> ret = new ArrayList<>();
                ret.add(name);
                ret.add("");
                ret.add(Colors.GOLD + "Required Items:");

                ItemStack is;
                for(int i = 0; i < recipe.inputItems.size(); i++)
                {
                    is = recipe.inputItems.get(i);
                    String line = is.getDisplayName() + " (" + is.getCount() + ")";
                    ret.add(line);
                }
                return ret;
            }
            else if(te != null && te.activeRecipe != null)
            {
                String name = te.activeRecipe.posterChild.getDisplayName().getFormattedText();
                ArrayList<String> ret = new ArrayList<>();
                ret.add(name);
                ret.add("");
                ret.add(Colors.GOLD + "Required Items:");

                ItemStack is;
                for(int i = 0; i < te.activeRecipe.inputItems.size(); i++)
                {
                    is = te.activeRecipe.inputItems.get(i);
                    String line = is.getDisplayName() + " (" + is.getCount() + ")";
                    ret.add(line);
                }
                return ret;
            }
            else
            {
                ArrayList<String> ret = new ArrayList<>();
                ret.add(Colors.RED + "Internal Error: No recipe found.");
                return ret;
            }
        }
    }
}
