package qub;

public class InMemoryStorageItemAttributeValueInteger extends InMemoryStorageItemAttributeValueBase
{
    private final int value;

    private InMemoryStorageItemAttributeValueInteger(int value)
    {
        this.value = value;
    }

    public static InMemoryStorageItemAttributeValueInteger create(int value)
    {
        return new InMemoryStorageItemAttributeValueInteger(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return Integer.class;
    }

    @Override
    public Result<Integer> getIntegerValue()
    {
        return Result.success(this.value);
    }
}
