package qub;

/**
 * A {@link StorageItemAttributeArray} that can modify its elements.
 */
public interface MutableStorageItemAttributeArray extends StorageItemAttributeArray
{
    /**
     * Set the value at the provided attributeIndex.
     * @param attributeIndex The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributeArray setBoolean(int attributeIndex, boolean attributeValue);

    /**
     * Set the value at the provided attributeIndex.
     * @param attributeIndex The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributeArray setInteger(int attributeIndex, int attributeValue);

    /**
     * Set the value at the provided attributeIndex.
     * @param attributeIndex The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributeArray setLong(int attributeIndex, long attributeValue);

    /**
     * Set the value at the provided attributeIndex.
     * @param attributeIndex The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributeArray setDouble(int attributeIndex, double attributeValue);

    /**
     * Set the value at the provided attributeIndex.
     * @param attributeIndex The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributeArray setString(int attributeIndex, String attributeValue);

    /**
     * Set the value at the provided attributeIndex.
     * @param attributeIndex The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributeArray setMap(int attributeIndex, StorageItemAttributeMap attributeValue);

    /**
     * Set the value at the provided attributeIndex.
     * @param attributeIndex The index of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributeArray setArray(int attributeIndex, StorageItemAttributeArray attributeValue);
}
