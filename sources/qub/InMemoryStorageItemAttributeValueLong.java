package qub;

public class InMemoryStorageItemAttributeValueLong extends InMemoryStorageItemAttributeValueBase
{
    private final long value;

    private InMemoryStorageItemAttributeValueLong(long value)
    {
        this.value = value;
    }

    public static InMemoryStorageItemAttributeValueLong create(long value)
    {
        return new InMemoryStorageItemAttributeValueLong(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return Long.class;
    }

    @Override
    public Result<Long> getLongValue()
    {
        return Result.success(this.value);
    }
}
