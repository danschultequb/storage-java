package qub;

public interface StorageAttribute
{
    /**
     * Get the {@link StorageAttributeValue} value of this {@link StorageAttribute}.
     */
    public StorageAttributeValue getValue();

    /**
     * Get the {@link Boolean} value of this {@link StorageAttribute}.
     */
    public default Result<Boolean> getBooleanValue()
    {
        return this.getValue().getBooleanValue();
    }

    /**
     * Get the {@link Integer} value of this {@link StorageAttribute}.
     */
    public default Result<Integer> getIntegerValue()
    {
        return this.getValue().getIntegerValue();
    }

    /**
     * Get the {@link Long} value of this {@link StorageAttribute}.
     */
    public default Result<Long> getLongValue()
    {
        return this.getValue().getLongValue();
    }

    /**
     * Get the {@link Double} value of this {@link StorageAttribute}.
     */
    public default Result<Double> getDoubleValue()
    {
        return this.getValue().getDoubleValue();
    }

    /**
     * Get the {@link String} value of this {@link StorageAttribute}.
     */
    public default Result<String> getStringValue()
    {
        return this.getValue().getStringValue();
    }

    /**
     * Get the {@link StorageAttributeMap} value of this {@link StorageAttribute}.
     */
    public default Result<StorageAttributeMap> getMapValue()
    {
        return this.getValue().getMapValue();
    }

    /**
     * Get the {@link StorageAttributeArray} value of this {@link StorageAttribute}.
     */
    public default Result<StorageAttributeArray> getArrayValue()
    {
        return this.getValue().getArrayValue();
    }
}
