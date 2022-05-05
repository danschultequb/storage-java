package qub;

public class InMemoryStorageAttributeValueString extends InMemoryStorageAttributeValueBase
{
    private final String value;

    private InMemoryStorageAttributeValueString(String value)
    {
        this.value = value;
    }

    public static InMemoryStorageAttributeValueString create(String value)
    {
        return new InMemoryStorageAttributeValueString(value);
    }

    @Override
    protected Class<?> getValueType()
    {
        return String.class;
    }

    public String getValue()
    {
        return this.value;
    }

    @Override
    public Result<String> getStringValue()
    {
        return Result.success(this.value);
    }

    @Override
    public String toString()
    {
        return this.value;
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeValueString &&
            Strings.equal(this.getValue(), ((InMemoryStorageAttributeValueString)rhs).getValue());
    }
}
