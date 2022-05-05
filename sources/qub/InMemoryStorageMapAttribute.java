package qub;

public class InMemoryStorageMapAttribute implements StorageMapAttribute
{
    private final String name;
    private final StorageAttributeValue value;

    private InMemoryStorageMapAttribute(String name, StorageAttributeValue value)
    {
        PreCondition.assertNotNull(name, "name");
        PreCondition.assertNotNull(value, "value");

        this.name = name;
        this.value = value;
    }

    public static InMemoryStorageMapAttribute create(String name, StorageAttributeValue value)
    {
        return new InMemoryStorageMapAttribute(name, value);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public StorageAttributeValue getValue()
    {
        return this.value;
    }

    @Override
    public boolean equals(Object rhs)
    {
        return StorageMapAttribute.equals(this, rhs);
    }

    @Override
    public String toString()
    {
        return StorageMapAttribute.toString(this);
    }
}
