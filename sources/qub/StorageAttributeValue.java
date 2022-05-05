package qub;

/**
 * A generic placeholder type for the value of a {@link StorageAttribute}.
 */
public interface StorageAttributeValue
{
    /**
     * Get the value of this {@link StorageAttributeValue} as a {@link Boolean}.
     */
    public Result<Boolean> getBooleanValue();

    /**
     * Get the value of this {@link StorageAttributeValue} as a {@link Integer}.
     */
    public Result<Integer> getIntegerValue();

    /**
     * Get the value of this {@link StorageAttributeValue} as a {@link Long}.
     */
    public Result<Long> getLongValue();

    /**
     * Get the value of this {@link StorageAttributeValue} as a {@link Double}.
     */
    public Result<Double> getDoubleValue();

    /**
     * Get the value of this {@link StorageAttributeValue} as a {@link String}.
     */
    public Result<String> getStringValue();

    /**
     * Get the value of this {@link StorageAttributeValue} as a {@link StorageAttributeMap}.
     */
    public Result<StorageAttributeMap> getMapValue();

    /**
     * Get the value of this {@link StorageAttributeValue} as a {@link StorageAttributeArray}.
     */
    public Result<StorageAttributeArray> getArrayValue();
}
