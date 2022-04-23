package qub;

public class InMemoryStorageItemAttributeValueString extends InMemoryStorageItemAttributeValueBase
{
    private final String value;

    private InMemoryStorageItemAttributeValueString(String value)
    {
        PreCondition.assertNotNull(value, "value");
        
        this.value = value;
    }

    public static InMemoryStorageItemAttributeValueString create(String value)
    {
        return new InMemoryStorageItemAttributeValueString(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return String.class;
    }

    @Override
    public Result<String> getStringValue()
    {
        return Result.success(this.value);
    }
}
