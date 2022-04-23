package qub;

/**
 * Attribute that have been assigned to a {@link StorageItem}.
 */
public interface StorageItemAttributes extends StorageItemAttributeMap
{
    /**
     * Create a new {@link MutableStorageItemAttributes} object.
     */
    public static MutableStorageItemAttributes create()
    {
        return InMemoryStorageItemAttributes.create();
    }

    /**
     * Get the {@link DateTime} when these {@link StorageItemAttributes} were set on the
     * {@link StorageItem}.
     */
    public DateTime getTimestamp();
}
