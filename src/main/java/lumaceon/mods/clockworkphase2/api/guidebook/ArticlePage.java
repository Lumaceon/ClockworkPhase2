package lumaceon.mods.clockworkphase2.api.guidebook;

import lumaceon.mods.clockworkphase2.api.guidebook.renderers.GuidebookCustomRender;

/**
 * Article pages are always saved and loaded to/from a single string tag. In order to make this more human-readable,
 * this class exists to translate code within that string to and from the string.
 *
 * For example, the [TITLE]New Article[/TITLE] code within the string would set the title to "New Article", but we
 * don't wish for the developer to touch that code as if it were actual text (unless they're editing the title).
 */
public class ArticlePage
{
    public ContainerGuidebook containerReference;
    /** A string representing the human-readable text to be displayed on this page */
    public String pageText = "";
    /** The title of this page (can be null) */
    public String titleText = "";
    /** Images (if any) to display, represented by simple string IDs */
    public String[] images = null;
    /** Inventories representing crafting recipes to display on this page */
    public InventoryGuidebook[] craftingRecipes = null;
    /** Custom renderers that preform their own work. Often used to display custom recipes. */
    public GuidebookCustomRender[] customRenderers = null;

    public ArticlePage(String string, ContainerGuidebook containerReference) {
        this.containerReference = containerReference;
        loadFromString(string);
    }

    public void addCraftingRecipeFromKey(String key)
    {
        GuidebookRegistry.GuidebookRecipe recipe = GuidebookRegistry.getRecipeForString(key);
        if(recipe != null)
        {
            if(craftingRecipes == null || craftingRecipes.length == 0)
                craftingRecipes = new InventoryGuidebook[1];
            else
            {
                InventoryGuidebook[] temp = craftingRecipes;
                craftingRecipes = new InventoryGuidebook[temp.length + 1];
                System.arraycopy(temp, 0, craftingRecipes, 0, temp.length);
            }
            craftingRecipes[craftingRecipes.length - 1] = new InventoryGuidebook(recipe);
            containerReference.updateContents(craftingRecipes);
        }
    }

    public void addImageFromKey(String key)
    {
        if(images == null || images.length == 0)
            images = new String[1];
        else
        {
            String[] temp = images;
            images = new String[temp.length + 1];
            System.arraycopy(temp, 0, images, 0, temp.length);
        }
        images[images.length - 1] = key;
    }

    public void addCustomFromKey(String key)
    {
        GuidebookCustomRender recipe = GuidebookRegistry.getCustomRenderForString(key);
        if(recipe != null)
        {
            if(customRenderers == null || customRenderers.length == 0)
                customRenderers = new GuidebookCustomRender[1];
            else
            {
                GuidebookCustomRender[] temp = customRenderers;
                customRenderers = new GuidebookCustomRender[temp.length + 1];
                System.arraycopy(temp, 0, customRenderers, 0, temp.length);
            }
            customRenderers[customRenderers.length - 1] = recipe;
        }
    }

    /**
     * Converts the page to a single string to be saved in an article.
     * @return A string representing this page.
     */
    public String saveToString()
    {
        String ret = "";
        if(titleText != null && titleText.length() > 0)
        {
            ret = ret.concat("[TITLE]");
            ret = ret.concat(titleText);
            ret = ret.concat("[/TITLE]");
        }

        if(images != null && images.length > 0)
            for(String image : images)
                if(image != null && image.length() > 0)
                {
                    ret = ret.concat("[IMAGE]");
                    ret = ret.concat(image);
                    ret = ret.concat("[/IMAGE]");
                }

        if(craftingRecipes != null && craftingRecipes.length > 0)
            for(InventoryGuidebook craftingRecipe : craftingRecipes)
                if(craftingRecipe != null)
                {
                    ret = ret.concat("[CRAFTING]");
                    ret = ret.concat(craftingRecipe.guidebookRecipe.stringID);
                    ret = ret.concat("[/CRAFTING]");
                }

        return ret.concat(pageText);
    }

    public void loadFromString(String string)
    {
        if(string == null)
            return;

        //Load title.
        if(string.startsWith("[TITLE]") && string.contains("[/TITLE]"))
        {
            titleText = string.substring(7, string.indexOf("[/TITLE]"));
            string = string.substring(string.indexOf("[/TITLE]") + 8);
        }

        //Load images.
        while(string.startsWith("[IMAGE]") && string.contains("[/IMAGE]"))
        {
            if(images == null || images.length == 0)
                images = new String[1];
            else
            {
                String[] temp = images;
                images = new String[temp.length + 1];
                System.arraycopy(temp, 0, images, 0, temp.length);
            }
            images[images.length - 1] = string.substring(7, string.indexOf("[/IMAGE]"));
            string = string.substring(string.indexOf("[/IMAGE]") + 8);
        }

        //Load crafting recipes.
        while(string.startsWith("[CRAFTING]") && string.contains("[/CRAFTING]"))
        {
            GuidebookRegistry.GuidebookRecipe recipe = GuidebookRegistry.getRecipeForString(string.substring(10, string.indexOf("[/CRAFTING]")));
            if(recipe == null)
            {
                string = string.substring(string.indexOf("[/CRAFTING]") + 11);
                continue;
            }

            if(craftingRecipes == null || craftingRecipes.length == 0)
                craftingRecipes = new InventoryGuidebook[1];
            else
            {
                InventoryGuidebook[] temp = craftingRecipes;
                craftingRecipes = new InventoryGuidebook[temp.length + 1];
                System.arraycopy(temp, 0, craftingRecipes, 0, temp.length);
            }
            craftingRecipes[craftingRecipes.length - 1] = new InventoryGuidebook(recipe);
            string = string.substring(string.indexOf("[/CRAFTING]") + 11);
        }

        //Set readable text to display.
        pageText = string;

        //Update container with crafting recipes.
        containerReference.updateContents(craftingRecipes);
    }
}
