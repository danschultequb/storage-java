package qub;

public interface StorageAttributeArray extends Indexable<StorageAttributeValue>
{
    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value at the provided index.
     */
    public default Result<Boolean> getBoolean(int index)
    {
        return this.get(index).getBooleanValue();
    }

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value at the provided index.
     */
    public default Result<Integer> getInteger(int index)
    {
        return this.get(index).getIntegerValue();
    }

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value at the provided index.
     */
    public default Result<Long> getLong(int index)
    {
        return this.get(index).getLongValue();
    }

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value at the provided index.
     */
    public default Result<Double> getDouble(int index)
    {
        return this.get(index).getDoubleValue();
    }

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value at the provided index.
     */
    public default Result<String> getString(int index)
    {
        return this.get(index).getStringValue();
    }

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value at the provided index.
     */
    public default Result<StorageAttributeMap> getMap(int index)
    {
        return this.get(index).getMapValue();
    }

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value at the provided index.
     */
    public default Result<StorageAttributeArray> getArray(int index)
    {
        return this.get(index).getArrayValue();
    }
}
