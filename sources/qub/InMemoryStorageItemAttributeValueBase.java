package qub;

public abstract class InMemoryStorageItemAttributeValueBase implements StorageItemAttributeValue
{
    /**
     * Get the type of the value stored in this {@link StorageItemAttributeValue}.
     */
    protected abstract Class<?> getValueType();

    protected <T> Result<T> wrongType(Class<T> expectedValueType)
    {
        PreCondition.assertNotNull(expectedValueType, "expectedValueType");

        final Class<?> actualValueType = this.getValueType();
        return Result.error(new WrongTypeException("Expected " + Types.getTypeName(expectedValueType) + ", but found " + Types.getTypeName(actualValueType) + " instead."));
    }

    @Override
    public Result<StorageItemAttributeArray> getArrayValue()
    {
        return this.wrongType(StorageItemAttributeArray.class);
    }

    @Override
    public Result<Boolean> getBooleanValue()
    {
        return this.wrongType(Boolean.class);
    }

    @Override
    public Result<Double> getDoubleValue()
    {
        return this.wrongType(Double.class);
    }

    @Override
    public Result<Integer> getIntegerValue()
    {
        return this.wrongType(Integer.class);
    }

    @Override
    public Result<Long> getLongValue()
    {
        return this.wrongType(Long.class);
    }

    @Override
    public Result<StorageItemAttributeMap> getMapValue()
    {
        return this.wrongType(StorageItemAttributeMap.class);
    }

    @Override
    public Result<String> getStringValue()
    {
        return this.wrongType(String.class);
    }
}
