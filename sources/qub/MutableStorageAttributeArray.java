package qub;

/**
 * A {@link StorageAttributeArray} that can modify its elements.
 */
public interface MutableStorageAttributeArray extends StorageAttributeArray, MutableIndexable<StorageAttributeValue>
{
    public static InMemoryStorageAttributeArray create()
    {
        return InMemoryStorageAttributeArray.create();
    }

    /**
     * Set the value at the provided index.
     * @param index The index of the value to set.
     * @param value The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageAttributeArray set(int index, StorageAttributeValue value);

    /**
     * Insert the value at the provided index.
     * @param index The index to insert the value at.
     * @param value The value to insert.
     * @return This object for method chaining.
     */
    public MutableStorageAttributeArray insert(int index, StorageAttributeValue value);

    /**
     * Add the provided value to the end of this {@link MutableStorageAttributeArray}.
     * @param value The value to add.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray add(StorageAttributeValue value)
    {
        return this.insert(this.getCount(), value);
    }

    /**
     * Set the value at the provided index.
     * @param index The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray setBoolean(int index, boolean attributeValue)
    {
        return this.set(index, InMemoryStorageAttributeValueBoolean.get(attributeValue));
    }

    /**
     * Insert the value at the provided index.
     * @param index The index to insert the value at.
     * @param attributeValue The value to insert.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray insertBoolean(int index, boolean attributeValue)
    {
        return this.insert(index, InMemoryStorageAttributeValueBoolean.get(attributeValue));
    }

    /**
     * Add the provided value to the end of this {@link MutableStorageAttributeArray}.
     * @param attributeValue The value to add.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray addBoolean(boolean attributeValue)
    {
        return this.add(InMemoryStorageAttributeValueBoolean.get(attributeValue));
    }

    /**
     * Set the value at the provided index.
     * @param index The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray setInteger(int index, int attributeValue)
    {
        return this.set(index, InMemoryStorageAttributeValueInteger.create(attributeValue));
    }

    /**
     * Set the value at the provided index.
     * @param index The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray setLong(int index, long attributeValue)
    {
        return this.set(index, InMemoryStorageAttributeValueLong.create(attributeValue));
    }

    /**
     * Set the value at the provided index.
     * @param index The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray setDouble(int index, double attributeValue)
    {
        return this.set(index, InMemoryStorageAttributeValueDouble.create(attributeValue));
    }

    /**
     * Set the value at the provided index.
     * @param index The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray setString(int index, String attributeValue)
    {
        return this.set(index, InMemoryStorageAttributeValueString.create(attributeValue));
    }

    /**
     * Set the value at the provided index.
     * @param index The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray setMap(int index, StorageAttributeMap attributeValue)
    {
        return this.set(index, InMemoryStorageAttributeValueMap.create(attributeValue));
    }

    /**
     * Set the value at the provided index.
     * @param index The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageAttributeArray setArray(int index, StorageAttributeArray attributeValue)
    {
        return this.set(index, InMemoryStorageAttributeValueArray.create(attributeValue));
    }
}
