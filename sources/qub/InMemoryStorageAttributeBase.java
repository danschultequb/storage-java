package qub;

public abstract class InMemoryStorageAttributeBase implements StorageAttribute
{
    private final StorageAttributeValue value;

    protected InMemoryStorageAttributeBase(StorageAttributeValue value)
    {
        PreCondition.assertNotNull(value, "value");

        this.value = value;
    }

    @Override
    public StorageAttributeValue getValue()
    {
        return this.value;
    }
}
