package qub;

public interface ItemStorage
{
    /**
     * Get an {@link Item} with the provided itemName.
     * @param itemName The name of the {@link Item} to return.
     */
    public default Result<Item> getItem(String itemName)
    {
        PreCondition.assertNotNullAndNotEmpty(itemName, "itemName");

        return Result.success(Item.create(this, itemName));
    }

    /**
     * Iterate through the {@link Item}s that are in this {@link ItemStorage}.
     */
    public Iterator<Item> iterateItems();

    /**
     * Get whether an {@link Item} with the provided name exists.
     * @param itemName The name of the {@link Item}.
     */
    public Result<Boolean> itemExists(String itemName);

    /**
     * Get the contents that have been assigned to the {@link Item} with the provided name.
     * @param itemName The name of the {@link Item}.
     */
    public Result<ByteReadStream> getItemContents(String itemName);

    /**
     * Set the contents for the {@link Item} with the provided name.
     * @param itemName The name of the {@link Item}.
     * @param itemContents The contents to assign to the {@link Item}.
     */
    public default Result<Void> setItemContents(String itemName, byte[] itemContents)
    {
        PreCondition.assertNotNullAndNotEmpty(itemName, "itemName");
        PreCondition.assertNotNull(itemContents, "itemContents");

        return this.setItemContents(itemName, InMemoryByteStream.create(itemContents).endOfStream());
    }

    /**
     * Set the contents for the {@link Item} with the provided name.
     * @param itemName The name of the {@link Item}.
     * @param contents The contents to assign to the {@link Item}.
     */
    public Result<Void> setItemContents(String itemName, ByteReadStream contents);

    /**
     * Create a new {@link Item}.
     * @return The newly created {@link Item}.
     */
    public default Result<Item> createItem()
    {
        return this.createItem(CreateItemParameters.create());
    }

    /**
     * Create a new {@link Item} based on the provided {@link CreateItemParameters}.
     * @param parameters The {@link CreateItemParameters} that define the new {@link Item}.
     * @return The newly created {@link Item}.
     */
    public Result<Item> createItem(CreateItemParameters parameters);

    /**
     * Delete the {@link Item} with the provided name.
     * @param itemName The name of the {@link Item} to delete.
     */
    public Result<Void> deleteItem(String itemName);

    /**
     * Get the {@link Item} with the provided itemName if it exists. If it doesn't exist, a new
     * {@link Item} with the provided name will be created and returned.
     * @param itemName The name of the {@link Item} to get or create.
     */
    public default Result<Item> getOrCreateItem(String itemName)
    {
        PreCondition.assertNotNullAndNotEmpty(itemName, "itemName");

        return this.getOrCreateItem(CreateItemParameters.create()
            .setName(itemName));
    }

    /**
     * Get the {@link Item} with the name in the provided parameters. If the {@link Item} doesn't
     * exist, a new {@link Item} will be created using the provided {@link CreateItemParameters} and
     * returned.
     * @param parameters The {@link CreateItemParameters} that will be used to get or create the
     *                   {@link Item}.
     */
    public Result<Item> getOrCreateItem(CreateItemParameters parameters);
}
