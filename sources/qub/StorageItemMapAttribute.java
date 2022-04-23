package qub;

public class StorageItemMapAttribute extends InMemoryStorageItemAttributeBase
{
    private final String name;

    private StorageItemMapAttribute(String name, StorageItemAttributeValue value)
    {
        super(value);

        this.name = name;
    }

    public static StorageItemMapAttribute create(String name, StorageItemAttributeValue value)
    {
        return new StorageItemMapAttribute(name, value);
    }

    /**
     * Get the name of this {@link StorageItemMapAttribute}.
     */
    public String getName()
    {
        return this.name;
    }
}
