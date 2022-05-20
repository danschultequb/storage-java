package qub;

public class Item
{
    private final ItemStorage itemStorage;
    private final String name;

    private Item(ItemStorage itemStorage, String name)
    {
        PreCondition.assertNotNull(itemStorage, "itemStorage");
        PreCondition.assertNotNullAndNotEmpty(name, "name");

        this.itemStorage = itemStorage;
        this.name = name;
    }

    public static Item create(ItemStorage itemStorage, String name)
    {
        return new Item(itemStorage, name);
    }

    /**
     * Get the {@link ItemStorage} that this {@link Item} belongs to.
     */
    public ItemStorage getItemStorage()
    {
        return this.itemStorage;
    }

    /**
     * Get the name of this {@link Item}.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Get whether this {@link Item} exists.
     */
    public Result<Boolean> exists()
    {
        return this.itemStorage.itemExists(this.name);
    }

    /**
     * Get the contents that have been assigned to this {@link Item}.
     */
    public Result<ByteReadStream> getContents()
    {
        return this.itemStorage.getItemContents(this.name);
    }

    /**
     * Set the contents that are assigned to this {@link Item}.
     * @param contents The contents that are assigned to this {@link Item}.
     */
    public Result<Void> setContents(byte[] contents)
    {
        return this.itemStorage.setItemContents(this.name, contents);
    }

    /**
     * Set the contents that are assigned to this {@link Item}.
     * @param contents The contents that are assigned to this {@link Item}.
     */
    public Result<Void> setContents(ByteReadStream contents)
    {
        return this.itemStorage.setItemContents(this.name, contents);
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof Item && this.equals((Item)rhs);
    }

    public boolean equals(Item rhs)
    {
        return rhs != null &&
            this.itemStorage.equals(rhs.itemStorage) &&
            this.name.equals(rhs.name);
    }
}
