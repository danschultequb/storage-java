package qub;

public interface StorageItemAttributeArray extends Indexable<StorageItemAttribute>
{
    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value associated with the provided attributeName.
     */
    public Result<Boolean> getBoolean(int index);

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value associated with the provided attributeName.
     */
    public Result<Integer> getInteger(int index);

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value associated with the provided attributeName.
     */
    public Result<Long> getLong(int index);

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value associated with the provided attributeName.
     */
    public Result<Double> getDouble(int index);

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value associated with the provided attributeName.
     */
    public Result<String> getString(int index);

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value associated with the provided attributeName.
     */
    public Result<StorageItemAttributeMap> getMap(int index);

    /**
     * Get the value at the provided index.
     * @param index The index of the element in the array to return.
     * @return The value associated with the provided attributeName.
     */
    public Result<StorageItemAttributeArray> getArray(int index);
}
