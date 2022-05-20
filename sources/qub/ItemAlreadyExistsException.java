package qub;

public class ItemAlreadyExistsException extends AlreadyExistsException
{
    private final Item alreadyExistingItem;

    public ItemAlreadyExistsException(Item alreadyExistingItem)
    {
        super(ItemAlreadyExistsException.getMessage(alreadyExistingItem));

        this.alreadyExistingItem = alreadyExistingItem;
    }

    private static String getMessage(Item alreadyExistingItem)
    {
        PreCondition.assertNotNull(alreadyExistingItem, "alreadyExistingItem");

        return "An " + Types.getTypeName(Item.class) + " already exists with the name " + Strings.escapeAndQuote(alreadyExistingItem.getName()) + " in this " + Types.getTypeName(ItemStorage.class) + ".";
    }

    /**
     * Get the {@link Item} that already exists.
     */
    public Item getAlreadyExistingItem()
    {
        return this.alreadyExistingItem;
    }
}
