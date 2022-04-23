package qub;

/**
 * A {@link StorageItemAttributeValue} type that stores a boolean value.
 */
public class InMemoryStorageItemAttributeValueBoolean extends InMemoryStorageItemAttributeValueBase
{
    private final static InMemoryStorageItemAttributeValueBoolean falseValue = InMemoryStorageItemAttributeValueBoolean.create(false);
    private final static InMemoryStorageItemAttributeValueBoolean trueValue = InMemoryStorageItemAttributeValueBoolean.create(true);

    private final boolean value;

    private InMemoryStorageItemAttributeValueBoolean(boolean value)
    {
        this.value = value;
    }

    private static InMemoryStorageItemAttributeValueBoolean create(boolean value)
    {
        return new InMemoryStorageItemAttributeValueBoolean(value);
    }

    public static InMemoryStorageItemAttributeValueBoolean get(boolean value)
    {
        return value
            ? InMemoryStorageItemAttributeValueBoolean.getTrue()
            : InMemoryStorageItemAttributeValueBoolean.getFalse();
    }

    public static InMemoryStorageItemAttributeValueBoolean getFalse()
    {
        return InMemoryStorageItemAttributeValueBoolean.falseValue;
    }

    public static InMemoryStorageItemAttributeValueBoolean getTrue()
    {
        return InMemoryStorageItemAttributeValueBoolean.trueValue;
    }

    /**
     * Get the boolean value that is stored by this {@link InMemoryStorageItemAttributeValueBoolean}.
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
}
