package qub;

/**
 * A {@link StorageAttributes} object that can be modified.
 */
public interface MutableStorageAttributes extends StorageAttributes, MutableStorageAttributeMap
{
    /**
     * Set the {@link DateTime} when these {@link StorageAttributes} were set on the
     * {@link StorageItem}.
     * @param timestamp The {@link DateTime} when these {@link StorageAttributes} were set on
     * the {@link StorageItem}.
     * @return This object for method chaining.
     */
    public MutableStorageAttributes setTimestamp(DateTime timestamp);

    @Override
    public MutableStorageAttributes setValue(String attributeName, StorageAttributeValue attributeValue);

    @Override
    public default MutableStorageAttributes setValue(StorageMapAttribute attribute)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setValue(attribute);
    }

    @Override
    public default MutableStorageAttributes setValues(Iterable<StorageMapAttribute> attributes)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setValues(attributes);
    }

    @Override
    public default MutableStorageAttributes setBoolean(String attributeName, boolean attributeValue)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setBoolean(attributeName, attributeValue);
    }

    @Override
    public default MutableStorageAttributes setInteger(String attributeName, int attributeValue)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setInteger(attributeName, attributeValue);
    }

    @Override
    public default MutableStorageAttributes setLong(String attributeName, long attributeValue)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setLong(attributeName, attributeValue);
    }

    @Override
    public default MutableStorageAttributes setDouble(String attributeName, double attributeValue)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setDouble(attributeName, attributeValue);
    }

    @Override
    public default MutableStorageAttributes setString(String attributeName, String attributeValue)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setString(attributeName, attributeValue);
    }

    @Override
    public default MutableStorageAttributes setMap(String attributeName, StorageAttributeMap attributeValue)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setMap(attributeName, attributeValue);
    }

    @Override
    public default MutableStorageAttributes setArray(String attributeName, StorageAttributeArray attributeValue)
    {
        return (MutableStorageAttributes)MutableStorageAttributeMap.super.setArray(attributeName, attributeValue);
    }
}
