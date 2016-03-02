package lumaceon.mods.clockworkphase2.api.guidebook;

import java.util.ArrayList;

public class Categories
{
    public static final ArrayList<Category> CATEGORIES = new ArrayList<Category>(8);

    public static final Category JOURNAL_ENTRIES = registerCategory(new Category("Journal Entries"));
    public static final Category CLOCKWORK = registerCategory(new Category("Clockwork"));
    public static final Category TEMPORAL_MANIPULATION = registerCategory(new Category("Temporal Manipulation"));
    public static final Category THIRD_AGE = registerCategory(new Category("The Third Age"));
    public static final Category SECOND_AGE = registerCategory(new Category("The Second Age"));
    public static final Category FIRST_AGE = registerCategory(new Category("The First Age"));
    public static final Category ZEROTH_AGE = registerCategory(new Category("The Origin Phase"));

    public static Category registerCategory(Category category) {
        CATEGORIES.add(category);
        return category;
    }
}
