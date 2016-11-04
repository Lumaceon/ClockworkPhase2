package lumaceon.mods.clockworkphase2.api.guidebook;

import java.util.ArrayList;

public class Categories
{
    public static final ArrayList<Category> CATEGORIES = new ArrayList<Category>(8);

    public static final Category GETTING_STARTED = registerCategory(new Category("Getting Started"));

    public static Category registerCategory(Category category) {
        CATEGORIES.add(category);
        return category;
    }
}
