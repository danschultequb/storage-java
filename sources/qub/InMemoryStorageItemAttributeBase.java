package qub;

public abstract class InMemoryStorageItemAttributeBase implements StorageItemAttribute
{
    private final StorageItemAttributeValue value;

    protected InMemoryStorageItemAttributeBase(StorageItemAttributeValue value)
    {
        PreCondition.assertNotNull(value, "value");

        this.value = value;
    }

    @Override
    public StorageItemAttributeValue getValue()
    {
        return this.value;
    }
}
