package qub;

/**
 * A {@link StorageItemAttributeMap} that can modify its entries.
 */
public interface MutableStorageItemAttributeMap extends StorageItemAttributeMap
{
    /**
     * Remove all of the {@link StorageItemMapAttribute}s in this {@link MutableStorageItemAttributeMap}.
     */
    public void clear();

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributeMap setValue(String attributeName, StorageItemAttributeValue attributeValue);

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributeMap setBoolean(String attributeName, boolean attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageItemAttributeValueBoolean.get(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributeMap setInteger(String attributeName, int attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageItemAttributeValueInteger.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributeMap setLong(String attributeName, long attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageItemAttributeValueLong.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributeMap setDouble(String attributeName, double attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageItemAttributeValueDouble.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributeMap setString(String attributeName, String attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageItemAttributeValueString.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributeMap setMap(String attributeName, StorageItemAttributeMap attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageItemAttributeValueMap.create(attributeValue));
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributeMap setArray(String attributeName, StorageItemAttributeArray attributeValue)
    {
        return this.setValue(attributeName, InMemoryStorageItemAttributeValueArray.create(attributeValue));
    }
}
