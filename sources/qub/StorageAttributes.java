package qub;

/**
 * Attribute that have been assigned to a {@link StorageItem}.
 */
public interface StorageAttributes extends StorageAttributeMap
{
    /**
     * Create a new {@link MutableStorageAttributes} object.
     */
    public static MutableStorageAttributes create()
    {
        return InMemoryStorageAttributes.create();
    }

    /**
     * Get the {@link DateTime} when these {@link StorageAttributes} were set on the
     * {@link StorageItem}.
     */
    public DateTime getTimestamp();
}
