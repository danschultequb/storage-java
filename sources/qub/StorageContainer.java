package qub;

/**
 * The interface for interacting with a {@link StorageContainer}.
 */
public interface StorageContainer
{
    /**
     * Get an {@link Iterator} that will iterate through the {@link StorageItem}s in this {@link StorageContainer}.
     */
    public Iterator<? extends StorageItem> iterateItems();

    /**
     * Get the {@link StorageItem} with the provided id.
     * @param id The id of the {@link StorageItem}.
     */
    public Result<? extends StorageItem> getItem(String id);

    /**
     * Create a new empty {@link StorageItem}.
     * @return The newly created {@link StorageItem}.
     */
    public Result<? extends StorageItem> createItem();

    /**
     * Create a new {@link StorageItem} using the provided parameters.
     * @param parameters The parameters that define the new {@link StorageItem}.
     * @return The newly created {@link StorageItem}.
     */
    public Result<? extends StorageItem> createItem(StorageItemCreateParameters parameters);
}