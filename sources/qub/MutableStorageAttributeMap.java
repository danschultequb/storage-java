package qub;

/**
 * A {@link StorageAttributeMap} that can modify its entries.
 */
public interface MutableStorageAttributeMap extends StorageAttributeMap
{
    /**
     * Remove all of the {@link StorageMapAttribute}s in this {@link MutableStorageAttributeMap}.
     */
    public void clear();

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageAttributeMap setValue(String attributeName, StorageAttributeValue attributeValue);

    /**
     * Set the provided attribute.
     * @param attribute The attribute to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setValue(StorageMapAttribute attribute)
    {
        PreCondition.assertNotNull(attribute, "attribute");

        return this.setValue(attribute.getName(), attribute.getValue());
    }

    /**
     * Set the provided attributes.
     * @param attributes The attributes to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setValues(Iterable<StorageMapAttribute> attributes)
    {
        PreCondition.assertNotNull(attributes, "attributes");

        for (final StorageMapAttribute attribute : attributes)
        {
            this.setValue(attribute);
        }

        return this;
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setBoolean(String attributeName, boolean attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageAttributeValueBoolean.get(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setInteger(String attributeName, int attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageAttributeValueInteger.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setLong(String attributeName, long attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageAttributeValueLong.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setDouble(String attributeName, double attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageAttributeValueDouble.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setString(String attributeName, String attributeValue)
    {
        PreCondition.assertNotNull(attributeName, "attributeName");
        PreCondition.assertNotNull(attributeValue, "attributeValue");

        return this.setValue(attributeName, InMemoryStorageAttributeValueString.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setMap(String attributeName, StorageAttributeMap attributeValue)
    {
        PreCondition.assertNotNull(attributeName, "attributeName");
        PreCondition.assertNotNull(attributeValue, "attributeValue");

        return this.setValue(attributeName, InMemoryStorageAttributeValueMap.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeMap setArray(String attributeName, StorageAttributeArray attributeValue)
    {
        PreCondition.assertNotNull(attributeName, "attributeName");
        PreCondition.assertNotNull(attributeValue, "attributeValue");

        return this.setValue(attributeName, InMemoryStorageAttributeValueArray.create(attributeValue));
    }
}
