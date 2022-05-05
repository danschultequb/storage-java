package qub;

public class InMemoryStorageAttributeValueMap extends InMemoryStorageAttributeValueBase
{
    private final StorageAttributeMap value;

    private InMemoryStorageAttributeValueMap(StorageAttributeMap value)
    {
        PreCondition.assertNotNull(value, "value");

        this.value = value;
    }

    public static InMemoryStorageAttributeValueMap create(StorageAttributeMap value)
    {
        return new InMemoryStorageAttributeValueMap(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return StorageAttributeMap.class;
    }

    public StorageAttributeMap getValue()
    {
        return this.value;
    }

    @Override
    public Result<StorageAttributeMap> getMapValue()
    {
        return Result.success(this.getValue());
    }

    @Override
    public String toString()
    {
        return this.value.toString();
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeValueMap &&
            this.getValue().equals(((InMemoryStorageAttributeValueMap)rhs).getValue());
    }
}
