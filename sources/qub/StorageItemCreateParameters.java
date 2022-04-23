package qub;

/**
 * Parameters that can be used to create a new {@link StorageItem}.
 */
public class StorageItemCreateParameters
{
    private String id;
    private DateTime createdAt;
    private DateTime lastModified;
    private StorageItemAttributes attributes;

    private StorageItemCreateParameters()
    {
    }

    /**
     * Create a new {@link StorageItemCreateParameters} object.
     */
    public static StorageItemCreateParameters create()
    {
        return new StorageItemCreateParameters();
    }

    /**
     * Get the id of the {@link StorageItem} to create. If no id is provided, the
     * {@link StorageContainer} will generate a new id for the new {@link StorageItem}.
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Set the id of the {@link StorageItem} to create. If no id is provided, the
     * {@link StorageContainer} will generate a new id for the new {@link StorageItem}.
     * @param id The id of the {@link StorageItem} to create.
     * @return This object for method chaining.
     */
    public StorageItemCreateParameters setId(String id)
    {
        PreCondition.assertNotNull(id, "id");

        this.id = id;

        return this;
    }

    /**
     * Get the {@link DateTime} that the {@link StorageItem} was created at. If no {@link DateTime}
     * is provided, the {@link StorageContainer} will use its current time for the new
     * {@link StorageItem}. This parameter can be useful when copying a {@link StorageItem} to a
     * different {@link StorageContainer}.
     */
    public DateTime getCreatedAt()
    {
        return this.createdAt;
    }

    /**
     * Set the {@link DateTime} that the {@link StorageItem} was created at. If no {@link DateTime}
     * is provided, the {@link StorageContainer} will use its current time for the new
     * {@link StorageItem}. This parameter can be useful when copying a {@link StorageItem} to a
     * different {@link StorageContainer}.
     * @param createdAt The {@link DateTime} that the {@link StorageItem} was created at.
     * @return This object for method chaining.
     */
    public StorageItemCreateParameters setCreatedAt(DateTime createdAt)
    {
        PreCondition.assertNotNull(createdAt, "createdAt");

        this.createdAt = createdAt;

        return this;
    }

    /**
     * Get the {@link DateTime} that the {@link StorageItem} was last modified. If no
     * {@link DateTime} is provided, the {@link StorageContainer} will use its current time for the
     * new {@link StorageItem}. This parameter can be useful when copying a {@link StorageItem} to a
     * different {@link StorageContainer}.
     */
    public DateTime getLastModified()
    {
        return this.lastModified;
    }

    /**
     * Set the {@link DateTime} that the {@link StorageItem} was last modified. If no
     * {@link DateTime} is provided, the {@link StorageContainer} will use its current time for the
     * new {@link StorageItem}. This parameter can be useful when copying a {@link StorageItem} to a
     * different {@link StorageContainer}.
     * @param lastModified The {@link DateTime} that the {@link StorageItem} was last modified.
     * @return This object for method chaining.
     */
    public StorageItemCreateParameters setLastModified(DateTime lastModified)
    {
        PreCondition.assertNotNull(lastModified, "lastModified");

        this.lastModified = lastModified;

        return this;
    }

    public StorageItemAttributes getAttributes()
    {
        return this.attributes;
    }

    /**
     * Set the {@link StorageItemAttributes} for the {@link StorageItem}.
     * @param attributes The {@link StorageItemAttributes} for the {@link StorageItem}.
     * @return This object for method chaining.
     */
    public StorageItemCreateParameters setAttributes(StorageItemAttributes attributes)
    {
        PreCondition.assertNotNull(attributes, "attributes");

        this.attributes = attributes;

        return this;
    }
}
