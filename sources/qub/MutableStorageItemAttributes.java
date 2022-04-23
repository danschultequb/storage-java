package qub;

/**
 * A {@link StorageItemAttributes} object that can be modified.
 */
public interface MutableStorageItemAttributes extends StorageItemAttributes, MutableStorageItemAttributeMap
{
    /**
     * Set the {@link DateTime} when these {@link StorageItemAttributes} were set on the
     * {@link StorageItem}.
     * @param timestamp The {@link DateTime} when these {@link StorageItemAttributes} were set on
     * the {@link StorageItem}.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributes setTimestamp(DateTime timestamp);

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public MutableStorageItemAttributes setValue(String attributeName, StorageItemAttributeValue attributeValue);

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributes setBoolean(String attributeName, boolean attributeValue)
    {
        return (MutableStorageItemAttributes)MutableStorageItemAttributeMap.super.setBoolean(attributeName, attributeValue);
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributes setInteger(String attributeName, int attributeValue)
    {
        return (MutableStorageItemAttributes)MutableStorageItemAttributeMap.super.setInteger(attributeName, attributeValue);
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributes setLong(String attributeName, long attributeValue)
    {
        return (MutableStorageItemAttributes)MutableStorageItemAttributeMap.super.setLong(attributeName, attributeValue);
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributes setDouble(String attributeName, double attributeValue)
    {
        return (MutableStorageItemAttributes)MutableStorageItemAttributeMap.super.setDouble(attributeName, attributeValue);
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributes setString(String attributeName, String attributeValue)
    {
        return (MutableStorageItemAttributes)MutableStorageItemAttributeMap.super.setString(attributeName, attributeValue);
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributes setMap(String attributeName, StorageItemAttributeMap attributeValue)
    {
        return (MutableStorageItemAttributes)MutableStorageItemAttributeMap.super.setMap(attributeName, attributeValue);
    }

    /**
     * Set the value associated with the provided attributeName.
     * @param attributeName The name of the value to set.
     * @param attributeValue The value to set.
     * @return This object for method chaining.
     */
    public default MutableStorageItemAttributes setArray(String attributeName, StorageItemAttributeArray attributeValue)
    {
        return (MutableStorageItemAttributes)MutableStorageItemAttributeMap.super.setArray(attributeName, attributeValue);
    }
}
