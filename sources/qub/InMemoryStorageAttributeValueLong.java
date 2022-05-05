package qub;

public class InMemoryStorageAttributeValueLong extends InMemoryStorageAttributeValueBase
{
    private final long value;

    private InMemoryStorageAttributeValueLong(long value)
    {
        this.value = value;
    }

    public static InMemoryStorageAttributeValueLong create(long value)
    {
        return new InMemoryStorageAttributeValueLong(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return Long.class;
    }

    public long getValue()
    {
        return this.value;
    }

    @Override
    public Result<Long> getLongValue()
    {
        return Result.success(this.getValue());
    }

    @Override
    public String toString()
    {
        return Longs.toString(this.value);
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeValueLong &&
            this.getValue() == ((InMemoryStorageAttributeValueLong)rhs).getValue();
    }
}
