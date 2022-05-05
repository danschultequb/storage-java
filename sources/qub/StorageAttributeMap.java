package qub;

/**
 * A collection of attribute values.
 */
public interface StorageAttributeMap extends Iterable<StorageMapAttribute>
{
    public static InMemoryStorageAttributeMap create()
    {
        return InMemoryStorageAttributeMap.create();
    }
    
    /**
     * Get an {@link Iterator} that returns the names of the attributes in this
     * {@link StorageAttributeMap}.
     */
    public Iterator<String> iterateAttributeNames();

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public Result<? extends StorageAttributeValue> getValue(String attributeName);

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<Boolean> getBoolean(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getBooleanValue().await();
        });
    }

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<Integer> getInteger(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getIntegerValue().await();
        });
    }

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<Long> getLong(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getLongValue().await();
        });
    }

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<Double> getDouble(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getDoubleValue().await();
        });
    }

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<String> getString(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getStringValue().await();
        });
    }

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<StorageAttributeMap> getMap(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getMapValue().await();
        });
    }

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<StorageAttributeArray> getArray(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getArrayValue().await();
        });
    }
}
