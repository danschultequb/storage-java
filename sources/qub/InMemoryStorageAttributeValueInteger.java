package qub;

public class InMemoryStorageAttributeValueInteger extends InMemoryStorageAttributeValueBase
{
    private final int value;

    private InMemoryStorageAttributeValueInteger(int value)
    {
        this.value = value;
    }

    public static InMemoryStorageAttributeValueInteger create(int value)
    {
        return new InMemoryStorageAttributeValueInteger(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return Integer.class;
    }

    public int getValue()
    {
        return this.value;
    }

    @Override
    public Result<Integer> getIntegerValue()
    {
        return Result.success(this.getValue());
    }

    @Override
    public String toString()
    {
        return Integers.toString(this.value);
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeValueInteger &&
            this.getValue() == ((InMemoryStorageAttributeValueInteger)rhs).getValue();
    }
}
