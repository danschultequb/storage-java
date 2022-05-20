package qub;

public class ItemNotFoundException extends NotFoundException
{
    private final Item notFoundItem;

    public ItemNotFoundException(String message, Item notFoundItem)
    {
        super(message);

        this.notFoundItem = notFoundItem;
    }

    public ItemNotFoundException(String message)
    {
        this(message, null);
    }

    public ItemNotFoundException(Item notFoundItem)
    {
        this(ItemNotFoundException.getMessage(notFoundItem), notFoundItem);
    }

    private static String getMessage(Item notFoundItem)
    {
        PreCondition.assertNotNull(notFoundItem, "notFoundItem");

        return "No " + Types.getTypeName(Item.class) + " named " + Strings.escapeAndQuote(notFoundItem.getName()) + " found in this " + Types.getTypeName(ItemStorage.class) + ".";
    }

    /**
     * Get the {@link Item} that doesn't exist.
     */
    public Item getNotFoundItem()
    {
        return this.notFoundItem;
    }
}
