package qub;

public class InMemoryStorageItemAttributeValueArray extends InMemoryStorageItemAttributeValueBase
{
    private final StorageItemAttributeArray value;

    private InMemoryStorageItemAttributeValueArray(StorageItemAttributeArray value)
    {
        PreCondition.assertNotNull(value, "value");

        this.value = value;
    }

    public static InMemoryStorageItemAttributeValueArray create(StorageItemAttributeArray value)
    {
        return new InMemoryStorageItemAttributeValueArray(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return StorageItemAttributeArray.class;
    }

    @Override
    public Result<StorageItemAttributeArray> getArrayValue()
    {
        return Result.success(this.value);
    }
}
