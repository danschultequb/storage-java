package qub;

public class InMemoryStorageItemAttributes implements MutableStorageItemAttributes
{
    private final InMemoryStorageItemAttributeMap values;
    private DateTime timestamp;

    private InMemoryStorageItemAttributes()
    {
        this.values = InMemoryStorageItemAttributeMap.create();
    }

    public static InMemoryStorageItemAttributes create()
    {
        return new InMemoryStorageItemAttributes();
    }

    @Override
    public DateTime getTimestamp()
    {
        return this.timestamp;
    }

    @Override
    public InMemoryStorageItemAttributes setTimestamp(DateTime timestamp)
    {
        PreCondition.assertNotNull(timestamp, "timestamp");

        this.timestamp = timestamp;

        return this;
    }

    @Override
    public Iterator<StorageItemMapAttribute> iterate()
    {
        return this.values.iterate();
    }

    @Override
    public Iterator<String> iterateAttributeNames()
    {
        return this.values.iterateAttributeNames();
    }

    @Override
    public Result<StorageItemAttributeValue> getValue(String attributeName)
    {
        return this.values.getValue(attributeName);
    }

    @Override
    public void clear()
    {
        this.values.clear();
    }

    @Override
    public InMemoryStorageItemAttributes setValue(String attributeName, StorageItemAttributeValue attributeValue)
    {
        this.values.setValue(attributeName, attributeValue);

        return this;
    }
}
