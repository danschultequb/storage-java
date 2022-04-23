package qub;

/**
 * An item that is contained within a {@link StorageContainer}.
 */
public interface StorageItem
{
    /**
     * Get the id of this {@link StorageItem}.
     */
    public String getId();

    /**
     * Get the last {@link DateTime} that this {@link StorageItem} was modified.
     */
    public DateTime getLastModified();

    /**
     * Get the {@link DateTime} that this {@link StorageItem} was created at.
     */
    public DateTime getCreatedAt();

    /**
     * Get the {@link StorageItemAttributes} that are assigned to this {@link StorageItem}.
     */
    public Result<StorageItemAttributes> getAttributes();

    /**
     * Set the {@link StorageItemAttributes} that are assigned to this {@link StorageItem}.
     * @param attributes The {@link StorageItemAttributes} that are assigned to this
     * {@link StorageItem}.
     */
    public Result<Void> setAttributes(StorageItemAttributes attributes);

    /**
     * Get whether this {@link StorageItem} is equal to the provided {@link StorageItem}.
     * @param rhs The {@link StorageItem} to compare to this {@link StorageItem}.
     */
    public boolean equals(StorageItem rhs);
}
