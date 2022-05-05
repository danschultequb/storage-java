package qub;

public class InMemoryStorageAttributeValueArray extends InMemoryStorageAttributeValueBase
{
    private final StorageAttributeArray value;

    private InMemoryStorageAttributeValueArray(StorageAttributeArray value)
    {
        PreCondition.assertNotNull(value, "value");

        this.value = value;
    }

    public static InMemoryStorageAttributeValueArray create(StorageAttributeArray value)
    {
        return new InMemoryStorageAttributeValueArray(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return StorageAttributeArray.class;
    }

    @Override
    public Result<StorageAttributeArray> getArrayValue()
    {
        return Result.success(this.value);
    }

    @Override
    public String toString()
    {
        return this.value.toString();
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeValueArray &&
            this.value.equals(((InMemoryStorageAttributeValueArray)rhs).value);
    }
}
