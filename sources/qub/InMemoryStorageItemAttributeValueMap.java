package qub;

public class InMemoryStorageItemAttributeValueMap extends InMemoryStorageItemAttributeValueBase
{
    private final StorageItemAttributeMap value;

    private InMemoryStorageItemAttributeValueMap(StorageItemAttributeMap value)
    {
        PreCondition.assertNotNull(value, "value");
        
        this.value = value;
    }

    public static InMemoryStorageItemAttributeValueMap create(StorageItemAttributeMap value)
    {
        return new InMemoryStorageItemAttributeValueMap(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return StorageItemAttributeMap.class;
    }

    @Override
    public Result<StorageItemAttributeMap> getMapValue()
    {
        return Result.success(this.value);
    }
}
