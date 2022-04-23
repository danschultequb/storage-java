package qub;

/**
 * A collection of attribute values.
 */
public interface StorageItemAttributeMap extends Iterable<StorageItemMapAttribute>
{
    /**
     * Get an {@link Iterator} that returns the names of the attributes in this
     * {@link StorageItemAttributeMap}.
     */
    public Iterator<String> iterateAttributeNames();

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public Result<StorageItemAttributeValue> getValue(String attributeName);

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<Boolean> getBoolean(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageItemAttributeValue attributeValue = this.getValue(attributeName).await();
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
            final StorageItemAttributeValue attributeValue = this.getValue(attributeName).await();
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
            final StorageItemAttributeValue attributeValue = this.getValue(attributeName).await();
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
            final StorageItemAttributeValue attributeValue = this.getValue(attributeName).await();
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
            final StorageItemAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getStringValue().await();
        });
    }

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<StorageItemAttributeMap> getMap(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageItemAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getMapValue().await();
        });
    }

    /**
     * Get the value associated with the provided attributeName.
     * @param attributeName The name of the value to get.
     * @return The value associated with the provided attributeName.
     */
    public default Result<StorageItemAttributeArray> getArray(String attributeName)
    {
        return Result.create(() ->
        {
            final StorageItemAttributeValue attributeValue = this.getValue(attributeName).await();
            return attributeValue.getArrayValue().await();
        });
    }
}
