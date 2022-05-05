package qub;

public class InMemoryStorageAttributes implements MutableStorageAttributes
{
    private final InMemoryStorageAttributeMap values;
    private DateTime timestamp;

    private InMemoryStorageAttributes()
    {
        this.values = InMemoryStorageAttributeMap.create();
    }

    public static InMemoryStorageAttributes create()
    {
        return new InMemoryStorageAttributes();
    }

    @Override
    public DateTime getTimestamp()
    {
        return this.timestamp;
    }

    @Override
    public InMemoryStorageAttributes setTimestamp(DateTime timestamp)
    {
        PreCondition.assertNotNull(timestamp, "timestamp");

        this.timestamp = timestamp;

        return this;
    }

    @Override
    public Iterator<StorageMapAttribute> iterate()
    {
        return this.values.iterate();
    }

    @Override
    public Iterator<String> iterateAttributeNames()
    {
        return this.values.iterateAttributeNames();
    }

    @Override
    public Result<StorageAttributeValue> getValue(String attributeName)
    {
        return this.values.getValue(attributeName);
    }

    @Override
    public void clear()
    {
        this.values.clear();
    }

    @Override
    public InMemoryStorageAttributes setValue(String attributeName, StorageAttributeValue attributeValue)
    {
        this.values.setValue(attributeName, attributeValue);

        return this;
    }

    @Override
    public InMemoryStorageAttributes setValues(Iterable<StorageMapAttribute> attributes)
    {
        return (InMemoryStorageAttributes)MutableStorageAttributes.super.setValues(attributes);
    }

    public JSONObject toJson()
    {
        return JSONObject.create()
            .setNumber("timestamp", this.timestamp.getDurationSinceEpoch().toMilliseconds().getValue())
            .setString("values", this.values.toString());
    }

    @Override
    public String toString()
    {
        return this.toJson().toString();
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributes &&
            this.equals((InMemoryStorageAttributes)rhs);
    }

    public boolean equals(InMemoryStorageAttributes rhs)
    {
        return rhs != null &&
            this.timestamp.equals(rhs.timestamp) &&
            this.values.equals(rhs.values);
    }
}
