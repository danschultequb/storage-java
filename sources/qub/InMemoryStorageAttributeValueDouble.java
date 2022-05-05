package qub;

public class InMemoryStorageAttributeValueDouble extends InMemoryStorageAttributeValueBase
{
    private final double value;

    private InMemoryStorageAttributeValueDouble(double value)
    {
        this.value = value;
    }

    public static InMemoryStorageAttributeValueDouble create(double value)
    {
        return new InMemoryStorageAttributeValueDouble(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return Double.class;
    }

    public double getValue()
    {
        return this.value;
    }

    @Override
    public Result<Double> getDoubleValue()
    {
        return Result.success(this.getValue());
    }

    @Override
    public String toString()
    {
        return Doubles.toString(this.value);
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeValueDouble &&
            this.getValue() == ((InMemoryStorageAttributeValueDouble)rhs).getValue();
    }
}
