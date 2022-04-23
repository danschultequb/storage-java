package qub;

public interface StorageItemAttribute
{
    /**
     * Get the {@link StorageItemAttributeValue} value of this {@link StorageItemAttribute}.
     */
    public StorageItemAttributeValue getValue();

    /**
     * Get the {@link Boolean} value of this {@link StorageItemAttribute}.
     */
    public default Result<Boolean> getBooleanValue()
    {
        return this.getValue().getBooleanValue();
    }

    /**
     * Get the {@link Integer} value of this {@link StorageItemAttribute}.
     */
    public default Result<Integer> getIntegerValue()
    {
        return this.getValue().getIntegerValue();
    }

    /**
     * Get the {@link Long} value of this {@link StorageItemAttribute}.
     */
    public default Result<Long> getLongValue()
    {
        return this.getValue().getLongValue();
    }

    /**
     * Get the {@link Double} value of this {@link StorageItemAttribute}.
     */
    public default Result<Double> getDoubleValue()
    {
        return this.getValue().getDoubleValue();
    }

    /**
     * Get the {@link String} value of this {@link StorageItemAttribute}.
     */
    public default Result<String> getStringValue()
    {
        return this.getValue().getStringValue();
    }

    /**
     * Get the {@link StorageItemAttributeMap} value of this {@link StorageItemAttribute}.
     */
    public default Result<StorageItemAttributeMap> getMapValue()
    {
        return this.getValue().getMapValue();
    }

    /**
     * Get the {@link StorageItemAttributeArray} value of this {@link StorageItemAttribute}.
     */
    public default Result<StorageItemAttributeArray> getArrayValue()
    {
        return this.getValue().getArrayValue();
    }
}
