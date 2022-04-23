package qub;

public class InMemoryStorageItemAttributeValueDouble extends InMemoryStorageItemAttributeValueBase
{
    private final double value;

    private InMemoryStorageItemAttributeValueDouble(double value)
    {
        this.value = value;
    }

    public static InMemoryStorageItemAttributeValueDouble create(double value)
    {
        return new InMemoryStorageItemAttributeValueDouble(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return Double.class;
    }

    @Override
    public Result<Double> getDoubleValue()
    {
        return Result.success(this.value);
    }
}
