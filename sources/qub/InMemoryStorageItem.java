package qub;

/**
 * A {@link StorageItem} implementation that exists only in-memory.
 */
public class InMemoryStorageItem extends StorageItemBase
{
    private final InMemoryStorageContainer container;
    private final String id;
    private DateTime createdAt;
    private DateTime lastModified;
    private InMemoryStorageItemAttributes attributes;

    private InMemoryStorageItem(InMemoryStorageContainer container, String id)
    {
        PreCondition.assertNotNull(container, "container");
        PreCondition.assertNotNull(id, "id");

        this.container = container;
        this.id = id;

        this.createdAt = container.getCurrentDateTime();
        this.lastModified = this.createdAt;
    }

    public static InMemoryStorageItem create(InMemoryStorageContainer container, String id)
    {
        return new InMemoryStorageItem(container, id);
    }
    
    @Override
    public String getId()
    {
        return this.id;
    }

    @Override
    public DateTime getCreatedAt()
    {
        return this.createdAt;
    }

    public InMemoryStorageItem setCreatedAt(DateTime createdAt)
    {
        PreCondition.assertNotNull(createdAt, "createdAt");

        this.createdAt = createdAt;

        return this;
    }

    @Override
    public DateTime getLastModified()
    {
        return this.lastModified;
    }

    public InMemoryStorageItem setLastModified(DateTime lastModified)
    {
        PreCondition.assertNotNull(lastModified, "lastModified");

        this.lastModified = lastModified;

        return this;
    }

    @Override
    public Result<StorageItemAttributes> getAttributes()
    {
        return Result.success(this.attributes);
    }

    @Override
    public Result<Void> setAttributes(StorageItemAttributes attributes)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(StorageItem rhs)
    {
        // TODO Auto-generated method stub
        return false;
    }
}
