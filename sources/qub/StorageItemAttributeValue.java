package qub;

/**
 * A generic placeholder type for the value of a {@link StorageItemAttribute}.
 */
public interface StorageItemAttributeValue
{
    /**
     * Get the value of this {@link StorageItemAttributeValue} as a {@link Boolean}.
     */
    public Result<Boolean> getBooleanValue();

    /**
     * Get the value of this {@link StorageItemAttributeValue} as a {@link Integer}.
     */
    public Result<Integer> getIntegerValue();

    /**
     * Get the value of this {@link StorageItemAttributeValue} as a {@link Long}.
     */
    public Result<Long> getLongValue();

    /**
     * Get the value of this {@link StorageItemAttributeValue} as a {@link Double}.
     */
    public Result<Double> getDoubleValue();

    /**
     * Get the value of this {@link StorageItemAttributeValue} as a {@link String}.
     */
    public Result<String> getStringValue();

    /**
     * Get the value of this {@link StorageItemAttributeValue} as a {@link StorageItemAttributeMap}.
     */
    public Result<StorageItemAttributeMap> getMapValue();

    /**
     * Get the value of this {@link StorageItemAttributeValue} as a {@link StorageItemAttributeArray}.
     */
    public Result<StorageItemAttributeArray> getArrayValue();
}
