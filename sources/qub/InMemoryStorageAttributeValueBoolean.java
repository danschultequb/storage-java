package qub;

/**
 * A {@link StorageAttributeValue} type that stores a boolean value.
 */
public class InMemoryStorageAttributeValueBoolean extends InMemoryStorageAttributeValueBase
{
    private final static InMemoryStorageAttributeValueBoolean falseValue = InMemoryStorageAttributeValueBoolean.create(false);
    private final static InMemoryStorageAttributeValueBoolean trueValue = InMemoryStorageAttributeValueBoolean.create(true);

    private final boolean value;

    private InMemoryStorageAttributeValueBoolean(boolean value)
    {
        this.value = value;
    }

    private static InMemoryStorageAttributeValueBoolean create(boolean value)
    {
        return new InMemoryStorageAttributeValueBoolean(value);
    }

    public static InMemoryStorageAttributeValueBoolean get(boolean value)
    {
        return value
            ? InMemoryStorageAttributeValueBoolean.getTrue()
            : InMemoryStorageAttributeValueBoolean.getFalse();
    }

    public static InMemoryStorageAttributeValueBoolean getFalse()
    {
        return InMemoryStorageAttributeValueBoolean.falseValue;
    }

    public static InMemoryStorageAttributeValueBoolean getTrue()
    {
        return InMemoryStorageAttributeValueBoolean.trueValue;
    }

    /**
     * Get the boolean value that is stored by this {@link InMemoryStorageAttributeValueBoolean}.
     */
    public boolean getValue()
    {
        return this.value;
    }

    @Override
    public Result<Boolean> getBooleanValue()
    {
        return Result.success(this.getValue());
    }

    @Override
    protected Class<?> getValueType()
    {
        return Boolean.class;
    }

    @Override
    public String toString()
    {
        return Booleans.toString(this.value);
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeValueBoolean &&
            this.getValue() == ((InMemoryStorageAttributeValueBoolean)rhs).getValue();
    }
}
