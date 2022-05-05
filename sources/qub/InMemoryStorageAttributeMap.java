package qub;

public class InMemoryStorageAttributeMap implements MutableStorageAttributeMap
{
    private final MutableMap<String,StorageAttributeValue> attributes;

    private InMemoryStorageAttributeMap()
    {
        this.attributes = Map.create();
    }

    public static InMemoryStorageAttributeMap create()
    {
        return new InMemoryStorageAttributeMap();
    }

    public Result<StorageAttributeValue> getValue(String attributeName)
    {
        PreCondition.assertNotNull(attributeName, "attributeName");

        return this.attributes.get(attributeName)
            .convertError(NotFoundException.class, () ->
            {
                return new NotFoundException("No attribute found with the name: " + Strings.escapeAndQuote(attributeName));
            });
    }

    public InMemoryStorageAttributeMap setValue(String attributeName, StorageAttributeValue attributeValue)
    {
        PreCondition.assertNotNull(attributeName, "attributeName");
        PreCondition.assertNotNull(attributeValue, "attributeValue");

        this.attributes.set(attributeName, attributeValue);

        return this;
    }

    @Override
    public void clear()
    {
        this.attributes.clear();
    }

    @Override
    public Iterator<String> iterateAttributeNames()
    {
        return this.attributes.getKeys().iterate();
    }

    @Override
    public Iterator<StorageMapAttribute> iterate()
    {
        return this.attributes.iterate()
            .map((MapEntry<String,StorageAttributeValue> entry) ->
            {
                return StorageMapAttribute.create(entry.getKey(), entry.getValue());
            });
    }

    @Override
    public String toString()
    {
        return this.attributes.toString();
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeMap &&
            this.attributes.equals(((InMemoryStorageAttributeMap)rhs).attributes);
    }
}
