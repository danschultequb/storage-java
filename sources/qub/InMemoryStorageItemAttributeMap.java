package qub;

public class InMemoryStorageItemAttributeMap implements MutableStorageItemAttributeMap
{
    private final MutableMap<String,StorageItemAttributeValue> attributes;

    private InMemoryStorageItemAttributeMap()
    {
        this.attributes = Map.create();
    }

    public static InMemoryStorageItemAttributeMap create()
    {
        return new InMemoryStorageItemAttributeMap();
    }

    public Result<StorageItemAttributeValue> getValue(String attributeName)
    {
        PreCondition.assertNotNullAndNotEmpty(attributeName, "attributeName");

        return this.attributes.get(attributeName);
    }

    public InMemoryStorageItemAttributeMap setValue(String attributeName, StorageItemAttributeValue attributeValue)
    {
        PreCondition.assertNotNullAndNotEmpty(attributeName, "attributeName");
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
    public Iterator<StorageItemMapAttribute> iterate()
    {
        return this.attributes.iterate()
            .map((MapEntry<String,StorageItemAttributeValue> entry) ->
            {
                return StorageItemMapAttribute.create(entry.getKey(), entry.getValue());
            });
    }
}
